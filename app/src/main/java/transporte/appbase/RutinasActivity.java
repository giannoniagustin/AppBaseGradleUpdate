package transporte.appbase;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import java.util.ArrayList;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Configuracion.ArchivoCache;
import transporte.appbase.Interfaz.ManejadorInterfaz;
import transporte.appbase.Ruta.Corte;
import transporte.appbase.Rutinas.AdapterListener;
import transporte.appbase.Rutinas.AgregarPuntoEstadia;
import transporte.appbase.Rutinas.NoticeDialogFragment;
//import transporte.appbase.Rutinas.Rutina;
import transporte.appbase.Rutinas.RutinaG;
import transporte.appbase.Rutinas.SwipeableRecyclerViewTouchListener;
import transporte.appbase.Servidor.EnviarRutinaCommand;


public class RutinasActivity extends AppCompatActivity implements AgregarPuntoEstadia.OnFragmentInteractionListener,google_place_search.OnFragmentInteractionListener,AdapterListener,NoticeDialogFragment.NoticeDialogListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RutinaG> myDataset = new ArrayList<>();
    private ArrayList<Corte> cortes = new ArrayList<>();
    private String nombreUsuario;
    private Global global;
    private ImageButton verTodasRutinas;
    private ImageButton agregarPuntoEstadia;
    private ManejadorInterfaz manejadorInterfaz;
    private ArchivoCache archivoCache;
    private int posicionEliminada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rutinas);


        //   global = (Global) getApplication();
        global = Global.instance();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator()); //nuevo

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        nombreUsuario = getIntent().getStringExtra("nombre_usuario");

        myDataset = getIntent().getParcelableArrayListExtra("todas_rutinas"); //Cada rutiina contiene seteada su direccion destino correpondiente
        cortes = getIntent().getParcelableArrayListExtra("todos_cortes");



//        Toast.makeText(this,"traza"+myDataset.get(0).getTrazas().get(1).toString(),Toast.LENGTH_LONG).show();

        //-------------PRUEBA----------
/*        global.getManejadorRutinas().setContext(this);
        global.getManejadorRutinas().solicitarRutinasPorDia();//por ahora solo elimina las existentes...
        global.getManejadorRutinas().procesarRespuestaServidor(myDataset.get(0));
        global.getManejadorRutinas().procesarRespuestaServidor(myDataset.get(1));
   */     //-------------PRUEBA----------




        archivoCache = global.getArchivoCache();
        archivoCache.setContext(this);


        manejadorInterfaz = global.getManejadorInterfaz();


        verTodasRutinas = (ImageButton)findViewById(R.id.boton_vista_mapa_todas_rutinas);
        verTodasRutinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manejadorInterfaz.dibujarRutinas(myDataset);
                //regresar al mapsActivity
                onBackPressed();
            }
        });
        agregarPuntoEstadia = (ImageButton)findViewById(R.id.boton_editar_rutina);
       agregarPuntoEstadia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //visualizar funcion para agregar punto estadia del Maps Activity


            }
        });



        try {
            mAdapter = new MyAdapter(myDataset,this);
            mRecyclerView.setAdapter(mAdapter);


            //nuevo

            SwipeableRecyclerViewTouchListener swipeTouchListener =
                    new SwipeableRecyclerViewTouchListener(mRecyclerView,
                            new SwipeableRecyclerViewTouchListener.SwipeListener() {
                                @Override
                                public boolean canSwipe(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                    for ( int position : reverseSortedPositions) {
                                        mostrarDialogoConsulta();
                                        //    myDataset.remove(position);
                                        //    mAdapter.notifyItemRemoved(position);
                                        posicionEliminada = position;
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                    for ( int position : reverseSortedPositions) {
                                        mostrarDialogoConsulta();
                                        //    myDataset.remove(position);
                                        //    mAdapter.notifyItemRemoved(position);
                                        posicionEliminada = position;
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            });

            mRecyclerView.addOnItemTouchListener(swipeTouchListener);
            //fin nuevo





        }
        catch(Exception e){
            Log.LOGGER.severe(e.toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rutinas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void rutinaIngresada(RutinaG rutinaEnviar) {

        global.getArchivoCache().borrarCache();
        EnviarRutinaCommand enviarRutinaCommand = new EnviarRutinaCommand();
        enviarRutinaCommand.setRutina(rutinaEnviar);
        enviarRutinaCommand.setUsuario(nombreUsuario);
        enviarRutinaCommand.setManejadorInterfaz(global.getManejadorInterfaz());
        global.getManejadorServidor().agregarElemento(enviarRutinaCommand);
        //salir de este activity y volver al del mapa

        onBackPressed();
    }

    @Override
    public void LugarSeleccionado(Place lugar) {
        //nada
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onItemClickPosicionRutina(int position) {
        ArrayList<RutinaG> rutinas = new ArrayList<RutinaG>();
        rutinas.add(myDataset.get(position));
        try {

        //    manejadorInterfaz.dibujarRutinas(rutinas);
            manejadorInterfaz.dibujarRutinasCortes(rutinas, cortes);

            //regresar al mapsActivity
            onBackPressed();
        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }
   @Override
    public void onItemClickEditarRutina(int position) {
        //nada por ahora
    }


    public void mostrarDialogoConsulta() {

        DialogFragment dialog = Notificaciones.crearDialogoTextoConsulta("Desea eliminar la rutina?");

        dialog.show(this.getFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {//COMENTADO GIANNO RUTINA G
     //  Toast.makeText(this, "positiva" + posicionEliminada, Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Se eliminaria la rutina del servidor para que no la use en su aprendizaje",Toast.LENGTH_LONG).show();

  /*      Rutina rutinaEliminada = myDataset.get(posicionEliminada);
        myDataset.remove(posicionEliminada);
        mAdapter.notifyItemRemoved(posicionEliminada);
    //  Command para eliminar rutina
        EliminarRutinaCommand eliminarRutinaCommand = new EliminarRutinaCommand();
        eliminarRutinaCommand.setManejadorInterfaz(global.getManejadorInterfaz());
        eliminarRutinaCommand.setRutina(rutinaEliminada);
        global.getManejadorServidor().agregarElemento(eliminarRutinaCommand);

        //eliminar cache
        global.getArchivoCache().borrarCache();

*/
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //    Toast.makeText(this,"negativa"+posicionEliminada,Toast.LENGTH_LONG).show();
        mRecyclerView.setAdapter(mAdapter);
    }
}

