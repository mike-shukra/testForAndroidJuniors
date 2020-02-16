package ru.yogago.testforandroidjuniors.jokes;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JokesViewModel extends ViewModel {

    private final String LOG_TAG = "myLog";
    private Model model;
    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> mTextCountJokes;
    private MutableLiveData<ArrayList<String>> mJokes;

    public JokesViewModel() {
        this.model = new Model(this);
        this.mText = new MutableLiveData<>();
        this.mTextCountJokes = new MutableLiveData<>();
        this.mJokes = new MutableLiveData<>();
        this.mText.setValue("Введите количество");
    }

    void setContent(final int countStr){
        this.model.loadContent(countStr);
    }

    void setContentToView(ArrayList<String> arr){
        mJokes.setValue(arr);
    }

    LiveData<String> getText() {
        return mText;
    }

    MutableLiveData<Integer> getTextCountJokes() {
        return mTextCountJokes;
    }

    MutableLiveData<ArrayList<String>> getJokes() {
        return mJokes;
    }

}