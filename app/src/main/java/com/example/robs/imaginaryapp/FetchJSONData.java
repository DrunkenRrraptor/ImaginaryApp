package com.example.robs.imaginaryapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Robs on 12.11.18.
 */

public class FetchJSONData {

    public static final List<TourData> ITEMS = new ArrayList<>(  );
    public static final Map<String, TourData> ITEM_MAP = new HashMap<String, TourData>();

    String api_all_tours = "http://api.foxtur.com/v1/tours/";
    String api_top_five_tours = "http://api.foxtur.com/v1/tours/top5/";
    String api_tour_detail_append_id_and_slash = "http://api.foxtur.com/v1/tours/";
    String api_contact_company = "http://api.foxtur.com/v1/contact/";
    String api_tour_id_1_get_800x600 = "http://api.foxtur.com/v1/tours/1?w=800&amp;h=600";
    String api_url_prefix = "http://api.foxtur.com/v1/";

    static {

        // static data; should be retrieved from the api (-> TourListActivity)

        TourData example1 = new TourData( 1, "Rhino Tour", "See Rhinos in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/1/", R.drawable.park_logo_2_2_2_icon1_1, "http://lorempixel.com/400/200/animals/1/", "http://lorempixel.com/800/600/animals/1/", "2018-11-04T12:31:04+01:00", "2018-12-11T12:31:04+01:00", 10.3);

        TourData example2 = new TourData( 2, "Gorilla Tour", "See Gorillas in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/2/", R.drawable.park_logo_2_2_2_icon1_2, "http://lorempixel.com/400/200/animals/2/", "http://lorempixel.com/800/600/animals/2/", "2018-11-04T13:57:03+01:00", "2018-12-11T13:57:03+01:00", 20.6);

        TourData example3 = new TourData( 3, "Tiger Tour", "See Tigers in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/3/", R.drawable.park_logo_2_2_2_icon2_1, "http://lorempixel.com/400/200/animals/3/", "http://lorempixel.com/800/600/animals/3/", "2018-11-04T13:58:44+01:00", "2018-12-11T13:58:44+01:00", 30.9);

        TourData example4 = new TourData( 4, "Giraffe Tour", "See Giraffes in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/4/", R.drawable.park_logo_2_2_2_icon2_2, "http://lorempixel.com/400/200/animals/4/", "http://lorempixel.com/800/600/animals/4/", "2018-11-04T14:00:04+01:00", "2018-12-11T14:00:04+01:00", 41.2);

        TourData example5 = new TourData( 5, "Bambi Tour", "See Bambis in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/5/", R.drawable.park_logo_2_2_2_icon2_1, "http://lorempixel.com/400/200/animals/5/", "http://lorempixel.com/800/600/animals/5/", "2018-11-04T14:01:37+01:00", "2018-12-11T14:01:37+01:00", 51.5);

        TourData example6 = new TourData( 6, "Pig Tour", "See Pigs in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/6/", R.drawable.park_logo_2_2_2_icon2_1, "http://lorempixel.com/400/200/animals/6/", "http://lorempixel.com/800/600/animals/6/", "2018-11-02T12:42:41+01:00", "2018-12-09T12:42:41+01:00", 61.8);


        TourData example7 = new TourData( 7, "Cat Tour", "See Cat in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/7/", R.drawable.park_logo_2_2_2_icon1_1, "http://lorempixel.com/400/200/animals/7/", "http://lorempixel.com/800/600/animals/7/", "2018-11-02T12:42:41+01:00", "2018-12-09T12:42:41+01:00", 72.1);

        TourData example8 = new TourData( 8, "Cute Little Doggy Tour", "See Cute little Doggies in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/8/", R.drawable.park_logo_2_2_2_icon1_1, "http://lorempixel.com/400/200/animals/8/", "http://lorempixel.com/800/600/animals/8/", "2018-11-02T12:42:41+01:00", "2018-12-09T12:42:41+01:00", 82.4);

        TourData example9 = new TourData( 9, "Dog Tour", "See Dog in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/9/", R.drawable.park_logo_2_2_2_icon1_1, "http://lorempixel.com/400/200/animals/9/", "http://lorempixel.com/800/600/animals/9/", "2016-11-09T12:42:41+01:00", "2017-11-09T12:42:41+01:00", 92.7);

        addItem( example1 );
        addItem( example2 );
        addItem( example3 );
        addItem( example4 );
        addItem( example5 );
        addItem( example6 );
        addItem( example7 );
        addItem( example8 );
        addItem( example9 );

        /*NetworkManager.getInstance().somePostRequestReturningString(new Object(), new CustomListener<String>()
        {
            @Override
            public void getResult(String result)
            {
                if (!result.isEmpty())
                {
                    Log.e( "JSONRESULT", result );
                }
            }
        });*/

/*

        JsonObjectRequest jsonObjReq = new JsonObjectRequest( Request.Method.GET,
                "http://api.foxtur.com/v1/tours/", null,
                new Response.Listener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Success Callback
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Failure Callback
                    }
                })
        {

            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                //headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;
            }
        };

    */

    }

    /*public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }*/

    /*public void cancelAllRequests(String tag) {
        getRequestQueue().cancelAll(tag);
    }*/

    private static void addItem(TourData item) {
        ITEMS.add( item );
        ITEM_MAP.put( Integer.toString( item.getId() ), item );
    }

}
