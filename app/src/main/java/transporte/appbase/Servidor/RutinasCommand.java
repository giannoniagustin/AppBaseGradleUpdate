package transporte.appbase.Servidor;

import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.util.Calendar;

import transporte.appbase.Configuracion.ArchivoCache;
import transporte.appbase.Interfaz.ManejadorInterfaz;

/**
 * Created by soled_000 on 15/7/2016.
 */
public abstract class RutinasCommand extends ServidorCommand{

    private LatLng puntoActual;
    protected ManejadorInterfaz manejadorInterfaz;
    protected String nombreUsuario;
    protected ArchivoCache archivoCache;
    protected Calendar fechaConsulta;

    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }

    public void setUsuario(String usuario){
        this.nombreUsuario = usuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setFechaConsulta(Calendar fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public Calendar getFechaConsulta(){
        return fechaConsulta;
    }

    public void setPuntoActual(LatLng puntoActual){
        this.puntoActual = puntoActual;
    }

    public LatLng getPuntoActual() {
        return puntoActual;
    }

    public abstract void Ejecutar();
}
