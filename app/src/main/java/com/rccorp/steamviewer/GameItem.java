package com.rccorp.steamviewer;

/**
 * Created by Rutwik on 16/03/18.
 */

public class GameItem {


    private String Name;
    private String Thumb;
    private String Desc;
    private String Score;
    private String Players;
    private String Appid;

    public GameItem(String gameName, String gameThumb,String shortDesc,String metaScore,String currPlayers,String appid) {
        Name = gameName;
        Thumb = gameThumb;
        Desc=shortDesc;
        Score=metaScore;
        Players=currPlayers;
        Appid=appid;
    }

    public String getGameName() {
        return Name;
    }

    public String getGameThumb() {
        return Thumb;
    }

    public String getDesc() {
        return Desc;
    }

    public String getScore() {
        return Score;
    }

    public String getPlayers() {
        return Players;
    }

    public String getAppid() {
        return Appid;
    }
}
