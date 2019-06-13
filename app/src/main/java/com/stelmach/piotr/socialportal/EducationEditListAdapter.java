package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stelmach.piotr.socialportal.Api.SocialPortalProfile;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.UserEducation;
import com.stelmach.piotr.socialportal.Models.UserProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class EducationEditListAdapter extends ArrayAdapter<UserEducation>{

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SocialPortalProfile socialPortalProfile = retrofit.create(SocialPortalProfile.class);

    private Context mContext;
    private int mResource;

    ImageView editImageView;
    ImageView deleteImageView;

    String userToken;

    EditProfileFragment editEduInProfile;

    private EditEducationAdapterCallback editEducationAdapterCallback;



    public EducationEditListAdapter(@NonNull Context context, int resource, @NonNull List<UserEducation> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        userToken = sharedPref.getString("Token", "Alternative");
        Log.d("LOAD_TOKEN", userToken);

    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String school=getItem(position).getSchool();
        String deegree=getItem(position).getDegree();
        String from=getItem(position).getFrom();
        String to=getItem(position).getTo();
        Boolean current=getItem(position).getCurrent();

        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);


        TextView schoolTextView=convertView.findViewById(R.id.schoolEduTextView);
        TextView deegreeTextView=convertView.findViewById(R.id.deegreeEduTextView);
        TextView fromTextView=convertView.findViewById(R.id.fromEduTextView);
        TextView toTextView=convertView.findViewById(R.id.toEduTextView);
        TextView currentTextView=convertView.findViewById(R.id.currentEduTextView);

        schoolTextView.setText("School "+school);
        deegreeTextView.setText("Deegree "+deegree);
        fromTextView.setText("From "+formatDate(from));
        toTextView.setText("To "+to);
        if(current==false) currentTextView.setText("Current: no");
        else currentTextView.setText("Current: yes");

        //editImageView=convertView.findViewById(R.id.editImageV);
        deleteImageView=convertView.findViewById(R.id.deleteImageV);




        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editEduInProfile.deleteEduFromProfile(getItem(position).getId());
                editEducationAdapterCallback.deleteEduFromProfile(getItem(position).getId());
            }
        });

        return convertView;
    }

    public void setCallback(EditEducationAdapterCallback callback){

        this.editEducationAdapterCallback = callback;
    }

    public interface EditEducationAdapterCallback{
        //public void editEduInProfile(UserEducation userEducation);
        public void  deleteEduFromProfile(String userId);
    }

    private String formatDate(String enterDate){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(enterDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy, Ka").format(date);
        return formattedDate;
    }



}
