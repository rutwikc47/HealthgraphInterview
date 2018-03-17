package com.rccorp.steamviewer.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rccorp.steamviewer.Game;
import com.rccorp.steamviewer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    ImageView detthumb;
    TextView detname;
    TextView detdesc;
    TextView detscore;
    TextView detplayers;
    String week;
    String appid;
    String players;
    String BASEURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent locIntent=getIntent();

        week="concurrent_week";

        detthumb=(ImageView)findViewById(R.id.thumbdet_imgv);
        detname=(TextView)findViewById(R.id.namedet_tv);
        detdesc=(TextView)findViewById(R.id.descdet_tv);
        detscore=(TextView)findViewById(R.id.metadet_tv);
        detplayers=(TextView)findViewById(R.id.currplaydet_tv);

        String name=locIntent.getStringExtra("Name");
        String thumb=locIntent.getStringExtra("Thumb");
        String desc=locIntent.getStringExtra("Desc");
        String score=locIntent.getStringExtra("Score");
        appid=locIntent.getStringExtra("Appid");

        BASEURL = "https://steamdb.info/api/GetGraph/?type=concurrent_week&appid="+appid;

        checkConnection();

        Glide.with(DetailActivity.this).load(thumb).into(detthumb);
        detname.setText(name);
        detdesc.setText(getResources().getString(R.string.desc)+desc);
        detscore.setText(getResources().getString(R.string.meta)+""+score);

    }

    private class PlayerTask extends AsyncTask<Game,Void,Game>{


        @Override
        protected void onPostExecute(Game aVoid) {
            super.onPostExecute(aVoid);
            detplayers.setText("Active Players:- "+players);

        }

        @Override
        protected Game doInBackground(Game... games) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String playerjson = null;

            try {
                URL url=new URL(BASEURL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                playerjson = buffer.toString();
            } catch (IOException e) {
                // If the code didn't successfully get the Movies data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }
            try {
                return getPlayerDatafromJson(playerjson);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    public Game getPlayerDatafromJson(String playersjson)
            throws JSONException {

        JSONObject idobj=new JSONObject(playersjson);
        JSONObject dataobj=idobj.getJSONObject("data");

        JSONArray valuesarray=dataobj.getJSONArray("values_twitch");

        String playerval=valuesarray.getString(0);


        Game mGame=new Game();
        mGame.setPlayers(playerval);
        players=playerval;
        return mGame;

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
            new PlayerTask().execute();
        }else{
            Toast.makeText(DetailActivity.this, "No Internet Connection-Cannot Fetch Active Players", Toast.LENGTH_SHORT).show();
        }
    }

}
