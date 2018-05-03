package transporte.appbase.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import transporte.appbase.Archivos.ControladorArchivos;

public class WifiChangeReceiver extends BroadcastReceiver {

    private boolean wifiConectado;

    public WifiChangeReceiver() {
        wifiConectado = false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            // TODO: This method is called when the BroadcastReceiver is receiving
            // an Intent broadcast.
            transporte.appbase.Archivos.Log.LOGGER.entering("","");
            transporte.appbase.Archivos.Log.LOGGER.info("Cambio conexion Wifi");
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            boolean isConnected = wifi != null && wifi.isConnectedOrConnecting() ||
                mobile != null && mobile.isConnectedOrConnecting();
            if (isConnected) {
                transporte.appbase.Archivos.Log.LOGGER.info("Conexion Establecida");
                wifiConectado = true;
                //envio de broadcast al manejadorSErvidor
                Intent notifyIntent = new Intent("receive_conection");
                notifyIntent.putExtra("conexion", true);
                context.sendBroadcast(notifyIntent);




                ControladorArchivos manangerArchivos = new ControladorArchivos(context);
                manangerArchivos.enviarArchivosPendientes();
//                ControladorArchivos manangerArchivosLog = new ControladorArchivos(context);
                manangerArchivos.enviarArchivoLog();
            } else {
                //   Log.d("Network Available ", "NO");
                wifiConectado = false;
            }
        }catch (Exception e){
            transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());}

    }
    //funcion creata para el caso de test
    public boolean getWifiConectado(Context context){
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return (wifi.isConnected());
        }catch (Exception e){
            transporte.appbase.Archivos.Log.LOGGER.severe(e.toString());
            return  false;
        }
    }
}

