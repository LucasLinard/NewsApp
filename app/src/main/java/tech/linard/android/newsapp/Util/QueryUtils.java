package tech.linard.android.newsapp.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.EventLog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import tech.linard.android.newsapp.Model.Section;
import tech.linard.android.newsapp.Model.Story;

/**
 * Created by lucas on 30/11/16.
 */
import static tech.linard.android.newsapp.Activity.MainActivity.LOG_TAG;

public final class QueryUtils {
    private QueryUtils() {
    }

    public static List<Section> fetchSectionData(String mUrl) {
        URL url = createUrl(mUrl);
        String jsonResponse =null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e ) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return extractSections(jsonResponse);
    }

    public static List<Story> fetchStoryData(String mUrl) {
        URL url = createUrl(mUrl);
        String jsonResponse =null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e ) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return extractStories(jsonResponse);
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        Log.e(LOG_TAG, ": httprequest");
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.e(LOG_TAG, ": read");
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Section> extractSections(String jsonResponse) {
        ArrayList<Section> sections = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject response = jsonObject.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject currentJSON = results.getJSONObject(i);
                String id = currentJSON.optString("id");
                String webTitle = currentJSON.optString("webTitle");
                String webUrl = currentJSON.optString("webUrl");
                String apiUrl = currentJSON.optString("apiUrl");

                Section currentSection = new Section(id, webTitle, webUrl, apiUrl);
                sections.add(currentSection);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return sections;
    }
    private static List<Story> extractStories(String jsonResponse) {
        ArrayList<Story> stories = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject response = jsonObject.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject currentJSON = results.getJSONObject(i);
                String id = currentJSON.optString("id");
                String type = currentJSON.optString("type");
                String sectionId = currentJSON.optString("sectionId");
                String sectionName = currentJSON.optString("sectionName");
                String webPublicationDate = currentJSON.optString("webPublicationDate");
                String webTitle = currentJSON.optString("webTitle");
                String webUrl = currentJSON.optString("webUrl");
                String apiUrl = currentJSON.optString("apiUrl");

                Story currentStory = new Story(id, type, sectionId,  sectionName,
                        webPublicationDate, webTitle, webUrl,  apiUrl);

                stories.add(currentStory);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return stories;
    }
}
