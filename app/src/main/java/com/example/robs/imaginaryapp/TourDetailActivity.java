package com.example.robs.imaginaryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

/**
 * An activity representing a single Tour detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TourListActivity}.
 */
public class TourDetailActivity extends AppCompatActivity {

    String phoneNumber = "+436661234567";
    String concatPhoneNumber = "tel: 0" + (phoneNumber.substring( 3, 12 ));

    Company companyData = new Company( "Imaginary National Park",
                                       "Random Street 42", "Wonderland", concatPhoneNumber);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tour_detail );
        Toolbar toolbar = (Toolbar) findViewById( R.id.detail_toolbar );
        setSupportActionBar( toolbar );

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
            arguments.putString( TourDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra( TourDetailFragment.ARG_ITEM_ID ) );

            //arguments.getParcelableArray( "list_item" );

            TourDetailFragment fragment = new TourDetailFragment();
            fragment.setArguments( arguments );
            getSupportFragmentManager().beginTransaction()
                    .add( R.id.tour_detail_container, fragment )
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            navigateUpTo( new Intent( this, TourListActivity.class ) );
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    public void onClickTextViewCallBooking(View view) {

        // textView because button was not to be found with findViewById

        final int CALL_REQUEST = 100;

        try
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(TourDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST);

                    return;
                }
            }

            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData( Uri.parse(companyData.getPhone()) );
            phoneIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity( phoneIntent );

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

}
