package tech.linard.android.newsapp.Util;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import tech.linard.android.newsapp.Model.Section;

/**
 * Created by lucas on 30/11/16.
 */

public class SectionLoader extends AsyncTaskLoader<List<Section>> {
    private static final String LOG_TAG = SectionLoader.class.getName();
    private String mUrl;

    public SectionLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, ": StartLoading");
        forceLoad();
    }

    @Override
    public List<Section> loadInBackground() {
        Log.d(LOG_TAG, ": loadInBackground");
        if (mUrl == null) {
            return null;
        }
        List<Section> sections = QueryUtils.fetchSectionData(mUrl);
        return sections;
    }
}

