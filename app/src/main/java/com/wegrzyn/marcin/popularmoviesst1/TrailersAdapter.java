package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 03.03.2018.
 * wireamg@gmail.com
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>{

    private List<Trailer> trailerList;
    private final Context context;


    TrailersAdapter(List<Trailer> trailerList, Context context) {
        this.trailerList = trailerList;
        this.context = context;

    }

    @Override
    public TrailersAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.item_trailer,parent,false);

        Log.d(NetworkUtils.TAG,"################################################################");
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.title.setText(trailerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (trailerList == null) return 0;
        else return trailerList.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TrailerViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_trailer_title_tv);

        }
    }
    public void setData(List<Trailer> trailerList){
        this.trailerList = trailerList;
    }
}
