// Java file for detailed view
// Uses item_detail.xml

package com.codepath.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by jansonlau on 8/2/16.
 */
public class MovieDetails extends AppCompatActivity {
    // DECLARE EACH VIEW
    // Eliminate findViewById calls by using @BindView on fields for improved view lookups
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.ivMovieImage) ImageView ivMovieImage;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.rbRating) RatingBar rbRating;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail); // item_detail is XML file
        ButterKnife.bind(this); // improve view lookups by using Butterknife

        // OBTAIN PASSED IN VALUES
        String originalTitle = getIntent().getStringExtra("originalTitle");
        String posterPath    = getIntent().getStringExtra("posterPath");
        Double rating        = getIntent().getDoubleExtra("rating", 0);
        String overview      = getIntent().getStringExtra("overview");
        String releaseDate   = getIntent().getStringExtra("releaseDate");

        // SET TEXT/RATING FOR EACH VIEW WITH PASSED IN VALUES
        tvTitle.setText(originalTitle);
        tvOverview.setText(overview);
        tvReleaseDate.setText(releaseDate);
        rbRating.setRating( (float)(rating/2) ); // Divide by 2 because vote_average is given out of 10 while there are 5 stars for rating

        // load posterPath with current object
        Picasso.with(this).load(posterPath)
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.ic_launcher) // placeholder with launcher icon when poster is loading
                .error(R.drawable.ic_launcher)
                .into(ivMovieImage);
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
