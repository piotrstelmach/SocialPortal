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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stelmach.piotr.socialportal.Api.SocialPortalProfile;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SocialPortalProfile socialPortalProfile=retrofit.create(SocialPortalProfile.class);

    Context fragmentContext;

    ImageView mAvatarImageView;
    TextView mProfileNameTv;
    TextView mProfileHandleTv;
    TextView mProfileCompanyTv;
    TextView mProfileLocationTv;
    TextView mProfileWebsiteTv;
    TextView mProfileSkillsTv;

    ListView mEducationListView;
    ListView mExperienceListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContext=container.getContext();
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String userToken=sharedPref.getString("Token","Alternative");
        Log.d("LOAD_TOKEN",userToken);

        Call<UserProfile> userProfileCall=socialPortalProfile.getCurrentUserProfile(userToken);

        userProfileCall.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                Log.d("RETROFIT","Response profile: "+response.message());
                if(response.isSuccessful() && response.body()!=null){
                    Log.d("RETROFIT", "avatar: "+response.body().getUser().getAvatar());
                    setBasicProfileData(response.body());
                }else{
                    Toast.makeText(fragmentContext,"Unathorized",Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(fragmentContext,"Failed to connect. Check connection and try again",Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void setBasicProfileData(UserProfile userProfile){
        mAvatarImageView=getView().findViewById(R.id.profileImageView);
        mProfileNameTv=getView().findViewById(R.id.profileNameTextView);
        mProfileHandleTv=getView().findViewById(R.id.handleProfileTextView);
        mProfileCompanyTv=getView().findViewById(R.id.companyTextView);
        mProfileLocationTv=getView().findViewById(R.id.locationTextView);
        mProfileWebsiteTv=getView().findViewById(R.id.websiteTextView);
        mProfileSkillsTv=getView().findViewById(R.id.skillsTextView);

        String avatarNormal=userProfile.getUser().getAvatar().substring(2);
        Picasso.get().load("http://"+avatarNormal).into(mAvatarImageView);

        mProfileNameTv.setText(userProfile.getUser().getName());
        mProfileHandleTv.setText(userProfile.getHandle());
        mProfileCompanyTv.setText(userProfile.getCompany());
        mProfileLocationTv.setText(userProfile.getLocation());
        mProfileWebsiteTv.setText(userProfile.getWebsite());

        String skll="\n";
        if(userProfile.getSkills()!=null){
            List<String> skills=userProfile.getSkills();
            for (String skill : skills) {
                skll+= skill;
                skll+=" \n";
            }
            mProfileSkillsTv.setText("Skills: "+ skll);
        }

        mEducationListView=getView().findViewById(R.id.edu1ListView);
        mExperienceListView=getView().findViewById(R.id.exp1ListView);

        EducationListAdapter educationListAdapter=new EducationListAdapter(fragmentContext,
                R.layout.adapter_edu_list_layout,userProfile.getEducation());
        ExperienceListAdapter experienceListAdapter=new ExperienceListAdapter(fragmentContext,
                R.layout.adapter_exp_list_layout,userProfile.getExperience());

        mEducationListView.setAdapter(educationListAdapter);
        mExperienceListView.setAdapter(experienceListAdapter);


    }




}
