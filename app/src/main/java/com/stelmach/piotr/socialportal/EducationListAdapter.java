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

import java.util.List;

public class EducationListAdapter extends ArrayAdapter<UserEducation> {

    private Context mContext;
    private int mResource;

    public EducationListAdapter(@NonNull Context context, int resource, @NonNull List<UserEducation> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String school=getItem(position).getSchool();
        String deegree=getItem(position).getDegree();
        String from=getItem(position).getFrom();
        String to=getItem(position).getTo();
        int current=getItem(position).getCurrent();

        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);


        TextView schoolTextView=convertView.findViewById(R.id.schoolEduTextView);
        TextView deegreeTextView=convertView.findViewById(R.id.deegreeEduTextView);
        TextView fromTextView=convertView.findViewById(R.id.fromEduTextView);
        TextView toTextView=convertView.findViewById(R.id.toEduTextView);
        TextView currentTextView=convertView.findViewById(R.id.currentEduTextView);

        schoolTextView.setText(school);
        deegreeTextView.setText(deegree);
        fromTextView.setText(from);
        toTextView.setText(to);
        if(current==0) currentTextView.setText("no");
        else currentTextView.setText("yes");

        return convertView;
    }
}
