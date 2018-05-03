package transporte.appbase.Google;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

/**
 * Created by Agustín on 06/07/2015.
 */
public class ApiClient implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static GoogleApiClient mGoogleApiClient;

    public ApiClient(Context Contexto) {
        try {


            mGoogleApiClient = new GoogleApiClient.Builder(Contexto)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .enableAutoManage((FragmentActivity) Contexto,0,this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();
        } catch (Exception e) {

            String a = e.getMessage();
        }

    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static GoogleApiClient ConexionApi() {


        return mGoogleApiClient;


    }


}
