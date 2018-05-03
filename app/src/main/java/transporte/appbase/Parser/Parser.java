package transporte.appbase.Parser;

/**
 * Created by Agustín on 07/04/2016.
 */


        import java.io.InputStream;
        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.Scanner;
        import java.util.StringTokenizer;


public class Parser {


    public static DateFormat df;
    public  static SimpleDateFormat formataño= new SimpleDateFormat("yyyy");
    public  static  SimpleDateFormat formatdia = new SimpleDateFormat("dd");
    public  static SimpleDateFormat formathora= new SimpleDateFormat("HH");
    public  static SimpleDateFormat formatminutos = new SimpleDateFormat("mm");
    public  static SimpleDateFormat formatsegundos = new SimpleDateFormat("ss");
    public  static SimpleDateFormat  formatmes = new SimpleDateFormat("MM");
    public  static SimpleDateFormat  formatFecha = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public Parser() {
        super();

        df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    }



   /*
    //metodo si le paso un punto que retorne string de linea formateado P:_
    public String coordenadasTrack(String lat, String longi) {
        return "_P:" + "," + lat + "," + longi + "," + Util.ALTITUD;
    }

    //metodo si le paso una fecha que retorne string de linea formateado F:_
    public static String fechaToString(String mes, String dia, String año, String hr, String minutos) {
        return "_D:" + dia + "/" + mes + "/" + año + "/"+ hr + "/" + minutos;
    }
    //metodo si le paso una fecha que retorne string de linea formateado F:_
    public static String actividadToString(String actividad) {
        return "_A:" + actividad+Util.SALTO_LINEA;
    }*/



    public static String mesFecha(Date fecha){
        return	formatmes.format(fecha);


    }
    public static String añoFecha(Date fecha){
        return	formataño.format(fecha);


    }
    public static String diaFecha(Date fecha){
        return	formatdia.format(fecha);


    }
    public static String HoraFecha(Date fecha){
        return	formathora.format(fecha);


    }
    public static String MinutoFecha(Date fecha){
        return	formatminutos.format(fecha);


    }
    public static String SegundosFecha(Date fecha){
        return	formatsegundos.format(fecha);


    }
    public static String Fecha(Date fecha){
        return	formatFecha.format(fecha);


    }
    public static String fechaArchivo(Date fecha)
    {
        return mesFecha(fecha)+"_"+diaFecha(fecha)+"_"+añoFecha(fecha);

    }
    public static String fechaToString(Date fecha)
    {
        return añoFecha(fecha)+"-"+mesFecha(fecha)+"-"+diaFecha(fecha)+" "+HoraFecha(fecha)+":"+MinutoFecha(fecha)+":"+SegundosFecha(fecha);

    }



}

