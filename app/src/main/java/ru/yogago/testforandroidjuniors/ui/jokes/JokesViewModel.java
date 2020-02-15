package ru.yogago.testforandroidjuniors.ui.jokes;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokesViewModel extends ViewModel {

    private final String LOG_TAG = "myLog";
    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> mTextCountJokes;
    private MutableLiveData<ArrayList<String>> mJokes;

    private static ApiIcndbCom apiIcndbCom;
    private Retrofit retrofit;

    public JokesViewModel() {
        mText = new MutableLiveData<>();
        mTextCountJokes = new MutableLiveData<>();
        mJokes = new MutableLiveData<>();
        mText.setValue("Введите количество");
    }

    void loadContent(final int countStr, final JokesViewModel jokesViewModel){
        Log.d(LOG_TAG, "JokesViewModel - loadContent: " + this.hashCode());

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.icndb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiIcndbCom = retrofit.create(ApiIcndbCom.class);
        apiIcndbCom.getData(countStr).enqueue(new Callback<JokesModel>() {
            @Override
            public void onResponse(Call<JokesModel> call, Response<JokesModel> response) {
                Log.d(LOG_TAG, "JokesViewModel - loadContent - onResponse: Данные успешно пришли, но надо проверить response.body() на null " + this.hashCode());
                assert response.body() != null;
                List<ValueModel> listValue = response.body().getValue();
                ArrayList<String> jokes = new ArrayList<>();
                for (ValueModel value : listValue) {
                    jokes.add(value.getJoke());
                }
                jokesViewModel.setContentToView((ArrayList<String>) jokes);
            }
            @Override
            public void onFailure(Call<JokesModel> call, Throwable t) {
                Log.d(LOG_TAG, "Error - onFailure: " + t);
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