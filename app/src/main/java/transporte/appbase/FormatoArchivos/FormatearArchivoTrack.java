package transporte.appbase.FormatoArchivos;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;


public class FormatearArchivoTrack {

    private ProcesarDatos procesarDatos;
    private ArchivoTrackProcesado archivoTrackProcesado;
    private String latitud,longitud,dia,hora,minutos,mes,año;
    private static final String PATH_ARCHIVO_PROCESADO = "storage/emulated/0/AppProcesado.txt";


    public FormatearArchivoTrack() {

         procesarDatos = new ProcesarDatos();
        try {
            archivoTrackProcesado = new ArchivoTrackProcesado(PATH_ARCHIVO_PROCESADO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Levantar archivo track txt
   public void muestraContenido(String rutaArchivo) throws IOException {

       try{
           // Abrimos el archivo
           FileInputStream fstream = new FileInputStream(rutaArchivo);
           // Creamos el objeto de entrada
           DataInputStream entrada = new DataInputStream(fstream);
           // Creamos el Buffer de Lectura
           BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
           String strLinea;
           // Leer el archivo linea por linea
           while ((strLinea = buffer.readLine()) != null)   {
               //parsear la linea
               parsearLinea(strLinea);
           }
           // Cerramos el archivo
           entrada.close();
       }catch (Exception e){ //Catch de excepciones
           System.err.println("Ocurrio un error: " + e.getMessage());
       }

    }


 /*   @TargetApi(Build.VERSION_CODES.KITKAT)
    public void  loadExternal(String linea) throws FileNotFoundException {

        String text = "Tipo: 5 Actividad: Tilting Prob: 100 -37.3263524:-59.1390761:10:20:2015:00:03:10 Tipo: 3 Actividad: Nada Prob: 62 -37.3263331:-59.1391304:06:21:2015:00:03:41 ";
        Scanner br = new Scanner(linea);
        br.useDelimiter("\\s*Tipo: \\s*");
        String strLine;
        while (br.hasNext()) {
            strLine = br.next();
            strLine.length();
            parsearLinea(strLine);
            //   Date d = obtenerFecha(strLine);
            //obtener F: y P:


        }
    }
*/
    public void parsearLinea(String texto){
        Scanner br = new Scanner(texto);
        br.useDelimiter("\\s*:\\s*");

        //Quedan para trabajar más adelante por Tipo de Actividad
        String previo0 = br.next();
        String previo1 = br.next();
        String previo2 = br.next();

        String lat = br.next();
        StringTokenizer st = new StringTokenizer(lat," ");

        String prob = st.nextToken();
        latitud = st.nextToken();

        longitud = br.next();
        mes = br.next();
        dia = br.next();
        año = br.next();
        hora = br.next();
        minutos = br.next();

        archivoTrackProcesado.escribir(procesarDatos.coordenadasTrack(latitud,longitud));
        archivoTrackProcesado.escribir(procesarDatos.fechaToString(mes,dia,año,hora,minutos));

    }

}
