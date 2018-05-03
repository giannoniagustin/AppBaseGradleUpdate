package transporte.appbase.Servidor;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Configuracion.ArchivoCache;

import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Notificaciones;
import transporte.appbase.Parser.ManejadorParser;

import transporte.appbase.Parser.RutinaTypeAdapterBis;
import transporte.appbase.Rutinas.RutinaG;

public class PedirRutinasCommad extends RutinasCommand {





    @Override
    public void procesarRespuesta(InputStream s) {

        manejadorInterfaz.cerrarDialogoEspera();
        //parseo respuesta
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(RutinaG.class, new RutinaTypeAdapterBis());
            final Gson gson = gsonBuilder.create();
            JsonReader json = new JsonReader(new InputStreamReader(s));
        //    final RutinaG rutina = gson.fromJson(json, RutinaG.class); // no levanto de a una, sino todas en un array de RutinaG
            ArrayList<RutinaG> rut = gson.fromJson(json,RutinaG.class);
            //   ArrayList<RutinaG> rut = manejadorParser.leerRutina(s);
             if (rut != null){
                //envio de las rutinas parseadas al manejador de interfaz para ser mostradas en las cardView
                 manejadorInterfaz.cambioActivityRutinas(rut, null ,nombreUsuario);

                }
           else{
                 int valor = Integer.parseInt(Notificaciones.getValorErrorServer()); //-99; error server
                 manejadorInterfaz.mostrarNotificacionServidor(valor);
             }


        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());

        }

    }


    @Override
    public void Ejecutar() {
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.pedirRutinasPorDia(this);
    }

    @Override
    public void Deshacer() {

    }



}
