package com.codepath.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by jansonlau on 8/2/16.
 */
public class MovieDetails extends AppCompatActivity {
    ImageView ivMovieImage;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbRating;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        String posterPath = getIntent().getStringExtra("posterPath");
        String title = getIntent().getStringExtra("originalTitle");
        Float rating = (float) getIntent().getDoubleExtra("rating", 0);
        String overview = getIntent().getStringExtra("overview");

        tvTitle.setText(title);
        tvOverview.setText(overview);
        rbRating.setRating(rating/2); // Divide by 2 because vote_average is given out of 10 while there are 5 stars for rating

        // load posterPath with current object
        Picasso.with(this).load(posterPath).fit().centerCrop()
                .placeholder(R.drawable.ic_launcher) // placeholder with launcher icon when poster is loading
                .error(R.drawable.ic_launcher)
                .into(ivMovieImage);
    }

    public void onSubmit(View v) {
        Intent data = new Intent();
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        // closes the activity and returns to first screen
        this.finish();
    }
}
