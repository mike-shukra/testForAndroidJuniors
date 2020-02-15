package ru.yogago.testforandroidjuniors.ui.jokes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JokesModel {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private List<ValueModel> value = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ValueModel> getValue() {
        return value;
    }

    public void setValue(List<ValueModel> value) {
        this.value = value;
    }

}
