package transporte.appbase.Servidor;

import java.io.InputStream;

import transporte.appbase.Archivos.Archivo;
import transporte.appbase.Archivos.ArchivoLog;
import transporte.appbase.Archivos.ControladorArchivos;
import transporte.appbase.Archivos.ElementoArchivo;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Ruta.ElementoRuta;
import transporte.appbase.Usuario;


/**
 * Created by soled_000 on 16/10/2015.
 */
public abstract class EnviarArchivoCommand extends ServidorCommand {

    protected ElementoArchivo archivo;
    protected ManejadorInterfaz manejadorInterfaz;
    protected String nombreUsuario;
    protected ControladorArchivos controladorArchivos;

    public void setArchivo(ElementoArchivo archivo) {
        this.archivo = archivo;
    }


    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }
    public void setControladorArchivos(ControladorArchivos controladorArchivos){
        this.controladorArchivos = controladorArchivos;
    }
    public void setUsuario(String usuario){
        this.nombreUsuario = usuario;
    }

    @Override
    public abstract void procesarRespuesta(InputStream s);



    public void Ejecutar(){

        super.server.enviarArchivoGeneral(archivo, this,nombreUsuario);
    }


    @Override
    public void Deshacer() {

    }


}
