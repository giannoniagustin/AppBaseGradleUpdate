package transporte.appbase.Parser;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import transporte.appbase.Ruta.ConjuntoTrazas;
import transporte.appbase.Ruta.Traza;
import transporte.appbase.Rutinas.RutinaG;

/**
 * Created by soled_000 on 16/6/2016.
 */

//Clase para adaptar todas las rutinas del dia que llegan del servidor
public class RutinaTypeAdapterBis extends TypeAdapter {

    @Override
    public void write(JsonWriter out, Object value) throws IOException {
    }

    @Override
    public ArrayList<RutinaG> read(JsonReader in) throws IOException {

        ArrayList<RutinaG> rutinas = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonElement objetoParseado = jsonParser.parse(in);

        if (objetoParseado.isJsonArray()) {
            JsonArray array = objetoParseado.getAsJsonArray();
            for (int i = 0; i< array.size(); i++){
               rutinas.add(parsearRutina(array.get(i)));
            }
        }
        return rutinas;
    }

public RutinaG parsearRutina(JsonElement objetoParseado){

    ConjuntoTrazas trazas = new ConjuntoTrazas();
    final RutinaG rutinaG = new RutinaG();
    Long fecha;
    Calendar calendar;
    Double lat, longi;
    JsonObject punto;

    rutinaG.setpOrigen(objetoParseado.getAsJsonObject().get("origen").getAsString());
    rutinaG.setpDestino(objetoParseado.getAsJsonObject().get("destino").getAsString());
    rutinaG.setNombre(objetoParseado.getAsJsonObject().get("nombre").getAsString());

    fecha = objetoParseado.getAsJsonObject().get("fechaInicio").getAsLong();
    calendar = Calendar.getInstance();
    calendar.setTimeInMillis(fecha);
    rutinaG.setFechaInicio(calendar);

    fecha = objetoParseado.getAsJsonObject().get("fechaFin").getAsLong();
    calendar = Calendar.getInstance();
    calendar.setTimeInMillis(fecha);
    rutinaG.setFechaFin(calendar);

   punto = objetoParseado.getAsJsonObject().get("ptOrigen").getAsJsonObject();
   lat = punto.getAsJsonObject().get("latitude").getAsDouble();
   longi = punto.getAsJsonObject().get("longitude").getAsDouble();
   rutinaG.setOrigenRutina(new LatLng(lat, longi));

    punto = objetoParseado.getAsJsonObject().get("ptDestino").getAsJsonObject();
    lat = punto.getAsJsonObject().get("latitude").getAsDouble();
    longi = punto.getAsJsonObject().get("longitude").getAsDouble();
    rutinaG.setDestinoRutina(new LatLng(lat, longi));

    JsonObject cam =  objetoParseado.getAsJsonObject().get("caminos").getAsJsonObject();
    if (cam.getAsJsonObject().get("todos").isJsonArray()) {

        JsonArray arregloTrazas = cam.getAsJsonObject().get("todos").getAsJsonArray();
        for (int i = 0; i < arregloTrazas.size(); i++){
            trazas.add(new Traza());
            trazas.get(trazas.size() - 1).setOrigenTraza(rutinaG.getOrigenRutina());
            trazas.get(trazas.size() - 1).setDestinoTraza(rutinaG.getDestinoRutina());
            if (arregloTrazas.get(i).isJsonArray()){
                JsonArray arreglo = arregloTrazas.get(i).getAsJsonArray();
                for (int j = 0; j < arreglo.size(); j++){
                    JsonObject traza = arreglo.get(j).getAsJsonObject();
                    longi = traza.getAsJsonObject().get("longitude").getAsDouble();
                    lat = traza.getAsJsonObject().get("latitude").getAsDouble();
                    trazas.get(trazas.size() - 1).setPuntoRuta(new LatLng(lat, longi));
                }

            }else{
                JsonObject traza = arregloTrazas.get(i).getAsJsonObject();
                longi = traza.getAsJsonObject().get("longitude").getAsDouble();
                lat = traza.getAsJsonObject().get("latitude").getAsDouble();
                trazas.get(trazas.size() - 1).setPuntoRuta(new LatLng(lat, longi));
            }

        }
        rutinaG.setTrazas(trazas);
/*
            if (arregloTrazas.get(i).isJsonArray()){
                JsonArray arreglo = arregloTrazas.get(i).getAsJsonArray();
                for (int j = 0; j < arreglo.size(); j++){
                    JsonObject traza = arreglo.getAsJsonObject();
                    longi = traza.getAsJsonObject().get("longitude").getAsDouble();
    //                lat = traza.getAsJsonObject().get("latitude").getAsDouble();
    //                trazas.get(trazas.size() - 1).setPuntoRuta(new LatLng(lat, longi));
    /////            }
    /*        }
            JsonObject traza = arregloTrazas.get(i).getAsJsonObject();
            longi = traza.getAsJsonObject().get("longitude").getAsDouble();
    //        lat = traza.getAsJsonObject().get("latitude").getAsDouble();
   //         trazas.get(trazas.size() - 1).setPuntoRuta(new LatLng(lat, longi));
    //    }
    }





/*
    if (objetoParseado.getAsJsonObject().get("caminos").isJsonArray()) {

        JsonArray arregloTrazas = objetoParseado.getAsJsonObject().get("caminos").getAsJsonArray();
        for (int i = 0; i < arregloTrazas.size(); i++)
        {
            trazas.add(new Traza());
            if (arregloTrazas.get(i).isJsonArray()) {
                JsonArray arregloPuntos = arregloTrazas.get(i).getAsJsonArray();

                JsonObject trazaO = arregloPuntos.get(0).getAsJsonObject(); //origen traza
                JsonObject trazaD = arregloPuntos.get(1).getAsJsonObject(); //destino traza
   //             longi = trazaO/*.get("punto")*///.getAsJsonObject().get("longitude").getAsDouble();
   //             lat = trazaO/*.get("punto")*/.getAsJsonObject().get("latitude").getAsDouble();
   //             trazas.get(trazas.size() -1).setOrigenTraza(new LatLng(lat,longi));
  //              longi = trazaD/*.get("punto")*/.getAsJsonObject().get("longitude").getAsDouble();
  //              lat = trazaD/*.get("punto")*/.getAsJsonObject().get("latitude").getAsDouble();
  //              trazas.get(trazas.size() -1).setDestinoTraza(new LatLng(lat,longi));
/*
                for (int j = 2; j < arregloPuntos.size(); j++) {

                    JsonObject traza = arregloPuntos.get(j).getAsJsonObject();
       //             longi = traza/*.get("punto")*///.getAsJsonObject().get("longitude").getAsDouble();
       //             lat = traza/*.get("punto")*/.getAsJsonObject().get("latitude").getAsDouble();
     //               trazas.get(trazas.size() - 1).setPuntoRuta(new LatLng(lat, longi));
/*
                }
            }
        }
        rutinaG.setTrazas(trazas);
                }
 */
  //  return rutinaG;
    }
 //   System.out.println("rutina "+ rutinaG.getTrazas().conjuntoTrazasToString());
    return rutinaG;
}}
