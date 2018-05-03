package transporte.appbase.Servidor;

import java.io.InputStream;
import java.util.ArrayList;
import transporte.appbase.Archivos.Log;
import transporte.appbase.AsyncRespuesta;
import transporte.appbase.Configuracion.ArchivoCache;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Ruta.Corte;


public class ConsultarPuntosEstadiaCacheCommand extends PuntosEstadiaCommand {


    private AsyncRespuesta delegate = null;


    @Override
    public void procesarRespuesta(InputStream s){


        //parseo respuesta
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            ArrayList<Corte> puntos = manejadorParser.LeerCorte(s);
            if (puntos != null){
                //delega esta info al MapsActivity para setear las rutinas existentes del usuario
                delegate.finObtencionPECache(puntos);
            }

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());

        }
    }


    public AsyncRespuesta getAsyncRespuesta() {
        return delegate;
    }

    public void setAsyncRespuesta(AsyncRespuesta asyncRespuesta) {
        this.delegate = asyncRespuesta;
    }
}
