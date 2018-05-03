/*package transporte.appbase.Servidor;

import java.io.InputStream;
import java.util.HashMap;

import transporte.appbase.Archivos.Log;

/**
 * Created by soled_000 on 13/10/2015.
 */
/*
public class PedidoPuntosCargaServidor extends ConsultaServidorGenerica {

    private PedirPuntosCargaCommand pedirPuntosCargaCommand;


    public PedidoPuntosCargaServidor(PedirPuntosCargaCommand pedirPuntosCargaCommand) {

        //    super(context);
        this.pedirPuntosCargaCommand = pedirPuntosCargaCommand;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected InputStream doInBackground(HashMap<String, Object>... params) {
        return super.doInBackground(params);
    }

    @Override
    protected void onPostExecute(InputStream s) {

        Log.LOGGER.info("Post execute reportar corte" + s);

        /*super.getDialog().dismiss();
        super.getValor().respuestaServidorReporteCorte(s);*/

/*
        pedirPuntosCargaCommand.procesarRespuesta(s);


    }
}
*/