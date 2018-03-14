package com.wegrzyn.marcin.popularmoviesst1;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

    private final ListItemClickListener mItemClickListener;

    public interface ListItemClickListener{
        void onListItemClick(int clickItem);
    }


    TrailersAdapter(List<Trailer> trailerList, Context context, ListItemClickListener mItemClickListener) {
        this.trailerList = trailerList;
        this.context = context;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public TrailersAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trailer,parent,false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.title.setText(trailerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (trailerList == null) return 0;
        else return trailerList.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView title;

        TrailerViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_trailer_title_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
    public void setData(List<Trailer> trailerList){
        this.trailerList = trailerList;
    }
}
