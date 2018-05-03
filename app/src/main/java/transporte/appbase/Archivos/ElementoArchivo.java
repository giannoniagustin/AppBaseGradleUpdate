package transporte.appbase.Archivos;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Agustín on 29/09/2015.
 */
public abstract class ElementoArchivo /*implements AsyncRespuestaEnvioArchivos */ {

    protected String nombre;
    protected String extension;
    protected Date fechaCreacion;
    protected File file;
    protected FileOutputStream fOutputStream;
    protected GZIPOutputStream zipFile;
    protected String path;
    protected boolean existente;

    public ElementoArchivo(String extension, String path, String nombre) {
        this.extension = extension;
        this.path = path;
        this.nombre = nombre;
    }

    public boolean isExistente() {
        return existente;
    }

    public void setExistente(boolean existente) {
        this.existente = existente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getExtension() {
        return extension;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public void crearArchivo() {
        try {
            File dir = new File(ControladorArchivos.PATH_ARCH_TRACK);
            dir.mkdirs();
            file = new File(dir, nombre);
            existente = file.exists();


            fOutputStream = new FileOutputStream(file, true);


            crearArchivoZip();


        } catch (Exception e) {

            Log.LOGGER.severe(e.toString());
        }


    }

    protected void crearArchivoZip() {

        try {


            zipFile = new GZIPOutputStream(new BufferedOutputStream(fOutputStream));
        } catch (Exception e) {

            Log.LOGGER.severe(e.toString());
        }
    }


    public void enviarArchivo(Context contexto) {
    }


    public void grabarArchivoZip(String caracteres) throws IOException {

        try {
            caracteres = caracteres + ControladorArchivos.SALTO_LINEA;
            fOutputStream = new FileOutputStream(file, true);
            zipFile = new GZIPOutputStream(new BufferedOutputStream(fOutputStream));
            zipFile.write(caracteres.getBytes(Charset.forName("UTF-8")));

            zipFile.flush();
            zipFile.close();
            fOutputStream.close();


        } catch (Exception e) {
            zipFile.flush();
            zipFile.close();
            fOutputStream.close();

            Log.LOGGER.severe(e.toString());
        }
    }

    public boolean borrarArchivo() {
        try {

            return new File(ControladorArchivos.PATH_ARCH_TRACK, nombre).delete();
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return false;
        }
    }


}
