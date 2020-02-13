package ru.yogago.testforandroidjuniors.ui.jokes;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class JokesViewModel extends ViewModel {

    private final String LOG_TAG = "myLog";
    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> mTextCountJokes;
    private MutableLiveData<ArrayList<String>> mJokes;

    public JokesViewModel() {
        mText = new MutableLiveData<>();
        mTextCountJokes = new MutableLiveData<>();
        mJokes = new MutableLiveData<>();
        mText.setValue("Введите количество");
    }

    void loadContent(final int countStr, final JokesViewModel jokesViewModel){
        Log.d(LOG_TAG, "JokesViewModel - loadContent: " + this.hashCode());
        Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() {
                return new BackgroundRestApiAction(countStr).doAction();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object result) throws Exception {
                        //Use result for something
                        jokesViewModel.setContentToView((ArrayList<String>) result);
                    }
                });
    }

    private void setContentToView(ArrayList<String> arr){
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