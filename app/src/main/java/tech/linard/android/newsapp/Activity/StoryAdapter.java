package tech.linard.android.newsapp.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tech.linard.android.newsapp.Model.Section;
import tech.linard.android.newsapp.Model.Story;
import tech.linard.android.newsapp.R;

/**
 * Created by lucas on 30/11/16.
 */

public class StoryAdapter extends ArrayAdapter<Story> {

    public StoryAdapter(Context context, List<Story> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null)  {
            listitemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.story_list_item
                    , parent
                    , false);
        }
        Story story = getItem(position);

        TextView storyTitle = (TextView) listitemView.findViewById(R.id.story_title);
        storyTitle.setText(story.getWebTitle());

        TextView storySection = (TextView) listitemView.findViewById(R.id.story_section);
        storySection.setText(story.getSectionName());

        return listitemView;
    }
}
