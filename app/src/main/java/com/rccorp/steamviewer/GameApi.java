package com.rccorp.steamviewer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rutwik on 16/03/18.
 */

public interface GameApi {


    @GET("/api/appdetails/")
    Call<Game> getGameData(@Query("appids")String appid);

    @GET("api/GetGraph/")
    Call<Game> getGamePlayers(@Query("type")String week, @Query("appid")String appid);

}
