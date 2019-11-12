package com.alttab.isotopeandroid.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.ViewHolders.SessionViewHolder;
import com.alttab.isotopeandroid.database.Session;

import java.util.List;

public class SessionRecyclerViewAdapter extends RecyclerView.Adapter<SessionViewHolder> {

    private Context mContext;
    private List<Session> sessionList;

    public SessionRecyclerViewAdapter(Context mContext, List<Session> sessionList) {
        this.mContext = mContext;
        this.sessionList = sessionList;
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.session_item_layout, parent, false);
        return new SessionViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        if (sessionList != null) {
            Session _session = sessionList.get(position);
            holder.setmSession(_session);
        }
    }

    @Override
    public int getItemCount() {
        if (sessionList != null)
            return sessionList.size();
        return 0;
    }

    public void updateData(List<Session> list) {
        this.sessionList = list;
        notifyDataSetChanged();
    }

}
