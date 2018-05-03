package transporte.appbase.Servidor;

import java.io.InputStream;
import java.util.ArrayList;

import transporte.appbase.Archivos.Log;

import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Notificaciones;
import transporte.appbase.Parser.ManejadorParser;
//import transporte.appbase.Rutinas.Rutina;
import transporte.appbase.Rutinas.RutinaG;


public class EnviarRutinaCommand extends ServidorCommand {

    private RutinaG rutina;
    private ManejadorInterfaz manejadorInterfaz;
    private String nombreUsuario;

    public void setRutina(RutinaG c){
        rutina=c;
    }

    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }

    public void setUsuario(String usuario){
        this.nombreUsuario = usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public void procesarRespuesta(InputStream s) {
        manejadorInterfaz.cerrarDialogoEspera();
        ManejadorParser manejadorParser = new ManejadorParser();
        try {

            Integer clave = manejadorParser.parsearRespuesta(s);
            manejadorInterfaz.mostrarNotificacionServidor(clave);
            if (clave > Notificaciones.getValorLimite()) {
                ArrayList<RutinaG> dibujar = new ArrayList<>();
               // dibujar.add(rutina);COMENTADO GIANNO MIGRANDO A NUEVA RUTINA
           //     manejadorInterfaz.dibujarRutina(dibujar);
            }

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void Ejecutar() {
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.enviarRutina(rutina, this);
    }

    @Override
    public void Deshacer() {

    }
}
