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
import com.stelmach.piotr.socialportal.Models.Post;

import java.util.List;

public class PostListAdapter extends ArrayAdapter<Post> {

    private Context mContext;
    int mResource;

    public PostListAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name=getItem(position).getName();
        String avatar=getItem(position).getAvatar();
        String text=getItem(position).getText();
        String date=getItem(position).getDate();

        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);

        TextView nameTv=convertView.findViewById(R.id.postNameTV);
        ImageView avatarIV=convertView.findViewById(R.id.postImageView);
        TextView textTV=convertView.findViewById(R.id.postTextView);
        TextView dateTV=convertView.findViewById(R.id.postDateTV);

        nameTv.setText(name);
        String avatarNormal=avatar.substring(2);
        Picasso.get().load("http://"+avatarNormal).resize(80,80).into(avatarIV);
        textTV.setText(text);
        dateTV.setText(date);


        return convertView;

    }
}
