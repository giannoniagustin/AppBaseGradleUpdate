package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Parser.ManejadorParser;

/**
 * Created by Agustín on 05/12/2015.
 */
public class ListarArchivosCommand extends ServidorCommand {

    private ManejadorInterfaz manejadorInterfaz;

    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }

    @Override
    public void procesarRespuesta(InputStream s) {

        ManejadorParser manejadorParser = new ManejadorParser();
        try {

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }

    @Override
    public void Ejecutar() {

        super.server.lisrArchivos("",this);

    }

    @Override
    public void Deshacer() {

    }
}
