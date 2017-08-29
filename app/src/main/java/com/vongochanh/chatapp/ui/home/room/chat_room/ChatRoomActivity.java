package com.vongochanh.chatapp.ui.home.room.chat_room;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.ui.home.room.list_rooms.RoomsListActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity implements ChatRoomContract.View{
    public static final String EXTRA_EMAIL_USER = "EXTRA EMAIL USER";

    //mvp
    ChatRoomContract.Presenter mPresenter;

    //views
    ListView mChatFrame;
    ChatRoomAdapter mAdapter;
    List<ChatMessage> mMessagesList;

    EditText mMessageInput;
    Button mMessageSend;

    /*Lifecycle methods*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        init();
    }

    private void init() {
        //init presenter
        String roomName = getIntent().getStringExtra(RoomsListActivity.EXTRA_ROOM_NAME);
        mPresenter = new ChatRoomPresenter(this, roomName);

        //inflate views
        mMessageInput = (EditText) findViewById(R.id.messageInput);
        mMessageSend = (Button) findViewById(R.id.messageSend);

        mChatFrame = (ListView) findViewById(R.id.chatFrame);
        mMessagesList = new ArrayList<>();
        mMessagesList.add(new ChatMessage("ngh@ma", "asas"));
        mAdapter = new ChatRoomAdapter(this, R.layout.item_message_chat,mMessagesList);
        mChatFrame.setAdapter(mAdapter);

        mChatFrame.post(new Runnable() {
            public void run() {
                mChatFrame.setAdapter(mAdapter);
            }
        });

        //set listeners
        mMessageSend.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String message = mMessageInput.getText().toString();
                mMessageInput.setText("");
                sendMessage(message);
            }
        });
    }

    private void sendMessage(String message) {
        if (mPresenter != null && !TextUtils.isEmpty(message)) {
            mPresenter.sendMessage(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }
    /*End*/

    /*View interface methods*/
    @Override
    public void updateChatFrame(List<ChatMessage> list) {
        mAdapter.updateData(list);
    }

    @Override
    public void updateChatFrame(ChatMessage chatMessage) {
        mAdapter.addData(chatMessage);
    }

    /*End*/
}
