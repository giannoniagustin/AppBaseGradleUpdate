package transporte.appbase.Parser;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InputStreamReader;

import transporte.appbase.Ruta.Traza;

/**
 * Created by soled_000 on 7/7/2016.
 */
public class TrazaTypeAdapter extends TypeAdapter {

    @Override
    public void write(JsonWriter out, Object value) throws IOException {

    }

    @Override
    public Traza read(JsonReader in) throws IOException {

        Double lat, longi;
        Traza ruta = new Traza();
        JsonParser jsonParser = new JsonParser();
        JsonElement objetoParseado = jsonParser.parse(in);

        for (int j = 0; j < objetoParseado.getAsJsonArray().size(); j++) {
                JsonObject traza = objetoParseado.getAsJsonArray().get(j).getAsJsonObject();
                longi = traza.getAsJsonObject().get("longitude").getAsDouble();
                lat = traza.getAsJsonObject().get("latitude").getAsDouble();
                ruta.setPuntoRuta(new LatLng(lat, longi));
            }
        return ruta;
    }
}
