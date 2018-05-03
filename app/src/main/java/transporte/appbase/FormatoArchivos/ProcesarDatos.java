package transporte.appbase.FormatoArchivos;

public class ProcesarDatos {

    private int altitud = 197;

    //metodo si le paso un punto que retorne string de linea formateado P:_
    public String coordenadasTrack(String lat, String longi) {
        return "_P:" + "," + lat + "," + longi + "," + altitud;
    }

    //metodo si le paso una fecha que retorne string de linea formateado F:_
    public String fechaToString(String mes, String dia, String año, String hr, String minutos) {
        return "_D:" + dia + "/" + mes + "/" + año + "/"+ hr + "/" + minutos;
    }
}
