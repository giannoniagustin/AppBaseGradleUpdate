package transporte.appbase.Servidor;

import android.content.ContentValues;

import java.io.InputStream;

import transporte.appbase.Archivos.Archivo;
import transporte.appbase.Archivos.ControladorArchivos;
import transporte.appbase.Archivos.Log;
import transporte.appbase.DBMananger.BdHelper;
import transporte.appbase.DBMananger.DataBaseMananger;
import transporte.appbase.Parser.ManejadorParser;

/**
 * Created by soled_000 on 16/10/2015.
 */
public class EnviarArchivoTrackCommand extends EnviarArchivoCommand {

//    private Archivo archivo;


 /*   public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }
 */


    @Override
    public void procesarRespuesta(InputStream s) {


        try {

       //     BdHelper bdHelper = new BdHelper(contexto, ControladorArchivos.BD);
       //     ContentValues data = new ContentValues();
     //      controladorArchivos.actualizarBaseArchivoEnviado(archivo.getNombre());

           // int valor = Integer.parseInt(result.trim());
            ManejadorParser manejadorParser = new ManejadorParser();

            Integer valor = manejadorParser.parsearRespuesta(s);
            if (valor == 1) {//Envio con exito

                controladorArchivos.actualizarBaseArchivoEnviado(super.archivo.getNombre());
            //    data.put(DataBaseMananger.CN_ENVIADO, "1");
            //    bdHelper.UpdateTabla(DataBaseMananger.TABLE_NAME, data, DataBaseMananger.CN_NAME + "= '" + super.archivo.getNombre() + "'");
                Log.LOGGER.info("Respuesta servidor archivo enviado: " + super.archivo.getNombre());

                //      new File(ControladorArchivos.PATH_ARCH_TRACK, nombreArchivo).delete();//Borra el archivo enviado
              //  this.borrarArchivo();
                super.archivo.borrarArchivo();
                Log.LOGGER.info("Se borro el archivo "+ super.archivo.getNombre());

            } else {
                if (valor == 0 || valor == 2) {//Error al enviar

                    controladorArchivos.actualizarBaseIntentos(super.archivo.getNombre());
               //     bdHelper.IncrementarIntentos(super.archivo.getNombre());

                }
            }


        } catch (Exception e) {

            Log.LOGGER.severe(e.toString());

            //Incrementa en uno la cantidad de intentos de envio
        //    BdHelper bdHelper = new BdHelper(contexto, ControladorArchivos.BD);
        //    bdHelper.IncrementarIntentos(super.archivo.getNombre());
            controladorArchivos.actualizarBaseIntentos(super.archivo.getNombre());
        }
    }
}
