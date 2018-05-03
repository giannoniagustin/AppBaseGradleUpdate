package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Util.Command;

/**
 * Created by soled_000 on 18/4/2016.
 */
public class EliminarUsuarioCommand extends ServidorCommand {
    @Override
    public void procesarRespuesta(InputStream s) {
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            int clave = manejadorParser.parsearRespuesta(s);
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void Ejecutar() {
        super.server.eliminarUsuarioServer(this);
    }

    @Override
    public void Deshacer() {

    }
}
