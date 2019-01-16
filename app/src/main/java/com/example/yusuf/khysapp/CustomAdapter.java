package com.example.yusuf.khysapp;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.*;

public class CustomAdapter extends ArrayAdapter
{
    MyLinkedList LinkedList;
    Activity context;

    ////////////////////********************------CONSTRUCTOR--------********////////////////////////////////////////////////////
    public CustomAdapter(Activity context,String[] messages) {
        super(context,R.layout.message_layout, messages);
        LinkedList = new MyLinkedList();
        LinkedList.AddStrArray(messages);
        this.context = context;
    }



    ////////////////////********************------OVERRIDE--------********////////////////////////////////////////////////////
    @Override
    public View getView(int position,View contentView,ViewGroup parent)
    {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = vi.inflate(R.layout.message_layout, null);
        findWidgets(contentView,position);
        return contentView;
    }




    ////////////////////********************------FUNCTIONS--------********////////////////////////////////////////////////////
    public void findWidgets(View contentView,int position)
    {
        LinearLayout linlay1 = (LinearLayout)contentView.findViewById(R.id.linlay2);
        LinearLayout rellay1 = (LinearLayout) contentView.findViewById(R.id.rellay2);
        TextView txtMessage = (TextView)contentView.findViewById(R.id.textView1);
        txtMessage.setText(LinkedList.getString(position));
        setPositionAccordingToUser(linlay1,rellay1,position,txtMessage);
    }
    public void setPositionAccordingToUser(LinearLayout linlay1,LinearLayout rellay1, int position,TextView txtMessage)
    {
        if(!LinkedList.isFromMe(position))
        {
            rellay1.setBackgroundColor(Color.WHITE);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) rellay1.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            rellay1.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) linlay1.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
            linlay1.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            txtMessage.setLayoutParams(layoutParams);
        }
        else {
            rellay1.setBackgroundColor(Color.GREEN);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) rellay1.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            rellay1.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) linlay1.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
            linlay1.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            txtMessage.setLayoutParams(layoutParams);
        }
    }

}
