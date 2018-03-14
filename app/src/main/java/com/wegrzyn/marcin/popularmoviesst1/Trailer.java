package com.wegrzyn.marcin.popularmoviesst1;

/**
 * Created by Marcin Węgrzyn on 28.02.2018.
 * wireamg@gmail.com
 */

class Trailer {

    private final String name;
    private final String key;

    Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    String getKey() {
        return key;
    }
}
