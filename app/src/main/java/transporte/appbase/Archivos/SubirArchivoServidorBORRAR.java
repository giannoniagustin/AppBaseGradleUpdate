/*package transporte.appbase.Archivos;


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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


//Clase asincronica para enviar un archivo al servidor
public class SubirArchivoServidor  extends AsyncTask <HashMap<String, Object>, Void, String> {


    private HashMap<String, Object> parametros;
    private AsyncRespuestaEnvioArchivos valor = null;

    public SubirArchivoServidor () {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected  String doInBackground(HashMap<String, Object>... params) {
        try {

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

                // you can add an if statement here and do other actions based
                // on response
            }
            Log.LOGGER.info("respuesta servidor: " + responseStr);



            return responseStr;
        } catch (Exception e){

            Log.LOGGER.severe(e.toString());
            return "0";
        }

    }

    @Override
    protected void onPostExecute(String result) {
        try {
            super.onPostExecute(result);
            //Usar el resultado 0 (no enviado) o 1(enviado) para actualizar la base sqLite o 2(archivo existente en servidor)
            getValor().procesarRespuestaServidor(result);
        }catch
                (Exception e){Log.LOGGER.severe(e.toString() + "Parametro: " + result);}
    }

    public AsyncRespuestaEnvioArchivos getValor(){
        return valor;
    }
    public void setValor(AsyncRespuestaEnvioArchivos v){
        valor= v;
    }

}*/