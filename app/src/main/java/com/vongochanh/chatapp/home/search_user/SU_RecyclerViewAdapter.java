package com.vongochanh.chatapp.home.search_user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.model.User;

import java.util.List;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

public class SU_RecyclerViewAdapter extends RecyclerView.Adapter<SearchingUserHolder>
        implements SearchingUserContract.Listener.HolderClickListener {
    Context mContext;
    SearchingUserContract.Listener.ItemClickListener mListener;
    List<User> mUserList;

    public SU_RecyclerViewAdapter(Context mContext, SearchingUserContract.Listener.ItemClickListener listener, List<User> mUserList) {
        this.mContext = mContext;
        mListener = listener;
        this.mUserList = mUserList;
    }

    public void resetData(List<User> users) {
        mUserList.clear();
        mUserList.addAll(users);
        notifyDataSetChanged();
    }

    public void addUser(User user) {
        mUserList.add(user);
        notifyDataSetChanged();
    }

    @Override
    public SearchingUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_list_founded_user, parent, false);

        return new SearchingUserHolder(view, this/*SearchingUserContract.Listener.HolderClickListener*/);
    }

    @Override
    public void onBindViewHolder(SearchingUserHolder holder, int position) {
        User user = mUserList.get(position);
        holder.render(user);

        holder.updatePosition(position);
    }

    @Override
    public int getItemCount() {
        if (mUserList == null) {
            return 0;
        }
        return mUserList.size();
    }

    /*----------- SearchingUserLitener.ItemClickListener IMPLEMENT ---------*/
    @Override
    public void onUserHolderClick(int holderPosition) {
        if (mListener != null) {
            mListener.onUserItemClick(mUserList.get(holderPosition));
        }
    }
}
