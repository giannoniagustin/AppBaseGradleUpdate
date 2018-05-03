package transporte.appbase.Util;



import java.util.Date;
import java.text.DateFormat;

import java.text.SimpleDateFormat;


/**
 * Created by Agustn on 26/07/2015.
 */
public class Adaptador {
    public static DateFormat df;
    public  static SimpleDateFormat formataño= new SimpleDateFormat("yyyy");
    public  static  SimpleDateFormat formatdia = new SimpleDateFormat("dd");
    public  static SimpleDateFormat formathora= new SimpleDateFormat("HH");
    public  static SimpleDateFormat formatminutos = new SimpleDateFormat("mm");
    public  static SimpleDateFormat formatsegundos = new SimpleDateFormat("ss");
    public  static SimpleDateFormat  formatmes = new SimpleDateFormat("MM");

    public Adaptador() {

    }

    public String formatoFecha(Date fecha,String formato)
    {
       // df =  new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        df =  new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      return  df.format(fecha);
    }
    public long fechaActual(){



        Date fecha = new Date();
        return fecha.getTime();
    }
}
