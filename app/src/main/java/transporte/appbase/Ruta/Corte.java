package transporte.appbase.Ruta;

import android.os.Parcel;
import android.os.Parcelable;

public class Corte extends Punto implements Parcelable {
    private int idpoint;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
//    private int tipo;
    private String nombreIcono;


    public Corte(int idpoint, double latitude, double longitude, String fechaCreacion, String fechaInicio, String fechaFin, String descripcion,/*int tipo*/String nombreIcono) {
        super(latitude,longitude,fechaCreacion);
        this.idpoint = idpoint;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
    //    this.tipo = tipo;
        this.nombreIcono = nombreIcono;

    }


    public Corte(Parcel in){
        super(in);
        idpoint = in.readInt();
        fechaInicio = in.readString();
        fechaFin = in.readString();
        descripcion = in.readString();
        nombreIcono = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Corte> CREATOR = new Creator<Corte>() {
        @Override
        public Corte createFromParcel(Parcel in) {
            return new Corte(in);
        }

        @Override
        public Corte[] newArray(int size) {
            return new Corte[size];
        }
    };

    public int getIdpoint() {
        return idpoint;
    }

    public void setIdpoint(int idpoint) {
        this.idpoint = idpoint;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

/*
    @Override
    public void dibujar(GoogleMap mGeneral) { //no va maaaaaaaaaas
      /*  try {

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(super.latLng);
            markerOptions.title(descripcion);
            //   String tipoIcono =
            markerOptions.icon(BitmapDescriptorFactory.fromResource(Iconos.getIcono(nombreIcono)));

            mGeneral.addMarker(markerOptions);

        } catch (Exception e) {

            Log.LOGGER.severe(e.toString() + "Parametros: MapaGenral" + mGeneral.toString() + "Iconmno :" +  Iconos.getIcono(nombreIcono));
        }
*/
/*
    }

    @Override
    public void borrar(GoogleMap mGeneral) {
        //implementar
    }

        */
    public String getNombreIcono() {
        return nombreIcono;
    }

    public void setNombreIcono(String nombreIcono) {
        this.nombreIcono = nombreIcono;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(idpoint);
        dest.writeString(fechaInicio);
        dest.writeString(fechaFin);
        dest.writeString(descripcion);
        dest.writeString(nombreIcono);

    }
}
