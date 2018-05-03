package transporte.appbase.Rutinas;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by Agustín on 28/03/2016.
 */
public class PuntoEstadia {

    private LatLng punto;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;

    public PuntoEstadia(LatLng punto, String nombre, String fechaInicio, String fechaFin) {
        this.punto = punto;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LatLng getPunto() {
        return punto;
    }

    public void setPunto(LatLng punto) {
        this.punto = punto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
