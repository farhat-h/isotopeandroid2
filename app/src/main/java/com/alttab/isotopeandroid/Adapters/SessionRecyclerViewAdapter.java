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

    public static final int ITEM_TP = 1;
    public static final int ITEM_TD = 2;
    public static final int ITEM_C = 3;
    public static final int ITEM_EMPTY = 4;

    private Context mContext;
    private List<Session> sessionList;

    public SessionRecyclerViewAdapter(Context mContext, List<Session> sessionList) {
        this.mContext = mContext;
        this.sessionList = sessionList;
    }

    @Override
    public int getItemViewType(int position) {
        Session session = sessionList.get(position);
        if (session.sessionId.equals(Session.EMPTY_ID))
            return ITEM_EMPTY;

        else if (session.type.equals("TP"))
            return ITEM_TP;

        else if (session.type.equals("TD"))
            return ITEM_TD;

        else if (session.type.equals("C"))
            return ITEM_C;
        else return ITEM_EMPTY;
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
                resource = R.layout.empty_session_layout;
                break;
        }

        View itemView = LayoutInflater.from(mContext).inflate(resource, parent, false);
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

}
