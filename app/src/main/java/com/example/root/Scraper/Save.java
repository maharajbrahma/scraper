package com.example.root.Scraper;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


/**
 * A simple {@link Fragment} subclass.
 */
public class Save extends Fragment {
    //SharedPreferences preferences;
    AdView adViewSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save,container,false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("ID_PREF", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);


        final EditText member_id = (EditText) view.findViewById(R.id.editText_id);
        member_id.setText(preferences.getString("MemberID",""));
        Button save = (Button) view.findViewById(R.id.button_save);
        if(isNetworkAvailable() == true)
        {
            save.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String id = member_id.getText().toString();
                    editor.putString("MemberID", id);
                    editor.commit();
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(member_id.getWindowToken(), 0);
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),"",Snackbar.LENGTH_SHORT);
                    snackbar.setDuration(700);
                    snackbar.setText("Saved");
                    snackbar.show();


                }
            });
        }
        else
        {
            member_id.setFocusable(false);
            save.setEnabled(false);
        }


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Save");

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}