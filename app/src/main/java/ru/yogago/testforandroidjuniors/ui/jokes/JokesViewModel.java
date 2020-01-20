package ru.yogago.testforandroidjuniors.ui.jokes;

import android.util.Log;
import java.util.ArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JokesViewModel extends ViewModel {

    private final String LOG_TAG = "myLog";
    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> mTextCountJokes;
    private MutableLiveData<ArrayList<String>> mJokes;
    private BackgroundRestApiAction backgroundRestApiAction;

    public JokesViewModel() {
        mText = new MutableLiveData<>();
        mTextCountJokes = new MutableLiveData<>();
        mJokes = new MutableLiveData<>();
        mText.setValue("Введите количество");
    }

    void loadContent(int countStr){
        this.backgroundRestApiAction = new BackgroundRestApiAction(countStr);
        this.backgroundRestApiAction.setJokesViewModel(this);
        this.backgroundRestApiAction.execute();
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