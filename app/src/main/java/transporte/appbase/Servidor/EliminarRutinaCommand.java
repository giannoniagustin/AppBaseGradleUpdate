package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Notificaciones;
import transporte.appbase.Parser.ManejadorParser;
//import transporte.appbase.Rutinas.Rutina;
import transporte.appbase.Rutinas.RutinaG;

/**
 * Created by soled_000 on 29/01/2016.
 */
public class EliminarRutinaCommand extends ServidorCommand  {

    private RutinaG rutina;
    private ManejadorInterfaz manejadorInterfaz;

    public void setRutina(RutinaG c){
        rutina=c;
    }

    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }

    @Override
    public void procesarRespuesta(InputStream s) {
        ManejadorParser manejadorParser = new ManejadorParser();
        try {

            Integer clave = manejadorParser.parsearRespuesta(s);
            if (clave <= Notificaciones.getValorLimite())
                manejadorInterfaz.mostrarNotificacionServidor(clave);


        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }

    @Override
    public void Ejecutar() {
        super.server.eliminarRutina(this);
    }

    @Override
    public void Deshacer() {

    }

    public RutinaG getRutina() {
        return rutina;
    }
}
