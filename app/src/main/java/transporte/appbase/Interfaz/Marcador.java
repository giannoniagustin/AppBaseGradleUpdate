package transporte.appbase.Interfaz;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Agustín on 06/10/2015.
 */
public class Marcador {
    private LatLng LatLng;
    private String descripcion;
    private String nombreIcono;

    public Marcador(LatLng LatLng, String descripcion, String nombreIcono) {
        this.LatLng = LatLng;
        this.descripcion = descripcion;
        this.nombreIcono = nombreIcono;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLatLng(LatLng latLng) {
        this.LatLng = latLng;
    }

    public void setNombreIcono(String nombreIcono) {
        this.nombreIcono = nombreIcono;
    }

    public LatLng getLatLng() {
        return LatLng;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreIcono() {
        return nombreIcono;
    }
}
