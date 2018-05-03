package transporte.appbase.Tracker;



import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import transporte.appbase.Archivos.Archivo;
import transporte.appbase.Archivos.ControladorArchivos;

public class TrackUbicacionHistoria{

    Context contexto;
   private Archivo archivoTrack;

  public TrackUbicacionHistoria(Context pcontext) {
      contexto=pcontext;

      ControladorArchivos controladorArchivos=new ControladorArchivos(contexto);
      archivoTrack=controladorArchivos.crearArchivoTrack();
      if (!archivoTrack.isExistente())
      {
          controladorArchivos.procesarArchivoTrack(archivoTrack.getNombre()/*,contexto*/);
          archivoTrack.setExistente(true);
      }

  }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public void escribir(String caracteres) {
        try {

            String fechaCreacion=  DateFormat.format("MM_dd_yyyy", archivoTrack.getFechaCreacion()).toString();
            String fechaActual=DateFormat.format("MM_dd_yyyy", Calendar.getInstance().getTime()).toString();

            if(fechaActual.equals(fechaCreacion)){

            archivoTrack.grabarArchivoZip(caracteres);}

            else{

               /* Archivo archivoAnterior;
                archivoAnterior=archivoTrack;*/

                ControladorArchivos controladorArchivos=new ControladorArchivos(contexto);
                archivoTrack=controladorArchivos.crearArchivoTrack();

               controladorArchivos.procesarArchivoTrack(archivoTrack.getNombre()/*,contexto*/);


            }

        } catch (Exception e) {

            transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());
        }

    }

    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                null), albumName);
        if (!file.mkdirs()) {
            Log.e("Error", "Directory not created");
        }
        return file;
    }

}
