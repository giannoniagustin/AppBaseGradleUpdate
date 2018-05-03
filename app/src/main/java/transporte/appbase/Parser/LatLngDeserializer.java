/*package transporte.appbase.Parser;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class LatLngDeserializer implements JsonDeserializer<LatLng> {

    @Override
    public LatLng deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jobject = json.getAsJsonObject();

        return new LatLng(
                jobject.get("Latitud").getAsDouble(),
                jobject.get("Longitud").getAsDouble());
    }


}
*/