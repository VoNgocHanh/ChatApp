package com.vongochanh.chatapp.room.chat_room;

import java.util.List;

/**
 * Created by Vo Ngoc Hanh on 8/24/2017.
 */

public interface ChatRoomContract {
    public interface View {
        void updateChatFrame(List<ChatMessage> list);
        void updateChatFrame(ChatMessage chatMessage);
    }

    public interface Presenter {
        void sendMessage(String message);

        void destroy();
    }
}
