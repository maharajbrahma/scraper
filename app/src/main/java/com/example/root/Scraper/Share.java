package com.example.root.Scraper;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;

/**
 * Created by root on 24/4/17.
 */

public class Share extends android.support.v4.app.Fragment {
    private Button buttonShare;
    AdView adViewShare;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share,container,false);
        buttonShare = (Button)view.findViewById(R.id.button_share);
        adViewShare=(AdView) view.findViewById(R.id.adViewShare);
       //Adview
        MobileAds.initialize(getContext(),
                "ca-app-pub-8398979833844274~5519452973");

        adViewShare = (AdView) view.findViewById(R.id.adViewShare);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewShare.loadAd(adRequest);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationInfo info = getContext().getApplicationContext().getApplicationInfo();
                String filepath = info.sourceDir;
                Intent send = new Intent();
                send.setAction(Intent.ACTION_SEND);
                send.setType("*/*");
                send.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filepath)));
                startActivity(Intent.createChooser(send,"Share scrapper"));

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Share");

    }
}
