// 2. Parse network response (turn response into Movie and store into Movie class)

package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jansonlau on 7/30/16.
 */
public class Movie  {
    public double getMovieRating() {
        return rating;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    Double rating;
    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;

    public Movie(JSONObject jsonObject) throws JSONException { // parse JSONObject
        this.rating = jsonObject.getDouble("vote_average");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>(); // create array to add in Movies

        // iterate through each element in the array
        for (int x = 0; x < array.length(); x++) {
            // convert each JSON element into a movie
            try {
                results.add(new Movie(array.getJSONObject(x)));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
