package com.alttab.isotopeandroid.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alttab.isotopeandroid.Helper;
import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.database.Major;

public class MajorAutocompleteItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Major major;
    private Context ctx;
    private View itemView;

    public MajorAutocompleteItemViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.ctx = context;
        this.itemView = itemView;
    }

    public void setMajor(Major major) {
        this.major = major;
        ((TextView) this.itemView.findViewById(R.id.major_option)).setText(major.fullName);
    }

    @Override
    public void onClick(View v) {
        Helper.getInstance(ctx).setMajorId(major.majorId);
        // Launch Next Activity
    }
}
