package transporte.appbase.Parser;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Ruta.Corte;

/**
 * Created by Agustín on 24/03/2016.
 */
public class LeerRutaParserGianno  extends LeerObjetoParser {
    @Override
    public Object leerObjeto(JsonReader reader) {
        try {

            Gson gs=new Gson();
            LatLng latLng= gs.fromJson(reader,LatLng.class);
            return latLng;
        }catch (Exception e){

            Log.LOGGER.severe(e.toString());
            return null;
        }
    }
}
