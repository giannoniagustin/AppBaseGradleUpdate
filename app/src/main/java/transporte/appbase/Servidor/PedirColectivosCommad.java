package transporte.appbase.Servidor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Parser.ManejadorParserSUMO;
import transporte.appbase.Ruta.Cole;
import transporte.appbase.Ruta.Corte;

/**
 * Created by soled_000 on 13/10/2015.
 */
public class PedirColectivosCommad extends ServidorCommand{

 //   private Server server;
    private ManejadorInterfaz manejadorInterfaz;
    String linea= null;

//Deberia ir el codigo del colectivo
    public PedirColectivosCommad(String lin) {
       linea = lin;
    }


 /*   public void setServer(Server server) {
        this.server = server;
    }
*/
    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }


    @Override
    public void procesarRespuesta(InputStream s) {

        manejadorInterfaz.cerrarDialogoEspera();
        //parseo respuesta
        ManejadorParserSUMO manejadorParserSumo = new ManejadorParserSUMO();
        try {
           // manejadorInterfaz.DibujarCorte(manejadorParser.LeerCorte(s));

            ArrayList<Cole> arreglo =  manejadorParserSumo.LeerColes(s, linea);
            Log.LOGGER.severe(arreglo.toString());
            manejadorInterfaz.DibujarColes(arreglo);

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void Ejecutar() {
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.pedirColectivos(this, linea);

    }

    @Override
    public void Deshacer() {

    }
}
