package com.example.yusuf.khysapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    int Targetport = 7788;
    public static boolean WaitForSignal; // MAIN COMMUNICATION SEMAFOR BETWEEN CLASSES
    public static boolean is_succeed,server_is_close; // VARIABLES THAT CHECK THE SUCCESS REGISTERING AND SERVER ONLINE STATUS
    EditText[] Texts;
    Button registerButton ;



    ////////////////////********************------OVERRIDE--------********////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegisterFragment regfrag = (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1); // get fragment
        Texts= regfrag.getTexts(); // get Texts from fragment
        registerButton = regfrag.getButton(); // get button from fragment
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Register();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }






    ////////////////////********************------FUNCTIONS--------********////////////////////////////////////////////////////
    public void Register() throws InterruptedException {
        WaitForSignal=false;
        is_succeed=true; server_is_close=false;
        int count =0;
        if(Texts[0].getText().toString().length()<6||Texts[0].getText().toString().length()>30) // CONTROL
        {
            Toast.makeText(this,"Kullanıcı adı uzunluğu 6 ile 30 karakter arasında olmalıdır",Toast.LENGTH_LONG).show();
            return;
        }
        if(Texts[1].getText().toString().length()<6||Texts[1].getText().toString().length()>30)// CONTROL
        {
            Toast.makeText(this,"Şifre uzunluğu 6 ile 30 karakter arasında olmalıdır",Toast.LENGTH_LONG).show();
            return;
        }
        if(Texts[2].getText().toString().length()<2||Texts[2].getText().toString().length()>30)// CONTROL
        {
            Toast.makeText(this,"Ad uzunluğu 2 ile 30 karakter arasında olmalıdır",Toast.LENGTH_LONG).show();
            return;
        }
        if(Texts[3].getText().toString().length()<2||Texts[3].getText().toString().length()>30)// CONTROL
        {
            Toast.makeText(this,"Soyad uzunluğu 2 ile 30 karakter arasında olmalıdır",Toast.LENGTH_LONG).show();
            return;
        }
        if(Texts[4].getText().toString().length()!= 11)// CONTROL
        {
            Toast.makeText(this,"Telefon numarası 11 karakterterli olmalıdır.Format: 05XXXXXXXXX",Toast.LENGTH_LONG).show();
            return;
        }
        for(EditText e : Texts) // IF CONTAINS /// THEN ERROR
        {
            if(e.getText().toString().contains("///"))
            {
                Toast.makeText(this, "/// yasak karakterler", Toast.LENGTH_SHORT).show();
                return ;
            }
        }
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss").format(Calendar.getInstance().getTime()); // REGISTER DATE
        LogInStartClientPrinter printer2 = new LogInStartClientPrinter(Texts[0].getText().toString().trim(),
                Texts[1].getText().toString().trim(),MainActivity.Targetaddr, Targetport,"InsertUser:"
                ,Texts[2].getText().toString().trim(),Texts[3].getText().toString().trim(),Texts[4].getText().toString().trim(),
                "Kullanıcı",timeStamp); // REGISTER COMMAND
        printer2.execute();
        while(!WaitForSignal && count <50) // THIS SEGMENT IS A SEMAFOR WAITS FOR A SIGNAL FROM REQUEST'S ANSWER.
        {
            Thread.sleep(100);
            if(count++==49)
            {
                server_is_close=true;
            }
        }
        Thread.sleep(5);
        if(server_is_close)// IF SERVER IS CLOSE
        {
            Toast.makeText(this, "Bağlantı sırasında bir sorun ortaya çıktı", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(!is_succeed)// IF CHECKING IS NOT SUCCESSFULL, GIVE AN ERROR.
        {
            Toast.makeText(this, "Böyle bir kullanıcı var", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Kaydedildi", Toast.LENGTH_SHORT).show(); // RETURN TO LOGIN ACTIVITY
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

