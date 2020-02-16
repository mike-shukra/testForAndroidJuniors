package ru.yogago.testforandroidjuniors.jokes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiIcndbCom {
    @GET("/jokes/random/{count}")
    Call<JokesModel> getData(@Path("count")int count);
}
