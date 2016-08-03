// 1. Use android-async-http to handle the entire process of sending and parsing network requests

package com.codepath.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.flickster.adapters.MovieArrayAdapter;
import com.codepath.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter; // declare movie array adapter
    @BindView(R.id.lvMovies) ListView lvItems; // Eliminate findViewById calls by using @BindView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        int orientation = getResources().getConfiguration().orientation;
        ButterKnife.bind(this);

        movies = new ArrayList<>(); // initialize movies
        movieAdapter = new MovieArrayAdapter(this, movies, orientation); // instantiate movie adapter with list of movies
        lvItems.setAdapter(movieAdapter); // set list view Adapter with movie Adapater
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        // Use android-async-http to handle the entire process of sending and parsing network requests
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults)); // add results into movies
                    movieAdapter.notifyDataSetChanged(); // notify data changed
                    Log.d("DEBUG", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        // Add a click listener to look for clicks to launch detail view
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movieSelected = movies.get(i); // get movie selected from array
                launchDetailView(movieSelected);
            }
        });
    }

    protected void launchDetailView(Movie movie) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MovieActivity.this, MovieDetails.class);

        // PASS IN PARAMETERS
        i.putExtra("rating", movie.getMovieRating());
        i.putExtra("originalTitle", movie.getOriginalTitle());
        i.putExtra("overview", movie.getOverview());
        i.putExtra("posterPath", movie.getPosterPath());
        i.putExtra("releaseDate", movie.getReleaseDate());

        // START NEW WINDOW
        startActivity(i);
    }
}
