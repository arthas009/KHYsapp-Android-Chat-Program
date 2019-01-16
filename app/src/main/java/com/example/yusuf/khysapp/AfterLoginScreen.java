package com.example.yusuf.khysapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AfterLoginScreen extends AppCompatActivity {

    public static String[] userlist;
    Button log_out,show_messages;
    ListView UserList;
    public static boolean wentback=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_screen);
        FindOrDesignWidgets();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.afterloginmenu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        wentback = true;
    }




    ////////////////////********************------FUNCTIONS--------********////////////////////////////////////////////////////
    public void logout()
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        SharedPreferences p1 = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p1.edit();
        e.putString("isUpdated","no");
        e.apply();
        finish();
    }
    public void goMessageScreenWithValue(String currentusername)
    {
        Bundle data = getIntent().getExtras();
        Intent k = new Intent(this,MessageScreen.class);
        k.putExtra("otheruser",currentusername);
        k.putExtra("me",data.getString("username"));
        startActivity(k);
    }
    public void FindOrDesignWidgets()
    {
        UserListFragment userfrag = (UserListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1); // get fragment
        UserList = userfrag.getList();
        ListAdapter adapter = new UserListCustomAdapter(this,userlist);
        UserList.setAdapter(adapter);
        UserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                userlistclick(adapterView,i);

            }
        });
        log_out = (Button)findViewById(R.id.button4);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }
    public void userlistclick(AdapterView<?> adapterView,int i) {
        String currentusername = String.valueOf(adapterView.getItemAtPosition(i));
        goMessageScreenWithValue(currentusername);
    }
}
