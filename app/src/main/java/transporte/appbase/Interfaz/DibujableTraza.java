package transporte.appbase.Interfaz;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import transporte.appbase.Ruta.Traza;

/**
 * Created by soled_000 on 01/12/2015.
 */
public class DibujableTraza implements Dibujable {



    //  private ArrayList<LatLng> puntosIntermedios;
    private Traza traza;


//    private int color;

 /*   public int getColor() {
        return color;
    }*/
    public Traza getTraza() {
        return traza;
    }

 /*   public void setColor(int color) {
        this.color = color;
    }
*/
 /*   public DibujableTraza(ArrayList<LatLng> puntos){
        this.puntosIntermedios = puntos;

    }
*/
    public DibujableTraza(Traza t){
        this.traza = t;
    }
    @Override
    public void Accept(Dibujador dibujador) {
        dibujador.dibujarTraza(this);
    }

    public ArrayList<LatLng> getPuntosIntermedios() {
        return traza.getRuta();
    }


}
