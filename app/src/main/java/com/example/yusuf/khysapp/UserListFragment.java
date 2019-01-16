package com.example.yusuf.khysapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UserListFragment extends Fragment {

    View view;

    ////////////////////********************------OVERRIDE--------********////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_list, container, false);
        return view;
    }


    ////////////////////********************------FUNCTIONS--------********////////////////////////////////////////////////////
    public ListView getList()
    {
        return view.findViewById(R.id.userListView);
    }
}
