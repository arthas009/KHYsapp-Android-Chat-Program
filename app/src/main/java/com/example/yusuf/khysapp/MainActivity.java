package com.example.yusuf.khysapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    int Targetport = 7788; // THE PORT THAT WE WILL USE
    public static String Targetaddr = "192.168.1.26"; // SERVER ADRESS
    EditText t1, t2; // EDITTEXTS OF MAIN ACTIVITY
    Button b1; // LOGIN BUTTON
    ToggleButton remember_me,auto_login;
    public static boolean WaitForSignal, is_succeed,unf,server_is_close; // unf = USER NOT FOUND, is_succeed = success login,
    // server_is_close = decide server is close or not
    public static String name , surname;

    ////////////////////********************------OVERRIDE--------********////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences p1 = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        findWidgets(); // finds and designgs widgets of main activity.
        rememberme(p1); //Remember me set function
        autologin(p1); // Auto login set function
    }

    ////////////////////********************------FUNCTIONS--------********////////////////////////////////////////////////////
    public void logIn(String username, String password,boolean is_auto_logged) throws IOException, InterruptedException {
        if(!is_auto_logged) {
            if (t1.getText().toString().contains("///") || t1.getText().toString().contains("///")) // if contains "///" then error
            {
                Toast.makeText(this, "/// yasak karakterler", Toast.LENGTH_SHORT).show();
                return;
            }
            if (t1.getText().toString().length() < 6 || t2.getText().toString().length() < 6)// if username less than 6 or password less than 6 then error
            {
                Toast.makeText(this, "Kullanıcı adı veya şifre 6 karakterden küçük olamaz", Toast.LENGTH_LONG).show();
                return;
            }
            if (t1.getText().toString().length() > 30 || t2.getText().toString().length() > 30)// if username greater than 30 or password too then error
            {
                Toast.makeText(this, "Kullanıcı adı veya şifre 30 karakterden büyük olamaz", Toast.LENGTH_LONG).show();
                return;
            }
        }
        int count = 0;
        server_is_close=false; // to decide server is close or not
        WaitForSignal=false; // THIS WILL KEEP ALL PROGRAM IN CONTROL.
        //WAIT FOR SIGNAL ALWAYS KEEPS ACTIVITIES WAITING FOR A SIGNAL FROM REQUESTS
        is_succeed=false; // TO DECIDE SUCCESSFULL LOGIN OR UNSUCCESSFULL LOGIN
        unf=false; // TO DECIDE USER IS FOUND OR NOT IN DATABASE
        LogInStartClientPrinter printer = new LogInStartClientPrinter(username,
                password, Targetaddr,
                Targetport,"LogIn:");// COMMAND IS THE LOCK VARIABLE TO DECIDE WHAT REQUEST WE WILL SENDE TO SERVER.
        printer.execute();
        while(!WaitForSignal && count <50) // THIS SEGMENT IS A SEMAFOR WAITS FOR A SIGNAL FROM REQUEST'S ANSWER.
        {
            Thread.sleep(100);
            if(count++==49)
            {
                server_is_close=true; // IF WE WAIT TOO MUCH, THEN THROW A CONNECTION ERROR.
            }
        }
        Thread.sleep(5);
        if(server_is_close) // IF SERVER IS CLOSE
        {
            Toast.makeText(this, "Bağlantı sırasında bir sorun ortaya çıktı", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(unf) // IF USER NOT FOUND
        {
            Toast.makeText(this, "Kullanıcı bulunamadı", Toast.LENGTH_SHORT).show();
            return ;
        }
        if (is_succeed) // IF LOGIN IS SUCCESSFULL, STEP INTO NEXT ACTIVITY.
        {
            SuccessfullLogin(username,password);
        }
        else // OR THROW A PASSWORD ERROR
        {
            Toast.makeText(this, "Şifre hatalı", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void RegisterScreen(View view) // THIS GOES TO REGISTER SCREEN
    {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void SuccessfullLogin(String username,String password) // WORKS ON SUCCESSFULL LOGIN
    {
        if(remember_me.isChecked())
        {
            SharedPreferences p1;
            p1 = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = p1.edit();
            e.putString("rememberme", "yes");
            e.putString("username", username);
            e.putString("password", password);
            e.apply();
        }
        else
        {
            SharedPreferences p1;
            p1 = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = p1.edit();
            e.putString("rememberme", "no");
            e.apply();
        }
        if(auto_login.isChecked())
        {
            SharedPreferences p1;
            p1 = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = p1.edit();
            e.putString("username", username);
            e.putString("password", password);
            e.putString("isUpdated","yes");
            e.apply();
        }
        Intent i = new Intent(this, AfterLoginScreen.class);
        i.putExtra("username", username);
        i.putExtra("name", name);
        i.putExtra("surname", surname);
        startActivity(i);
        finish();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void findWidgets() // FIND AND DESIGN WIDGETS
    {
        t1 = (EditText) findViewById(R.id.editText); //username
        t2 = (EditText) findViewById(R.id.editText2); //password
        b1 = (Button)findViewById(R.id.button2); // login button
        TextView beni_hatirla = (TextView)findViewById(R.id.textView);
        TextView otomatik_giris = (TextView)findViewById(R.id.textView5);
        beni_hatirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(remember_me.isChecked())
                {
                    remember_me.setBackgroundColor(Color.RED);
                    remember_me.setChecked(false);
                }
                else
                {
                    remember_me.setBackgroundColor(Color.GREEN);
                    remember_me.setChecked(true);
                }
            }
        });
        otomatik_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(auto_login.isChecked())
                {
                    auto_login.setBackgroundColor(Color.RED);
                    auto_login.setChecked(false);
                }
                else
                {
                    auto_login.setBackgroundColor(Color.GREEN);
                    auto_login.setChecked(true);
                }
            }
        });
        remember_me = (ToggleButton)findViewById(R.id.toggleButton);
        remember_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(remember_me.isChecked())
                {
                    remember_me.setBackgroundColor(Color.GREEN);
                }
                else
                {
                    remember_me.setBackgroundColor(Color.RED);
                }
            }
        });
        auto_login = (ToggleButton)findViewById(R.id.toggleButton2);
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(auto_login.isChecked())
                {
                    auto_login.setBackgroundColor(Color.GREEN);
                }
                else
                {
                    auto_login.setBackgroundColor(Color.RED);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    logIn(t1.getText().toString(),t2.getText().toString(),false);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        auto_login.setChecked(false);
        auto_login.setBackgroundColor(Color.RED);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void rememberme(SharedPreferences p1) // REMEMBER ME SET FUNCTION
    {
        if(p1.getString("rememberme","").equals("yes"))
        {
            remember_me.setChecked(true);
            remember_me.setBackgroundColor(Color.GREEN);
            t1.setText(p1.getString("username",""));
            t2.setText(p1.getString("password",""));
        }
        else
        {
            remember_me.setChecked(false);
            remember_me.setBackgroundColor(Color.RED);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void autologin(SharedPreferences p1)// AUTO LOGIN SET FUNCTION
    {
        if(p1.getString("isUpdated","").equals("yes"))
        {

            try {
                logIn(p1.getString("username",""),p1.getString("password",""),true);
                // Retrieve infos from shared prefences to login automatically
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        else
        {
            auto_login.setChecked(false);
            auto_login.setBackgroundColor(Color.RED);
        }

    }

}