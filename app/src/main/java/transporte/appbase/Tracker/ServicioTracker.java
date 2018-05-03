package transporte.appbase.Tracker;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import transporte.appbase.R;


public class ServicioTracker extends Service implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private Location ubicacionActual = null;
    private Vector<LatLng> ultimasUbicaciones;
    private TrackUbicacionHistoria track;
    private int tipoActividad = DetectedActivity.UNKNOWN;
    private int probabilidad = 0;
    private String nombreActividad = "Desconocida";//HARD CODE



    protected static final String TAG = "creating-and-monitoring-geofences";

    /**
     * Provides the entry point to Google Play services.
     */
 //   protected GoogleApiClient mGoogleApiClient;

    /**
     * The list of geofences used in this sample.
     */
    protected ArrayList<Geofence> mGeofenceList;

    /**
     * Used to keep track of whether geofences were added.
     */
    private boolean mGeofencesAdded;

    /**
     * Used when requesting to add or remove geofences.
     */
    private PendingIntent mGeofencePendingIntent;

    /**
     * Used to persist application state about whether geofences were added.
     */
    private SharedPreferences mSharedPreferences;


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }


    private PendingIntent mActivityRecognitionPendingIntent;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final int activityType = intent.getIntExtra("activity_type", 0);
            int p = intent.getIntExtra("confidence", 0);
            tipoActividad = activityType;
            probabilidad = p;
            // Toast.makeText(this, broadcastReceiver.getNameFromType(activityType), Toast.LENGTH_SHORT).show();

            //armarlo segun perfiles de actividad y tambien ver lo del porcentaje de la actividad(mayor a 75%)
            try {


                switch (activityType) {

                    case DetectedActivity.IN_VEHICLE: {//valor 0
                        mLocationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(5 * 1000)        //5 segundos, en milisegundos
                                .setFastestInterval(5 * 1000)
                                .setSmallestDisplacement(10);
                        nombreActividad = "Vehiculo";
                        break;
                    }
                    case DetectedActivity.ON_BICYCLE: { //valor 1
                        mLocationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10 * 1000)        //10 segundos, en milisegundos
                                .setFastestInterval(10 * 1000)
                                .setSmallestDisplacement(25);
                        nombreActividad = "Bici";
                        break;

                    }
                    case DetectedActivity.RUNNING: { //valor 8
                        mLocationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(15 * 1000)        //15 segundos, en milisegundos
                                .setFastestInterval(15 * 1000)
                                .setSmallestDisplacement(25);
                        nombreActividad = "Corriendo";
                        break;

                    }
                    case DetectedActivity.ON_FOOT: { //valor 2
                        mLocationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                                .setInterval(15 * 1000)        //15 segundos, en milisegundos
                                .setFastestInterval(15 * 1000)
                                .setSmallestDisplacement(25);
                        nombreActividad = "CaminandoRapido";
                        break;


                    }
                    case DetectedActivity.WALKING: { //valor 7
                        mLocationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                                .setInterval(15 * 1000)        //15 segundos, en milisegundos
                                .setFastestInterval(15 * 1000)
                                .setSmallestDisplacement(25);
                        nombreActividad = "Caminando";
                        break;
                    }

                    case DetectedActivity.STILL: { //3
                        mLocationRequest = LocationRequest.create()
                                //  .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                                .setPriority(LocationRequest.PRIORITY_NO_POWER)
                                .setInterval(3000 * 1000)        //3000 segundos, en milisegundos
                                .setFastestInterval(3000 * 1000)
                      /*      .setSmallestDisplacement(500)*/;
                        nombreActividad = "Nada";
                        break;
                    }
                    case DetectedActivity.TILTING: { //valor 5
                        mLocationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                                .setInterval(15 * 1000)        //15 segundos, en milisegundos
                                .setFastestInterval(15 * 1000)
                                .setSmallestDisplacement(25);
                        nombreActividad = "Tilting";
                        break;
                    }
                    case DetectedActivity.UNKNOWN: {
                        mLocationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                                .setInterval(30 * 1000)        //30 segundos, en milisegundos
                                .setFastestInterval(30 * 1000)
                                .setSmallestDisplacement(25);
                        nombreActividad = "Desconocida";
                        break;

                    }
                }

                suscribirseLocation(mLocationRequest);
            }catch (Exception e){
                transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());}
        }
    };

    @Override
    public void onCreate() {

  try {

      ultimasUbicaciones=new Vector<>();
      mGoogleApiClient = new GoogleApiClient.Builder(this)
              .addConnectionCallbacks(this)
              .addOnConnectionFailedListener(this)
              .addApi(LocationServices.API)
              .addApi(ActivityRecognition.API)
                      //  .enableAutoManage((FragmentActivity) Map, 0, this)
              .build();


      //Location generico para los casos de actividad desconocida
      mLocationRequest = LocationRequest.create()
              .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
              .setInterval(15 * 1000)        // 15 segundos, en milisegundos
              .setFastestInterval(15 * 1000)
              .setSmallestDisplacement(50);
      mGoogleApiClient.connect();
      track = new TrackUbicacionHistoria(this);

      registerReceiver(broadcastReceiver, new IntentFilter("receive_recognition"));




//Gianno

      // Empty list for storing geofences.
      mGeofenceList = new ArrayList<Geofence>();

      // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
      mGeofencePendingIntent = null;

      // Retrieve an instance of the SharedPreferences object.
      mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,
              MODE_PRIVATE);

      // Get the value of mGeofencesAdded from SharedPreferences. Set to false as a default.
      mGeofencesAdded = mSharedPreferences.getBoolean(Constants.GEOFENCES_ADDED_KEY, false);
   //   setButtonsEnabledState();

      // Get the geofences used. Geofence data is hard coded in this sample.
      populateGeofenceList();

      // Kick off the request to build GoogleApiClient.
      //buildGoogleApiClient();
  }catch (Exception e){
      transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());}

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        Toast.makeText(this, "Se detuvo correctamente el servicio", Toast.LENGTH_SHORT).show();
    }

    public void enviarCambioUbicacion(Location location) {
        try {


            Intent notifyIntent = new Intent("receive_location");
            notifyIntent.putExtra("latitud", location.getLatitude());
            notifyIntent.putExtra("longitud", location.getLongitude());
            notifyIntent.putExtra("actividad", nombreActividad);
            sendBroadcast(notifyIntent);
        }catch (Exception e){
            transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());}
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
             ubicacionActual = location;
            ultimasUbicaciones.add(new LatLng(ubicacionActual.getLatitude(),ubicacionActual.getLongitude()));
            enviarCambioUbicacion(location);
            Date fecha = new Date();
            track.escribir(" Tipo: " + tipoActividad + " " + "Actividad: " + nombreActividad + " Prob: " + probabilidad + " " + Double.toString(ubicacionActual.getLatitude()) +
                            ":" +
                            Double.toString(ubicacionActual.getLongitude()) + ":" + DateFormat.format("MM:dd:yyyy:HH:mm:ss", fecha.getTime())
            );
        }catch (Exception e){
            transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());}

    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
            ubicacionActual = location;
            Intent intent = new Intent(this, ActivityRecognitionReceiverIntentService.class);
            mActivityRecognitionPendingIntent = PendingIntent.getService(this, 10000, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mGoogleApiClient, 10000, mActivityRecognitionPendingIntent);

            //ESTO ES PARA PONER GEOFENCESaddGeofencesButtonHandler(null);

        } catch (Exception e) {

            transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());
        }
    }

    public void  suscribirseLocation(LocationRequest mLocationRequest){
        try {


            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }catch (Exception e){
            transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());}
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Suspendido", "ConnectionSupend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Suspendido","ConnectionSupend");
    }

    // Binder
    private final IBinder mBinder = new LocalBinder();



    public class LocalBinder extends Binder {
        ServicioTracker getService() {
            try {


                // Returna a instancia do SeuService
                return ServicioTracker.this;
            }catch (Exception e){
                transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());
            return  null;}
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //metodo para acceder desde la CapaServicio que retorna la ultima location conocida
    public Location ultimaUbicacionConocida(){
        return ubicacionActual;
    }
    public Vector<LatLng> ultimasUbicaciones(){
        return ultimasUbicaciones;
    }
    public String ultimaActividadConocida() {
        return nombreActividad;
    }

    /**
     * This sample hard codes geofence data. A real app might dynamically create geofences based on
     * the user's location.
     */
    public void populateGeofenceList() {
        for (Map.Entry<String, LatLng> entry : Constants.BAY_AREA_LANDMARKS.entrySet()) {

            mGeofenceList.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(entry.getKey())

                            // Set the circular region of this geofence.
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Constants.GEOFENCE_RADIUS_IN_METERS
                    )

                            // Set the expiration duration of the geofence. This geofence gets automatically
                            // removed after this period of time.
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)

                            // Set the transition types of interest. Alerts are only generated for these
                            // transition. We track entry and exit transitions in this sample.
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)

                            // Create the geofence.
                    .build());
        }
    }
    /**
     * Adds geofences, which sets alerts to be notified when the device enters or exits one of the
     * specified geofences. Handles the success or failure results returned by addGeofences().
     */
    public void addGeofencesButtonHandler(View view) {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    // The GeofenceRequest object.
                    getGeofencingRequest(),
                    // A pending intent that that is reused when calling removeGeofences(). This
                    // pending intent is used to generate an intent when a matched geofence
                    // transition is observed.
                    getGeofencePendingIntent()
            )/*.setResultCallback(this)*/; // Result processed in onResult(). GIANNOOO
        } catch (Exception e) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());
        }
    }
    /**
     * Builds and returns a GeofencingRequest. Specifies the list of geofences to be monitored.
     * Also specifies how the geofence notifications are initially triggered.
     */
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(mGeofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }
    /**
     * Gets a PendingIntent to send with the request to add or remove Geofences. Location Services
     * issues the Intent inside this PendingIntent whenever a geofence transition occurs for the
     * current list of geofences.
     *
     * @return A PendingIntent for the IntentService that handles geofence transitions.
     */
    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
