package com.alttab.isotopeandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alttab.isotopeandroid.R;
import com.alttab.isotopeandroid.database.Major;

import java.util.List;

public class MajorAutocompleteAdapter extends ArrayAdapter<Major> {

    private Context mContext;
    private List<Major> majors;
    private int resource;

    public MajorAutocompleteAdapter(@NonNull Context context, int resource, @NonNull List<Major> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.majors = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(resource, parent, false);

        Major major = getItem(position);
        ((TextView) convertView.findViewById(R.id.major_option)).setText(major.fullName);
        return convertView;
    }
}
