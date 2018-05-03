package transporte.appbase.Servidor;


import java.io.InputStream;


import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;

public class PedirParquimetrosCommad extends ServidorCommand{


    private ManejadorInterfaz manejadorInterfaz;


    public PedirParquimetrosCommad() {

    }

    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }


    @Override
    public void procesarRespuesta(InputStream s) {

        manejadorInterfaz.cerrarDialogoEspera();
        //parseo respuesta
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            manejadorInterfaz.DibujarCorte(manejadorParser.LeerCorte(s));

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public void Ejecutar() {
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.pedirParquimetros(this);

    }

    @Override
    public void Deshacer() {

    }
}
