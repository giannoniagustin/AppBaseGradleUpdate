package transporte.appbase.Interfaz;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by soled_000 on 04/11/2015.
 */
public class MarcadorRutina extends Marcador {
    String fecha;

    public MarcadorRutina(LatLng destino, String descripcion, String nombreIcono, String fecha) {

        super(destino,descripcion, nombreIcono);
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}
