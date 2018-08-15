package com.cab.user.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cab.user.ui.MainActivity;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by MTAJ-11 on 9/25/2017.
 */

public class ReadTask extends AsyncTask<String, Void, String> {

    GoogleMap map;
    Context mcontext;

    public ReadTask(GoogleMap map, Context context) {

        this.map = map;
        this.mcontext = context;
    }

    @Override
    protected String doInBackground(String... url) {
        // TODO Auto-generated method stub
        String data = "";
        try {
            MapHttpConnection http = new MapHttpConnection();
            data = http.readUr(url[0]);


        } catch (Exception e) {
            // TODO: handle exception
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        new ParserTask(map,mcontext).execute(result);
    }

}
