package transporte.appbase.Servidor;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Notificaciones;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Ruta.Corte;
import transporte.appbase.Rutinas.RutinaG;

/**
 * Created by soled_000 on 1/7/2016.
 */
public class PedirPrediccionRutinaCommand extends PedirRutinasCommad {



    @Override
    public void Ejecutar() { //agregarle una fecha
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.pedirPrediccionRutina(this/*, archivoCache*/);

    }

    @Override
    public void procesarRespuesta(InputStream s){

        ManejadorParser manejadorParser = new ManejadorParser();

        ArrayList<Corte> cortes = new ArrayList<>();
        manejadorInterfaz.cerrarDialogoEspera();

        try {

            JsonReader jsone = new JsonReader(new InputStreamReader(s));
            JsonParser jsonParser = new JsonParser();
            JsonElement objetoParseado = jsonParser.parse(jsone);
            if(objetoParseado.getAsJsonObject().get("cortes").isJsonArray()) {

                JsonArray arreglo = objetoParseado.getAsJsonObject().get("cortes").getAsJsonArray();
                InputStream salidaCortes = new ByteArrayInputStream(arreglo.toString().getBytes());

                cortes = manejadorParser.LeerCorte(salidaCortes);
                //    manejadorInterfaz.DibujarCorte(cortes);
            }
            else{
                int valor = Integer.parseInt(Notificaciones.getValorErrorServer()); //-99; error server
                manejadorInterfaz.mostrarNotificacionServidor(valor);
            }

            if(objetoParseado.getAsJsonObject().get("rutina").isJsonArray()) {

                JsonArray arrayRutina = objetoParseado.getAsJsonObject().get("rutina").getAsJsonArray();
                if (arrayRutina.size() !=0) {
                    ArrayList<RutinaG> rutinas = manejadorParser.parsearRutinas(arrayRutina);
                    if (rutinas != null) {

                        manejadorInterfaz.cambioActivityRutinas(rutinas,cortes, nombreUsuario);
                    }
                }

            }
        }
        catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

}
}
