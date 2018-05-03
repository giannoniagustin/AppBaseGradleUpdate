package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Archivos.ArchivoLog;
import transporte.appbase.Archivos.ElementoArchivo;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Parser.ManejadorParser;

/**
 * Created by soled_000 on 16/10/2015.
 */
public class EnviarArchivoLogCommand extends EnviarArchivoCommand {






    @Override
    public void procesarRespuesta(InputStream s) {

        ManejadorParser manejadorParser = new ManejadorParser();
        String result = manejadorParser.parsearRespuestaString(s);
        Log.LOGGER.info("Resultado envio archivo log: "+result);

    }
}
