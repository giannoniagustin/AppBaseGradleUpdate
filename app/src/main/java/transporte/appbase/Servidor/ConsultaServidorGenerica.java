/*package transporte.appbase.Servidor;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * Created by soled_000 on 26/07/2015.
 */
/*
public abstract class ConsultaServidorGenerica extends AsyncTask<HashMap<String, Object>, Void, InputStream> {



    private String url;
    private HashMap<String, Object> parametros;
    private InputStream salida;

    public ConsultaServidorGenerica(/*Context context) {*/

 //       this.context = context; }

/*
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected InputStream doInBackground(HashMap<String, Object>... params) {
        salida = null;
        parametros = params[0];

        ArrayList<NameValuePair> parametrosConcatenar;
        parametrosConcatenar= (ArrayList<NameValuePair>) parametros.get("parametrosConsulta");

        HttpClient client = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
        url = parametros.get("url").toString();
        url += "?";
        String paramString = URLEncodedUtils.format(parametrosConcatenar, "utf-8");
        url += paramString;
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpget, contexto);
            HttpEntity entity = response.getEntity();
            String a  = EntityUtils.toString(entity, HTTP.UTF_8);
            salida = new ByteArrayInputStream(a.getBytes());


        //    if (entity!=null) {
        //        salida = entity.getContent();
         //       String ss = EntityUtils.toString(entity);
        //    }
            return salida;
       //     salida = EntityUtils.toString(entity, HTTP.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
            String falla = "-99";

            salida = new ByteArrayInputStream(falla.getBytes());
            String ss= salida.toString();
            return salida;
        //    return "-99";//Cambiar a devolver error conexion servidor
        }
     //   return salida;
    }*/
/*
    @Override
    protected abstract void onPostExecute(InputStream s);

 /*   public AsyncRespuesta getValor() {
        return valor;
    }

    public void setValor(AsyncRespuesta valor) {
        this.valor = valor;
    }
    public ProgressDialog getDialog() {
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
*/