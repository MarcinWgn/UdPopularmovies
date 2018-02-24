package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
    final private ListItemClickListener itemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickItemIndex);
    }

     MoviesAdapter(Context context, List<Movie> listMovie, ListItemClickListener itemClickListener) {

        this.listMovie = listMovie;
        this.context = context;
        this.itemClickListener = itemClickListener;
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
                .resize(400,600)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

     class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        MoviesViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.poster_IW);
            view.setOnClickListener(this);
        }

         @Override
         public void onClick(View v) {
             itemClickListener.onListItemClick(getAdapterPosition());
         }
     }

     public void setData(List<Movie> listMovie){
        this.listMovie = listMovie;
     }
}
