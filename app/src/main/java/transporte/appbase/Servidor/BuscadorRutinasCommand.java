package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Parser.ManejadorParser;

/**
 * Created by Agustín on 09/11/2015.
 */
public class BuscadorRutinasCommand extends ServidorCommand {

    @Override
    public void procesarRespuesta(InputStream s) {
        try {

            ManejadorParser manejadorParser = new ManejadorParser();


        } catch (Exception e){

            Log.LOGGER.severe(s.toString());
        }

    }

    @Override
    public void Ejecutar() {

        super.server.buscarRutinas("",this);

    }

    @Override
    public void Deshacer() {

    }
}
