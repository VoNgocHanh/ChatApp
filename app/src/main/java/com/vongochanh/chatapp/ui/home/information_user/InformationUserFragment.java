package com.vongochanh.chatapp.ui.home.information_user;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vongochanh.chatapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationUserFragment extends Fragment {


    public InformationUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information_user, container, false);
    }

}
