package com.wegrzyn.marcin.popularmoviesst1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marcin Węgrzyn on 20.02.2018.
 * wireamg@gmail.com
 */

class Movie implements Parcelable {

    private final String title;
    private final String releaseDate;
    private final String posterLocalization;
    private final String voteAverage;
    private final String plotSynopsis;

    Movie(String title, String releaseDate, String posterLocalization, String voteAverage, String plotSynopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterLocalization = posterLocalization;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        posterLocalization = in.readString();
        voteAverage = in.readString();
        plotSynopsis = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() { return releaseDate; }

    public String getPosterLocalization() {
        return posterLocalization;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(posterLocalization);
        dest.writeString(voteAverage);
        dest.writeString(plotSynopsis);
    }
}
