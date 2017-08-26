package com.vongochanh.chatapp.room.list_rooms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.room.chat_room.ChatRoomActivity;

import java.util.ArrayList;
import java.util.Set;

public class RoomsListActivity extends AppCompatActivity implements RoomsListView{
    //logic mvp
    RoomsListPresenter mPresenter;

    //extras
    public static final String EXTRA_ROOM_NAME = "room name";

    //views
    private ListView mRoomsListView;
    private EditText mRoomValue;
    private Button mAddRoom;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mRoomsArrayList = new ArrayList<>();

    /*Lifecycle methods*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        init();
    }

    private void init() {
        //init presenter
        mPresenter = new RoomsListPresenterImpl(this);

        //inflate views
        mRoomsListView = (ListView) findViewById(R.id.roomsList);
        mRoomValue = (EditText) findViewById(R.id.roomValue);
        mAddRoom = (Button) findViewById(R.id.addRoom);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mRoomsArrayList);
        mRoomsListView.setAdapter(mAdapter);

        //set listeners
        mAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter != null) {
                    String roomName = mRoomValue.getText().toString();
                    mPresenter.addRoom(roomName);
                }
            }
        });

        mRoomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openChatRoom(view);
            }
        });
    }

    //start room chat activity by click some item in room listview
    private void openChatRoom(View view) {
        String roomName = ((TextView) view).getText().toString();

        Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
        intent.putExtra(EXTRA_ROOM_NAME, roomName);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }
    /*End Lifecyle*/

    /*RoomView interface methods*/
    ProgressDialog mProgressDialog;
    @Override
    public void showAddRoomProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Adding...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    @Override
    public void hideAddRoomProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void upadteRoomListView(Set<String> set) {
        mRoomsArrayList.clear();
        mRoomsArrayList.addAll(set);
        mAdapter.notifyDataSetChanged();
    }

    /*End*/

}
