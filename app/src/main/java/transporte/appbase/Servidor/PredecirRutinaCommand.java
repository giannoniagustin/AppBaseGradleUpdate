/*package transporte.appbase.Servidor;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Vector;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Notificaciones;
import transporte.appbase.Parser.LeerRutaParserGianno;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Parser.RutinaTypeAdapter;
import transporte.appbase.Rutinas.Prediccion;
//import transporte.appbase.Rutinas.Rutina;
import transporte.appbase.Rutinas.RutinaG;

/**
 * Created by Agustín on 16/03/2016.
 */
/*
public class PredecirRutinaCommand extends ServidorCommand {

    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }

    private ManejadorInterfaz manejadorInterfaz;
    private Vector<LatLng> ultimasUbicaciones;

    public PredecirRutinaCommand(Vector<LatLng> pultimasUbicaciones) {
        this.ultimasUbicaciones = pultimasUbicaciones;
    }

    @Override
    public void procesarRespuesta(InputStream s) {



        manejadorInterfaz.cerrarDialogoEspera();
        //parseo respuesta
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(RutinaG.class, new RutinaTypeAdapter());
            final Gson gson = gsonBuilder.create();
            JsonReader json = new JsonReader(new InputStreamReader(s));
            final RutinaG rutina = gson.fromJson(json, RutinaG.class);
            ArrayList<RutinaG> rut =new ArrayList<>();
            rut.add(rutina); //manejadorParser.leerRutina(s);
            if (rut != null){
                //envio de las rutinas parseadas al manejador de interfaz para ser mostradas en las cardView
                manejadorInterfaz.cambioActivityRutinas(rut, "GiannoHardCode");
            }
            else{
                int valor = Integer.parseInt(Notificaciones.getValorErrorServer()); //-99; error server
                manejadorInterfaz.mostrarNotificacionServidor(valor);
            }


        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());

        }
    }

    public Vector<LatLng> getUltimasUbicaciones() {
        return ultimasUbicaciones;
    }

    @Override
    public void Ejecutar() {


      //  super.server.predecirRutina("", this);
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.predecirRutina("",this);

    }

    @Override
    public void Deshacer() {

    }

    public String getEventos() {
        return null;
    }
}

*/