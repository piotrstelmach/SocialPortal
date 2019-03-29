package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.stelmach.piotr.socialportal.Api.SocialPortalProfile;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.UserProfile;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SocialPortalProfile socialPortalProfile=retrofit.create(SocialPortalProfile.class);

    Context fragmentContext;

    ListView mSearchListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContext=container.getContext();
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        menu.clear();
        inflater.inflate(R.menu.search_bar_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.searchProfile);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView= (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getSearchResult(s.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void getSearchResult(String handle){
        Call<UserProfile> userProfileCall=socialPortalProfile.getUserProfileByHandle(handle);

        userProfileCall.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if(response.isSuccessful()){
                    Log.d("RETROFIT", "onResponse: "+response.body().getUser().toString()+" "+response.body().getSkills());
                    setSearchData(response.body());
                }else{
                    Toast.makeText(getContext(),"We don't have this profile. Sorry",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(getContext(),"Connection failed try again",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setSearchData(UserProfile userProfile){
        List<UserProfile> userProfiles = new ArrayList<UserProfile>();
        Log.d("PROFILE_LIST", "setSearchData: "+userProfile.toString());
        userProfiles.add(userProfile);
        //Log.d("PROFILE_LIST", "setSearchData: "+userProfile.toString());
        mSearchListView=getView().findViewById(R.id.searchListView);

        SearchListAdapter searchListAdapter=new SearchListAdapter(getContext(),R.layout.adapter_search_list_layout,userProfiles);




        mSearchListView.setAdapter(searchListAdapter);
    }
}
