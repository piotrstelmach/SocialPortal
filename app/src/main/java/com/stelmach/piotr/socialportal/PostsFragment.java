package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stelmach.piotr.socialportal.Api.PostsInterface;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.CurrentUser;
import com.stelmach.piotr.socialportal.Models.Post;
import com.stelmach.piotr.socialportal.Models.PostToSend;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsFragment extends Fragment {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PostsInterface postsInterface=retrofit.create(PostsInterface.class);
    SocialPortalUser socialPortalUser=retrofit.create(SocialPortalUser.class);

    Context fragmentContext;

    String userToken;
    CurrentUser currentUser;

    ImageView mAvatarImageView;
    TextView mNameTextView;
    ListView listView;
    Button mSendPostButton;
    EditText mPostContentEditText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContext=container.getContext();
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.fragment_posts,container,false);
        mSendPostButton=relativeLayout.findViewById(R.id.sendCommentButton);
        mPostContentEditText=relativeLayout.findViewById(R.id.newPostEditText);

        mSendPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPost(mPostContentEditText.getText().toString());
                mPostContentEditText.getText().clear();

            }
        });
        GetCurrentUser();
        return relativeLayout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        userToken = sharedPref.getString("Token", "Alternative");


        GetPosts();


    }

    /*private void setCurrentUserData(){
        mAvatarImageView= getView().findViewById(R.id.avatarImageView);
        mNameTextView=getView().findViewById(R.id.usernameTextView);

        if(currentUser!=null){
            String prefix;
            String avatarNormal = currentUser.getAvatar();

            if (avatarNormal.charAt(0) == '/') {
                prefix = "http:";
            } else {
                prefix = "http://";
                avatarNormal.substring(2);
            }

            Log.d("SETDATA", "setCurrentUserData: " + avatarNormal);
            Picasso.get().load(prefix + avatarNormal).into(mAvatarImageView);

            mNameTextView.setText(currentUser.getName());
        }

    }*/

    private void setListViewResources(List<Post> postList) {
        listView=getView().findViewById(R.id.postsListView);
        PostListAdapter adapter=new PostListAdapter(fragmentContext,R.layout.adapter_posts_list_layout,postList);
        listView.setAdapter(adapter);
    }

    private void sendPost(String post){

        PostToSend userPost=new PostToSend();

        userPost.setName(currentUser.getName());
        userPost.setUser(currentUser);
        userPost.setText(post);

        Call<Post> postCall=postsInterface.CreateNewPost(userToken,userPost);

        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                GetPosts();
                setListViewHeightBasedOnChildren(listView);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
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
    private void GetPosts(){
        Call<List<Post>> listCall=postsInterface.GetAllPosts();

        listCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d("RETROFIT IN FRAGMENT",response.message());
                if(response.isSuccessful()){
                    setListViewResources(response.body());

                }else{
                    Toast.makeText(fragmentContext,"Unathorized",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("RETROFIT",t.getLocalizedMessage());
            }
        });
    }
}
