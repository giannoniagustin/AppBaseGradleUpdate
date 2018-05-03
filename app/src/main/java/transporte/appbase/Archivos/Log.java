package transporte.appbase.Archivos;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Created by Agustín on 17/09/2015.
 */
public class Log {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    static public void setup() throws IOException {

        try {


            FileOutputStream f,fH;
            File dir = new File (ControladorArchivos.PATH_LOG_USER_TXT);
            dir.mkdirs();
            File file = new File(dir,ControladorArchivos.NOMBRE_LOG_USER_TXT);
            File fileH = new File(dir,ControladorArchivos.NOMBRE_LOG_USER_HTML);
            f = new FileOutputStream(file,true);
            fH = new FileOutputStream(fileH,true);


            LOGGER.setLevel(Level.ALL);
            fileTxt = new FileHandler(ControladorArchivos.PATH_LOG_USER_TXT+"/"+ControladorArchivos.NOMBRE_LOG_USER_TXT,true);

            fileHTML = new FileHandler(ControladorArchivos.LOG_USER_HTML,true);


            // create a TXT formatter
            formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            formatterHTML = new MyHtmlFormatter();
            fileHTML.setFormatter(formatterHTML);
            LOGGER.addHandler(fileTxt);
            LOGGER.addHandler(fileHTML);
            LOGGER.info("Se Crearon los archivos de log");



            // create an HTML formatter


        } catch (Exception e){

            LOGGER.setLevel(Level.SEVERE);
            LOGGER.severe("No se crearon los archivos Excepcion");


            e.toString();
        }
    }




}
