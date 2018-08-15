package com.cab.user.helper;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.android.gms.maps.model.JointType.ROUND;

/**
 * Created by MTAJ-11 on 9/25/2017.
 */

public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>>{

    GoogleMap map;
    Context mcontext;
    PolylineOptions lineOptions = null;

    public ParserTask(GoogleMap map, Context context) {

        this.map= map;
        this.mcontext = context;
    }



    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            DirectionsJSONParser parser = new DirectionsJSONParser();

            // Starts parsing data
            routes = parser.parse(jObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points = null;




        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            // Adding all the points in the route to LineOptions
//            lineOptions.addAll(points);
//            lineOptions.width(5);
//            lineOptions.color(Color.BLUE);


            lineOptions.addAll(points);
            lineOptions.width(10);
            lineOptions.color(Color.parseColor("#2196F3"));
            lineOptions.geodesic(true);
            lineOptions.startCap(new SquareCap());
            lineOptions.endCap(new SquareCap());
            lineOptions.jointType(ROUND);



//            lineOptions.addAll(polyLineList);
//            greyPolyLine = mMap.addPolyline(lineOptions);
        }

  /*     LatLng start = new LatLng(18.015365, -77.499382);
        LatLng  waypoint= new LatLng(18.01455, -77.499333);
        LatLng  end = new LatLng(18.012590, -77.500659);


        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.WALKING)
                .withListener(this)
                .waypoints(start, waypoint, end)
                .build();
        routing.execute();*/

        // Drawing polyline in the Google Map for the i-th route

        if(lineOptions!=null){
            if(map!= null){
        map.addPolyline(lineOptions);
    }}}

//    @Override
//    public void onRoutingFailure() {
//
//    }
//
//    @Override
//    public void onRoutingStart() {
//
//    }
//
//    @Override
//    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {
//
//    }
//
//    @Override
//    public void onRoutingCancelled() {
//
//    }

}
