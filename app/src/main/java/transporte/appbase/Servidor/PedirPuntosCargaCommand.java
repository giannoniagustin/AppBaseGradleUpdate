package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;

/**
 * Created by soled_000 on 13/10/2015.
 */
public class PedirPuntosCargaCommand extends ServidorCommand {


    private ManejadorInterfaz manejadorInterfaz;


    public PedirPuntosCargaCommand() {

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
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            manejadorInterfaz.DibujarCorte(manejadorParser.LeerCorte(s));

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void Ejecutar() {
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.pedirPuntosCarga(this);

    }

    @Override
    public void Deshacer() {
    }
}
