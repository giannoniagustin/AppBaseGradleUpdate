package transporte.appbase.Parser;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Ruta.Cole;
import transporte.appbase.Ruta.Corte;
import transporte.appbase.Ruta.Traza;
import transporte.appbase.Rutinas.RutinaG;
import transporte.appbase.Servidor.LeerCorteParser;
import transporte.appbase.Servidor.LeerRutinaParser;

/**
 * Created by Agustín on 13/10/2015.
 */
public class ManejadorParser {

    private LeerCorteParser leerCorteParser;
    private LeerRutinaParser leerRutinaParser;

    public ManejadorParser() {
        this.leerCorteParser = new LeerCorteParser();
        this.leerRutinaParser = new LeerRutinaParser();
    }

    public ArrayList<Corte> LeerCorte(InputStream in) {

        try {
           
            ArrayList<Corte> cortes = (ArrayList) leerCorteParser.readJsonStream(in);
            return cortes;
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return null;
        }
    }

    public ArrayList<RutinaG> leerRutina(InputStream in) {

        try {
            ArrayList<Object> rutinas =  leerRutinaParser.readJsonStream(in);
            return (ArrayList)rutinas;
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return null;
        }
    }
/*
public Vector getCoordenadasColectivos(String message){

				Vector coles = new Vector();


				return coles;
        }
 */
    public ArrayList<Cole> LeerColes(InputStream in) {

        try {
            ArrayList<Cole> coles =  new ArrayList<Cole>();
            String contenido = this.parsearRespuestaString(in);
            if (contenido.contains("L.marker")){
                String[] colectivos = contenido.split("L.marker");

                for (int i = 1; i < colectivos.length; i++){
                    try{
                        String coordenadas = colectivos[i].split("\\]")[0];
                        coordenadas = coordenadas.replace("([", "");
                        coordenadas = coordenadas.replace(" ","");
                        String datos = colectivos[i].split("title: \\'\\[")[1];
                        String ident = datos.split("]")[0];
                        String time = datos.split(" ")[1];
                        String vel = datos.split(" ")[2];
                        Cole c1 = new Cole(ident,
                                Double.parseDouble(coordenadas.split(",")[0]),
                                Double.parseDouble(coordenadas.split(",")[1]),
                                time,
                                "Movil " + ident + " hora " + time + " Vel. " + vel,   // CAMBIAR
                                "IC_BUS_AZUL"          //Dependera del colectivoa  ver
                                );

                        coles.add(c1);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                //System.out.println("--------------------------------------------------------------");
            }



            return (ArrayList)coles;
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return null;
        }
    }




    public String parsearRespuestaString(InputStream in){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    Log.LOGGER.severe(e.toString());
                }
            }
        }

        String response = sb.toString();
        return response;
    }

    public String LeerSaldo(InputStream in) {

        try {
            String saldo =  this.parsearRespuestaString(in);
            return saldo;
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
            return null;
        }
    }
    
    public Integer parsearRespuesta(InputStream in){

        return(Integer.parseInt(parsearRespuestaString(in).trim()));
    }

    //se puede cambiar a Punto
    public ArrayList<LatLng> procesarCaminoObtenido(List<List<HashMap<String, String>>> result) {
    //    ListaRuta elem = new ListaCamino();

        ArrayList<LatLng> elem = new ArrayList<>();

        // recorriendo todas las rutas
        for (int i = 0; i < result.size(); i++) {

            // Obteniendo el detalle de la ruta
            List<HashMap<String, String>> path = result.get(i);

            // Obteniendo todos los puntos y/o coordenadas de la ruta
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));

             //   elem.agregar(new Punto(lat, lng, ""));
                elem.add(new LatLng(lat,lng));
            }


        }
        return elem;

    }


    public  ArrayList<Corte>  parsearCortes(InputStream s) {

        ArrayList<Corte> cortes = new ArrayList<>();
        JsonReader jsone = new JsonReader(new InputStreamReader(s));//borar
        JsonParser jsonParser = new JsonParser();//borrar
        JsonElement objetoParseado = jsonParser.parse(jsone); //borrar
        if(objetoParseado.getAsJsonObject().get("cortes").isJsonArray()) {

            JsonArray arreglo = objetoParseado.getAsJsonObject().get("cortes").getAsJsonArray();
            InputStream salidaCortes = new ByteArrayInputStream(arreglo.toString().getBytes());

            cortes = LeerCorte(salidaCortes);
        }
        return cortes;
    }

    public Traza parsearTrazas(/*InputStream s*/JsonArray todos){
        InputStream s = new ByteArrayInputStream(todos.toString().getBytes());


        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Traza.class, new TrazaTypeAdapter());
        final Gson gson = gsonBuilder.create();
        JsonReader json = new JsonReader(new InputStreamReader(s));



       return (gson.fromJson(json, Traza.class));

    }


    public ArrayList<RutinaG> parsearRutinas(JsonArray arrayRutina) {
        InputStream s = new ByteArrayInputStream(arrayRutina.toString().getBytes());


        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RutinaG.class, new RutinaTypeAdapterBis());
        final Gson gson = gsonBuilder.create();
        JsonReader json = new JsonReader(new InputStreamReader(s));

        return (gson.fromJson(json,RutinaG.class));
    }
}
