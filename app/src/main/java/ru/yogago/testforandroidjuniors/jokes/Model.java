package ru.yogago.testforandroidjuniors.jokes;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model {

    private final String LOG_TAG = "myLog";
    private JokesViewModel jokesViewModel;
    private ArrayList<String> jokes;

    Model(JokesViewModel jokesViewModel) {
        this.jokesViewModel = jokesViewModel;
    }

    void loadContent(final int countStr){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.icndb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiIcndbCom apiIcndbCom = retrofit.create(ApiIcndbCom.class);
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

}
