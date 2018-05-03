package transporte.appbase.Servidor;


import java.io.InputStream;
import java.util.ArrayList;

import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Ruta.Corte;

/**
 * Created by Agustín on 05/10/2015.
 */
public class AgregarCorteCommand extends ServidorCommand {
    private Corte corte;

    private ManejadorInterfaz manejadorInterfaz;



    public void setCorte(Corte c){
        corte=c;
    }


    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }

    @Override
    public void procesarRespuesta(InputStream s) {

        manejadorInterfaz.cerrarDialogoEspera();
        ManejadorParser manejadorParser = new ManejadorParser();
        try {
            manejadorInterfaz.mostrarNotificacionServidor(manejadorParser.parsearRespuesta(s));
            ArrayList<Corte> dibujar = new ArrayList<>();
            dibujar.add(corte);
            manejadorInterfaz.DibujarCorte(dibujar);
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }


    }



    @Override
    public void Ejecutar() {

        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.EnviarCorte(corte,this);

    }

    @Override
    public void Deshacer() {

    }


}
