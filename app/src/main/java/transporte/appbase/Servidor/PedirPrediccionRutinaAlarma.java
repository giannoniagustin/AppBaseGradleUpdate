package transporte.appbase.Servidor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Global;
import transporte.appbase.Notificaciones;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Parser.RutinaTypeAdapterBis;
import transporte.appbase.Ruta.Corte;
import transporte.appbase.Rutinas.MyReceiver;
import transporte.appbase.Rutinas.RutinaG;

/**
 * Created by soled_000 on 15/7/2016.
 */
public class PedirPrediccionRutinaAlarma extends RutinasCommand {


    private Context context;
    private ArrayList<Corte> cortes = new ArrayList<>();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void Ejecutar() {
        super.server.pedirPrediccionRutina(this);
    }

    @Override
    public void Deshacer() {

    }

    @Override
    public void procesarRespuesta(InputStream s) {

        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(RutinaG.class, new RutinaTypeAdapterBis());
            final Gson gson = gsonBuilder.create();
            JsonReader json = new JsonReader(new InputStreamReader(s));

            JsonParser jsonParser = new JsonParser();
            JsonElement objetoParseado = jsonParser.parse(json);

            if(objetoParseado.getAsJsonObject().get("cortes").isJsonArray()) {

                JsonArray arreglo = objetoParseado.getAsJsonObject().get("cortes").getAsJsonArray();
                InputStream salidaCortes = new ByteArrayInputStream(arreglo.toString().getBytes());

                cortes = manejadorParser.LeerCorte(salidaCortes);
                //    manejadorInterfaz.DibujarCorte(cortes);
            }
            if (objetoParseado.getAsJsonObject().get("rutina").isJsonArray()) {

                JsonArray arrayRutina = objetoParseado.getAsJsonObject().get("rutina").getAsJsonArray();
                if (arrayRutina.size() != 0) {
                    ArrayList<RutinaG> rutinas = manejadorParser.parsearRutinas(arrayRutina);
                    if (rutinas != null) {

                        //proceso la rutina para almacenarla en la BD
                        Global.instance().getManejadorRutinas().procesarRespuestaServidor(rutinas.get(0));

                        Calendar fin = rutinas.get(0).getFechaFin();

                        Calendar calendar = Calendar.getInstance();
                    /*   int d = calendar.getTime().getDay();
                        calendar.set(Calendar.HOUR_OF_DAY, fin.HOUR_OF_DAY);
                        calendar.set(Calendar.HOUR, fin.HOUR);
                        calendar.set(Calendar.MINUTE,fin.MINUTE);
                        calendar.set(Calendar.SECOND, fin.SECOND);
*/

                        Date dateFin = new Date();
                        dateFin.setDate(calendar.getTime().getDay());
                        dateFin.setYear(calendar.getTime().getYear());
                        dateFin.setMonth(calendar.getTime().getMonth());
                        dateFin.setMinutes(fin.getTime().getMinutes());
                        dateFin.setHours(fin.getTime().getHours());

                        calendar.setTime(dateFin);


                        int  horaNueva = calendar.getTime().getHours();
                        int minutosNuevo = calendar.getTime().getMinutes();
                        Long when;
                //jaiio       if (!Global.instance().getManejadorRutinas().esFechaMenor(calendar)) {
                //           when = Global.instance().getManejadorRutinas().getProximoHorarioRutina(calendar);
                //        }
                //        else
                //        {
                            calendar = Calendar.getInstance();
                            calendar.add(calendar.MINUTE, 2);//30);
                            int  hora = calendar.getTime().getHours();
                            int minuto = calendar.getTime().getMinutes();

                            when = calendar.getTimeInMillis();

                //        }
                        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                        Intent intent  = new Intent(context, MyReceiver.class);
                        intent.putParcelableArrayListExtra("proxima_rutina", rutinas);
                        intent.putParcelableArrayListExtra("cortes_todos", cortes);
                        PendingIntent pIntent = PendingIntent.getBroadcast(context, 1, intent,PendingIntent.FLAG_CANCEL_CURRENT);

                        manager.set(AlarmManager.RTC, when, pIntent);//ver si va rtc_wakeup
                   //     Global.instance().getManejadorInterfaz().mostrarNotificacionTexto("alarma seteada " + calendar.getTime().toString());

                    }
                }


            }
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }
}