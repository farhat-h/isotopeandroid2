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

    private static final int ITEM_TP = 1;
    private static final int ITEM_TD = 2;
    private static final int ITEM_C = 3;

    private Context mContext;
    private List<Session> sessionList;

    public SessionRecyclerViewAdapter(Context mContext, List<Session> sessionList) {
        this.mContext = mContext;
        this.sessionList = sessionList;
    }

    @Override
    public int getItemViewType(int position) {
        String type = "UNDEFINED_TYPE";
        if (sessionList != null)
            type = sessionList.get(position).type;

        switch (type) {
            case "TP":
                return ITEM_TP;
            case "TD":
                return ITEM_TD;
            case "C":
                return ITEM_C;
            default:
                return ITEM_TP;
        }

    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int resource = -1;
        switch (viewType) {
            case ITEM_TP:
                resource = R.layout.session_tp_layout;
                break;
            case ITEM_TD:
                resource = R.layout.session_td_layout;
                break;
            case ITEM_C:
                resource = R.layout.session_c_layout;
                break;

            default:
                resource = R.layout.session_td_layout;
                break;

        }
        View itemView = LayoutInflater.from(mContext).inflate(resource, parent, false);
        return new SessionViewHolder(itemView);
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
