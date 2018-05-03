package transporte.appbase;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.HashMap;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Rutinas.NoticeDialogFragment;

public class Notificaciones {

    static HashMap<Integer, String> respuestas;
    private static final int VALOR_LIMITE = 0;

   private static final String VALOR_ERROR_SERVER = "-99";

       public Notificaciones() {

            respuestas = new HashMap<>();
            respuestas.put(-1, "Error: Usuario inexistente");
            respuestas.put(1, "Inicio de sesion correcto");
            respuestas.put(-2, "Error: Contrasenia invalida");
            respuestas.put(2, "Registro de usuario correcto");
            respuestas.put(-3, "Error: No se pudo crear la cuenta");
            respuestas.put(-4, "Error: No se pudo iniciar sesion");
            respuestas.put(-5, "Error: Usuario existente");
       //     respuestas.put(0, "Las contrasenias no coinciden");  BORRAR PORQUE NO VIENE COMO RESPUESTA DEL SERVIDOR
            respuestas.put(-99, "Error en Conexion al Servidor");
            respuestas.put(-6,"Error, no se puedo agregar el usuario de twitter");
            respuestas.put(-7,"Error al agregar el corte");
            respuestas.put(3, "Corte reportado con exito");
            respuestas.put(8,"Rutina agregada con éxito");
            respuestas.put(-8,"Error al agregar la rutina");
            respuestas.put(9,"Rutina eliminada con exito");
            respuestas.put(-9,"Error al eliminar la rutina");
           respuestas.put(-10,"No autorizado");

           respuestas.put(12,"Punto de estadia agregado con éxito");
           respuestas.put(-12,"Error al intentar agregar el punto de estadia");

    }

    public static String getRespuesta(Integer clave) {
        return respuestas.get(clave);
    }

    public static Dialog crearDialogo(Integer clave, Context context) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(getRespuesta(clave))
                    .setTitle("Atencion")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            return builder.create();
        }catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return  null;
    }}

    public static Dialog crearDialogoTexto(String texto, Context context) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(texto)
                    .setTitle("Atencion")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            return builder.create();
        }catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return  null;
        }}


    public static DialogFragment crearDialogoTextoConsulta(String texto) {
        try {

            DialogFragment dialog = new NoticeDialogFragment(texto);

            return dialog;

            /*

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(texto)
                    .setTitle("Atencion")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });


            return builder.create();
            */

        }catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return  null;
        }
    }

    public static int getValorLimite() {
        return VALOR_LIMITE;
    }

    public static String getValorErrorServer() {
        return VALOR_ERROR_SERVER;
    }

}