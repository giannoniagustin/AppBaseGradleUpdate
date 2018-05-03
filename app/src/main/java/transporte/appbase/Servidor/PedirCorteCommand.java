package transporte.appbase.Servidor;


import java.io.InputStream;
import java.util.ArrayList;

import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Ruta.Corte;

/**
 * Created by Agustín on 07/10/2015.
 */
public class PedirCorteCommand extends ServidorCommand{

    private ManejadorInterfaz manejadorInterfaz;
    private Double latitud;
    private Double longitud;
    private int distancia;


    public PedirCorteCommand(Double latitud, Double longitud, int distancia) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.distancia = distancia;
    }


/*    public void setServer(Server server) {
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
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            ArrayList<Corte> arreglo;
            arreglo = manejadorParser.LeerCorte(s);
            manejadorInterfaz.DibujarCorte(arreglo);

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void Ejecutar() {
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.PedirCorte(latitud, longitud, distancia, this);

    }

    @Override
    public void Deshacer() {

    }
}
