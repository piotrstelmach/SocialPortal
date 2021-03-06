package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stelmach.piotr.socialportal.Api.SocialPortalProfile;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.CreateProfile;
import com.stelmach.piotr.socialportal.Models.CurrentUser;
import com.stelmach.piotr.socialportal.Models.PostUserProfile;
import com.stelmach.piotr.socialportal.Models.UserEducation;
import com.stelmach.piotr.socialportal.Models.UserExperience;
import com.stelmach.piotr.socialportal.Models.UserProfile;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileFragment extends Fragment implements AddEducationDialog.AddEducationDialogListener,
        EducationEditListAdapter.EditEducationAdapterCallback, AddExperienceDialog.AddExperienceDialogListener ,
        ExperienceEditListAdapter.EditExperienceAdapterCallback {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SocialPortalProfile socialPortalProfile = retrofit.create(SocialPortalProfile.class);
    SocialPortalUser socialPortalUser=retrofit.create(SocialPortalUser.class);
    Context fragmentContext;

    String userID;

    ListView mEduEditListView;
    ListView mExpEditListView;

    TextView emptyTextView;
    //TextView mExpEmptyTextView;

    ImageView mAvatarImageView;
    EditText mProfileNameTv;
    EditText mProfileHandleTv;
    EditText mProfileCompanyTv;
    EditText mProfileLocationTv;
    EditText mProfileWebsiteTv;
    EditText mProfileSkillsTv;

    ImageView mAddEducationImageView;
    ImageView mAddExperienceImageView;

    String userToken;

    EducationEditListAdapter educationListAdapter;
    ExperienceEditListAdapter experienceListAdapter;

    Button mButtonSave;
    FragmentManager fm;

    CurrentUser currentUser;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        userToken = sharedPref.getString("Token", "Alternative");
        Log.d("LOAD_TOKEN", userToken);




        Call<UserProfile> userProfileCall = socialPortalProfile.getCurrentUserProfile(userToken);

        userProfileCall.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.d("RETROFIT", "Response profile: " + response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("RETROFIT", "avatar: " + response.body().getUser().getAvatar());
                    setBasicProfileData(response.body(),false);
                } else {
                    //Toast.makeText(fragmentContext, "Unathorized", Toast.LENGTH_LONG)
                            //.show();
                    Log.d("RETROFIT", "reponse body: " + response.body());
                    setBasicProfileData(response.body(),true);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(fragmentContext, "Failed to connect. Check connection and try again", Toast.LENGTH_LONG)
                        .show();
            }
        });



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save_profile_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.saveProfileMenuButton) {
            GetCurrentUser();

            CreateProfile newUserProfile=new CreateProfile();

            newUserProfile.setCompany(mProfileCompanyTv.getText().toString());
            newUserProfile.setHandle(mProfileHandleTv.getText().toString());
            newUserProfile.setLocation(mProfileLocationTv.getText().toString());
            newUserProfile.setWebsite(mProfileWebsiteTv.getText().toString());
            //List<String> userSkills= Arrays.asList(mProfileSkillsTv.getText().toString().split(","));
            newUserProfile.setSkills(mProfileSkillsTv.getText().toString());
            newUserProfile.setStatus("developer");
            Log.d("RETROFIT:","New profile body "+newUserProfile.toString());
            AddNewProfile(newUserProfile);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAddEducationImageView=getView().findViewById(R.id.educationAddTV);
        mAddExperienceImageView=getView().findViewById(R.id.experienceAddTV);

        mAddEducationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEducation();
            }
        });

        mAddExperienceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewExperience();
            }
        });

    }

    private void setBasicProfileData(final UserProfile userProfile, boolean emptyUser) {

        if(!emptyUser) {
            GetCurrentUser();
            userID=userProfile.getUser().getId();
            mEduEditListView = getView().findViewById(R.id.edu1EditListView);
            mExpEditListView = getView().findViewById(R.id.exp1EditListView);

            emptyTextView = getView().findViewById(android.R.id.empty);
            //mExpEmptyTextView = getView().findViewById(R.id.expEmptyTextView);

            mExpEditListView.setEmptyView(emptyTextView);
            mEduEditListView.setEmptyView(emptyTextView);

            mAvatarImageView = getView().findViewById(R.id.profileImageView);
            mProfileNameTv = getView().findViewById(R.id.profileEditNameTextView);
            mProfileHandleTv = getView().findViewById(R.id.handleProfileEditTextView);
            mProfileCompanyTv = getView().findViewById(R.id.companyEditTextView);
            mProfileLocationTv = getView().findViewById(R.id.locationEditTextView);
            mProfileWebsiteTv = getView().findViewById(R.id.websiteEditTextView);
            mProfileSkillsTv = getView().findViewById(R.id.skillsPlainText);

            String prefix;
            String avatarNormal=userProfile.getUser().getAvatar();

            if(avatarNormal.charAt(0)=='/'){
                prefix="http:";
            }else{
                prefix="http://";
                avatarNormal.substring(2);
            }

            Picasso.get().load(prefix + avatarNormal).into(mAvatarImageView);

            mProfileNameTv.setText(userProfile.getUser().getName());
            mProfileHandleTv.setText(userProfile.getHandle());
            mProfileCompanyTv.setText(userProfile.getCompany());
            mProfileLocationTv.setText(userProfile.getLocation());
            mProfileWebsiteTv.setText(userProfile.getWebsite());

            String skll = "\n";
            if (userProfile.getSkills() != null) {
                List<String> skills = userProfile.getSkills();
                for (String skill : skills) {
                    skll += skill;
                    skll += ",";
                }
                mProfileSkillsTv.setText(skll);
            }

            educationListAdapter = new EducationEditListAdapter(getContext(),
                    R.layout.adapter_edu_edit_list_layout, userProfile.getEducation());
            experienceListAdapter = new ExperienceEditListAdapter(getContext(),
                    R.layout.adapter_exp_edit_list_layout, userProfile.getExperience());


            educationListAdapter.setCallback(this);
            experienceListAdapter.setCallback(this);
            mEduEditListView.setAdapter(educationListAdapter);
            mExpEditListView.setAdapter(experienceListAdapter);

            setListViewHeightBasedOnChildren(mEduEditListView);
            setListViewHeightBasedOnChildren(mExpEditListView);

        }else{
            mEduEditListView = getView().findViewById(R.id.edu1EditListView);
            mExpEditListView = getView().findViewById(R.id.exp1EditListView);

            emptyTextView = getView().findViewById(android.R.id.empty);
            //mExpEmptyTextView = getView().findViewById(R.id.expEmptyTextView);

            mExpEditListView.setEmptyView(emptyTextView);
            mEduEditListView.setEmptyView(emptyTextView);

            mAvatarImageView = getView().findViewById(R.id.profileImageView);
            mProfileNameTv = getView().findViewById(R.id.profileEditNameTextView);
            mProfileHandleTv = getView().findViewById(R.id.handleProfileEditTextView);
            mProfileCompanyTv = getView().findViewById(R.id.companyEditTextView);
            mProfileLocationTv = getView().findViewById(R.id.locationEditTextView);
            mProfileWebsiteTv = getView().findViewById(R.id.websiteEditTextView);
            mProfileSkillsTv = getView().findViewById(R.id.skillsPlainText);
            mButtonSave=getView().findViewById(R.id.profileFirstSave);

            //String avatarNormal = currentUser.getAvatar().substring(2);
            //Picasso.get().load("http:" + avatarNormal).into(mAvatarImageView);

            mProfileNameTv.setText("");
            mProfileHandleTv.setText("");
            mProfileCompanyTv.setText("");
            mProfileLocationTv.setText("");
            mProfileWebsiteTv.setText("");

            String skll = "";
            mProfileSkillsTv.setText(skll);


            List<UserEducation> userEducationList=new ArrayList<>();
            List<UserExperience> userExperienceList=new ArrayList<>();

            educationListAdapter = new EducationEditListAdapter(getContext(),
                    R.layout.adapter_edu_edit_list_layout, userEducationList);
            experienceListAdapter = new ExperienceEditListAdapter(getContext(),
                    R.layout.adapter_exp_edit_list_layout, userExperienceList);


            educationListAdapter.setCallback(this);
            experienceListAdapter.setCallback(this);
            mEduEditListView.setAdapter(educationListAdapter);
            mExpEditListView.setAdapter(experienceListAdapter);

            setListViewHeightBasedOnChildren(mEduEditListView);
            setListViewHeightBasedOnChildren(mExpEditListView);

            mButtonSave.setVisibility(View.VISIBLE);
            mButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GetCurrentUser();

                    CreateProfile newUserProfile=new CreateProfile();

                    newUserProfile.setCompany(mProfileCompanyTv.getText().toString());
                    newUserProfile.setHandle(mProfileHandleTv.getText().toString());
                    newUserProfile.setLocation(mProfileLocationTv.getText().toString());
                    newUserProfile.setWebsite(mProfileWebsiteTv.getText().toString());
                    //List<String> userSkills= Arrays.asList(mProfileSkillsTv.getText().toString().split(","));
                    newUserProfile.setSkills(mProfileSkillsTv.getText().toString());
                    newUserProfile.setStatus("developer");
                    Log.d("RETROFIT:","New profile body "+newUserProfile.toString());
                     AddNewProfile(newUserProfile);

                }
            });
            mButtonSave.setVisibility(View.INVISIBLE);

        }

    }

    private void GetCurrentUser(){
        Call<CurrentUser> getCurrentUserData=socialPortalUser.GetCurrentUser(userToken);

        getCurrentUserData.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                Log.d("RETROFIT", "Current user from edit: " + response.body());
                currentUser=response.body();
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable throwable) {
                Log.d("RETROFIT", "Current user from edit: " + throwable.getMessage());
            }
        });
    }

    private void addNewEducation(){
        AddEducationDialog addEducationDialog=new AddEducationDialog();
        addEducationDialog.setTargetFragment(EditProfileFragment.this,1);
        addEducationDialog.show(getFragmentManager(),"Add education");

    }

    private void addNewExperience(){
        AddExperienceDialog addExperienceDialog=new AddExperienceDialog();
        addExperienceDialog.setTargetFragment(EditProfileFragment.this,1);
        addExperienceDialog.show(getFragmentManager(),"Add experience");
    }

    @Override
    public void applyEduData(String school, String deegree, String fieldOfStudy, String dateFrom, String dateTo, Boolean current, String description) {
        UserEducation userEducation=new UserEducation(school,deegree,fieldOfStudy,dateFrom,dateTo,
                current,description);

        Log.d("DATA_FROM_DIALOG", "applyEduData: "+userEducation.toString());

        addEdu(userEducation);


    }

    private void addEdu(UserEducation userEducation){
        Call<PostUserProfile> callUserProfile=socialPortalProfile.addEducationToUserProfile(userToken,userEducation);

        callUserProfile.enqueue(new Callback<PostUserProfile>() {
            @Override
            public void onResponse(Call<PostUserProfile> call, Response<PostUserProfile> response) {
                if(response.isSuccessful()){
                    Log.d("DATA_FROM_DIALOG", "getting edu: "+response.body().getEducation().toString());
                    educationListAdapter.clear();
                    educationListAdapter.addAll(response.body().getEducation());
                    setListViewHeightBasedOnChildren(mEduEditListView);
                }
                Log.d("DATA_FROM_DIALOG", "getting code: "+response.code());
            }

            @Override
            public void onFailure(Call<PostUserProfile> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to add. Try again later or" +
                        "check internet connection.",Toast.LENGTH_LONG).show();

                Log.d("DATA_FROM_DIALOG",t.getLocalizedMessage());
                Log.d("DATA_FROM_DIALOG",t.getMessage());

            }
        });
    }

    //for formating listviews heights
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void deleteEduFromProfile(String id){
        Call<PostUserProfile> userProfileCall=socialPortalProfile.deleteEducationFromUserProfile(userToken,
                id);

        userProfileCall.enqueue(new Callback<PostUserProfile>() {
            @Override
            public void onResponse(Call<PostUserProfile> call, Response<PostUserProfile> response) {
                Log.d("RETROFIT_EDU_ADAPTER", "onResponse: "+response.body().getEducation().toString());
                if(response.isSuccessful()) {
                    educationListAdapter.clear();
                    educationListAdapter.addAll(response.body().getEducation());
                    setListViewHeightBasedOnChildren(mEduEditListView);
                }else{
                    Log.d("DATA_FROM_DIALOG", "getting code: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<PostUserProfile> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to add. Try again later or" +
                        "check internet connection.",Toast.LENGTH_LONG).show();

                Log.d("DATA_FROM_DIALOG",t.getLocalizedMessage());
                Log.d("DATA_FROM_DIALOG",t.getMessage());
            }
        });
    }


    @Override
    public void applyExpData(String title, String company, String location, String dateFrom, String dateTo, Boolean current, String description) {
        UserExperience userExperience=new UserExperience(current,title,company,location,dateFrom,dateTo,
                description);

        Call<PostUserProfile> postUserProfileCall=socialPortalProfile.addExperienceToUserProfile(userToken,
                userExperience);

        postUserProfileCall.enqueue(new Callback<PostUserProfile>() {
            @Override
            public void onResponse(Call<PostUserProfile> call, Response<PostUserProfile> response) {
                if(response.isSuccessful()){
                    Log.d("DATA_FROM_DIALOG", "getting edu: "+response.body().getEducation().toString());
                    experienceListAdapter.clear();
                    experienceListAdapter.addAll(response.body().getExperience());
                    setListViewHeightBasedOnChildren(mExpEditListView);
                }
                Log.d("DATA_FROM_DIALOG", "getting code: "+response.code());
            }

            @Override
            public void onFailure(Call<PostUserProfile> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to add. Try again later or" +
                        "check internet connection.",Toast.LENGTH_LONG).show();

                Log.d("DATA_FROM_DIALOG",t.getLocalizedMessage());
                Log.d("DATA_FROM_DIALOG",t.getMessage());
            }
        });
    }

    @Override
    public void deleteExpFromProfile(String userId) {
        Call<PostUserProfile> userProfileCall=socialPortalProfile.deleteExperienceFromUserProfile(userToken,
                userId);

        userProfileCall.enqueue(new Callback<PostUserProfile>() {
            @Override
            public void onResponse(Call<PostUserProfile> call, Response<PostUserProfile> response) {
                Log.d("RETROFIT_EDU_ADAPTER", "onResponse: "+response.body().getEducation().toString());
                if(response.isSuccessful()) {
                    experienceListAdapter.clear();
                    experienceListAdapter.addAll(response.body().getExperience());
                    setListViewHeightBasedOnChildren(mExpEditListView);
                }else{
                    Log.d("DATA_FROM_DIALOG", "getting code: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<PostUserProfile> call, Throwable t) {
                Toast.makeText(getContext(),"Failed to add. Try again later or" +
                        "check internet connection.",Toast.LENGTH_LONG).show();

                Log.d("DATA_FROM_DIALOG",t.getLocalizedMessage());
                Log.d("DATA_FROM_DIALOG",t.getMessage());
            }
        });
    }

    private void AddNewProfile(CreateProfile createProfile){
        Call<UserProfile> userProfileCall = socialPortalProfile.createOrEditUserProfile(userToken,createProfile);

        userProfileCall.enqueue(new Callback<UserProfile>() {

            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                try {
                    Log.d("RETROFIT","Message: "+response.message()+" Code: "+response.code()+" Error body "+response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setBasicProfileData(response.body(),false);
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable throwable) {
                Toast.makeText(getContext(),"Failed to save. Please try again",Toast.LENGTH_LONG);
            }
        });

    }
}

