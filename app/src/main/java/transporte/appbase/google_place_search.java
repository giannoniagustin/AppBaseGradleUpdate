package transporte.appbase;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Google.ApiClient;
import transporte.appbase.Rutinas.AgregarPuntoEstadia;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link google_place_search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link google_place_search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class google_place_search extends DialogFragment implements GoogleApiClient.OnConnectionFailedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView mAutocompleteView;



   /* private TextView mPlaceDetailsText;

    private TextView mPlaceDetailsAttribution;*/

  /*  private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));*/
  private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
          new LatLng(-55.0086589,-73.3178403), new LatLng(-36.566535, -94.3899246));


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment google_place_search.
     */
    // TODO: Rename and change types and number of parameters
    public static google_place_search newInstance(String param1, String param2) {
        try {
            google_place_search fragment = new google_place_search();
            Bundle args = new Bundle();
       /* args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
            //   fragment.setArguments(args);
            return fragment;
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
            return null;}
    }

    public google_place_search() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            new ApiClient(getActivity());
            super.onCreate(savedInstanceState);
            mGoogleApiClient = ApiClient.ConexionApi();


            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }

        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }
    DialogFragment showDialog() {
        // Create the fragment and show it as a dialog.
        return google_place_search.newInstance("","");



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {


            View myInflatedView = inflater.inflate(R.layout.fragment_google_place_search, container, false);

            mAutocompleteView = (AutoCompleteTextView) myInflatedView.findViewById(R.id.autocomplete_places);


            // Register a listener that receives callbacks when a suggestion has been selected
            mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

            // Retrieve the TextViews that will display details and attributions of the selected place.
            // mPlaceDetailsText = (TextView) myInflatedView.findViewById(R.id.place_details);
            //   mPlaceDetailsAttribution = (TextView) myInflatedView.findViewById(R.id.place_attribution);

            // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
            // the entire world.
            mAdapter = new PlaceAutocompleteAdapter(getActivity().getApplicationContext(),R.layout.color_place_search /*android.R.layout.test_list_item*/,
                    mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);

            mAutocompleteView.setAdapter(mAdapter);

            // Set up the 'clear text' button that clears the text in the autocomplete view
     /*   Button clearButton = (Button) myInflatedView.findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAutocompleteView.setText("");
            }
        });*/


            // Inflate the layout for this fragment
            //   return inflater.inflate(R.layout.fragment_google_place_search, container, false);
            return myInflatedView;
        }catch (Exception e){Log.LOGGER.severe(e.toString());
        return  null;}

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);


        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            Log.LOGGER.severe(e.toString());
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");

        }
    }

    @Override
    public void onDetach() {
        try {
            super.onDetach();
            mListener = null;
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.LOGGER.warning("Conection result "+connectionResult.toString());

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        // public void onFragmentInteraction(Uri uri);
        void LugarSeleccionado(Place lugar);

    }
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
             read the place ID.
              */

            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            //   Log.i(TAG, "Autocomplete item selected: " + item.description);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
              details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);


            /*Toast.makeText(, "Clicked: " + item.description,
                    Toast.LENGTH_SHORT).show();*/
            // Log.i(TAG, "Called getPlaceById to get Place details for " + item.placeId);
        }
    };
    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                //   Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);


           /* // Format details of the place for display and show it in a TextView.
            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));

            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }*/

            AgregarPuntoEstadia rutinaCallback = (AgregarPuntoEstadia) getTargetFragment();
            if(rutinaCallback != null) {
                rutinaCallback.respuestaPlace(place);
            }
            else
                // Log.i(TAG, "Place details received: " + place.getName());
                mListener.LugarSeleccionado(place);//Envia al activity padre el lugar

            places.release();

    }
    };


    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
      /*  Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));*/
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));


    }




}
