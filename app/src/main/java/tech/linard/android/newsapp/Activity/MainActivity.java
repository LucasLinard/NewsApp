package tech.linard.android.newsapp.Activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import tech.linard.android.newsapp.Model.Section;
import tech.linard.android.newsapp.R;
import tech.linard.android.newsapp.Util.SectionLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Section>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String SECTION_REQUEST_URL
            = "http://content.guardianapis.com/sections?&api-key=065324a3-0dff-454a-a25a-28a07cca5dda&format=json";
    private static final int SECTION_LOADER_ID = 1;
    private ArrayList<Section> sections;
    private SectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView sectionsListView = (ListView) findViewById(R.id.list_sections);
        mAdapter = new SectionAdapter(this, new ArrayList<Section>());
        sectionsListView.setAdapter(mAdapter);
        sectionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Section currentSection = mAdapter.getItem(i);
                // SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                // SharedPreferences.Editor editor = sharedPref.edit();
                // editor.putBoolean("SECTION_SAVED", true);
                // editor.putString(getString(R.string.saved_section), currentSection.getId());
                // editor.commit();
                // startActivity(new Intent(getApplicationContext(), StoriesActivity.class));
            }
        });
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(SECTION_LOADER_ID, null, this);

    }

    @Override
    public Loader<List<Section>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, ": onCreateLoader");
        return new SectionLoader(this, SECTION_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Section>> loader, List<Section> sections) {
        Log.e(LOG_TAG, ": onLoadFinished");
        mAdapter.clear();
        if (sections != null && !sections.isEmpty()) {
            mAdapter.addAll(sections);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Section>> loader) {
        Log.e(LOG_TAG, ": onLoadReset");
        mAdapter.clear();
    }
}
