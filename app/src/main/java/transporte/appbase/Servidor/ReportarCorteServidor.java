/*package transporte.appbase.Servidor;

import android.content.Context;
import android.util.JsonReader;

import java.io.InputStream;
import java.util.HashMap;

import transporte.appbase.Archivos.Log;


/**
 * Created by soled_000 on 26/07/2015.
 */
/*
public class ReportarCorteServidor extends ConsultaServidorGenerica {
    private AgregarCorteCommand agregarCorteCommand;


    public ReportarCorteServidor(AgregarCorteCommand pagregarCorteCommand) {

        agregarCorteCommand = pagregarCorteCommand;
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
        agregarCorteCommand.procesarRespuesta(s);


    }
}*/
