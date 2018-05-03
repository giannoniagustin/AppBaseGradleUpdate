package transporte.appbase.Interfaz;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Hashtable;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Ruta.ConjuntoTrazas;
import transporte.appbase.Rutinas.RutinaG;
import transporte.appbase.Util.Iconos;

/**
 * Created by Agustín on 07/10/2015.
 */
public class Dibujador {
    private GoogleMap mMap;
    private Polyline line;
    private Hashtable<Integer, Integer> colores = new Hashtable<>();

    public Dibujador() {
        setColores();

    }
    public void setColores(){
        colores.put(0, Color.GREEN);
        colores.put(1, Color.RED);
        colores.put(2, Color.BLUE);
        colores.put(3, Color.BLACK);
        colores.put(4, Color.CYAN);
        colores.put(5, Color.DKGRAY);
        colores.put(6, Color.GRAY);
        colores.put(7, Color.LTGRAY);
        colores.put(8, Color.YELLOW);
        colores.put(9, Color.MAGENTA);
    }

    public int getRandomColores(){
        int range = ((colores.size() - 1) - 0) + 1;
        return (int) (Math.random() * range) + 0;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void DibujarCorte(DibujableCorte dibujableCorte) {

        try {


            MarkerOptions markerOptions = new MarkerOptions();
            LatLng a = dibujableCorte.getCorte().getLatLng();
            markerOptions.position(dibujableCorte.getCorte().getLatLng());
            markerOptions.title(dibujableCorte.getCorte().getDescripcion());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(Iconos.getIcono(dibujableCorte.getCorte().getNombreIcono())));

            mMap.addMarker(markerOptions);
        } catch (Exception e) {

            Log.LOGGER.severe(e.toString() + "Parametros: MapaGenral" + mMap.toString());
        }


    }

    public void DibujarMarcador(DibujableMarcador dibujableMarcador) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(dibujableMarcador.getMarcador().getLatLng());
        markerOptions.title(dibujableMarcador.getMarcador().getDescripcion());
        markerOptions.icon(BitmapDescriptorFactory.fromResource( Iconos.getIcono(dibujableMarcador.getMarcador().getNombreIcono())));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dibujableMarcador.getMarcador().getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        mMap.addMarker(markerOptions);


    }

    public void dibujarRutina(DibujableRutina dibujableRutina) {
        try {
           //se puede armar un dibujableMarcadorRutina e invocarlo para origen y destino
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(dibujableRutina.getMarcadorRutinaOrigen().getLatLng());
            markerOptions.title(dibujableRutina.getMarcadorRutinaOrigen().getDescripcion() + "\n" + dibujableRutina.getMarcadorRutinaOrigen().getFecha() /*+ "\n" + dibujableRutina.getMarcadorRutina().getHora()*/);
            //elegir otro icono y agregarlo a Iconos
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

      /*     mMap.addCircle(new CircleOptions()
                    .center(dibujableRutina.getMarcadorRutina().getLatLng())
                    .radius(50)
                    .strokeColor(Color.RED)
                    .fillColor(Color.rgb(255, 204, 204)));

       */
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dibujableRutina.getMarcadorRutinaOrigen().getLatLng()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

            mMap.addMarker(markerOptions);


            markerOptions = new MarkerOptions();
            markerOptions.position(dibujableRutina.getMarcadorRutinaDestino().getLatLng());
            markerOptions.title(dibujableRutina.getMarcadorRutinaDestino().getDescripcion() + "\n" + dibujableRutina.getMarcadorRutinaDestino().getFecha() /*+ "\n" + dibujableRutina.getMarcadorRutina().getHora()*/);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dibujableRutina.getMarcadorRutinaOrigen().getLatLng()));
            mMap.addMarker(markerOptions);

            RutinaG rutina = dibujableRutina.getRutina();
            dibujarConjuntoTrazas(new DibujableConjuntoTraza(rutina.getTrazas()));

        } catch (Exception e) {

            Log.LOGGER.severe(e.toString() + "Parametros: MapaGenral" + mMap.toString());
        }

    }

    public void dibujarTraza(DibujableTraza dibujableTraza){
        dibujarRuta(dibujableTraza);
        DibujarMarcador( new DibujableMarcador(new Marcador(dibujableTraza.getTraza().getOrigenTraza(), "Origen", "IC_RUTA")));
        DibujarMarcador( new DibujableMarcador(new Marcador(dibujableTraza.getTraza().getDestinoTraza(), "Destino", "IC_RUTA")));

    }


    public void dibujarRuta(DibujableTraza dibujableTraza) {
        try {
            PolylineOptions options = new PolylineOptions();
            options.addAll(dibujableTraza.getPuntosIntermedios());
            options.geodesic(true);
        //    options.color(dibujableTraza.getColor());
            options.color(colores.get(getRandomColores()));
            line = mMap.addPolyline(options);

        } catch (Exception e) {

            Log.LOGGER.severe(e.toString());

        }
    }

    //ojo seleccion de camino a imprimir, no todos!
    public void dibujarConjuntoTrazas(DibujableConjuntoTraza dibujableConjuntoTraza) {
        ArrayList<DibujableTraza> conjuntoTrazasDibujables = dibujableConjuntoTraza.getConjuntoTrazas();
        int cantidad = conjuntoTrazasDibujables.size();
        for(int i= 0; i < cantidad; i++){
            if (conjuntoTrazasDibujables.get(i).getTraza().getRuta().size()>10){
                dibujarRuta(conjuntoTrazasDibujables.get(i));
                return;
            }
    }


    }
}


