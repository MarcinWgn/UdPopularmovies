package com.wegrzyn.marcin.popularmoviesst1;

/**
 * Created by Marcin Węgrzyn on 20.02.2018.
 * wireamg@gmail.com
 */

 class Movie {

    private String title;
    private String releaseDate;
    private String posterLocalization;
    private String voteAverage;
    private String plotSynopsis;

     Movie(String title, String releaseDate, String posterLocalization, String voteAverage, String plotSynopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterLocalization = posterLocalization;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

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
}
