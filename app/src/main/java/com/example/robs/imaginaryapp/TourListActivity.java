package com.example.robs.imaginaryapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.robs.imaginaryapp.FetchJSONData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of Tours. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TourDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TourListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RequestQueue requestQueue;
    private static TourListActivity mInstance;

    static List<TourData> item_list = new ArrayList<>(  );

    String api_all_tours = "http://api.foxtur.com/v1/tours/";
    String api_top_five_tours = "http://api.foxtur.com/v1/tours/top5/";
    String api_tour_detail_append_id_and_slash = "http://api.foxtur.com/v1/tours/";
    String api_contact_company = "http://api.foxtur.com/v1/contact/";
    String api_tour_id_1_get_800x600 = "http://api.foxtur.com/v1/tours/1?w=800&amp;h=600";
    String api_url_prefix = "http://api.foxtur.com/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tour_list );

        mInstance = this;
        requestQueue = Volley.newRequestQueue( this );

        // Tool- and AppBar Display of ListActivity

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        //toolbar.setLogo( R.drawable.park_logo_2_2_2_icon1_2_t );
        //toolbar.setNavigationIcon( R.drawable.park_logo_2_2_2_icon1_2_t );
        toolbar.setTitle( getTitle() );

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setLogo( R.drawable.park_logo_2_2_2_icon1_2_t );
        //actionBar.setDisplayUseLogoEnabled( true );
        //actionBar.setDisplayShowHomeEnabled( true );

        if (findViewById( R.id.tour_detail_container ) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById( R.id.tour_list );
        assert recyclerView != null;
        setupRecyclerView( (RecyclerView) recyclerView );

        fetchJSONData();

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //recyclerView.setAdapter( new SimpleItemRecyclerViewAdapter( this, FetchJSONData.ITEMS, mTwoPane ) );
        recyclerView.setAdapter( new SimpleItemRecyclerViewAdapter( this, item_list, mTwoPane ) );
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final TourListActivity mParentActivity;
        private final List<TourData> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {                            // differ what Element is clicked
                TourData item = (TourData) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();

                    // Should pass the list with all items to the fragment

                    //arguments.putParcelableArray( "arraylist", item_list );
                    //arguments.putParcelableArrayList(  );

                    arguments.putString( TourDetailFragment.ARG_ITEM_ID, Integer.toString(  item.getId() ) );

                    //arguments.putParcelableArray( "arraylist", item_list );

                    TourDetailFragment fragment = new TourDetailFragment();
                    fragment.setArguments( arguments );
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace( R.id.tour_detail_container, fragment )
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent( context, TourDetailActivity.class );
                    intent.putExtra( TourDetailFragment.ARG_ITEM_ID, Integer.toString( item.getId() ) );

                    context.startActivity( intent );

                    //view.findViewById( R.id.landscape_logo ).setVisibility( View.GONE );

                }
            }
        };

        SimpleItemRecyclerViewAdapter(TourListActivity parent,
                                      List<TourData> items,
                                      boolean twoPane) {

            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from( parent.getContext() )
                    .inflate( R.layout.tour_list_content, parent, false );
            return new ViewHolder( view );
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            // show values for all elements

            holder.mTitleView.setText( mValues.get( position ).getTitle() );
            holder.mShortDescriptionView.setText( mValues.get( position ).getShortDescription() );
            holder.mPriceView.setText( Double.toString( mValues.get( position ).getPrice() ) + "0 €" );
            //holder.mEndDateView.setText( mValues.get( position ).getEndData() );
            holder.mEndDateView.setText( mValues.get( position ).getEndData().substring( 0, 10 ) );
            holder.mThumbImage.setImageResource( mValues.get( position ).getThumb_dummy() );

            holder.itemView.setTag( mValues.get( position ) );
            holder.itemView.setOnClickListener( mOnClickListener );

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mTitleView;
            final TextView mPriceView;
            final TextView mShortDescriptionView;
            final ImageView mThumbImage;
            final TextView mEndDateView;

            ViewHolder(View view) {
                super( view );
                mTitleView = (TextView) view.findViewById( R.id.list_tour_title );
                mShortDescriptionView = (TextView) view.findViewById( R.id.list_short_description );
                mPriceView = (TextView) view.findViewById( R.id.list_price );
                mThumbImage = (ImageView) view.findViewById( R.id.list_image_view );
                mEndDateView = (TextView) view.findViewById( R.id.list_end_date );

            }
        }
    }

    public void fetchJSONData(){

        // static data because json request only returned xml and "application/json" header did not fix it
        // the thus created listed is not being used because of problems concerning the intent/bundle of the arrayList to the fragment

        TourData item1 = new TourData( 1, "Rhino Tour", "See Rhinos in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/1/", R.drawable.park_logo_2_2_2_icon1_1, "http://lorempixel.com/400/200/animals/1/", "http://lorempixel.com/800/600/animals/1/", "2018-11-04T12:31:04+01:00", "2018-12-11T12:31:04+01:00", 10.3);

        TourData item2 = new TourData( 2, "Gorilla Tour", "See Gorillas in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/2/", R.drawable.park_logo_2_2_2_icon1_2, "http://lorempixel.com/400/200/animals/2/", "http://lorempixel.com/800/600/animals/2/", "2018-11-04T13:57:03+01:00", "2018-12-11T13:57:03+01:00", 20.6);

        TourData item3 = new TourData( 3, "Tiger Tour", "See Tigers in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/3/", R.drawable.park_logo_2_2_2_icon2_1, "http://lorempixel.com/400/200/animals/3/", "http://lorempixel.com/800/600/animals/3/", "2018-11-04T13:58:44+01:00", "2018-12-11T13:58:44+01:00", 30.9);

        TourData item4 = new TourData( 4, "Giraffe Tour", "See Giraffes in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/4/", R.drawable.park_logo_2_2_2_icon2_2, "http://lorempixel.com/400/200/animals/4/", "http://lorempixel.com/800/600/animals/4/", "2018-11-04T14:00:04+01:00", "2018-12-11T14:00:04+01:00", 41.2);

        TourData item5 = new TourData( 5, "Bambi Tour", "See Bambis in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/5/", R.drawable.park_logo_2_2_2_icon2_1, "http://lorempixel.com/400/200/animals/5/", "http://lorempixel.com/800/600/animals/5/", "2018-11-04T14:01:37+01:00", "2018-12-11T14:01:37+01:00", 51.5);

        TourData item6 = new TourData( 6, "Pig Tour", "See Pigs in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/6/", R.drawable.park_logo_2_2_2_icon2_1, "http://lorempixel.com/400/200/animals/6/", "http://lorempixel.com/800/600/animals/6/", "2018-11-02T12:42:41+01:00", "2018-12-09T12:42:41+01:00", 61.8);

        TourData item7 = new TourData( 7, "Cat Tour", "See Cat in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/7/", R.drawable.park_logo_2_2_2_icon1_1, "http://lorempixel.com/400/200/animals/7/", "http://lorempixel.com/800/600/animals/7/", "2018-11-02T12:42:41+01:00", "2018-12-09T12:42:41+01:00", 72.1);

        TourData item8 = new TourData( 8, "Cute Little Doggy Tour", "See Cute little Doggies in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/8/", R.drawable.park_logo_2_2_2_icon1_1, "http://lorempixel.com/400/200/animals/8/", "http://lorempixel.com/800/600/animals/8/", "2018-11-02T12:42:41+01:00", "2018-12-09T12:42:41+01:00", 82.4);

        TourData item9 = new TourData( 9, "Dog Tour", "See Dog in their natural habitat",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                "http://lorempixel.com/200/100/animals/9/", R.drawable.park_logo_2_2_2_icon1_1, "http://lorempixel.com/400/200/animals/9/", "http://lorempixel.com/800/600/animals/9/", "2016-11-09T12:42:41+01:00", "2017-11-09T12:42:41+01:00", 92.7);

        item_list.add( item1 );
        item_list.add( item2 );
        item_list.add( item3 );
        item_list.add( item4 );
        item_list.add( item5 );
        item_list.add( item6 );
        item_list.add( item7 );
        item_list.add( item8 );
        item_list.add( item9 );


        /*
        JsonObjectRequest requestTourData = new JsonObjectRequest( Request.Method.GET, api_all_tours, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("id");

                            Log.e( "JSON", "json array length " + jsonArray.length());

                        } catch (JSONException e) {

                            e.printStackTrace();
                            Log.e( "VLY-LOC", "error in volley loc - json exception" );

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Log.e( "VLY-LOC", "error in volley loc - error response" );

            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");

                Log.e( "JSON", "add header to request" );

                return params;
            }
        };

        /*
        {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");

                Log.e( "JSON", "add header to request" );

                return headers;
            }
        };

        TourListActivity.getInstance().addToRequestQueue(requestTourData, "headerRequest");

        //requestQueue.add(requestTourData);
        */



        /*
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, api_all_tours, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e( "JSON", "JSON Array Request works" );

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Log.e( "JSON", "Error in JSON Array Request" );

            }
        } );*/

        //requestQueue.add(jsonArrayRequest);







    }

}
