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
 * Created by Marcin Węgrzyn on 04.03.2018.
 * wireamg@gmail.com
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<Review> reviewList;
    private final Context context;

    ReviewsAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review,parent,false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        holder.author.setText(reviewList.get(position).getAuthor());
        holder.content.setText(reviewList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        if(reviewList == null)return 0;
        return reviewList.size();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {

        final TextView author;
        final TextView content;

        ReviewsViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.item_review_author_tv);
            content = itemView.findViewById(R.id.item_review_content_tv);

        }
    }
    public void setData(List<Review> reviewList){
        this.reviewList = reviewList;
    }
}
