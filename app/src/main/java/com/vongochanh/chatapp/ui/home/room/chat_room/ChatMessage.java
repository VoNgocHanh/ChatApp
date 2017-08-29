package com.vongochanh.chatapp.ui.home.room.chat_room;

import java.util.Date;

/**
 * Created by Vo Ngoc Hanh on 8/21/2017.
 */

public class ChatMessage {
    private String emailUser;
    private String content;
    private long time;

    public ChatMessage() {
    }

    public ChatMessage(String emailUser, String content) {
        this.emailUser = emailUser;
        this.content = content;
        time = new Date().getTime();
    }

    public ChatMessage(String emailUser, String content, long time) {
        this.emailUser = emailUser;
        this.content = content;
        this.time = time;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getContent() {
        return content;
    }

    public long getTime() {
        return time;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
