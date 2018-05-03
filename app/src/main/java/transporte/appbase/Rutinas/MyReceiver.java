package transporte.appbase.Rutinas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import transporte.appbase.Global;
import transporte.appbase.MapsActivity;
import transporte.appbase.R;
import transporte.appbase.Ruta.ConjuntoTrazas;
import transporte.appbase.Ruta.Corte;
import transporte.appbase.Ruta.Traza;
import transporte.appbase.RutinasActivity;
import transporte.appbase.Servidor.PedirPrediccionRutinaAlarma;
import transporte.appbase.Servidor.PedirPrediccionRutinaCommand;
import transporte.appbase.Tracker.CapaServicio;

/**
 * Created by soled_000 on 11/5/2016.
 */
public class MyReceiver extends android.content.BroadcastReceiver {

    private NotificationManager nm;
    private Calendar calendar;


    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence from = "Atención";
        CharSequence message = "Tu próxima actividad comienza en 30 min.";

        ArrayList<RutinaG> rutinas = intent.getParcelableArrayListExtra("proxima_rutina");
        ArrayList<Corte> cortes = intent.getParcelableArrayListExtra("cortes_todos");

        Intent i = new Intent(context, RutinasActivity.class);
        i.putExtra("todas_rutinas",rutinas);
        i.putExtra("todos_cortes",cortes);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i, 0);
    //
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.mipmap.logo);//R.drawable.iconapp);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo));
            mBuilder.setContentTitle(from);
            mBuilder.setContentText(message);
            mBuilder.setContentIntent(contentIntent);
            mBuilder.setColor(context.getResources().getColor(R.color.tw__transparent));
            nm.notify(1, mBuilder.build());
        }
        else{
            Notification notif = new Notification(R.drawable.iconapp, "Nueva actividad", System.currentTimeMillis());
            notif.setLatestEventInfo(context, from, message, contentIntent);
            nm.notify(1, notif);
        }


     //   int i = intent.getIntExtra("posicion",0);

        //Armar command de pedido de proxima rutina al servidor
        //se puede hacer con intentservice
        //PredecirRutinaCommand predecirRutinaCommand = new PredecirRutinaCommand();


        //----------supongo rutina obtenida de la prediccion del  servidor----------------//predecir Rutina
   /*     calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 24);
        calendar.set(Calendar.SECOND, 00);
        RutinaG rutina3 = new RutinaG();
        rutina3.setOrigenRutina(new LatLng(-37.33414951, -59.12427664));
        rutina3.setDestinoRutina(new LatLng(-37.340769, -59.120865));
        rutina3.setFechaInicio(calendar);
        rutina3.setNombre("Casa");
        rutina3.setpOrigen("Gym");
        rutina3.setpDestino("Casa");
        Traza r1 = new Traza();
        r1.setOrigenTraza(new LatLng(-37.333773, -59.125032));
        r1.setPuntoRuta(new LatLng(-37.335616, -59.123938));
        r1.setPuntoRuta(new LatLng(-37.337667, -59.122713));
        r1.setDestinoTraza(new LatLng(-37.339163, -59.121776));
        ConjuntoTrazas conjuntoTrazas1 = new ConjuntoTrazas();
        conjuntoTrazas1.add(r1);
        rutina3.setTrazas(conjuntoTrazas1);
    */    //----------

        PedirPrediccionRutinaAlarma pedirPrediccionRutinasCommad = new PedirPrediccionRutinaAlarma();
     //   pedirPrediccionRutinasCommad.setManejadorInterfaz(G.getManejadorInterfaz());
        pedirPrediccionRutinasCommad.setUsuario("soledc");//preferencia.getUsuario(this));

     //   Long horario = Global.instance().getManejadorRutinas().getProximoHorarioRutina(i); //pos

        Calendar calendar = Calendar.getInstance();
     //   calendar.setTimeInMillis(horario);
        Long horario = calendar.getTimeInMillis();//fecha actual porq sono la alarma y estoy calculando la proxima rutina

        pedirPrediccionRutinasCommad.setFechaConsulta(calendar);
        pedirPrediccionRutinasCommad.setPuntoActual(CapaServicio.retornarUltimaUbicacionConocida());
        pedirPrediccionRutinasCommad.setContext(context);
        Global.instance().getManejadorServidor().agregarElemento(pedirPrediccionRutinasCommad);
/*
        //reemplazar fila de la BD
        String p = String.valueOf(i);
        Global.instance().getManejadorRutinas().reemplazarRutina(rutina3, p);



      // resta 2 minutos cuando pide getProximoHorario

        //calcular hora de consulta(restar 30min)
        if (Global.instance().getManejadorRutinas().existeId(i)){
            Long proximoHorario = Global.instance().getManejadorRutinas().getProximoHorarioRutina(i);

            //notificar al gestor de la nueva rutina para que ejecute el metodo establecerAlarma()
            Intent notifyIntent = new Intent("receive_rutina");
            notifyIntent.putExtra("hora", proximoHorario);//date en milisegundos
            notifyIntent.putExtra("posicion",i+1);
            context.sendBroadcast(notifyIntent);
        }

*/

    }
}
