package transporte.appbase;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Servidor.EnviarUsuarioCommand;
import transporte.appbase.Servidor.EnviarUsuarioRegistroCommand;

public class RegistroActivity extends ActionBarActivity {

    private EditText email, usuario, contrasenia1, contrasenia2;
    private Button botonRegistro;
    private Usuario usuarioRegistro;
    private Context contexto;
    private Global global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registro);
            contexto = this;

       //     global = (Global) getApplication();
            global = Global.instance();

            global.getManejadorInterfaz().setContexto(this);
            global.getManejadorServidor().setContexto(this);

            email = (EditText) findViewById(R.id.editTextMail);
            usuario = (EditText) findViewById(R.id.editTextUsuario);
            contrasenia1 = (EditText) findViewById(R.id.editTextContrasenia1);
            contrasenia2 = (EditText) findViewById(R.id.editTextContrasenia2);
            botonRegistro = (Button) findViewById(R.id.buttonRegistro);


            botonRegistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String emailTexto = email.getText().toString();
                    if (!(global.getValidador().chequeoEmail(emailTexto))) {
                        email.setError("E-mail invalido");
                        email.requestFocus();
                    }
                    String contrasenia1Texto = contrasenia1.getText().toString();
                    Boolean controlContrasenia1 = global.getValidador().chequeoContrasenia(contrasenia1Texto);
                    if (!(controlContrasenia1)) {
                        contrasenia1.setError("Contrasenia invalida. Debe tener mas de 6 caracteres.");
                    }
                    String contrasenia2Texto = contrasenia2.getText().toString();
                    Boolean controlContrasenia2 = global.getValidador().chequeoContrasenia(contrasenia2Texto);
                    if (!(controlContrasenia2)) {
                        contrasenia2.setError("Contrasenia invalida. Debe tener mas de 6 caracteres.");

                    }
                    if (controlContrasenia1 && controlContrasenia2) {
                        if (!(global.getValidador().chequeoContraseniasIguales(contrasenia1Texto, contrasenia2Texto))) {

                            global.getManejadorInterfaz().mostrarNotificacionTexto("Las contrasenias no coinciden");

                        } else if (global.getValidador().chequeoEmail(emailTexto)) {
                            usuarioRegistro = new Usuario(emailTexto, usuario.getText().toString(), contrasenia1Texto);

                            EnviarUsuarioCommand agregarUsuarioRegistro = new EnviarUsuarioRegistroCommand();
                            agregarUsuarioRegistro.setUsuario(usuarioRegistro);
                            agregarUsuarioRegistro.setManejadorInterfaz(global.getManejadorInterfaz());
                            global.getManejadorServidor().agregarElemento(agregarUsuarioRegistro);

                        }
                    }


                }

            });
        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
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



}
