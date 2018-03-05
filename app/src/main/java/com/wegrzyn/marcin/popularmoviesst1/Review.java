package com.wegrzyn.marcin.popularmoviesst1;

/**
 * Created by Marcin Węgrzyn on 28.02.2018.
 * wireamg@gmail.com
 */

class Review {

    private String author;
    private String content;

    Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
