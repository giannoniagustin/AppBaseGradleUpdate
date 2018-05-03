package transporte.appbase.Ruta;

import android.os.Parcel;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

/**
 * Created by soled_000 on 26/5/2016.
 */
public class ConjuntoTrazas extends ArrayList<Traza> implements ElementoRuta {

    public ConjuntoTrazas() {

    }

    protected ConjuntoTrazas(Parcel in) {

        this.clear();

        // First we have to read the list size
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            Traza a = in.readParcelable(getClass().getClassLoader());
            this.add(a);
        }
    }

    public static final Creator<ConjuntoTrazas> CREATOR = new Creator<ConjuntoTrazas>() {
        @Override
        public ConjuntoTrazas createFromParcel(Parcel in) {
            return new ConjuntoTrazas(in);
        }

        @Override
        public ConjuntoTrazas[] newArray(int size) {
            return new ConjuntoTrazas[size];
        }
    };


    /*
        @Override
        public void dibujar(GoogleMap mGeneral) {
     /*       for (int i = 0; i < this.size(); i++){
                this.get(i).dibujar(mGeneral);
            }
        }*/
/*
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
        int size = this.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            Traza r = this.get(i);
            dest.writeParcelable(r, flags);
        }
    }

    public String conjuntoTrazasToString() {
        String conjuntoTrazas = "";
        int size = this.size();
        for (int i = 0; i < size; i++) {
            conjuntoTrazas += this.get(i).trazaToString();
        }
        return conjuntoTrazas;
    }
}