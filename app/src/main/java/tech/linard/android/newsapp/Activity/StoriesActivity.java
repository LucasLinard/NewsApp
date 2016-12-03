package tech.linard.android.newsapp.Activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    public  String BASE_STORY_REQUEST_URL = "http://content.guardianapis.com/search?";

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
    protected void onResume() {
        super.onResume();
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(STORY_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Story>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, ": onCreateLoader");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sectionFilter = sharedPrefs.getString(
                getString(R.string.settings_filter_by_key), getString(R.string.settings_filter_by_default));
        Uri baseUri = Uri.parse(BASE_STORY_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("api-key", "065324a3-0dff-454a-a25a-28a07cca5dda");
        uriBuilder.appendQueryParameter("section", sectionFilter);
        String STORY_REQUEST_URL = uriBuilder.toString();

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
