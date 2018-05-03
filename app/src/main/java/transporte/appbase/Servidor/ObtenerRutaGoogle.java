package transporte.appbase.Servidor;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;


import transporte.appbase.Notificaciones;

public class ObtenerRutaGoogle extends AsyncTask<HashMap<String,Object>,Void,InputStream> {

    protected String url;
    protected HashMap<String, Object> parametros;
    private ServidorCommand serverCommand;
    protected InputStream salida;

    public ObtenerRutaGoogle(ServidorCommand serverCommand) {

        this.serverCommand = serverCommand;

    }

    @Override
    protected  InputStream doInBackground(HashMap<String,Object>... params) {

            HashMap<String,Object> parametros=params[0];
            url = parametros.get("url").toString();

            HttpPost httppost = new HttpPost(url);
            HttpClient cliente=new DefaultHttpClient();
            HttpContext contexto=new BasicHttpContext();

        try {
            HttpResponse response = cliente.execute(httppost, contexto);
            HttpEntity entity = response.getEntity();
            String a  = EntityUtils.toString(entity, HTTP.UTF_8);
            salida = new ByteArrayInputStream(a.getBytes());

            return salida;

        } catch (Exception e) {

            String falla = Notificaciones.getValorErrorServer(); //"-99"

            salida = new ByteArrayInputStream(falla.getBytes());
            String ss= salida.toString();
            return salida;
        }
    }

    @Override
    protected void onPostExecute(InputStream s) {
        serverCommand.procesarRespuesta(s);

    }
}