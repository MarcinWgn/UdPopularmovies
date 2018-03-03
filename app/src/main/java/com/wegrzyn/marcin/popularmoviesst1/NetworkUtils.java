package com.wegrzyn.marcin.popularmoviesst1;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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

    private static final String REVIEWS_QUERY = "reviews";
    private static final String TRAILERS_QUERY = "videos";

    static final int TARGET_WIDTH = 400;
    static final int TARGET_HEIGHT = 600;

    private static final String SCHEME = "http";
    private static final String IMAGE_AUTHORITY = "image.tmdb.org";
    private static final String IMAGE_PATH = "t/p";
    private static final String IMAGE_SIZE_185 = "w185";
    private static final String IMAGE_SIZE_342 = "w342";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String RELEASE_DATE = "release_date";
    private static final String POSTER_PATH = "poster_path";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String AUTHORITY = "api.themoviedb.org";
    private static final String PATH = "3/movie";
    private static final String KEY_LABEL = "api_key";
    private static final String RESULTS = "results";
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

    // TODO: 24.02.2018 API Key
    private static final String API_KEY = BuildConfig.API_KEY;



    static Uri getImageUri(String imageId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME);
        builder.authority(IMAGE_AUTHORITY);
        builder.path(IMAGE_PATH);
        builder.appendPath(IMAGE_SIZE_185);
        builder.appendEncodedPath(imageId);
        return builder.build();

    }

    private static String queryMoviesUrl(String query) {
        Uri.Builder builder = getBaseUrlBuilder();
        builder.appendPath(query);
        builder.appendQueryParameter(KEY_LABEL, API_KEY);
        return getStringFromUrl(builder);
    }

    @NonNull
    private static Uri.Builder getBaseUrlBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME);
        builder.authority(AUTHORITY);
        builder.path(PATH);
        return builder;
    }

    private static String queryReviewsUrl(String id){
        Uri.Builder builder = getBaseUrlBuilder();
        builder.appendPath(id);
        builder.appendPath(REVIEWS_QUERY);
        builder.appendQueryParameter(KEY_LABEL, API_KEY);
        return getStringFromUrl(builder);
    }

    private static String queryTrailersUrl(String id){
        Uri.Builder builder = getBaseUrlBuilder();
        builder.appendPath(id);
        builder.appendPath(TRAILERS_QUERY);
        builder.appendQueryParameter(KEY_LABEL, API_KEY);
        return getStringFromUrl(builder);
    }

    @Nullable
    private static String getStringFromUrl(Uri.Builder builder) {
        URL urlQuery = createURL(builder.toString());
        try {
            return getResponseFromUrl(urlQuery);
        } catch (IOException e) {
            Log.e(TAG, "Error url response", e);
        }
        return null;
    }

    private static URL createURL(String stringUrl) {
        Log.d(TAG,stringUrl);
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

    private static JSONArray parseResults(String jsonString){

        if (jsonString != null && !jsonString.isEmpty()) {
            try {

                JSONObject sandwichJSONObject = new JSONObject(jsonString);

                if (sandwichJSONObject.has(RESULTS)) {
                    return sandwichJSONObject.getJSONArray(RESULTS);
                } else return null;
            } catch (JSONException e) {
                Log.e(TAG, "Error parse", e.getCause());
            }
        }
        return null;
    }

    private static List<Movie> jsonArrayMoviesToList(JSONArray array) {

        int length = array.length();
        List<Movie> moviesList = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            try {
                String id = array.getJSONObject(i).getString(ID);
                String title = array.getJSONObject(i).getString(TITLE);
                String releaseDate = array.getJSONObject(i).getString(RELEASE_DATE);
                String posterLocalization = array.getJSONObject(i).getString(POSTER_PATH);
                String voteAverage = array.getJSONObject(i).getString(VOTE_AVERAGE);
                String plotSynopsis = array.getJSONObject(i).getString(OVERVIEW);

                moviesList.add(new Movie(id, title, releaseDate, posterLocalization, voteAverage, plotSynopsis));

            } catch (JSONException e) {
                Log.e(TAG, "Error parse",e.getCause());
                return null;
            }
        }
        return moviesList;
    }

    private static List<Trailer> jsonArrayTrailerToList(JSONArray array){

        int length = array.length();
        List<Trailer> trailerList = new ArrayList<>(length);

        for (int i = 0; i<length ; i++){
            try {
                String key = array.getJSONObject(i).getString(KEY);
                String name = array.getJSONObject(i).getString(NAME);

                trailerList.add(new Trailer(name,key));
            } catch (JSONException e){
                Log.e(TAG, "Error parse",e.getCause());
                return null;
            }
        }
        return trailerList;
    }

    private static List<Review> jsonArrayReviewToList(JSONArray array){

        int length = array.length();
        List<Review> reviewList = new ArrayList<>(length);

        for (int i = 0; i<length ; i++){
            try {
                String author = array.getJSONObject(i).getString(AUTHOR);
                String content = array.getJSONObject(i).getString(CONTENT);

                reviewList.add(new Review(author,content));
            } catch (JSONException e){
                Log.e(TAG, "Error parse",e.getCause());
                return null;
            }
        }
        return reviewList;
    }

    static List<Movie> queryMovies(String queryType){
        String jsonString = queryMoviesUrl(queryType);
        JSONArray jsonArray = parseResults(jsonString);
        return jsonArrayMoviesToList(jsonArray);
    }

    static List<Trailer> gueryTrailer(String id){
        String jsonString = queryTrailersUrl(id);
        JSONArray jsonArray = parseResults(jsonString);
        return jsonArrayTrailerToList(jsonArray);
    }

    static List<Review> queryReview(String id){
        String jsonString = queryReviewsUrl(id);
        JSONArray jsonArray = parseResults(jsonString);
        return jsonArrayReviewToList(jsonArray);
    }

}
