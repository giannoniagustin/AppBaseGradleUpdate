package transporte.appbase.Parser;

import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


import transporte.appbase.Archivos.Log;

/**
 * Created by Agustín on 13/10/2015.
 */
public abstract class LeerObjetoParser {

    public ArrayList<Object> readJsonStream(InputStream in)  {
        try{
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));


        try {
            return leerCorteArray(reader);

        }
        finally {
            reader.close();
        }}catch (Exception e){

            Log.LOGGER.severe(e.toString());
            return  null;
        }
    }

    public ArrayList<Object> leerCorteArray(JsonReader reader) {
        try {
        String resultado=    reader.toString();
            //List cortes = new ArrayList();
            ArrayList<Object> listaC = new ArrayList();
            reader.setLenient(true);
            reader.beginArray();

            while (reader.hasNext()) {
                //cortes.add(leerCorte(reader));

                listaC.add(leerObjeto(reader));
            }
            reader.endArray();
            return listaC;
        }catch (Exception e){

            Log.LOGGER.severe(e.toString());
            return  null;
        }
    }

    public abstract Object leerObjeto(JsonReader reader);


}
