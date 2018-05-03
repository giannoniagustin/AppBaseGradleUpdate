package transporte.appbase.Servidor;

import java.io.InputStream;
import java.util.ArrayList;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Notificaciones;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Ruta.Corte;
import transporte.appbase.Rutinas.PuntoEstadia;

/**
 * Created by soled_000 on 18/7/2016.
 */
public class AgregarPuntoEstadiaCommand extends ServidorCommand {
    
    private PuntoEstadia puntoEstadia;
    private ManejadorInterfaz manejadorInterfaz;

    public void setPuntoEstadia(PuntoEstadia c){
        puntoEstadia=c;
    }

    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }
    @Override
    public void procesarRespuesta(InputStream s) {
        manejadorInterfaz.cerrarDialogoEspera();
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            int clave = manejadorParser.parsearRespuesta(s);
            manejadorInterfaz.mostrarNotificacionServidor(clave);
            if (clave > Notificaciones.getValorLimite()){
                manejadorInterfaz.dibujarPunto(puntoEstadia);
            }


        }catch (Exception e) {
            Log.LOGGER.severe(e.toString());
    }
    }

    @Override
    public void Ejecutar() {
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.enviarPuntoEstadia(this);
    }

    @Override
    public void Deshacer() {

    }

    public PuntoEstadia getPuntoEstadia() {
        return puntoEstadia;
    }
}
