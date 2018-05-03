package transporte.appbase.Configuracion;
/*
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Rutinas.Rutina;

public class ProcesarDirecciones {

    private static final String FILE_CACHE_DIRECCIONES = "Cache_Rutinas_direcciones.txt";
    private static final int VALORES_CACHE = 5; //idRutina, NombreRutina, direccion, latitud, longitud.
    private static final int ID_RUTINA = 0;
    private static final int NOMBRE_RUTINA = 1;
    private static final int DIREC_RUTINA = 2;
    private static final int LAT_RUTINA = 3;
    private static final int LONG_RUTINA = 4;
    private ArrayList<String> direcciones;
    private ArrayList<Integer> ids = null;
    private StringBuffer cache;
    private Context context;

    private HashMap<Integer,Rutina> resultado = null;

    public void setContext(Context c){
        this.context = c;
    }

    public ArrayList<Rutina> escribirCache(ArrayList<Rutina> rutinas) {

        ArrayList<Rutina> rutinasConDireccion;
        if (!(testDireccionesCacheadas())) {
            resultado= new HashMap<>();
            ArrayList<String> rutinaDireccion = getDireccion(rutinas);
            File file;
            FileOutputStream outputStream;
            try {
                  file = new File((context).getCacheDir(), FILE_CACHE_DIRECCIONES);
                  outputStream = new FileOutputStream(file);
                  for (int i = 0; i < rutinaDireccion.size(); i++) {
                        outputStream.write(rutinas.get(i).getIdRutina().toString().getBytes());
                        outputStream.write("\n".getBytes());
                        outputStream.write(rutinas.get(i).getNombreRutina().getBytes());
                        outputStream.write("\n".getBytes());
                        outputStream.write(rutinaDireccion.get(i).getBytes());
                        outputStream.write("\n".getBytes());
                        outputStream.write(((Double) rutinas.get(i).getDestinoRutina().latitude).toString().getBytes());
                        outputStream.write("\n".getBytes());
                        outputStream.write(((Double) rutinas.get(i).getDestinoRutina().longitude).toString().getBytes());
                        outputStream.write("\n".getBytes());
                  }
                    outputStream.close();

                    rutinasConDireccion =  agregarDirecciones(rutinas);

                    return rutinasConDireccion;

            } catch (Exception e) {
                Log.LOGGER.severe(e.toString());
            }
            }
        return null;
    }

    public ArrayList<String> getDireccion(ArrayList<Rutina> rutinas){
        direcciones = new ArrayList<>();
        Geocoder geocoder = new Geocoder(context);
        for (int i = 0;i<rutinas.size();i++){
            try {
                List<Address> addresses = geocoder.getFromLocation(rutinas.get(i).getDestinoRutina().latitude,rutinas.get(i).getDestinoRutina().longitude, 1);
                if(addresses != null){
                    Address fetchedAddress = addresses.get(0);
                    StringBuilder strAddress = new StringBuilder();
                    for(int j=0; j<fetchedAddress.getMaxAddressLineIndex(); j++) {
                        strAddress.append(fetchedAddress.getAddressLine(j)).append("-"); //CAMBIAR GUION POR ESPACIO?
                    }
                    direcciones.add(i,strAddress.toString());
                }
            } catch (IOException e) {
                Log.LOGGER.severe(e.toString());
            }

        }
        return direcciones;
    }

    public HashMap<Integer,Rutina> leerCache(){
        BufferedReader input;
        File file;
        resultado = new HashMap<>();
        ids = new ArrayList<>();
        ArrayList<String> re = new ArrayList<>();
        Rutina rutina;

        try {
            file = new File(context.getCacheDir(), FILE_CACHE_DIRECCIONES);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            cache = new StringBuffer();
            while ((line = input.readLine()) != null) {
               if(re.size()<VALORES_CACHE) {

                   cache.append(line);
                   re.add(line);
               }
                else {
                   rutina = new Rutina(Integer.parseInt(re.get(ID_RUTINA)),re.get(NOMBRE_RUTINA),new LatLng(0,0),new LatLng(Double.parseDouble(re.get(LAT_RUTINA)),Double.parseDouble(re.get(LONG_RUTINA))),"fecha");
                   rutina.setDireccionDestino(re.get(DIREC_RUTINA));

                   resultado.put(rutina.getIdRutina(), rutina);
                   ids.add(rutina.getIdRutina());
                   re = new ArrayList<>();
                   re.add(line);
               }
            }

            rutina = new Rutina(Integer.parseInt(re.get(ID_RUTINA)),re.get(NOMBRE_RUTINA),new LatLng(0,0),new LatLng(Double.parseDouble(re.get(LAT_RUTINA)),Double.parseDouble(re.get(LONG_RUTINA))),"fecha");
            rutina.setDireccionDestino(re.get(DIREC_RUTINA));
            resultado.put(rutina.getIdRutina(), rutina);
            ids.add(rutina.getIdRutina());
            return resultado;

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
        return null;
    }

    public boolean testDireccionesCacheadas(){
        if (resultado!= null)
            return true;
        return false;
    }
    public void borrarCache(){
        File cacheFile = new File(context.getCacheDir(), FILE_CACHE_DIRECCIONES);
        cacheFile.delete();
        resultado = null;
        ids = null;

    }

    public ArrayList<Integer> getIdRutinas(){
        return ids;
    }

   public ArrayList<Rutina> agregarDirecciones(ArrayList<Rutina> rut){
       ArrayList<Rutina> rutinas = new ArrayList<>();
       if (testDireccionesCacheadas()) {
        //   HashMap<Integer, Rutina> valores = leerCache();
           resultado = leerCache();
           for (int i = 0; i < rut.size(); i++) {
               rut.get(i).setDireccionDestino(resultado.get(rut.get(i).getIdRutina()).getDireccionDestino());
               rutinas.add(i, rut.get(i));
           }
           return rutinas;
       }
       else {
           rutinas = escribirCache(rut);
           return rutinas;
       }

   }

}
*/