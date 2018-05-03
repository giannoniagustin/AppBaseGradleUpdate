package transporte.appbase.Parser;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

import transporte.appbase.Rutinas.RutinaG;
//import transporte.appbase.Rutinas.Rutina;


public class DeserializadorRutina implements JsonDeserializer<RutinaG> {

    @Override
    public RutinaG deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jobject = json.getAsJsonObject();

        //como no nos interesa por ahora el pto de Origen no se lo pedimos al servidor, por eso mando por defecto 0,0
      /*  return new RutinaG(jobject.get("idRutina").getAsInt(),jobject.get("NombreRutina").getAsString(),new LatLng(0,0),
                new LatLng(jobject.get("Latitud").getAsDouble(),jobject.get("Longitud").getAsDouble()),jobject.get("FechaInicio").getAsString(),jobject.get("FechaFin").getAsString(),jobject.get("DireccionDestino").getAsString());
ACOMODAR*/
  return new RutinaG();  }


}