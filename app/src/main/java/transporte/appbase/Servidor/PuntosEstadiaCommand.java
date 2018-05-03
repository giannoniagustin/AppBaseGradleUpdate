package transporte.appbase.Servidor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Configuracion.ArchivoCache;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Ruta.Corte;

/**
 * Created by Agustín on 04/12/2015.
 */
public class PuntosEstadiaCommand extends ServidorCommand {


    private ArchivoCache archivoCache;
    private ManejadorInterfaz manejadorInterfaz;
    private String nombreUsuario;



    public PuntosEstadiaCommand() {

    }

    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }

    public void setArchivoCache(ArchivoCache archivoCache){
        this.archivoCache = archivoCache;
    }


    @Override
    public void procesarRespuesta(InputStream s) {
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

        super.server.pedirPuntosEstadia(this, archivoCache);

    }

    @Override
    public void Deshacer() {

    }

    public void setUsuario(String usuario) {
        this.nombreUsuario = usuario;
    }
    public String getUsuario(){
        return nombreUsuario;
    }
}
