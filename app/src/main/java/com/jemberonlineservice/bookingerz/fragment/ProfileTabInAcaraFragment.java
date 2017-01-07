package com.jemberonlineservice.bookingerz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jemberonlineservice.bookingerz.R;

/**
 * Created by vmmod on 1/3/2017.
 */

public class ProfileTabInAcaraFragment extends Fragment {
    public ProfileTabInAcaraFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_profiletabinacara, container,
                false);
    }
}
