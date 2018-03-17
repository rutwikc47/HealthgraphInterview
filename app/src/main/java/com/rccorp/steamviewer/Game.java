package com.rccorp.steamviewer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Rutwik on 16/03/18.
 */

public class Game implements Serializable{

    @SerializedName("name")
    @Expose
    private String Name;
    @SerializedName("header_image")
    @Expose
    private String thumbnail;
    @SerializedName("short_description")
    @Expose
    private String Description;
    @SerializedName("metacritic_score")
    @Expose
    private String Score;
    @SerializedName("current_players")
    @Expose
    private String Players;
    @SerializedName("steam_appid")
    @Expose
    private String Appid;

    public Game(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getPlayers() {
        return Players;
    }

    public void setPlayers(String players) {
        Players = players;
    }

    public String getAppid() {
        return Appid;
    }

    public void setAppid(String appid) {
        Appid = appid;
    }
}

