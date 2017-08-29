package com.vongochanh.chatapp.ui.home.room.list_rooms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vongochanh.chatapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomListFragment extends Fragment {


    public RoomListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_list, container, false);
    }

}
