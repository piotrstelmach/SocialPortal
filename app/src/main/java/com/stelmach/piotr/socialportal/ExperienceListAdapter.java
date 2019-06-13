package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stelmach.piotr.socialportal.Models.UserEducation;
import com.stelmach.piotr.socialportal.Models.UserExperience;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExperienceListAdapter extends ArrayAdapter<UserExperience> {
    private Context mContext;
    int mResource;

    public ExperienceListAdapter(@NonNull Context context, int resource, @NonNull List<UserExperience> objects) {
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

        titleTextView.setText("Title "+title);
        companyTextView.setText("Company "+company);
        locationTextView.setText("Location "+location);
        fromTextView.setText("From "+formatDate(from));
        if(to==null) toTextView.setText("Current Job");
        else toTextView.setText("To "+formatDate(to));
        descriptionTextView.setText("Description "+description);

        return convertView;
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
