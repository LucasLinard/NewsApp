package tech.linard.android.newsapp.Util;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import tech.linard.android.newsapp.Model.Section;
import tech.linard.android.newsapp.Model.Story;
import tech.linard.android.newsapp.R;

/**
 * Created by lucas on 30/11/16.
 */

public class StoryLoader extends AsyncTaskLoader<List<Story>> {
    private static final String LOG_TAG = SectionLoader.class.getName();
    private String mUrl;

    public StoryLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, ": StartLoading");
        forceLoad();
    }

    @Override
    public List<Story> loadInBackground() {
        Log.d(LOG_TAG, ": loadInBackground");
        if (mUrl == null) {
            return null;
        }
        List<Story> stories = QueryUtils.fetchStoryData(mUrl);
        return stories;
    }
}
