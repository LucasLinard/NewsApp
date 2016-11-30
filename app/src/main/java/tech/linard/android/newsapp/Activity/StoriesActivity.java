package tech.linard.android.newsapp.Activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tech.linard.android.newsapp.Model.Section;
import tech.linard.android.newsapp.Model.Story;
import tech.linard.android.newsapp.R;
import tech.linard.android.newsapp.Util.SectionLoader;
import tech.linard.android.newsapp.Util.StoryLoader;

import static tech.linard.android.newsapp.Activity.MainActivity.LOG_TAG;

public class StoriesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Story>> {
    private static final int STORY_LOADER_ID = 2;
    public  String STORY_REQUEST_URL = "http://content.guardianapis.com/search?order-by=newest&api-key=065324a3-0dff-454a-a25a-28a07cca5dda";

    // &section=politics
    public StoryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        final ListView storiesListView = (ListView) findViewById(R.id.list_stories);
        mAdapter = new StoryAdapter(this, new ArrayList<Story>());
        storiesListView.setAdapter(mAdapter);
        storiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mAdapter.getItem(i).getWebUrl()));
                startActivity(intent);
            }
        });
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(STORY_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Story>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, ": onCreateLoader");
        return new StoryLoader(this, STORY_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> stories) {
        Log.e(LOG_TAG, ": onLoadFinished");
        mAdapter.clear();
        if (stories != null && !stories.isEmpty()) {
            mAdapter.addAll(stories);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {
        Log.e(LOG_TAG, ": onLoadReset");
        mAdapter.clear();
    }
}
