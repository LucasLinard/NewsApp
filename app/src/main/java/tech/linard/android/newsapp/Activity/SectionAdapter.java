package tech.linard.android.newsapp.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.linard.android.newsapp.Model.Section;
import tech.linard.android.newsapp.R;

/**
 * Created by lucas on 30/11/16.
 */
public class SectionAdapter extends ArrayAdapter<Section> {
    public SectionAdapter(Context context, List<Section> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null)  {
            listitemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.section_list_item
                    , parent
                    , false);
        }
        Section section = getItem(position);

        TextView titleView = (TextView) listitemView.findViewById(R.id.section_title);
        titleView.setText(section.getWebTitle());

        return listitemView;
    }
}
