package com.example.yusuf.khysapp;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
public class LogInStartClientPrinter extends AsyncTask<Void, Void, Void> {
    String hostname,username,password,command,name,surname,phonenumber,position,datetime,message;
    String otheruser;
    int port;
    ListView messagesList;
    Activity context;

    ////////////////////********************------CONSTRUCTORS--------********////////////////////////////////////////////////////
    public LogInStartClientPrinter(String username, String password, String hostname, int port,String command)
    {
        this.username=username;
        this.password=password;
        this.hostname=hostname;
        this.command=command;
        this.port=port;
    }
    public LogInStartClientPrinter(String username, String password, String hostname, int port,String command,
                                   String name,String surname,String phonenumber,String position,String datetime)
    {
        this.name=name;
        this.surname=surname;
        this.phonenumber=phonenumber;
        this.position=position;
        this.username=username;
        this.password=password;
        this.datetime=datetime;
        this.hostname=hostname;
        this.command=command;
        this.port=port;
    }
    public LogInStartClientPrinter(String me,String otheruser,String command,String hostname,int port)
    {
        this.username=me;
        this.otheruser=otheruser;
        this.command=command;
        this.hostname = hostname;
        this.port = port;
    }
    public LogInStartClientPrinter(String me,String otheruser,String message,String command,String hostname,int port)
    {
        this.username=me;
        this.otheruser=otheruser;
        this.message = message;
        this.command=command;
        this.hostname = hostname;
        this.port = port;
    }
    public LogInStartClientPrinter(String me,String hostname,int port,String command)
    {
        this.username=me;
        this.command=command;
        this.hostname = hostname;
        this.port = port;
    }

    ////////////////////********************------OVERRIDE--------********////////////////////////////////////////////////////
    @Override
    protected Void doInBackground(Void... voids) {
        if(command.equals("LogIn:")) // IF COMMAND IS LOG IN THEN START LOG IN PROCESS
        {
            LogIn();
        }
        else if(command.equals("InsertUser:")) // IF ITS POSSIBLE, INSERT USER
        {
            InsertUser();
        }
        if(command.equals("SendMessage:")) // IF COMMAND IS LOG IN THEN START LOG IN PROCESS
        {
            SendMessage();
        }
        if(command.equals("DrawMessages:")) // IF COMMAND IS LOG IN THEN START LOG IN PROCESS
        {
            DrawMessages();
        }
        if(command.equals("CheckMessages:")) // IF COMMAND IS LOG IN THEN START LOG IN PROCESS
        {
            CheckForMessages();
        }
        return null;
    }




    ////////////////////********************------FUNCTIONS--------********////////////////////////////////////////////////////
    private void LogIn()
    {
        Socket printersocket =null;
        try{
            printersocket = new Socket(hostname,port);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(printersocket.getOutputStream())),true);
            out.println("LogIn:"+username+"///"+password);// MESSAGE DELIVERED TO SERVER AS LogIn:
            InputStream IS = printersocket.getInputStream();
            InputStreamReader ISR = new InputStreamReader(IS);
            BufferedReader in = new BufferedReader(ISR);
            while(true)// AFTER DELIVERED MESSAGE, START LISTENING TO SERVER
            {
                String answer = in.readLine();
                if(answer != null && answer.startsWith("true:"))// MESSAGE DELIVERED TO SERVER AS LogIn:. NOW WAITING FOR ANSWER.
                // IF ITS TRUE THEN SEND SIGNAL TO MAIN ACTIVITY
                {
                    answer = answer.substring(5);
                    String[] str = answer.split("///");
                    AfterLoginScreen.userlist = new String[str.length-2]; // GET ALL USERS TO AFTERLOGINSCREEN ACTIVITY
                    for(int i =2;i<str.length;i++)
                    {
                        AfterLoginScreen.userlist[i-2] =str[i];
                    }
                    MainActivity.WaitForSignal=true;
                    MainActivity.is_succeed=true;
                    MainActivity.name = str[0] ;
                    MainActivity.surname = str[1];
                    break;
                }
                else if(answer != null&&answer.equals("error"))// MESSAGE DELIVERED TO SERVER AS LogIn: NOW WAITING FOR ANSWER
                //IF ITS ERROR THEN SEND SIGNAL TO MAIN ACTIVITY AS ERROR
                {
                    MainActivity.WaitForSignal=true;
                    MainActivity.is_succeed=false;
                    break;
                }
                else if(answer != null && answer.equals("unf"))// MESSAGE DELIVERED TO SERVER AS LogIn: NOW WAITING FOR ANSWER
                //IF ITS ERROR THEN SEND SIGNAL TO MAIN ACTIVITY AS USER NOT FOUND
                {
                    MainActivity.WaitForSignal=true;
                    MainActivity.is_succeed=false;
                    MainActivity.unf = true;
                    break;
                }
            }
            printersocket.close();
        }
        catch (IOException e)
        {
            try {
                printersocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
    private void InsertUser()
    {
        Socket printersocket =null;
        try{
            printersocket = new Socket(hostname,port);
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(printersocket.getOutputStream())),true);
            out.println("InsertUser:"+name+"///"+surname+"///"+phonenumber+"///"+username+"///"+position+"///"+password+"///"+datetime);
            // ALL INFORMATIONS SENT HERE AS InsertUser:
            InputStream IS = printersocket.getInputStream();
            InputStreamReader ISR = new InputStreamReader(IS);
            BufferedReader in = new BufferedReader(ISR);
            Thread.sleep(5);
            while(true) // WAIT FOR ANSWER
            {
                String answer = in.readLine();
                if(answer.equals("UserRegistered"))// WAIT FOR ANSWER WHETHER USER IS REGISTERED SUCCESSFULLY OR NOT
                {
                    RegisterActivity.WaitForSignal=true;
                    RegisterActivity.is_succeed=true;
                    break;
                }
                else if(answer.equals("AnotherUserFound"));
                {
                    RegisterActivity.WaitForSignal=true;
                    RegisterActivity.is_succeed=false;
                    break;
                }
            }
            printersocket.close();
            return ;
        }
        catch (Exception e)
        {
            return;
        }
    }
    private void SendMessage()
    {
        Socket printersocket =null;
        try{
            printersocket = new Socket(hostname,port);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(printersocket.getOutputStream())),true);
            out.println("SendMessage:"+username+"///"+otheruser+"///"+message);// MESSAGE DELIVERED TO SERVER AS LogIn:
            InputStream IS = printersocket.getInputStream();
            InputStreamReader ISR = new InputStreamReader(IS);
            BufferedReader in = new BufferedReader(ISR);
            while(true)// AFTER DELIVERED MESSAGE, START LISTENING TO SERVER
            {
                String answer = in.readLine();
                if(answer.startsWith("true:"))// MESSAGE DELIVERED TO SERVER AS LogIn:. NOW WAITING FOR ANSER.
                // IF ITS TRUE THEN SEND SIGNAL TO MAIN ACTIVITY
                {
                    MessageScreen.WaitForSignal=true;
                    break;
                }

            }
            printersocket.close();
        }
        catch (IOException e)
        {

        }

    }
    private void DrawMessages()
    {
        Socket printersocket =null;
        try{
            printersocket = new Socket(hostname,port);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(printersocket.getOutputStream())),true);
            out.println("DrawMessages:"+username+"///"+otheruser);// MESSAGE DELIVERED TO SERVER AS LogIn:
            InputStream IS = printersocket.getInputStream();
            InputStreamReader ISR = new InputStreamReader(IS);
            BufferedReader in = new BufferedReader(ISR);
            while(true)// AFTER DELIVERED MESSAGE, START LISTENING TO SERVER
            {
                String answer = in.readLine();
                if(answer.startsWith("true:"))// MESSAGE DELIVERED TO SERVER AS LogIn:. NOW WAITING FOR ANSER.
                // IF ITS TRUE THEN SEND SIGNAL TO MAIN ACTIVITY
                {
                    int i = 0;
                    String[] Str = answer.substring(5).split("///");
                    MessageScreen.messages = new String[Str.length];
                    for(String mystring : Str)
                    {
                        MessageScreen.messages[i++] = mystring;
                    }
                    MessageScreen.WaitForSignal=true;
                    break;
                }
            }
            printersocket.close();
        }
        catch (IOException e)
        {

        }
    }
    private void CheckForMessages()
    {
        Socket printersocket =null;
        try{
            printersocket = new Socket(hostname,port);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(printersocket.getOutputStream())),true);
            out.println("CheckMessages:"+username+"///"+otheruser);// MESSAGE DELIVERED TO SERVER AS LogIn:
            InputStream IS = printersocket.getInputStream();
            InputStreamReader ISR = new InputStreamReader(IS);
            BufferedReader in = new BufferedReader(ISR);
            while(true)// AFTER DELIVERED MESSAGE, START LISTENING TO SERVER
            {
                String answer = in.readLine();
                if(answer.startsWith("true:"))// MESSAGE DELIVERED TO SERVER AS LogIn:. NOW WAITING FOR ANSER.
                // IF ITS TRUE THEN SEND SIGNAL TO MAIN ACTIVITY
                {
                    MessageScreen.WaitForSignal=true;
                    MessageScreen.newmessagefound = true;
                    break;
                }
                else
                {
                    MessageScreen.WaitForSignal=true;
                    MessageScreen.newmessagefound = false;
                    break;
                }
            }
            printersocket.close();
        }
        catch (IOException e)
        {

        }
    }

}