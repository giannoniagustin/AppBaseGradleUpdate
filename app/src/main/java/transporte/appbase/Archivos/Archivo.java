package transporte.appbase.Archivos;

import android.content.Context;
import android.text.format.DateFormat;
import java.nio.charset.Charset;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by Agustín on 24/09/2015.
 */
public class Archivo extends ElementoArchivo  {
    private Context contexto;



    public Archivo(String extension, String path, String nombre) {

            super(extension, path, nombre);
            if (nombre == null) {
                Date fechaHoy = Calendar.getInstance().getTime();
                String nombreArchivoHoy = ControladorArchivos.NOMBRE_ARCH_TRACK + DateFormat.format("MM_dd_yyyy", fechaHoy.getTime()) + ControladorArchivos.EXTENSION_ZIP;
                this.nombre = nombreArchivoHoy;
                this.fechaCreacion = Calendar.getInstance().getTime();
                crearArchivo();
            }


    }

/*
    public void enviarArchivo(Context pcontexto){
        //Preparar parametros

        try {
            contexto=pcontexto;

            Log.LOGGER.info("Archivo a enviar: " + nombre);
            //llamado asincronico
            Preferencias preferencia=new Preferencias();
            String usuario = preferencia.getUsuario(contexto);
            Log.LOGGER.info("Usuario archivo: "+usuario);

            Log.LOGGER.info("Path archivo: " + path);
            //       this.usuarioActual.add((new BasicNameValuePair("usuario", usuario)));

            ArrayList<NameValuePair> parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("usuario", usuario));

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", ControladorArchivos.URL_SERVIDOR);
            parametros.put("nombreArchivo", nombre);
            parametros.put("rutaArchivo", path);
            parametros.put("parametrosEnvio", parametrosConcatenar);

            SubirArchivoServidor enviarArchivo = new SubirArchivoServidor();
            enviarArchivo.setValor(this);
            enviarArchivo.execute(parametros, null, null);

        }catch (Exception e){
            Log.LOGGER.severe(e.toString()+"Archivo "+nombre+"Path: "+path);}

    }
*/


    public void grabarArchivo(String caracteres){
        try {

            fOutputStream.write(caracteres.getBytes(Charset.forName("UTF-8")));
            fOutputStream.flush();
            fOutputStream.close();
            fOutputStream.close();
        }catch (Exception e){

            Log.LOGGER.severe(e.toString());
        }
    }
  /*  public void grabarArchivoZip(String caracteres){

        try {
          String fechaCreacion=  DateFormat.format("MM_dd_yyyy", this.fechaCreacion).toString();
            String fechaActual=DateFormat.format("MM_dd_yyyy", Calendar.getInstance().getTime()).toString();


            if (fechaCreacion.equals(fechaActual)) {

                zipFile.write(caracteres.getBytes(Charset.forName("UTF-8")));
                zipFile.flush();
               // zipFile.close();
                fOutputStream.close();
            }
            else
            {

                nombre=ControladorArchivos.NOMBRE_ARCH_TRACK+ DateFormat.format("MM_dd_yyyy", Calendar.getInstance().getTime()) + ControladorArchivos.EXTENSION_ZIP;
                crearArchivo();
                zipFile.write(caracteres.getBytes(Charset.forName("UTF-8")));
                zipFile.flush();
             //   zipFile.close();
                fOutputStream.close();

            }

        }catch (Exception e){

            Log.LOGGER.severe(e.toString());
        }

    }*/
    public void cerrarArchivo()
    {
        try {
            zipFile.close();
        }catch (Exception e){

            Log.LOGGER.severe(e.toString());
        }



    }
/*
    @Override
    public void procesarRespuestaServidor(String result) {
        try {

            BdHelper bdHelper = new BdHelper(contexto,ControladorArchivos.BD);
            ContentValues data = new ContentValues();
            int valor = Integer.parseInt(result.trim());
            if (valor == 1) {//Envio con exito


                data.put(DataBaseMananger.CN_ENVIADO, "1");
                bdHelper.UpdateTabla(DataBaseMananger.TABLE_NAME, data, DataBaseMananger.CN_NAME + "= '" + nombre + "'");
                Log.LOGGER.info("Respuesta servidor archivo enviado: " + nombre);

          //      new File(ControladorArchivos.PATH_ARCH_TRACK, nombreArchivo).delete();//Borra el archivo enviado
                this.borrarArchivo();
                Log.LOGGER.info("Se borro el archivo "+nombre);

            } else {
                if (valor == 0 || valor == 2) {//Error al enviar


                    bdHelper.IncrementarIntentos(nombre);

                }
            }


        } catch (Exception e) {

            Log.LOGGER.severe(e.toString());

            //Incrementa en uno la cantidad de intentos de envio
            BdHelper bdHelper = new BdHelper(contexto, ControladorArchivos.BD);
            bdHelper.IncrementarIntentos(nombre);
        }
    }
*/

}

