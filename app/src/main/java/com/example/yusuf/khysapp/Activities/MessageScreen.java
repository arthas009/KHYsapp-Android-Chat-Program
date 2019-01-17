package com.example.yusuf.khysapp.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yusuf.khysapp.Adapters.CustomAdapter;
import com.example.yusuf.khysapp.Controller.LogInStartClientPrinter;
import com.example.yusuf.khysapp.R;

public class MessageScreen extends AppCompatActivity {

    ListView messagelist;
    EditText messagetext;
    Button sendbutton;
    String otheruser,me;
    TextView t;
    public static boolean WaitForSignal,newmessagefound;
    public static String[] messages = {};
    ////////////////////********************------OVERRIDE--------********////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_screen);
        setWidgetsandOthers();
        drawMessages();


    }

    @Override
    protected void onStart() {
        super.onStart();
        AfterLoginScreen.wentback = false;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!AfterLoginScreen.wentback) {
                    checkMessage();
                    if (newmessagefound) {
                        drawMessages();
                        scrollMyListViewToBottom();
                    }
                    handler.postDelayed(this, 3000);
                }

            }
        }, 700);
    }



    ////////////////////********************------FUNCTIONS--------********////////////////////////////////////////////////////
    public void setWidgetsandOthers()
    {
        Bundle data = getIntent().getExtras();
        otheruser = data.getString("otheruser");
        me = data.getString("me");
        t = (TextView)findViewById(R.id.messageToSend);
        t.setHint("Chat with "+otheruser);
        sendbutton = (Button)findViewById(R.id.SendButton);
        messagetext = (EditText)findViewById(R.id.messageToSend);
        messagelist = (ListView)findViewById(R.id.listView1);
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
    public void sendMessage()
    {
        if(t.getText().toString().equals("")) // IF NOTHING INPUT
        {
            return;
        }
        boolean server_is_close = false;
        WaitForSignal = false;
        int count = 0;
        LogInStartClientPrinter sendMessagePrinter = new LogInStartClientPrinter(me,otheruser,t.getText().toString(),"SendMessage:",
                MainActivity.Targetaddr,7788); // START AN SENDMESSAGE REQUEST
        sendMessagePrinter.execute();
        while(!WaitForSignal && count <50) // THIS SEGMENT IS A SEMAFOR WAITS FOR A SIGNAL FROM REQUEST'S ANSWER.
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count++==49)
            {
                server_is_close=true; // IF WE WAIT TOO MUCH, THEN THROW A CONNECTION ERROR.
            }
        }
        if(server_is_close)
        {
            if(server_is_close) // IF SERVER IS CLOSE
            {
                Toast.makeText(this, "Bağlantı sırasında bir sorun ortaya çıktı", Toast.LENGTH_SHORT).show();
                return ;
            }
        }
        t.setText("");
        drawMessages();


    }
    private void scrollMyListViewToBottom() {
        messagelist.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                messagelist.setSelection(messagelist.getCount() - 1);
            }
        });
    }
    public void drawMessages()
    {
        boolean server_is_close = false;
        WaitForSignal = false;
        int count = 0;
        LogInStartClientPrinter sendMessagePrinter = new LogInStartClientPrinter(me,otheruser,"DrawMessages:",
                MainActivity.Targetaddr,7788); // START AN SENDMESSAGE REQUEST
        sendMessagePrinter.execute();
        while(!WaitForSignal && count <50































                ) // THIS SEGMENT IS A SEMAFOR WAITS FOR A SIGNAL FROM REQUEST'S ANSWER.
        {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count++==49)
            {
                server_is_close=true; // IF WE WAIT TOO MUCH, THEN THROW A CONNECTION ERROR.
            }
        }
        /*if(server_is_close)
        {
            if(server_is_close) // IF SERVER IS CLOSE
            {
                Toast.makeText(this, "Bağlantı sırasında bir sorun ortaya çıktı", Toast.LENGTH_SHORT).show();
                return ;
            }
        }*/
        ListAdapter myAdapter = new CustomAdapter(this,messages);
        messagelist.setAdapter(myAdapter);
        scrollMyListViewToBottom();
    }
    public void checkMessage()
    {
        boolean server_is_close = false;
        WaitForSignal = false;
        int count = 0;
        LogInStartClientPrinter sendMessagePrinter = new LogInStartClientPrinter(me,otheruser,"CheckMessages:",
                MainActivity.Targetaddr,7788); // START AN SENDMESSAGE REQUEST
        sendMessagePrinter.execute();
        while(!WaitForSignal && count <50) // THIS SEGMENT IS A SEMAFOR WAITS FOR A SIGNAL FROM REQUEST'S ANSWER.
        {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count++==49)
            {
                server_is_close=true; // IF WE WAIT TOO MUCH, THEN THROW A CONNECTION ERROR.
            }
        }
       /* if(server_is_close)
        {
            if(server_is_close) // IF SERVER IS CLOSE
            {
                Toast.makeText(this, "Bağlantı sırasında bir sorun ortaya çıktı", Toast.LENGTH_SHORT).show();
                return ;
            }
        }*/
    }
}
