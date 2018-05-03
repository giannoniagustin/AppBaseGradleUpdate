/*package transporte.appbase.Rutinas;




import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Rutina implements Parcelable{

    private Integer idRutina;
    private String nombreRutina;
    private LatLng origenRutina;
    private LatLng destinoRutina;
    private String fechaInicio; //contiene la hora tmb.
    private String fechaFin; //NUEVOOO
    private String direccionDestino;

    public Rutina(String nombreRutina, LatLng origenRutina, LatLng destinoRutina, String fechaInicio,String fechaFin, String direccionDestino) {
        this.nombreRutina = nombreRutina;
        this.origenRutina = origenRutina;
        this.destinoRutina = destinoRutina;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idRutina = null; //no nos importa el id cuando se crea la rutina. No importa para editarla!!
        this.direccionDestino = direccionDestino;

    }
    //se necesita para construir el JSON de rutina del servidor.
    public Rutina(Integer idRutina,String nombreRutina, LatLng origenRutina, LatLng destinoRutina, String fechaInicio,String fechaFin,String direccionDestino) {
        this.nombreRutina = nombreRutina;
        this.origenRutina = origenRutina;
        this.destinoRutina = destinoRutina;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idRutina = idRutina;
        this.direccionDestino = direccionDestino;
    }

    protected Rutina(Parcel in) {
        idRutina = in.readInt(); //agregado para ser parseado junto con los datos obtenidos del servidor.
        nombreRutina = in.readString();
        origenRutina = in.readParcelable(LatLng.class.getClassLoader());
        destinoRutina = in.readParcelable(LatLng.class.getClassLoader());
        fechaInicio = in.readString();
        fechaFin = in.readString();
        direccionDestino = in.readString();
    }

    public Integer getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(Integer idRutina) {
        this.idRutina = idRutina;
    }

    public String getNombreRutina() {
        return nombreRutina;
    }

    public void setNombreRutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }

    public LatLng getOrigenRutina() {
        return origenRutina;
    }

    public void setOrigenRutina(LatLng origenRutina) {
        this.origenRutina = origenRutina;
    }

    public LatLng getDestinoRutina() {
        return destinoRutina;
    }

    public void setDestinoRutina(LatLng destinoRutina) {
        this.destinoRutina = destinoRutina;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fecha) {
        this.fechaInicio = fecha;
    }


    public static final Creator<Rutina> CREATOR = new Creator<Rutina>() {
        @Override
        public Rutina createFromParcel(Parcel in) {
            return new Rutina(in);
        }

        @Override
        public Rutina[] newArray(int size) {
            return new Rutina[size];
        }
    };

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        fechaFin = fechaFin;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idRutina); //nuevo para ser parseado
        dest.writeString(nombreRutina);
        dest.writeParcelable(origenRutina, flags);
        dest.writeParcelable(destinoRutina, flags);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeString(direccionDestino);
    }

    public void setDireccionDestino(String d){
        direccionDestino = d;
    }
    public String getDireccionDestino(){
        return direccionDestino;
    }
}
*/