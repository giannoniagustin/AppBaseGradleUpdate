package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Notificaciones;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Usuario;


public abstract class EnviarUsuarioCommand extends ServidorCommand {

    protected Usuario usuario;
    protected ManejadorInterfaz manejadorInterfaz;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
            if (clave <= Notificaciones.getValorLimite())
                manejadorInterfaz.mostrarNotificacionServidor(clave);
           else{
                //manejador de interfaz Pasa del activity original al MapsActivity
                manejadorInterfaz.cambioActivityMapa(usuario);
            }

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public abstract void Ejecutar();

    @Override
    public void Deshacer() {

    }
}
