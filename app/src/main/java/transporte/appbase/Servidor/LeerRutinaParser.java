package transporte.appbase.Servidor;

import com.google.gson.Gson;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Parser.DeserializadorRutina;
import transporte.appbase.Parser.LeerObjetoParser;
//import transporte.appbase.Rutinas.Rutina;
import transporte.appbase.Rutinas.RutinaG;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

/**
 * Created by soled_000 on 06/11/2015.
 */
public class LeerRutinaParser extends LeerObjetoParser {

    private RutinaG rutina;

    public LeerRutinaParser() {

    }

    public Object leerObjeto(JsonReader reader) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(RutinaG.class,new DeserializadorRutina());
            Gson gson = gsonBuilder.create();
            rutina = gson.fromJson(reader,RutinaG.class);

            return rutina;

        }catch (Exception e){

            Log.LOGGER.severe(e.toString());
            return null;
        }
    }
}
