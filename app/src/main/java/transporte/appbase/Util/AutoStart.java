package transporte.appbase.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import transporte.appbase.Tracker.ServicioTracker;

public class AutoStart extends BroadcastReceiver {
    public AutoStart() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       Intent i = new Intent(context, ServicioTracker.class);
        Toast.makeText(context, "Se esta guardando la ubicacion...", Toast.LENGTH_LONG).show();
        context.startService(i);

        Log.i("Autostart", "started");
    }
}
