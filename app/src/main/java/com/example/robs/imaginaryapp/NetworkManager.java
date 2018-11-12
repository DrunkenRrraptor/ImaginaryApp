package com.example.robs.imaginaryapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robs on 12.11.18.
 */

public class NetworkManager {

    // class to handle JSONRequests

    private static String TAG = "NetworkManager";
    private static NetworkManager instance = null;

    private RequestQueue requestQueue;

    String api_all_tours = "http://api.foxtur.com/v1/tours/";
    String api_top_five_tours = "http://api.foxtur.com/v1/tours/top5/";
    String api_tour_detail_append_id_and_slash = "http://api.foxtur.com/v1/tours/";
    String api_contact_company = "http://api.foxtur.com/v1/contact/";
    String api_tour_id_1_get_800x600 = "http://api.foxtur.com/v1/tours/1?w=800&amp;h=600";
    String api_url_prefix = "http://api.foxtur.com/v1/";

    public NetworkManager(Context context) {

        requestQueue = Volley.newRequestQueue(context.getApplicationContext());

    }

    public static synchronized NetworkManager getInstance(Context context)
    {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    public static synchronized NetworkManager getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void somePostRequestReturningString(Object param1, final CustomListener<String> listener)
    {

        String url = api_all_tours;

        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("param1", param1);

        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + ": ", "somePostRequest Response : " + response.toString());
                        if(null != response.toString())
                            listener.getResult(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (null != error.networkResponse)
                        {
                            Log.d(TAG + ": ", "Error Response code: " + error.networkResponse.statusCode);
                            //listener.getResult(false);
                        }
                    }
                });

        requestQueue.add(request);
    }


    /*public interface CustomListener<T>
    {
        public void getResult(T object);
    }*/




    public interface CustomListener<T>
    {
        public void getResult(T object);
    }

}
