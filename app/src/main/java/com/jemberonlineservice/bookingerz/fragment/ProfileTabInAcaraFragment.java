package com.jemberonlineservice.bookingerz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jemberonlineservice.bookingerz.R;
import com.jemberonlineservice.bookingerz.helper.ListAdapter;
import com.jemberonlineservice.bookingerz.helper.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vmmod on 1/3/2017.
 */

public class ProfileTabInAcaraFragment extends Fragment {
    ListView list;
    ListAdapter listAdapter;
    List<ListItem> itemList = new ArrayList<ListItem>();

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
