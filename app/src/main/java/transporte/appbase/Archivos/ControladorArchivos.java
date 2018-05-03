package transporte.appbase.Archivos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import transporte.appbase.Configuracion.Preferencias;
import transporte.appbase.DBMananger.BdHelper;
import transporte.appbase.DBMananger.DataBaseMananger;
import transporte.appbase.Global;
import transporte.appbase.ManejadorServidor;
import transporte.appbase.Servidor.EnviarArchivoCommand;
import transporte.appbase.Servidor.EnviarArchivoLogCommand;
import transporte.appbase.Servidor.EnviarArchivoTrackCommand;


public class ControladorArchivos /*implements AsyncRespuestaEnvioArchivos*/{

    private static final int BUFFER_SIZE = 1024;
    private String nombreArchivo;
    private Context contexto;
    private Global global;
    private ManejadorServidor manejadorServidor;
    private Preferencias preferencia;
    public     final static  String  TARJETA_MEMORIA=android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    public     final static  String  RAIZ_APP=TARJETA_MEMORIA+"/AppBase";
    public     final static  String  DATA_USER=RAIZ_APP+"/DataUser";
    public     final static  String  PATH_LOG_USER_TXT=RAIZ_APP+"/Log";
    public     final static String   NOMBRE_LOG_USER_TXT="Logging.txt";
    public     final static String   NOMBRE_LOG_USER_HTML="Logging.txt";
    public     final static  String  LOG_USER_HTML=RAIZ_APP+"/Log"+"/Logging.html";
    public     final static  String  BD=RAIZ_APP+"/BD/"+BdHelper.DB_NAME;
    public     final static  String PATH_ARCH_TRACK =RAIZ_APP+"/ArchTrack";
    public     final static String   NOMBRE_ARCH_TRACK="AppCortes_track_historia_";
    public final static String EXTENSION_ZIP=".gz";
    public final static String SALTO_LINEA="\r\n";

    private BdHelper bdHelper;

    public ControladorArchivos(Context context){

            this.nombreArchivo = "";
            this.preferencia = new Preferencias();
            this.contexto = context;

        //nuevo
        bdHelper = new BdHelper(contexto,ControladorArchivos.BD);

      //OJO CON ESTE NEEEEEEEEW
     //   manejadorServidor = new ManejadorServidor();
        manejadorServidor = Global.instance().getManejadorServidor();


    }


    //   public ControladorArchivos(Context context){contexto=context;}

    //Crea el command para enviar el archivo de trackeo al servidor
    public void enviarArchivoServidor(Archivo archivo/*String nombreArchivo, String path*/){

        EnviarArchivoCommand enviarArchivoTrackCommand = new EnviarArchivoTrackCommand();
        enviarArchivoTrackCommand.setControladorArchivos(this);
        String nombreUsuario = preferencia.getUsuario(contexto);
        enviarArchivoTrackCommand.setUsuario(nombreUsuario);
    //    Archivo archivo = new Archivo("zip",path,nombreArchivo);
        enviarArchivoTrackCommand.setArchivo(archivo);

        manejadorServidor.agregarElemento(enviarArchivoTrackCommand);


   /*
        try {

            Log.LOGGER.info("Archivo a enviar: " + nombreArchivo);
            //llamado asincronico
            String usuario = preferencia.getUsuario(contexto);
            Log.LOGGER.info("Usuario archivo: "+usuario);
            this.nombreArchivo = nombreArchivo;
            this.pathArchivo=path;
            Log.LOGGER.info("Path archivo: " + path);
     //       this.usuarioActual.add((new BasicNameValuePair("usuario", usuario)));

            ArrayList<NameValuePair> parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("usuario", usuario));

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_SERVIDOR);
            parametros.put("nombreArchivo", nombreArchivo);
            parametros.put("rutaArchivo", path);
            parametros.put("parametrosEnvio", parametrosConcatenar);

            SubirArchivoServidor enviarArchivo = new SubirArchivoServidor();
            enviarArchivo.setValor(this);
            enviarArchivo.execute(parametros, null, null);

        }catch (Exception e){
            Log.LOGGER.severe(e.toString()+"Archivo "+nombreArchivo+"Path: "+path);}
*/
    }

    public void zippearArchivo(String rutaArchivo, String rutaArchivoComprimido) throws Exception {
        // objetos en memoria
        FileInputStream fis = null;
        FileOutputStream fos = null;
        ZipOutputStream zipos = null;

        // buffer
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            // ruta y nombre del fichero a comprimir
            fis = new FileInputStream(rutaArchivo);
            // ruta y nombre del fichero contenedor del zip
            fos = new FileOutputStream(rutaArchivoComprimido);
            // fichero comprimido
            zipos = new ZipOutputStream(fos);
            //     String pFile = RUTA_ARCHIVO.substring(RUTA_ARCHIVO.lastIndexOf(File.separator +1));
            ZipEntry entry = new ZipEntry(rutaArchivo.substring(rutaArchivo.lastIndexOf("/") + 1));
            //    ZipEntry zipEntry = new ZipEntry(pFile);
            zipos.putNextEntry(entry);
            int len = 0;
            // zippear
            while ((len = fis.read(buffer, 0, BUFFER_SIZE)) != -1)
                zipos.write(buffer, 0, len);
            // volcar la memoria al disco
            zipos.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            // cerramos los files
            zipos.close();
            fis.close();
            fos.close();
        } // end try
    } // end Zippear


    //Revisa en la BD los archivos que tienen el valor 0 en el campo ENVIADO para enviarlos al servidor
    public void enviarArchivosPendientes() {
        try {

            //HACER CONSULTA DE LOS ARCHIVOS QUE NO FUERON ENVIADOS

            BdHelper bdHelper = new BdHelper(contexto, BD);
            Cursor registros = bdHelper.ConsultaTablaArchNoEnviados();

            while (registros.moveToNext()) {

                String nombreArchivo = registros.getString(registros.getColumnIndex(DataBaseMananger.CN_NAME));
                Archivo archivo=new Archivo("",/*ControladorArchivos.PATH_ARCH_TRACK*/"/storage/emulated/0/AppBase/ArchTrack",nombreArchivo);

            //    archivo.enviarArchivo(contexto); COMENTADO SOLE
                enviarArchivoServidor(archivo); //AGREGADO SOLE

                Log.LOGGER.info("Nombre archivo:"+nombreArchivo);
            //    this.enviarArchivoServidor(nombreArchivo, PATH_ARCH_TRACK);

           //Agregado para que envie los LOG todos los dias tmb.
            this.enviarArchivoLog();

            }
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());}
    }

    //Prepara el Command para enviar el archivo de Log al servidor
    public void enviarArchivoLog(){
        try {

            ArchivoLog archivoLog=new ArchivoLog(".txt",PATH_LOG_USER_TXT,NOMBRE_LOG_USER_TXT);
        //    archivoLog.enviarArchivo(contexto); COMENTADO SOLE

            //---AGREGADO SOLE-----
            EnviarArchivoCommand enviarArchivoLogCommand = new EnviarArchivoLogCommand();
            enviarArchivoLogCommand.setControladorArchivos(this);
            String nombreUsuario = preferencia.getUsuario(contexto);
            enviarArchivoLogCommand.setUsuario(nombreUsuario);

            enviarArchivoLogCommand.setArchivo(archivoLog);

            manejadorServidor.agregarElemento(enviarArchivoLogCommand);
            //-----------


        }catch (Exception e){

            Log.LOGGER.severe(e.toString());
        }
    }

    //Una vez terminado el dia, este metodo cambia el campo en la BD de -1 a 0 para que sea un archivo a enviar y luego da la orden de EnviarArchivosPendientes
    public void procesarArchivoTrack(String nombreArchivo/*,Context pcontexto*/){

        try {
            Log.LOGGER.entering("procesarArchivoTrack", "");
            //Inserta en la BD
            //Inserta un registro con el archivo que esta para enviar
            BdHelper bdHelper= new BdHelper(contexto,BD);
          //  contexto=pcontexto;
            Date fechaHoy=Calendar.getInstance().getTime();
            ContentValues data=new ContentValues();
            data.put(DataBaseMananger.CN_ENVIADO, "0");
            bdHelper.UpdateTabla(DataBaseMananger.TABLE_NAME, data, DataBaseMananger.CN_ENVIADO + "=-1");


            ContentValues registro = new ContentValues();
            registro.put("nombre", nombreArchivo);
            registro.put("fecha", fechaHoy.toString());
            registro.put("enviado", "-1");//Archivo en curso del dia
            registro.put("intento", "0");
            bdHelper.InsertTabla(DataBaseMananger.TABLE_NAME, registro);
            bdHelper.CloseBd();
        //Fin

        this.enviarArchivosPendientes();

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString() + "Nombre archivo " + nombreArchivo);
        }


    }

/* LAS RESPUESTAS DEL SERVIDOR SE TRABAJAN EN CADA COMMAND
    public  void procesarRespuestaServidor(String result) {

        try {

            BdHelper bdHelper = new BdHelper(contexto, BD);
            ContentValues data = new ContentValues();
            int valor = Integer.parseInt(result.trim());
            if (valor == 1) {//Envio con exito


                data.put(DataBaseMananger.CN_ENVIADO, "1");
                bdHelper.UpdateTabla(DataBaseMananger.TABLE_NAME, data, DataBaseMananger.CN_NAME + "= '" + nombreArchivo + "'");
                Log.LOGGER.info("Respuesta servidor archivo enviado: " + nombreArchivo);

                new File(ControladorArchivos.PATH_ARCH_TRACK, nombreArchivo).delete();//Borra el archivo enviado
                Log.LOGGER.info("Se borro el archivo "+nombreArchivo);

            } else {
                if (valor == 0 || valor == 2) {//Error al enviar

                    bdHelper.IncrementarIntentos(nombreArchivo);


                }
            }


        } catch (Exception e) {

            Log.LOGGER.severe(e.toString());

            //Incrementa en uno la cantidad de intentos de envio
            BdHelper bdHelper = new BdHelper(contexto, BD);
            bdHelper.IncrementarIntentos(nombreArchivo);
        }
    }
    */

    public Archivo crearArchivoTrack(){

        Archivo archivoTrack=new Archivo(null,ControladorArchivos.PATH_ARCH_TRACK,null);

        return archivoTrack;



    }

    //---------------NUEVO SOLE----------------------
    public void actualizarBaseArchivoEnviado(String nombre){

        ContentValues data = new ContentValues();
        data.put(DataBaseMananger.CN_ENVIADO, "1");
        bdHelper.UpdateTabla(DataBaseMananger.TABLE_NAME, data, DataBaseMananger.CN_NAME + "= '" + nombre + "'");
    }

    //Incrementa en uno la cantidad de intentos de envio
    public void actualizarBaseIntentos(String nombre){

        bdHelper.IncrementarIntentos(nombre);
    }

}
