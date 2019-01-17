package com.example.yusuf.khysapp.Adapters;

import android.content.Context;
import android.sax.TextElementListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yusuf.khysapp.R;

public class UserListCustomAdapter extends ArrayAdapter<String> {
    TextView userShow ;
    public UserListCustomAdapter(Context context, String[] Users) {
        super(context, R.layout.user_list_adapter_layout, Users);


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater yusufsInflater = LayoutInflater.from(getContext());
        View myView = yusufsInflater.inflate(R.layout.user_list_adapter_layout,parent,false);

        String currentItem = getItem(position);
        TextView myTview = (TextView)myView.findViewById(R.id.userShow);
        myTview.setText(currentItem);

        return myView;
    }
}
