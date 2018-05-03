package transporte.appbase.Archivos;


import transporte.appbase.Configuracion.Preferencias;

/**
 * Created by Agustín on 30/09/2015.
 */
public class ArchivoLog extends ElementoArchivo {
    private Preferencias preferencia;

    public ArchivoLog(String extension, String path, String nombre) {
        super(extension, path, nombre);
        preferencia=new Preferencias();
    }

 /*   @Override
    public void procesarRespuestaServidor(String result) {


        Log.LOGGER.info("Resultado envio archivo log: "+result);

    }
    */
/*
    @Override
    public void enviarArchivo(Context pcontexto) {

        try {

            Log.LOGGER.info("Archivo a enviar: " + nombre);
            //llamado asincronico

            String usuario = preferencia.getUsuario(pcontexto);
            Log.LOGGER.info("Usuario archivo: " + usuario);

            Log.LOGGER.info("Path archivo: " + path);


            ArrayList<NameValuePair> parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("usuario", usuario));

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url",ControladorArchivos.URL_SERVIDOR);
            parametros.put("nombreArchivo", nombre);
            parametros.put("rutaArchivo", path);
            parametros.put("parametrosEnvio", parametrosConcatenar);

            SubirArchivoServidor enviarArchivo = new SubirArchivoServidor();
            enviarArchivo.setValor(this);
            enviarArchivo.execute(parametros, null, null);

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString() + "Archivo " + nombre + "Path: " + path);
        }
    }*/
}
