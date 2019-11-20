package com.alttab.isotopeandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.ViewHolders.AlternativeViewHolder;
import com.alttab.isotopeandroid.database.NamedSession;
import com.alttab.isotopeandroid.database.Session;

import java.util.List;

public class AlternativesListAdapter extends RecyclerView.Adapter<AlternativeViewHolder> {

    private Context mContext;
    private List<NamedSession> sessionList;

    public AlternativesListAdapter(Context mContext, List<NamedSession> sessionList) {
        this.mContext = mContext;
        this.sessionList = sessionList;
    }


    @Override
    public int getItemViewType(int position) {
        final NamedSession s = sessionList.get(position);
        int viewTypeResource = -1;

        switch (s.type) {
            case Session.TYPE_C:
                viewTypeResource = R.layout.alternative_session_c;
                break;
            case Session.TYPE_TD:
                viewTypeResource = R.layout.alternative_session_layout;
                break;
            case Session.TYPE_TP:
                viewTypeResource = R.layout.alternative_session_tp;
                break;
            default:
                break;
        }
        return viewTypeResource;
    }

    @NonNull
    @Override
    public AlternativeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(viewType, parent, false);
        return new AlternativeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlternativeViewHolder holder, int position) {
        holder.bind(sessionList.get(position));
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }
}
