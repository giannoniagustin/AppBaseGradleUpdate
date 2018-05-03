package transporte.appbase.Servidor;


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

import transporte.appbase.Configuracion.ArchivoCache;
import transporte.appbase.Notificaciones;

public class ConsultaPuntosEstadiaTomcat extends ConsultaServidorTomcat{

    private ArchivoCache archivoCache;

    public ConsultaPuntosEstadiaTomcat(ServidorCommand serverCommand, HttpClient c, ArchivoCache archivoCache) {
        super(serverCommand,c);
        this.archivoCache = archivoCache;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        archivoCache.testCacheado();

    }

    @Override
    protected InputStream doInBackground(HashMap<String, Object>... params) {
        salida = null;
        parametros = params[0];

        ArrayList<NameValuePair> parametrosConcatenar;
        parametrosConcatenar= (ArrayList<NameValuePair>) parametros.get("parametrosConsulta");

    //    HttpClient client = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
        url = parametros.get("url").toString();
        url += "?";
        String paramString = URLEncodedUtils.format(parametrosConcatenar, "utf-8");
        url += paramString;
        HttpGet httpget = new HttpGet(url);

        if (archivoCache.getCacheLength() == 0) {
            try {
                HttpResponse response = client.execute(httpget, contexto);
                HttpEntity entity = response.getEntity();
                String a = EntityUtils.toString(entity, HTTP.UTF_8);
                salida = new ByteArrayInputStream(a.getBytes());

                archivoCache.escribirCache(a);

                return salida;

            } catch (IOException e) {
                e.printStackTrace();
                String falla = Notificaciones.getValorErrorServer(); //"-99"

                salida = new ByteArrayInputStream(falla.getBytes());
            //    String ss = salida.toString();
                return salida;

            }
        }else{
            StringBuffer stringBuffer = archivoCache.leerCache();
        //    String aa = stringBuffer.toString();
            byte[] bytes = stringBuffer.toString().getBytes();
            salida = new ByteArrayInputStream(bytes);
            return salida;
        }


    }



}