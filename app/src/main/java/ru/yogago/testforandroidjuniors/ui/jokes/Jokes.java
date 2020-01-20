package ru.yogago.testforandroidjuniors.ui.jokes;

import java.util.List;

public class Jokes {
    private String type;
    private List<Value> values;

    Jokes(String type, List<Value> values) {
        this.type = type;
        this.values = values;
    }
    public String getType() {
        return type;
    }

    List<Value> getValues() {
        return values;
    }
}
