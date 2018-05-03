package transporte.appbase.Servidor;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Notificaciones;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Ruta.Corte;
import transporte.appbase.Ruta.Punto;
import transporte.appbase.Ruta.Traza;

public class PedirRutaCommand extends ServidorCommand{

    private Punto origen;
    private Punto destino;

    private ManejadorInterfaz manejadorInterfaz;
    private ManejadorParser manejadorParser;

    public PedirRutaCommand() {
        this.manejadorParser = new ManejadorParser();
    }

    public void setManejadorInterfaz(ManejadorInterfaz manejadorInterfaz) {
        this.manejadorInterfaz = manejadorInterfaz;
    }

    public void setOrigen(Punto origen){
        this.origen = origen;
    }

    public void setDestino(Punto destino){
        this.destino = destino;
    }

    public Punto getOrigen() {
        return origen;
    }

    public Punto getDestino() {
        return destino;
    }

    @Override
    public void procesarRespuesta(InputStream s) {

        ArrayList<Corte> cortes = new ArrayList<>();
        manejadorInterfaz.cerrarDialogoEspera();

        try {

            JsonReader jsone = new JsonReader(new InputStreamReader(s));
            JsonParser jsonParser = new JsonParser();
            JsonElement objetoParseado = jsonParser.parse(jsone);
            if(objetoParseado.getAsJsonObject().get("cortes").isJsonArray()) {

                JsonArray arreglo = objetoParseado.getAsJsonObject().get("cortes").getAsJsonArray();
                InputStream salidaCortes = new ByteArrayInputStream(arreglo.toString().getBytes());

                cortes = manejadorParser.LeerCorte(salidaCortes);
            //    manejadorInterfaz.DibujarCorte(cortes);
            }
            else{
                int valor = Integer.parseInt(Notificaciones.getValorErrorServer()); //-99; error server
                manejadorInterfaz.mostrarNotificacionServidor(valor);
            }

            if(objetoParseado.getAsJsonObject().get("todos").isJsonArray()) {

                JsonArray arrayTodos = objetoParseado.getAsJsonObject().get("todos").getAsJsonArray();
                if (arrayTodos.size() !=0) {
                    Traza traza = manejadorParser.parsearTrazas(arrayTodos);
                    if (traza != null) {
                        traza.setOrigenTraza(this.getOrigen().getLatLng());
                        traza.setDestinoTraza(this.getDestino().getLatLng());
                        //   manejadorInterfaz.dibujarTraza(traza);
                        //   manejadorInterfaz.agregarElementosDibujar(traza,cortes);
                        manejadorInterfaz.agregarElementosDibujar(traza, cortes);
                    } else {
                        int valor = Integer.parseInt(Notificaciones.getValorErrorServer()); //-99; error server
                        manejadorInterfaz.mostrarNotificacionServidor(valor);
                    }
                } else{
                    manejadorInterfaz.mostrarNotificacionTexto("No existen rutas sin atravesar cortes o el Origen/Destino estan sobre un corte");
                    if (cortes != null){
                      //  manejadorInterfaz.DibujarCorte(cortes);
                        Traza t = new Traza();
                        t.setOrigenTraza(this.getOrigen().getLatLng());
                        t.setDestinoTraza(this.getDestino().getLatLng());
                        manejadorInterfaz.agregarElementosDibujar(t, cortes); //para q muestre los cortes y el o y d sin traza
                    }
                }
            }
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }

    @Override
    public void Ejecutar() {
        manejadorInterfaz.mostrarDialogoEspera("Cargando...");
        super.server.pedirCalculoRuta(this);
    }

    @Override
    public void Deshacer() {

    }
}
