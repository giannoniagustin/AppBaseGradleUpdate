package transporte.appbase.Configuracion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.apache.http.client.HttpClient;

import transporte.appbase.PantallaInicioActivity;

/**
 * Created by Agustín on 09/07/2015.
 */
public class Preferencias {
    SharedPreferences sharedPref;


    public void setPreferencia(Context context, String atributo, String valor) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(atributo, valor);
        editor.commit();
    }

    public Object getPreferencia(Context context, String atributo) {

        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(atributo, null);

    }
    public String getUsuario (Context context){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString("usuario", null);
    }

    public String getDireccion(Context context, String id){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(id, null);
    }


}
