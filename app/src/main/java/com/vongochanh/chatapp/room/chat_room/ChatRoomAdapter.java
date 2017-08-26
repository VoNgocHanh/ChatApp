package com.vongochanh.chatapp.room.chat_room;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vongochanh.chatapp.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vo Ngoc Hanh on 8/21/2017.
 */

public class ChatRoomAdapter extends ArrayAdapter<ChatMessage> {
    private List<ChatMessage> mList;
    private ArrayList<ViewHolder> mHolderList;

    private class ViewHolder{
        TextView contentMessageField, emailUserMessageField, timeMessageField;
    }

    public ChatRoomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ChatMessage> list) {
        super(context, resource, list);
        this.mList = list;
        mHolderList = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_message_chat, parent, false);

            holder = new ViewHolder();
            holder.contentMessageField = (TextView) convertView.findViewById(R.id.contentMessageField_item);
            holder.emailUserMessageField = (TextView) convertView.findViewById(R.id.emailUserField_item);
            holder.timeMessageField = (TextView) convertView.findViewById(R.id.timeMessageField_item);

            mHolderList.add(holder);
        }else{
            holder = mHolderList.get(position);
        }

        ChatMessage chatMessage = getItem(position);
        holder.emailUserMessageField.setText(chatMessage.getEmailUser());
        holder.contentMessageField.setText(chatMessage.getContent());

        Date date = new Date(chatMessage.getTime());
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        holder.timeMessageField.setText(format.format(date));

        return convertView;
    }

    public void updateData(List<ChatMessage> list) {
        mList.clear();
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    public void addData(ChatMessage chatMessage) {
        mList.add(chatMessage);
        this.notifyDataSetChanged();
    }
}
