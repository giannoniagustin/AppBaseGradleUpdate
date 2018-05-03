package transporte.appbase.Servidor;


import java.io.InputStream;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Parser.ManejadorParser;

/**
 * Created by Agustín on 07/10/2015.
 */
public class PedirSaldoCommand extends ServidorCommand{

//    private Server server;
    private ManejadorInterfaz manejadorInterfaz;
    String tarjeta;


    public PedirSaldoCommand(String tarjeta) {
        this.tarjeta = tarjeta;
    }


/*    public void setServer(Server server) {
        this.server = server;
    }
*/
    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }


    @Override
    public void procesarRespuesta(InputStream s) {

        manejadorInterfaz.cerrarDialogoEspera();
        //parseo respuesta
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            String saldo;
            saldo = manejadorParser.LeerSaldo(s);
            manejadorInterfaz.mostrarNotificacionTexto("El saldo de su tarjeta: " + tarjeta + " es: " + saldo);

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void Ejecutar() {
        manejadorInterfaz.mostrarDialogoEspera("Leyendo...");
        super.server.PedirSaldo(tarjeta,this);

    }

    @Override
    public void Deshacer() {

    }
}
