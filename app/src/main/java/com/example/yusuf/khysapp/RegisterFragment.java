package com.example.yusuf.khysapp;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
public class RegisterFragment extends Fragment {

    View view;
    EditText[] Texts;
    Button RegisterButton;

    ////////////////////********************------OVERRIDE--------********////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register,container,false);
        return view;
    }



    ////////////////////********************------FUNCTIONS--------********////////////////////////////////////////////////////
    public EditText[] getTexts() // THIS RETURNS TEXTS TO ACTIVITIES WHICH WANTS THESE
    {
        Texts = new EditText[5];
        Texts[0] = (EditText)view.findViewById(R.id.editText3);// USERNAME
        Texts[1] = (EditText)view.findViewById(R.id.editText4);// PASSWORD
        Texts[2] = (EditText)view.findViewById(R.id.editText5);// NAME
        Texts[3] = (EditText)view.findViewById(R.id.editText6);// SURNAME
        Texts[4] = (EditText)view.findViewById(R.id.editText8);// PHONE

        Texts[0].setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        if(cs.toString().matches("[0-9 ]+")){
                            return cs;
                        }
                        if(cs.toString().equals("ı")||cs.toString().equals("ö")||cs.toString().equals("ü")||
                                cs.toString().equals("ğ")||cs.toString().equals("ş")||cs.toString().equals("ç")){
                            return cs;
                        }
                        if(cs.toString().equals("İ")||cs.toString().equals("Ö")||cs.toString().equals("Ü")||
                                cs.toString().equals("Ğ")||cs.toString().equals("Ş")||cs.toString().equals("Ç")){
                            return cs;
                        }
                        if(cs.toString().equals("_")||cs.toString().equals("-")||cs.toString().equals(".")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
        Texts[1].setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        if(cs.toString().matches("[0-9 ]+")){
                            return cs;
                        }
                        if(cs.toString().equals("ı")||cs.toString().equals("ö")||cs.toString().equals("ü")||
                                cs.toString().equals("ğ")||cs.toString().equals("ş")){
                            return cs;
                        }
                        if(cs.toString().equals("İ")||cs.toString().equals("Ö")||cs.toString().equals("Ü")||
                                cs.toString().equals("Ğ")||cs.toString().equals("Ş")){
                            return cs;
                        }
                        return "";
                    }
                }
        });

        Texts[2].setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        if(cs.toString().equals("ı")||cs.toString().equals("ö")||cs.toString().equals("ü")||
                                cs.toString().equals("ğ")||cs.toString().equals("ş")){
                            return cs;
                        }
                        if(cs.toString().equals("İ")||cs.toString().equals("Ö")||cs.toString().equals("Ü")||
                                cs.toString().equals("Ğ")||cs.toString().equals("Ş")){
                            return cs;
                        }
                        if(cs.equals(" ")){ // for backspace
                            return cs;
                        }
                        return "";
                    }
                }
        });
        Texts[3].setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.equals(" ")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().equals("ı")||cs.toString().equals("ö")||cs.toString().equals("ü")||
                                cs.toString().equals("ğ")||cs.toString().equals("ş")){
                            return cs;
                        }
                        if(cs.toString().equals("İ")||cs.toString().equals("Ö")||cs.toString().equals("Ü")||
                                cs.toString().equals("Ğ")||cs.toString().equals("Ş")){
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }

                        return "";
                    }
                }
        });
        Texts[4].setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        if(cs.equals("") && Texts[4].getText().toString().length() <= 10){ // for backspace
                            return cs;
                        }
                        if(cs.equals(" ")&& Texts[4].getText().toString().length() <= 10){ //backspace
                            return cs;
                        }
                        if(cs.toString().matches("[0-9 ]+")&& Texts[4].getText().toString().length() <= 10){
                            return cs;
                        }
                        return "";
                    }
                }
        });
        Texts[4].setHint("05XXXXXXXXX");

        return Texts;
    }

    public Button getButton()// THIS RETURNS BUTTON TO ACTIVITIES WHICH WANTS THIS
    {
        RegisterButton = (Button)view.findViewById(R.id.button3);
        return RegisterButton;
    }
}
