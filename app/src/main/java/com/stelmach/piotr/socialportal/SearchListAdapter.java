package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stelmach.piotr.socialportal.Models.UserProfile;

import java.util.List;

public class SearchListAdapter extends ArrayAdapter<UserProfile> {

    private Context mContext;
    private int mResource;

    public SearchListAdapter(@NonNull Context context, int resource, @NonNull List<UserProfile> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String avatar=getItem(position).getUser().getAvatar();
        String username=getItem(position).getUser().getName();
        String company=getItem(position).getCompany();
        String location=getItem(position).getLocation();
        String website=getItem(position).getWebsite();


        //LayoutInflater inflater=LayoutInflater.from(mContext);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);

        ImageView mAvatarImageView=convertView.findViewById(R.id.searchProfileAvatar);
        TextView usernameTextView=convertView.findViewById(R.id.searchProfileNameTV);
        TextView companyTextView=convertView.findViewById(R.id.searchProfileCompanyTextView);
        TextView locationTextView=convertView.findViewById(R.id.searchLocationTextView);
        TextView websiteTextView=convertView.findViewById(R.id.searchWebsiteTextView);

        String avatarNormal=avatar.substring(2);
        Picasso.get().load("http://"+avatarNormal).into(mAvatarImageView);

        usernameTextView.setText(username);
        companyTextView.setText(company);
        locationTextView.setText(location);
        websiteTextView.setText(website);

        return convertView;
    }
}
