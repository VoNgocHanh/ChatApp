package com.vongochanh.chatapp.room.list_rooms;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vo Ngoc Hanh on 8/21/2017.
 */

public class RoomsListPresenterImpl implements RoomsListPresenter {
    private RoomsListView mView;

    private ArrayList<String> mRoomsArrayList = new ArrayList<>();
    private String mName = "";
    private DatabaseReference mRootDbRef = FirebaseDatabase.getInstance().getReference().getRoot();

    public RoomsListPresenterImpl(RoomsListView mView) {
        this.mView = mView;

        mRootDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onFirebaseDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void onFirebaseDataChange(DataSnapshot dataSnapshot) {
        Set<String> set = new HashSet<String>();
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            set.add(((DataSnapshot)i.next()).getKey());
        }
        if (mView != null) {
            mView.upadteRoomListView(set);
        }
    }

    @Override
    public void addRoom(String roomName) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(roomName, "");
        mRootDbRef.updateChildren(map);
    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }
}
