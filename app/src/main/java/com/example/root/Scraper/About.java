package com.example.root.Scraper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by root on 23/4/17.
 */
public class About extends Fragment {
    AdView adViewAbout,getAdViewAbout2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about,container,false);
        //Adview
        MobileAds.initialize(getContext(),
                "ca-app-pub-8398979833844274~5519452973");

        adViewAbout = (AdView) view.findViewById(R.id.adViewAbout);
        getAdViewAbout2=(AdView)view.findViewById(R.id.adViewAbout2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewAbout.loadAd(adRequest);
        getAdViewAbout2.loadAd(adRequest);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("About");

    }
}
