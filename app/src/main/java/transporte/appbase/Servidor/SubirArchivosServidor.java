package transporte.appbase.Servidor;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import transporte.appbase.Archivos.Log;


//Clase asincronica para enviar un archivo al servidor
public class SubirArchivosServidor extends AsyncTask <HashMap<String, Object>, Void, InputStream> {


    private HashMap<String, Object> parametros;
    private ServidorCommand enviarArchivoCommand;
    private InputStream salida;

    public SubirArchivosServidor(ServidorCommand enviarArchivoCommand) {
        this.enviarArchivoCommand = enviarArchivoCommand;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected  InputStream doInBackground(HashMap<String, Object>... params) {
        try {

            salida = null;
            String responseStr = "nada";
            String url;

            parametros = params[0];

            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar= (ArrayList<NameValuePair>) parametros.get("parametrosEnvio");
            // new HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            url = parametros.get("url").toString();
            url += "?";

            String paramString = URLEncodedUtils.format(parametrosConcatenar, "utf-8");//concatena solamente el usuario
            url += paramString;
            Log.LOGGER.info("Var Url: " + url);
            // post header
            HttpPost httpPost = new HttpPost(url);

            String pathArchivo = (String) parametros.get("rutaArchivo");

            File dir = new File(pathArchivo);
            dir.mkdirs();

            Log.LOGGER.info("Path: " + pathArchivo);

            String nombreArchivo = (String) parametros.get("nombreArchivo");
            File file = new File(dir, nombreArchivo);


            FileBody fileBody = new FileBody(file);

            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileBody);
            httpPost.setEntity(reqEntity);

            // execute HTTP post request
            HttpResponse response = null;

            response = httpClient.execute(httpPost);

            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                responseStr = EntityUtils.toString(resEntity).trim();
                salida = new ByteArrayInputStream(responseStr.getBytes());

                // you can add an if statement here and do other actions based
                // on response
            }
            Log.LOGGER.info("respuesta servidor: " + responseStr);



            return salida;
        } catch (Exception e){
            Log.LOGGER.severe(e.toString());
            String falla = "0"; //ojo constante

            salida = new ByteArrayInputStream(falla.getBytes());
            String ss= salida.toString();
            return salida;

        }

    }

    @Override
    protected void onPostExecute(InputStream result) {
        try {

            //Usar el resultado 0 (no enviado) o 1(enviado) para actualizar la base sqLite o 2(archivo existente en servidor)
            enviarArchivoCommand.procesarRespuesta(result);
        }catch
                (Exception e){Log.LOGGER.severe(e.toString() + "Parametro: " + result);}
    }



}