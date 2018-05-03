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
import java.util.Calendar;
import transporte.appbase.Ruta.ConjuntoTrazas;
import transporte.appbase.Ruta.Traza;
import transporte.appbase.Rutinas.RutinaG;

/**
 * Created by Agustín on 28/03/2016.
 */
public class RutinaTypeAdapter extends TypeAdapter { //ojo ver O y D de traza!! Preg a Gianno para reemplazarlo por el adapter de una rutina
    @Override
    public void write(JsonWriter out, Object value) throws IOException {

    }

    @Override
    public RutinaG read(JsonReader in) {

        try {
            ConjuntoTrazas trazas = new ConjuntoTrazas();
            final RutinaG rutinaG = new RutinaG();
            JsonParser jsonParser = new JsonParser();
            JsonElement objetoParseado = jsonParser.parse(in);
            if (objetoParseado.getAsJsonObject().get("caminos").isJsonArray()) {
                JsonArray arregloTrazas = objetoParseado.getAsJsonObject().get("caminos").getAsJsonArray();
                for (int i = 0; i < arregloTrazas.size(); i++)

                {
                    trazas.add(new Traza());
                    if (arregloTrazas.get(i).isJsonArray()) {
                        JsonArray arregloPuntos = arregloTrazas.get(i).getAsJsonArray();
                        for (int j = 0; j < arregloPuntos.size(); j++) {

                            JsonObject traza = arregloPuntos.get(j).getAsJsonObject();
                            Double lat = traza.get("punto").getAsJsonObject().get("latitude").getAsDouble();
                            Double lon = traza.get("punto").getAsJsonObject().get("longitude").getAsDouble();
                            trazas.get(trazas.size() - 1).setPuntoRuta(new LatLng(lat, lon));
                        }
                    }
                }
                rutinaG.setFechaInicio(Calendar.getInstance());
                rutinaG.setFechaFin(Calendar.getInstance());
                rutinaG.setOrigenRutina(trazas.get(0).getOrigenTraza());
                rutinaG.setDestinoRutina(trazas.get(0).getDestinoTraza());
                rutinaG.setTrazas(trazas);
                rutinaG.setpOrigen(objetoParseado.getAsJsonObject().get("origen").getAsString().replace("_A:", ""));
                rutinaG.setpDestino(objetoParseado.getAsJsonObject().get("destino").getAsString().replace("_A:", ""));
                rutinaG.setNombre(objetoParseado.getAsJsonObject().get("origen").getAsString() + objetoParseado.getAsJsonObject().get("destino").getAsString());//ver
            }
            return rutinaG;

        } catch (Exception E) {

            E.toString();
            return null;

        }
    }
}
