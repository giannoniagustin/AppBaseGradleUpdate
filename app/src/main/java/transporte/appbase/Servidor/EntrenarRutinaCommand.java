package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Parser.ManejadorParser;

/**
 * Created by Agustín on 30/10/2015.
 */
public class EntrenarRutinaCommand extends ServidorCommand {
    @Override
    public void procesarRespuesta(InputStream s) {
        try {

            ManejadorParser manejadorParser = new ManejadorParser();

            int clave = manejadorParser.parsearRespuesta(s);

            Log.LOGGER.info(Integer.toString(clave));

        } catch (Exception e){

            Log.LOGGER.severe(s.toString());
        }

    }

    @Override
    public void Ejecutar() {

        // super.manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.entrenarRutina("",this);

    }

    @Override
    public void Deshacer() {

    }
}
