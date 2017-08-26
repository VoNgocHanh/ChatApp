package com.vongochanh.chatapp.room.chat_room;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Vo Ngoc Hanh on 8/21/2017.
 */

public class ChatRoomPresenter implements ChatRoomContract.Presenter {
    ChatRoomContract.View mView;

    String mRoomName, mEmailUser;

    //Firebase
    private DatabaseReference mRoomDatabaseRef;

    public ChatRoomPresenter(ChatRoomContract.View view, String roomName) {
        mView = view;
        mRoomName = roomName;
        mEmailUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        mRoomDatabaseRef = FirebaseDatabase.getInstance().getReference().child(roomName);

        mRoomDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                showMessageOnChatFrame(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                showMessageOnChatFrame(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showMessageOnChatFrame(DataSnapshot dataSnapshot) {
        String contentMessage, emailUser;
        long time;
        ChatMessage chatMessage = null;

        Iterator iterator = dataSnapshot.getChildren().iterator();
        while (iterator.hasNext()) {
            contentMessage = (String) ((DataSnapshot)iterator.next()).getValue();
            emailUser = (String) ((DataSnapshot)iterator.next()).getValue();
            time = (long) ((DataSnapshot)iterator.next()).getValue();
            chatMessage = new ChatMessage(emailUser, contentMessage, time);
        }

        if (mView != null) {
            mView.updateChatFrame(chatMessage);
        }
    }

    /*Presenter interface methods*/
    @Override
    public void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(mEmailUser, message);
        Map<String, Object> map = new HashMap<>();
        String tempKey = mRoomDatabaseRef.push().getKey();
        map.put(tempKey, chatMessage);
        mRoomDatabaseRef.updateChildren(map);
    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }
    /*End*/
}
