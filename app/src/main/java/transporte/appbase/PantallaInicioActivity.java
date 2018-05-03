package transporte.appbase;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.client.HttpClient;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Configuracion.Preferencias;
import transporte.appbase.Servidor.EnviarUsuarioCommand;
import transporte.appbase.Servidor.EnviarUsuarioRegistradoCommand;
import transporte.appbase.Servidor.EnviarUsuarioTwitterCommand;


public class PantallaInicioActivity extends ActionBarActivity {

    private Global global;
    private HttpClient c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pantalla_inicio);
            global = Global.instance();
            global.getManejadorServidor().setContexto(this);
            Log.LOGGER.info("Version Android :"+Build.VERSION.SDK);//Armar clase configuracion inicial
        } catch (Exception e){
            Log.LOGGER.severe(e.toString());}

    }

    @Override
    public void onBackPressed() {
        try{
            super.onBackPressed();

        }catch ( Exception e){Log.LOGGER.severe(e.toString());}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            /*      Intent i = new Intent(PantallaInicioActivity.this, MapsActivity.class);
                  startActivity(i);*/
            Preferencias preferencia = new Preferencias();
            if (preferencia.getPreferencia(this, "usuario") == null) {

                this.c = global.getManejadorServidor().getServer().getClientUsuario();
                Intent i = new Intent(PantallaInicioActivity.this, InicioSesionActivity.class);
                startActivity(i);

            } else {
                String s = preferencia.getUsuario(this);




                global.getManejadorServidor().getServer().setClient(Global.instance().getClient());
                Usuario usuarioLogin = new Usuario(s);
                global.getManejadorInterfaz().setContexto(this);
                EnviarUsuarioCommand enviarUsuario = new EnviarUsuarioRegistradoCommand();
                enviarUsuario.setUsuario(usuarioLogin);
                enviarUsuario.setManejadorInterfaz(global.getManejadorInterfaz());
                global.getManejadorServidor().agregarElemento(enviarUsuario);

            }



        } catch (Exception e){Log.LOGGER.severe(e.toString());}

    }
}
