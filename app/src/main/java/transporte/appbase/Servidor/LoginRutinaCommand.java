package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Parser.ManejadorParser;

/**
 * Created by Agustín on 28/10/2015.
 */
public class LoginRutinaCommand extends  ServidorCommand {
    @Override
    public void procesarRespuesta(InputStream s) {
        try {

            ManejadorParser manejadorParser = new ManejadorParser();

                int clave = manejadorParser.parsearRespuesta(s);

            Log.LOGGER.info(Integer.toString(clave));

        } catch (Exception e){

            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void Ejecutar() {
       try {
           // super.manejadorInterfaz.mostrarDialogoEspera("Cargando...");
           super.server.loginRutina("", this);
       } catch (Exception e){
           Log.LOGGER.severe(e.toString());
       }

    }

    @Override
    public void Deshacer() {

    }
}
