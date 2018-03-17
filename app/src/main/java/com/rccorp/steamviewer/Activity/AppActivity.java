package com.rccorp.steamviewer.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rccorp.steamviewer.Deserializer;
import com.rccorp.steamviewer.Game;
import com.rccorp.steamviewer.GameAdapter;
import com.rccorp.steamviewer.GameApi;
import com.rccorp.steamviewer.GameItem;
import com.rccorp.steamviewer.R;
import com.rccorp.steamviewer.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    String [] appidlist;

    GameAdapter mGameAdapter;

    List<Game> gameList=new ArrayList<>();

    Game currGame;

    GameItem currGameItem;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        appidlist=getResources().getStringArray(R.array.appids);

        mRecyclerView = (RecyclerView) findViewById(R.id.game_recycler_view);

        mGameAdapter=new GameAdapter();

        currGame=new Game();

        checkConnection();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currGameItem=mGameAdapter.getGameDetails(position);
                Intent mIntent = new Intent(AppActivity.this, DetailActivity.class);
                mIntent.putExtra("Name",currGameItem.getGameName());
                mIntent.putExtra("Thumb",currGameItem.getGameThumb());
                mIntent.putExtra("Desc",currGameItem.getDesc());
                mIntent.putExtra("Score",currGameItem.getScore());
                mIntent.putExtra("Appid",currGameItem.getAppid());
                startActivity(mIntent);
            }
        }));

    }


    public static final String BASE_URL = "http://store.steampowered.com/";

    private static GsonConverterFactory buildGsonConvertor(String appid){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class,new Deserializer(appid,0));
        Gson mygson=gsonBuilder.create();

        return GsonConverterFactory.create(mygson);
    }

    public void getGameData(String[] appid) {

        for (String anAppidlist : appid) {
            Retrofit adapter = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(buildGsonConvertor(anAppidlist))
                    .build();

            GameApi api = adapter.create(GameApi.class);

            Call<Game> call = api.getGameData(anAppidlist);

            call.enqueue(new Callback<Game>() {
                @Override
                public void onResponse(Call<Game> call, Response<Game> response) {
                    Log.e("MainAct", "body" + response.body().getName());
                    mGameAdapter.addCardItem(new GameItem(response.body().getName(), response.body().getThumbnail(), response.body().getDescription(), response.body().getScore(), "-", response.body().getAppid()));
                    mRecyclerView.setAdapter(mGameAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(AppActivity.this));

                }

                @Override
                public void onFailure(Call<Game> call, Throwable t) {

                }


            });
        }

    }

        protected boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        }
        public void checkConnection(){
            if(isOnline()){
                getGameData(appidlist);
            }else{
                Toast.makeText(AppActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }

}
