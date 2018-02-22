package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 21.02.2018.
 * wireamg@gmail.com
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{

    private List<Movie> listMovie;
    private Context context;

     MoviesAdapter(Context context, List<Movie> listMovie) {

        this.listMovie = listMovie;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);

        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {

                Picasso.with(context)
                .load(NetworkUtils.getImageUri(listMovie.get(position).getPosterLocalization()))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

     class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        MoviesViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.poster_IW);

        }
    }
}
