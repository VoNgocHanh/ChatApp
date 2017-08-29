package com.vongochanh.chatapp.ui.home.room.list_rooms;

/**
 * Created by Vo Ngoc Hanh on 8/21/2017.
 */

import java.util.Set;


public interface RoomsListView {
    void showAddRoomProgress();

    void hideAddRoomProgress();

    void upadteRoomListView(Set<String> set);
}
