package com.example.robs.imaginaryapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robs.imaginaryapp.FetchJSONData;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Tour detail screen.
 * This fragment is either contained in a {@link TourListActivity}
 * in two-pane mode (on tablets) or a {@link TourDetailActivity}
 * on handsets.
 */
public class TourDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private TourData mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TourDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        if (getArguments().containsKey( ARG_ITEM_ID )) {
            mItem = FetchJSONData.ITEM_MAP.get( getArguments().getString( ARG_ITEM_ID ) );

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById( R.id.toolbar_layout );
            if (appBarLayout != null) {
                appBarLayout.setTitle( mItem.getTitle() );
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.tour_detail, container, false );

        if (mItem != null) {

            // set values of selected item

            ((TextView) rootView.findViewById( R.id.detail_title )).setText( mItem.getTitle() );
            ((TextView) rootView.findViewById( R.id.detail_description )).setText( mItem.getDescription() );
            ((ImageView) rootView.findViewById( R.id.detail_image )).setImageResource( mItem.getThumb_dummy() );
            ((TextView) rootView.findViewById( R.id.detail_start_end_date )).setText( (mItem.getStartData().substring( 0, 10 )) + " - " + mItem.getEndData().substring( 0, 10 ) );

            // Should display logo if no element is selected

            //((ImageView) rootView.findViewById( R.id.landscape_logo )).setVisibility( View.INVISIBLE  );

            /*
            Picasso.with(this.getContext())
                    .load(mItem.getImage400x200())
                    .centerCrop()
                    .fit()
                    .into( (ImageView) rootView.findViewById( R.id.detail_image ) );*/

        } //else
            //((ImageView) rootView.findViewById( R.id.detail_image )).setImageResource( R.drawable.park_logo_2_2_2_1 );

        return rootView;
    }


}
