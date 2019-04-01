package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stelmach.piotr.socialportal.Models.UserExperience;

import java.util.List;

public class ExperienceEditListAdapter extends ArrayAdapter<UserExperience> {
    private Context mContext;
    int mResource;

    public ExperienceEditListAdapter(@NonNull Context context, int resource, @NonNull List<UserExperience> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String title=getItem(position).getTitle();
        String company=getItem(position).getCompany();
        String location=getItem(position).getLocation();
        String from=getItem(position).getFrom();
        String to=getItem(position).getTo();
        String description=getItem(position).getDescription();

        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);

        TextView titleTextView=convertView.findViewById(R.id.titleExpTextView);
        TextView companyTextView=convertView.findViewById(R.id.companyExpTextView);
        TextView locationTextView=convertView.findViewById(R.id.locationExpTextView);
        TextView fromTextView=convertView.findViewById(R.id.fromExpTextView);
        TextView toTextView=convertView.findViewById(R.id.toExpTextView);
        TextView descriptionTextView=convertView.findViewById(R.id.descriptionExpTextView);

        titleTextView.setText(title);
        companyTextView.setText(company);
        locationTextView.setText(location);
        fromTextView.setText(from);
        if(to==null) toTextView.setText("Current Job");
        else toTextView.setText(to);
        descriptionTextView.setText(description);

        return convertView;
    }
}
