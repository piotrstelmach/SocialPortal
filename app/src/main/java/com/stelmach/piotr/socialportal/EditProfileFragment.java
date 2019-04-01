package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stelmach.piotr.socialportal.Api.SocialPortalProfile;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.UserProfile;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileFragment extends Fragment {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SocialPortalProfile socialPortalProfile = retrofit.create(SocialPortalProfile.class);

    Context fragmentContext;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String userToken = sharedPref.getString("Token", "Alternative");
        Log.d("LOAD_TOKEN", userToken);

        Call<UserProfile> userProfileCall = socialPortalProfile.getCurrentUserProfile(userToken);

        userProfileCall.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.d("RETROFIT", "Response profile: " + response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("RETROFIT", "avatar: " + response.body().getUser().getAvatar());
                    setBasicProfileData(response.body());
                } else {
                    Toast.makeText(fragmentContext, "Unathorized", Toast.LENGTH_LONG)
                            .show();
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

    private void setBasicProfileData(UserProfile userProfile) {

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

        String avatarNormal = userProfile.getUser().getAvatar().substring(2);
        Picasso.get().load("http://" + avatarNormal).into(mAvatarImageView);

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

        EducationEditListAdapter educationListAdapter = new EducationEditListAdapter(getContext(),
               R.layout.adapter_edu_edit_list_layout, userProfile.getEducation());
        ExperienceEditListAdapter experienceListAdapter = new ExperienceEditListAdapter(getContext(),
                R.layout.adapter_exp_edit_list_layout, userProfile.getExperience());

        mEduEditListView.setAdapter(educationListAdapter);
        mExpEditListView.setAdapter(experienceListAdapter);

    }

    private void addNewEducation(){

    }

    private void addNewExperience(){

    }
}

