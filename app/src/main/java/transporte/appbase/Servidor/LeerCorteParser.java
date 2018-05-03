package transporte.appbase.Servidor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import transporte.appbase.Parser.DeserializadorRutina;
import transporte.appbase.Parser.LeerObjetoParser;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Ruta.Corte;
//import transporte.appbase.Rutinas.Rutina;
import transporte.appbase.Ruta.Punto;

/**
 * Created by soled_000 on 3/8/2015.
 */
public class LeerCorteParser extends LeerObjetoParser {
    private Gson gs;

    public LeerCorteParser() {
        gs = new Gson();
    }

    public Object leerObjeto(JsonReader reader) {
        try {

            Gson gs=new Gson();
            Corte corteB= gs.fromJson(reader,Corte.class);
    //        Punto puntoB=gs.fromJson(reader,Punto.class);
            System.out.println("pasoCorte y puntoParser");
            return corteB;
        }catch (Exception e){

            Log.LOGGER.severe(e.toString());
            return null;
        }
    }




}
