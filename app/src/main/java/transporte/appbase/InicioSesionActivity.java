package transporte.appbase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Servidor.EnviarUsuarioCommand;
import transporte.appbase.Servidor.EnviarUsuarioLoginCommand;
import transporte.appbase.Servidor.EnviarUsuarioTwitterCommand;


public class InicioSesionActivity extends AppCompatActivity{

    private static final String TWITTER_KEY = "GQT8Qce3EghpcGrmjvzu1k1wv";
    private static final String TWITTER_SECRET = "uTo3VQUR5inqsJrTmXevaIJlW44CXbErXIqwimeU0z3OjyfKL3";

    private TwitterLoginButton loginButton;
    private EditText usuarioLoginIngreso, contraseniaLogin;
    private Button botonLogin;
    private Usuario usuarioLogin;
    private Context context;
    private Global global;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
        //    global = (Global) getApplication();
            global = Global.instance();
            context = this;

            ///Logueo y/o registro por Red Social Twitter
            TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
            Fabric.with(this, new Twitter(authConfig));
            setContentView(R.layout.activity_inicio_sesion);



            global.getManejadorInterfaz().setContexto(this);

            global.getManejadorServidor().setContexto(this);


            loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
            loginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    usuarioLogin = new Usuario(result.data.getUserName());

                    EnviarUsuarioCommand agregarUsuarioTwitter = new EnviarUsuarioTwitterCommand();
                    agregarUsuarioTwitter.setUsuario(usuarioLogin);
                    agregarUsuarioTwitter.setManejadorInterfaz(global.getManejadorInterfaz());
                    global.getManejadorServidor().agregarElemento(agregarUsuarioTwitter);

                }

                @Override
                public void failure(TwitterException exception) {
                    // Do something on failure
                }
            });

            //Logueo por cuenta del sistema
            usuarioLoginIngreso = (EditText) findViewById(R.id.editTextUsuarioLogin);
            contraseniaLogin = (EditText) findViewById(R.id.editTextContraseniaLogin);
            botonLogin = (Button) findViewById(R.id.buttonIniciarSesion);
            botonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String usuarioLoginTexto = usuarioLoginIngreso.getText().toString();
                    String contraseniaLoginTexto = contraseniaLogin.getText().toString();

                    if (!global.getValidador().chequeoContrasenia(contraseniaLoginTexto)) {
                        contraseniaLogin.setError("Contrasenia invalida. Debe tener mas de 6 caracteres.");
                    }

                    if (global.getValidador().chequeoContrasenia(contraseniaLoginTexto)) {
                        usuarioLogin = new Usuario(usuarioLoginTexto, contraseniaLoginTexto);

                        EnviarUsuarioCommand agregarUsuarioLogin = new EnviarUsuarioLoginCommand();
                        agregarUsuarioLogin.setUsuario(usuarioLogin);
                        agregarUsuarioLogin.setManejadorInterfaz(global.getManejadorInterfaz());
                        global.getManejadorServidor().agregarElemento(agregarUsuarioLogin);

                    }

                }
            });
        } catch (Exception e){
            Log.LOGGER.severe(e.toString());}
    }

    //VER EL RESULT (SE USA EN EL BACK DE TWITTER)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            loginButton.onActivityResult(requestCode, resultCode, data);
        } catch ( Exception e){Log.LOGGER.severe(e.toString());}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_inicio_sesion, menu);
            return true;
        } catch ( Exception e){
            Log.LOGGER.severe(e.toString());
            return  false;


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
            return false;
        }
    }

    public void crearRegistro(View v) {
        try {
           // Intent i = new Intent(InicioSesionActivity.this, RegistroActivity.class);
           // startActivity(i);
            global.getManejadorInterfaz().cambioActivityRegistro();

        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }
    }

    public void onResume() {

     try {
         super.onResume();
     } catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    @Override //Usar Notificaciones despues
    public void onBackPressed() {
        try {
            new AlertDialog.Builder(this)
                    .setTitle("ATENCION")
                    .setMessage("Realmente desea salir?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }).create().show();
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }



}
