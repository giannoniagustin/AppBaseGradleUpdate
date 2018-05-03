package transporte.appbase.Servidor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
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
 * Created by Agustín on 03/11/2015.
 */
public class ConsultaServidorTomcat extends ConsultaServerGenerica {
    protected HttpClient client ;

    public ConsultaServidorTomcat(ServidorCommand serverCommand,HttpClient c) {

        super(serverCommand);
        client=c;
    }

    @Override
    protected InputStream doInBackground(HashMap<String, Object>... params) {
        salida = null;
        parametros = params[0];

        ArrayList<NameValuePair> parametrosConcatenar;
        parametrosConcatenar= (ArrayList<NameValuePair>) parametros.get("parametrosConsulta");

       // client = new DefaultHttpClient();
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

/*
              if (entity!=null) {
                 salida = entity.getContent();
                   String ss = EntityUtils.toString(entity);
                }*/
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
    }

}
