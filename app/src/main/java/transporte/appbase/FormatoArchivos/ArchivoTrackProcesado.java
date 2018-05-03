package transporte.appbase.FormatoArchivos;

import java.io.FileWriter;
import java.io.IOException;


public class ArchivoTrackProcesado {
    private FileWriter fichero;

    public ArchivoTrackProcesado(String nombreArchivo) throws IOException {
        fichero = new FileWriter(nombreArchivo);

    }

    public void escribir(String texto){
        try {
            fichero.write(texto);
            fichero.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

