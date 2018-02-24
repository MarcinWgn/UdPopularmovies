package com.wegrzyn.marcin.popularmoviesst1;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Marcin Węgrzyn on 18.02.2018.
 * wireamg@gmail.com
 */

class NetworkUtils {

    static final String TAG = NetworkUtils.class.getSimpleName();
    static final String POPULAR_QUERY = "popular";
    static final String TOP_RATED_QUERY = "top_rated";
    static final int TARGET_WIDTH = 400;
    static final int TARGET_HEIGHT = 600;
    private static final String SCHEME = "http";
    private static final String IMAGE_AUTHORITY = "image.tmdb.org";
    private static final String IMAGE_PATH = "t/p";
    private static final String IMAGE_SIZE_185 = "w185";
    private static final String IMAGE_SIZE_342 = "w342";
    private static final String TITLE = "title";
    private static final String RELEASE_DATE = "release_date";
    private static final String POSTER_PATH = "poster_path";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String AUTHORITY = "api.themoviedb.org";
    private static final String PATH = "3/movie";
    private static final String KEY_LABEL = "api_key";

    // TODO: 24.02.2018 API Key
    private static final String API_KEY = "***********************************";


    static List<Movie> moviesList = new ArrayList<>();

    static Uri getImageUri(String imageId) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME);
        builder.authority(IMAGE_AUTHORITY);
        builder.path(IMAGE_PATH);
        builder.appendPath(IMAGE_SIZE_185);
        builder.appendEncodedPath(imageId);
        Log.d(TAG, builder.toString());
        return builder.build();

    }

    static String queryUrl(String query) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME);
        builder.authority(AUTHORITY);
        builder.path(PATH);
        builder.appendPath(query);
        builder.appendQueryParameter(KEY_LABEL, API_KEY);

        Log.d(TAG, builder.toString());

        URL urlQuery = createURL(builder.toString());
        try {
            return getResponseFromUrl(urlQuery);
        } catch (IOException e) {
            Log.e(TAG, "Error url response", e);
        }
        return null;
    }

    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error url creation ", e);
        }
        return url;
    }

    private static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }

    static List<Movie> parseResultsJson(String jsonString) {
        if (jsonString != null && !jsonString.isEmpty()) {
            try {

                JSONObject sandwichJSONObject = new JSONObject(jsonString);

                if (sandwichJSONObject.has("results")) {

                    JSONArray results = sandwichJSONObject.getJSONArray("results");
                    return jsonArrayToList(results);

                } else return null;
            } catch (JSONException e) {
                Log.e(TAG, "Error parse", e.getCause());
            }
        }
        return null;
    }

    private static List<Movie> jsonArrayToList(JSONArray array) {

        int length = array.length();
        List<Movie> moviesList = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            try {
                String title = array.getJSONObject(i).getString(TITLE);
                String releaseDate = array.getJSONObject(i).getString(RELEASE_DATE);
                String posterLocalization = array.getJSONObject(i).getString(POSTER_PATH);
                String voteAverage = array.getJSONObject(i).getString(VOTE_AVERAGE);
                String plotSynopsis = array.getJSONObject(i).getString(OVERVIEW);

                moviesList.add(new Movie(title, releaseDate, posterLocalization, voteAverage, plotSynopsis));

            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }
        return moviesList;
    }
}
