package transporte.appbase.Parser;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Agustín on 05/04/2016.
 */

//no va mas. ES ConjuntoTrazas
public class ListTrazas extends ArrayList<ArrayList<LatLng>> implements Parcelable {
    public ListTrazas() {
    }

    protected ListTrazas(Parcel in) {

        this.clear();

        // First we have to read the list size
        int size = in.readInt();
        ArrayList<LatLng> r = null;


        for (int i = 0; i < size; i++) {
            int size2=in.readInt();
            r = new ArrayList<>();
            for(int j=0;j < size2;j++) {

                r.add(new LatLng(in.readDouble(), in.readDouble()));

            }
            this.add(r);
        }


    }

    public static final Creator<ListTrazas> CREATOR = new Creator<ListTrazas>() {
        @Override
        public ListTrazas createFromParcel(Parcel in) {
            return new ListTrazas(in);
        }

        @Override
        public ListTrazas[] newArray(int size) {
            return new ListTrazas[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        int size = this.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            ArrayList<LatLng> r = this.get(i);
            int size2=r.size();
            dest.writeInt(size2);
            for(int j=0;j < size2;j++) {

                dest.writeDouble(r.get(j).latitude);
                dest.writeDouble(r.get(j).longitude);

            }
        }

    }


}
