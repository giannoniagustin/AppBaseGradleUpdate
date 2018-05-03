package transporte.appbase.Ruta;

import android.os.Parcel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by soled_000 on 26/5/2016.
 */
public class Traza implements ElementoRuta {

    private LatLng origenTraza;
    private LatLng destinoTraza;

    private ArrayList<LatLng> ruta;

    public Traza() {
        ruta = new ArrayList<>();
    }

    protected Traza(Parcel in) { //la traza se almacena de la siguiente manera: los primeros cuatros campos double son para los puntos origen y destino con su correspondiente lat y long - Los siguientes corresponden a la ruta entre ellos

        ruta = new ArrayList<>();
        Double origenLat = in.readDouble();
        Double origenLong= in.readDouble();
        Double destinoLat = in.readDouble();
        Double destinoLong= in.readDouble();
        this.origenTraza = new LatLng(origenLat,origenLong);
        this.destinoTraza = new LatLng(destinoLat,destinoLong);
        // First we have to read the list size
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            ruta.add(new LatLng(in.readDouble(), in.readDouble()));
        }
    }

    public static final Creator<Traza> CREATOR = new Creator<Traza>() {
        @Override
        public Traza createFromParcel(Parcel in) {
            return new Traza(in);
        }

        @Override
        public Traza[] newArray(int size) {
            return new Traza[size];
        }
    };
/*
    @Override
    public void dibujar(GoogleMap mGeneral) {
       }

    @Override
    public void borrar(GoogleMap mGeneral) {

    }
*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(origenTraza.latitude); //marca error
        dest.writeDouble(origenTraza.longitude);
        dest.writeDouble(destinoTraza.latitude);
        dest.writeDouble(destinoTraza.longitude);
        int size = ruta.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            LatLng r = ruta.get(i);
            dest.writeDouble(r.latitude);
            dest.writeDouble(r.longitude);
        }
    }


    public LatLng getOrigenTraza() {
        return origenTraza;
    }

    public void setOrigenTraza(LatLng origenTraza) {
        this.origenTraza = origenTraza;
    }

    public LatLng getDestinoTraza() {
        return destinoTraza;
    }

    public void setDestinoTraza(LatLng destinoTraza) {
        this.destinoTraza = destinoTraza;
    }
    public ArrayList<LatLng> getRuta() {
        return ruta;
    }

    public void setPuntoRuta(LatLng p){
        ruta.add(p);
    }

    public void setRuta(ArrayList<LatLng> ruta) {
        this.ruta = ruta;
    }

    public String trazaToString(){
        //  String traza = "origenLatitud"+origenTraza.latitude+";origenLongitud"+origenTraza.longitude+";destinoLatitud"+destinoTraza.latitude+";destinoLongitud"+destinoTraza.longitude+";";
        String traza = "Origen:"+origenTraza.toString()+";Destino:"+destinoTraza.toString()+";";
          for (int i = 0; i<ruta.size();i++){
              traza+=ruta.get(i).toString()+";";
          }
        return(traza);
    }
}
