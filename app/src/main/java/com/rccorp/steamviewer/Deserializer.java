package com.rccorp.steamviewer;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rutwik on 16/03/18.
 */

public class Deserializer implements JsonDeserializer {

    String currappid;
    int source;

    public Deserializer(String appid,int src){
        currappid=appid;
        source=src;
    }

    @Override
    public Game deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        List<Game> mGameList=new ArrayList<>();

        Game mGame=new Game();

        JsonObject idobject=json.getAsJsonObject();

        if (source==0){

            JsonObject outerobject=idobject.getAsJsonObject();

            JsonObject innerobject=outerobject.getAsJsonObject(currappid);

            JsonObject dataobject=innerobject.getAsJsonObject("data");

            JsonObject metascore=dataobject.getAsJsonObject("metacritic");

            mGame.setName(dataobject.get("name").getAsString());
            mGame.setThumbnail(dataobject.get("header_image").getAsString());
            mGame.setDescription(dataobject.get("short_description").getAsString());
            mGame.setScore(metascore.get("score").getAsString());
            mGame.setAppid(dataobject.get("steam_appid").getAsString());

            return mGame;
//            mGameList.add(mGame);

        }else {
            JsonObject dataobject=idobject.getAsJsonObject("data");

            JsonArray valuesobject=dataobject.getAsJsonArray("values_twitch");

            String value=valuesobject.getAsString();

            mGame.setPlayers(value);
        }

        return mGame;
    }
}
