package ru.yogago.testforandroidjuniors.ui.jokes;

import java.util.List;

class Value {
    private long id;
    private String joke;
    private List<String> categories;
    Value(long id, String joke, List<String> categories) {
        this.id = id;
        this.joke = joke;
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    String getJoke() {
        return joke;
    }

    public List<String> getCategories() {
        return categories;
    }
}
