package com.wegrzyn.marcin.popularmoviesst1;

/**
 * Created by Marcin Węgrzyn on 28.02.2018.
 * wireamg@gmail.com
 */

class Trailer {

    private String name;
    private String key;

    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
