package transporte.appbase.Interfaz;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Hashtable;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Configuracion.Preferencias;
import transporte.appbase.MapsActivity;
import transporte.appbase.Notificaciones;
import transporte.appbase.RegistroActivity;
import transporte.appbase.Ruta.Cole;
import transporte.appbase.Ruta.ConjuntoTrazas;
import transporte.appbase.Ruta.Corte;
import transporte.appbase.Ruta.Traza;
import transporte.appbase.Rutinas.PuntoEstadia;
import transporte.appbase.Rutinas.RutinaG;
import transporte.appbase.RutinasActivity;
import transporte.appbase.Tracker.CapaServicio;
import transporte.appbase.Usuario;
import transporte.appbase.Util.Iconos;

/**
 * Created by Agustín on 06/10/2015.
 */
public class ManejadorInterfaz {
    private Context context;
    private ArrayList<Dibujable> elementosDibujar;
    private GoogleMap mmap;
    private Dibujador dibujador;
    private ProgressDialog dialog;
 //   private Hashtable<Integer, Integer> colores = new Hashtable<>();

    public ManejadorInterfaz() {
        elementosDibujar = new ArrayList();
        dibujador = new Dibujador();
 //       setColores();
    }
/*
    public void setColores(){
        colores.put(0, Color.GREEN);
        colores.put(1, Color.RED);
        colores.put(2, Color.BLUE);
        colores.put(3, Color.BLACK);
        colores.put(4, Color.CYAN);
        colores.put(5, Color.DKGRAY);
        colores.put(6, Color.GRAY);
        colores.put(7, Color.LTGRAY);
        colores.put(8, Color.YELLOW);
        colores.put(9, Color.MAGENTA);
    }*/
    public void setContexto(Context context) {
        this.context = context;
    }

    public void setMmap(GoogleMap mmap) {
        this.mmap = mmap;
        dibujador.setmMap(mmap);
    }

    public ArrayList getElementosDibujar() {
        return elementosDibujar;
    }

    public void AgregarDibujable(ArrayList<Dibujable> objeto) {
        try {
            elementosDibujar.clear();
            elementosDibujar.addAll(objeto);


            this.Dibujar();
        } catch (Exception e) {

            Log.LOGGER.severe(e.toString());
        }
    }

    public void DibujarPosicionActual() {

    }
/*
    public int getRandomColores(){
        int range = ((colores.size() - 1) - 0) + 1;
        return (int) (Math.random() * range) + 0;
    }
*/
    public void DibujarCorte(ArrayList<Corte> cortes) {
        try {

            ArrayList<Dibujable> dibujable = new ArrayList<>();
            int i = 0;
            while (i < cortes.size()) {

                dibujable.add(new DibujableMarcador(new Marcador(cortes.get(i).getLatLng(), cortes.get(i).getDescripcion(), cortes.get(i).getNombreIcono())));
                i++;
                System.out.println("entro dibujar");
            }
            dibujable.add(new DibujableMarcador(new Marcador(CapaServicio.retornarUltimaUbicacionConocida(), "", "IC_MI_UBICACION")));//Agrega posicion actual para centrar el mapa

         AgregarDibujable(dibujable);

            //DibujarPosicionActual();
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());

        }

    }


    // se puede abstraer con anteriores?
    public void DibujarColes(ArrayList<Cole> coles) {
        try {

            ArrayList<Dibujable> dibujable = new ArrayList<>();
            int i = 0;
            while (i < coles.size()) {

                dibujable.add(new DibujableMarcador(new Marcador(coles.get(i).getLatLng(), coles.get(i).getDescripcion(), coles.get(i).getNombreIcono())));
                i++;
            }
            dibujable.add(new DibujableMarcador(new Marcador(CapaServicio.retornarUltimaUbicacionConocida(), "", "IC_MI_UBICACION")));//Agrega posicion actual para centrar el mapa
            AgregarDibujable(dibujable);

            //DibujarPosicionActual();
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }

    public void DibujarMarcador(Marcador marcador) {
        try {

            ArrayList<Dibujable> dibujable = new ArrayList<>();


            dibujable.add(new DibujableMarcador(marcador));


            AgregarDibujable(dibujable);
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }

    }
//ver una rutina, perfecto. Chequear el tema de ver todas!!!
/*    public void dibujarRutina(ArrayList<RutinaG> rutinas) {
        try {
            ArrayList<Dibujable> dibujable = new ArrayList<>();

            if (rutinas.size() > 0) {

                int i = 0;
                while (i < rutinas.size()) {
                    dibujarConjuntoTrazas(rutinas.get(i).getTrazas());
                //    dibujable.add(new DibujableMarcador(new Marcador(rutinas.get(i).getOrigenRutina(), rutinas.get(i).getpOrigen(), "IC_RUTA")));
                //    dibujable.add(new DibujableMarcador(new Marcador(rutinas.get(i).getDestinoRutina(), rutinas.get(i).getpDestino(), "IC_RUTA")));

               //     ConjuntoTrazas rutas = rutinas.get(i).getTrazas();//ver aca

                //    for (int j = 0; j < rutas.size(); j++) {
                    //    dibujable.addAll(dibujarRuta(rutas.get(j))); ojooooooooooo

                //    }

                    i++;
                }
                //      dibujable.add(new DibujableMarcador(new Marcador(CapaServicio.retornarUltimaUbicacionConocida(), "", "IC_MI_UBICACION")));//Agrega posicion actual para centrar el mapa
            //    AgregarDibujable(dibujable);
            }

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }

*/
    public void dibujarConjuntoTrazas(ConjuntoTrazas conjuntoTrazas){
        DibujableConjuntoTraza dibujableConjuntoTraza = new DibujableConjuntoTraza(conjuntoTrazas);
        ArrayList<Dibujable> dibujables = new ArrayList<>();
        dibujables.add(dibujableConjuntoTraza);
        AgregarDibujable(dibujables);
   /*     ArrayList<Dibujable> dibujables = new ArrayList<>();
        int cantidad = conjuntoTrazas.size();
        for(int i = 0; i < cantidad; i++){
            dibujables.addAll(dibujarRuta(conjuntoTrazas.get(i)));
        }
        AgregarDibujable(dibujables);*/
    }


/*
    public ArrayList<Dibujable>  dibujarRuta(Traza rutaCompleta) {

        int range = ((colores.size()-1) - 0) + 1;
        int colorRan= (int)(Math.random() * range) + 0;
        ArrayList<Dibujable> dibujable = new ArrayList<>();

        try {
            DibujableTraza dibujableTraza = new DibujableTraza(rutaCompleta.getRuta());
            dibujableTraza.setColor(colores.get(colorRan));
            DibujableMarcador dibujarMarcadorOrigen = new DibujableMarcador(new Marcador(rutaCompleta.getOrigenTraza(), "Origen", "IC_RUTA")); //ver nombre origen y destino
            DibujableMarcador dibujarMarcadorDestino = new DibujableMarcador(new Marcador(rutaCompleta.getDestinoTraza(), "Destino", "IC_RUTA"));
            dibujable.add(dibujableTraza);
            dibujable.add(dibujarMarcadorOrigen);
            dibujable.add(dibujarMarcadorDestino);



            return dibujable;
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
        return null;
    }
*/

    public void dibujarTraza(Traza rutaCompleta) {

        ArrayList<Dibujable> dibujable = new ArrayList<>();

        try {
            DibujableTraza dibujableTraza = new DibujableTraza(rutaCompleta);
            dibujable.add(dibujableTraza);
            AgregarDibujable(dibujable);

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }
/*    public void dibujarRutina(RutinaG rutinag) {
        try {
            DibujableRutina dibujableRutina = new DibujableRutina(rutinag);
            ArrayList<Dibujable> dibujable = new ArrayList<>();
            dibujable.add(dibujableRutina);
        //    dibujable.add(new DibujableMarcador(new Marcador(dibujableRuta.getOrigen(), rutinag.getpOrigen(), "IC_RUTA")));
        //    dibujable.add(new DibujableMarcador(new Marcador(dibujableRuta.getDestino(), rutinag.getpDestino(), "IC_RUTA")));
            AgregarDibujable(dibujable);
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }
*/

    public void agregarElementosDibujar(Traza traza, ArrayList<Corte> cortes){
        ArrayList<Dibujable> dibujarElementos = new ArrayList<>();
        DibujableTraza dibujableTraza = new DibujableTraza(traza);
        dibujarElementos.add(dibujableTraza);
        DibujableCorte dibujableCorte = null;
        for (int i =0;i<cortes.size();i++){
            dibujableCorte = new DibujableCorte(cortes.get(i));
            dibujarElementos.add(dibujableCorte);
        }
        AgregarDibujable(dibujarElementos);
    }

    public void Dibujar() {
        mmap.clear();
        int i = 0;
        while (i < elementosDibujar.size()) {

            elementosDibujar.get(i).Accept(dibujador);
            i++;
        }

    }

    public void EliminarDibujable(Object objeto) {

        elementosDibujar.remove(objeto);

    }

    public void mostrarDialogoEspera(String texto) {

        dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setMessage(texto);
        dialog.show();

    }

    /*  public void mostrarDialogoModal(String texto){

          dialog = new ProgressDialog(context);
          dialog.setCanceledOnTouchOutside(true);
          dialog.setCancelable(true);
          dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cerrar", (DialogInterface.OnClickListener) null);
          dialog.setMessage(texto);
          dialog.show();

      }
  */
    public void cerrarDialogoEspera() {
        dialog.dismiss();
    }

    public void mostrarNotificacionServidor(int numero) {
        Dialog dialogo = Notificaciones.crearDialogo(numero, context);
        if (dialogo != null) {
            dialogo.show();
        }

    }

    public void mostrarNotificacionTexto(String texto) {
        Dialog dialog = Notificaciones.crearDialogoTexto(texto, context);
        if (dialog != null) {
            dialog.show();
        }


    }

    public void cambioActivityMapa(Usuario usuario) {
        Preferencias preferencia = new Preferencias();//Manejo del archivo de preferencias
        preferencia.setPreferencia(context, "usuario", usuario.getNombreUsuario());
        Intent i = new Intent(context, MapsActivity.class);
        context.startActivity(i);
    }

    public void cambioActivityRegistro() {
        Intent i = new Intent(context, RegistroActivity.class);
        context.startActivity(i);
    }

    public void cambioActivityRutinas(ArrayList<RutinaG> rutinas,ArrayList<Corte> cortes ,String nombreUsuario) {

        Intent intent = new Intent(context, RutinasActivity.class);
        intent.putExtra("nombre_usuario", nombreUsuario);
        intent.putParcelableArrayListExtra("todas_rutinas", rutinas);
        intent.putParcelableArrayListExtra("todos_cortes",cortes);

        context.startActivity(intent);

    }

    public Context getContext() {
        return context;
    }


    public void dibujarRutinas(ArrayList<RutinaG> myDataset) {
        ArrayList<Dibujable> dibujables = new ArrayList<>();
        DibujableRutina dibujableRutina;
        for (int i = 0;i < myDataset.size(); i++){
            dibujableRutina = new DibujableRutina(myDataset.get(i));
            dibujables.add(dibujableRutina);
        }

        AgregarDibujable(dibujables);
    }


    public void limpiarMapa() {
        mmap.clear();
    }

    public void agregarMarkerCliqueable(LatLng point) {
        limpiarMapa();
        Marker marcadorEstadia = mmap.addMarker(new MarkerOptions()
                .position(point)
                .title("Punto seleccionado")
                .snippet("Clic para completar")
                .icon(BitmapDescriptorFactory.fromResource(Iconos.getIcono("IC_RUTA"))));
              //  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        marcadorEstadia.showInfoWindow();
    }


    public void dibujarRutinasCortes(ArrayList<RutinaG> rutinas, ArrayList<Corte> cortes) {
        ArrayList<Dibujable> dibujables = new ArrayList<>();
        DibujableRutina dibujableRutina;
        for (int i = 0;i < rutinas.size(); i++){
            dibujableRutina = new DibujableRutina(rutinas.get(i));
            dibujables.add(dibujableRutina);
        }
        DibujableCorte dibujableCorte = null;
        if (cortes!=null) {
            for (int i = 0; i < cortes.size(); i++) {
                dibujableCorte = new DibujableCorte(cortes.get(i));
                dibujables.add(dibujableCorte);
            }
        }


        AgregarDibujable(dibujables);
    }

    public void dibujarPunto(PuntoEstadia puntoEstadia) {
        ArrayList<Corte> puntos = new ArrayList<>();
        puntos.add(new Corte(-1,puntoEstadia.getPunto().latitude,puntoEstadia.getPunto().longitude,null,null,null,puntoEstadia.getNombre(),"IC_PUNTO_ESTADIA"));
        DibujarCorte(puntos);
    }


}
