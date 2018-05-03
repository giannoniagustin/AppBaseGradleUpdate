package transporte.appbase.Rutinas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import transporte.appbase.Ruta.ConjuntoTrazas;

public class RutinaG implements Parcelable {

    private Integer idRutina;
    private String pOrigen;
    private String pDestino;
    private LatLng origenRutina;
    private LatLng destinoRutina;
    private String nombre;
    private Calendar fechaInicio;
    private Calendar fechaFin;
    private ConjuntoTrazas trazas;

    public Integer getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(Integer idRutina) {
        this.idRutina = idRutina;
    }

    protected RutinaG(Parcel in) {
        nombre=in.readString();
        pOrigen = in.readString();
        pDestino = in.readString();
        origenRutina = in.readParcelable(LatLng.class.getClassLoader());
        destinoRutina = in.readParcelable(LatLng.class.getClassLoader());
        trazas= in.readParcelable(ConjuntoTrazas.class.getClassLoader());
        fechaInicio= (Calendar) in.readSerializable();
        fechaFin = (Calendar) in.readSerializable();

    }

    public RutinaG() {
    }

    public static final Creator<RutinaG> CREATOR = new Creator<RutinaG>() {
        @Override
        public RutinaG createFromParcel(Parcel in) {
            return new RutinaG(in);
        }

        @Override
        public RutinaG[] newArray(int size) {
            return new RutinaG[size];
        }
    };

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

    public String getpOrigen() {
        return pOrigen;
    }

    public void setpOrigen(String pOrigen) {
        this.pOrigen = pOrigen;
    }

    public String getpDestino() {
        return pDestino;
    }

    public void setpDestino(String pDestino) {
        this.pDestino = pDestino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Calendar getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Calendar fecha) {
        this.fechaInicio = fecha;
    }

    public Calendar getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Calendar fecha) {
        this.fechaFin = fecha;
    }


    public ConjuntoTrazas getTrazas() {
        return trazas;
    }

    public void setTrazas(ConjuntoTrazas trazas) {
        this.trazas = trazas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
   //     dest.writeString(pOrigen + pDestino); borrar
        dest.writeString(pOrigen);
        dest.writeString(pDestino);
        dest.writeParcelable(origenRutina, flags);
        dest.writeParcelable(destinoRutina, flags);
        dest.writeParcelable(trazas, flags);
        dest.writeSerializable(fechaInicio);
        dest.writeSerializable(fechaFin);

    }
}


