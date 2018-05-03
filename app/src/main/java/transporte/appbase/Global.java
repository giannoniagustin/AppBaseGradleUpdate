package transporte.appbase;

import android.app.Application;
import android.graphics.Color;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Configuracion.ArchivoCache;

import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Rutinas.ManejadorRutinas;
import transporte.appbase.Rutinas.RutinaG;
import transporte.appbase.Util.Iconos;

/**
 * Created by Agustín on 09/07/2015.
 */
public class Global extends Application {

    private static Global instance;
    private Iconos iconos = new Iconos();
    private Notificaciones notificaciones = new Notificaciones();

    private Validador validador;
    private ManejadorServidor manejadorServidor;
    private ManejadorInterfaz manejadorInterfaz;
    private ArchivoCache archivoCache;
    private HttpClient c;
    private ManejadorRutinas manejadorRutinas;

    public Global() {
        this.manejadorServidor = new ManejadorServidor();
        this.manejadorInterfaz = new ManejadorInterfaz();
    //    this.c = manejadorServidor.getServer().getClientUsuario();
        this.c = new DefaultHttpClient();
        manejadorServidor.getServer().setClient(c);
        this.manejadorRutinas = new ManejadorRutinas();
        this.validador = new Validador();
        this.archivoCache = new ArchivoCache();

    }

    public ArchivoCache getArchivoCache() {
        return archivoCache;
    }

    public void setArchivoCache(ArchivoCache archivoCache) {
        this.archivoCache = archivoCache;
    }

    public static Global instance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null)
            instance = new Global();


        try {
            Log.setup();
        } catch (Exception e){Log.LOGGER.severe(e.toString());}

        //      mGoogleApiClient = new ApiClient(this);
    }

    public Iconos getIconos() {
        return iconos;
    }

    public Validador getValidador(){return validador;}

    public ManejadorInterfaz getManejadorInterfaz(){
        return manejadorInterfaz;
    }
    public ManejadorServidor getManejadorServidor(){
        return manejadorServidor;
    }
    public ManejadorRutinas getManejadorRutinas(){return manejadorRutinas;}

    public HttpClient getClient (){
        return c;
    }


}
