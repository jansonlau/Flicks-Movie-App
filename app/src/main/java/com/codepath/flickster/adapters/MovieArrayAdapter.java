// 3. Use custom adapter to override getView to show items on phone

package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jansonlau on 7/31/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    private int orientation;

    private static class ViewHolder { // This optimizes scrolling the list by only requiring views to be looked up with findViewById when they first load.
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }

        public MovieArrayAdapter(Context context, List<Movie> movies, int orientation) {
        super(context, android.R.layout.simple_list_item_1, movies);
        this.orientation = orientation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get data for position
        Movie movie = getItem(position);
        ViewHolder viewHolder;

        // If view is not reused, inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder(); // If there's no view to re-use, inflate a brand new view for row
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);

            // lookup view to populate data
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.ivImage.setImageResource(0); // clear out image if reused

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle); // get reference to text view title
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview); // get reference to text view overview

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }
        else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // populate data
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        // show posters using Picasso (third-party library)
        if (orientation == Configuration.ORIENTATION_PORTRAIT) { // if orientation is portrait, load poster path
            Picasso.with(getContext()).load(movie.getPosterPath()).fit().centerCrop()
                    .placeholder(R.drawable.ic_launcher) // placeholder with launcher icon when poster is loading
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.ivImage);
        }
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE) { // if orientation is landscape, load backdrop path
            Picasso.with(getContext()).load(movie.getBackdropPath()).fit().centerCrop()
                    .placeholder(R.drawable.ic_launcher) // placeholder with launcher icon when poster is loading
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.ivImage);
        }

        return convertView; // return View
    }
}
