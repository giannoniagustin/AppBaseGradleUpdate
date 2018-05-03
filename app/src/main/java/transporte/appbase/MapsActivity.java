package transporte.appbase;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Configuracion.ArchivoCache;
import transporte.appbase.Configuracion.Preferencias;
import transporte.appbase.Interfaz.GrupoObject;

import transporte.appbase.Interfaz.Marcador;
import transporte.appbase.Parser.ManejadorParser;
import transporte.appbase.Ruta.Corte;

import transporte.appbase.Ruta.Punto;
import transporte.appbase.Rutinas.AgregarPuntoEstadia;
import transporte.appbase.Rutinas.DatePickerFragment;
import transporte.appbase.Rutinas.DatePickerFragmentRangoPruebas;
import transporte.appbase.Rutinas.MyReceiver;
import transporte.appbase.Rutinas.RutinaG;
import transporte.appbase.Servidor.AgregarCorteCommand;
import transporte.appbase.Servidor.ConsultarPuntosEstadiaCacheCommand;
import transporte.appbase.Servidor.EliminarUsuarioCommand;
import transporte.appbase.Servidor.PedirColectivosCommad;

import transporte.appbase.Servidor.PedirCorteCommand;
import transporte.appbase.Servidor.PedirParquimetrosCommad;
import transporte.appbase.Servidor.PedirPrediccionRutinaAlarma;
import transporte.appbase.Servidor.PedirPrediccionRutinaCommand;

import transporte.appbase.Servidor.PedirPuntosCargaCommand;
import transporte.appbase.Servidor.PedirRutaCommand;
import transporte.appbase.Servidor.PedirRutinasCommad;
import transporte.appbase.Servidor.PedirSaldoCommand;

//import transporte.appbase.Servidor.PredecirRutinaCommand;
import transporte.appbase.Servidor.PuntosEstadiaCommand;
import transporte.appbase.Tracker.CapaServicio;

import transporte.appbase.Util.Iconos;


public class MapsActivity extends FragmentActivity  implements AgregarPuntoEstadia.OnFragmentInteractionListener,google_place_search.OnFragmentInteractionListener,AsyncRespuesta,DatePickerFragment.OnDateSetListener {

    private static final String ORIGEN = "origen";
    private static final String DESTINO = "destino";
    private static final String VER_UBICACION = "ubicacion_seleccionada";
    private static final int ALARM_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private CapaServicio capaServicio = new CapaServicio(this);
    private LatLng latLng = null;
    private LatLng ubicacionActual=null;
    private Place placeActual = null;
    private String seleccionUbicacion = VER_UBICACION;
    private BroadcastReceiver broadcastReceiver;
    private PersonalizableGridAdapter adapterView,adapterViewUbicacion;
    private Context context;
    private int distanciaPedidoCortes;
    private String seleccionado;
    private Global global;
    private google_place_search fragmentGooglSearchPlace;
    private DialogFragment dialogGoogleSearchPlace;
    private Preferencias preferencia;
    private ArchivoCache archivoCache;
    private ArrayList<Corte> puntosEstadiaUsuario = new ArrayList<>();

    private BroadcastReceiver respuestaDestino;
    private BroadcastReceiver respuestaOrigen;
    private Marker marcadorEstadia;

    private ArrayList<RutinaG> rutinasPorDia;
    private Calendar calendar;
    private  Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_maps);

            context = this;
            botonAccionesMapa();
            botonMiUbicacion();
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            capaServicio.iniciarServicio(this); //borrar1
            suscribirseUbicacion();//borrar1
        //    global = (Global) getApplication();
            global =Global.instance();

            preferencia = new Preferencias();
            String u = preferencia.getUsuario(this);

            //Fragmento del google_Search_place
            fragmentGooglSearchPlace = new google_place_search();//create the fragment instance for the top fragment
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, fragmentGooglSearchPlace, "frag_google_search_place");
            fragmentTransaction.commit();


            global.getManejadorServidor().setContexto(this);

            archivoCache = global.getArchivoCache();
            archivoCache.setContext(this);
    /*        //consulta desde el inicio las rutinas del servidor
            ConsultarPuntosEstadiaCacheCommand consultarRutinasCommand = new ConsultarPuntosEstadiaCacheCommand();
            consultarRutinasCommand.setUsuario(preferencia.getUsuario(MapsActivity.this));
            consultarRutinasCommand.setArchivoCache(archivoCache);
            consultarRutinasCommand.setAsyncRespuesta(MapsActivity.this);
            global.getManejadorServidor().agregarElemento(consultarRutinasCommand);
*/


        }catch (Exception e)
        {Log.LOGGER.severe(e.toString());}

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    //Controla las acciones realizadas sobre el boton +
    public void botonAccionesMapa() {
        try {
            final Button popupBoton = (Button) findViewById(R.id.boton_acciones);
            popupBoton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(MapsActivity.this, v);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_acciones_mapa, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            return false;
                        }
                    });
                }

            });
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }
    //Controla las acciones realizadas sobre el ícono de localización
    public void botonMiUbicacion() {
        try {
            final ImageButton popupBoton = (ImageButton) findViewById(R.id.boton_miubicacion);
            popupBoton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    latLng = CapaServicio.retornarUltimaUbicacionConocida();
                    setUpMap();

                }

            });
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            turnGPSOn();

        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    @Override
    protected void onRestart() {
      //  setUpMapIfNeeded();//Agregado gianno!!
        super.onRestart();
    }


    public void turnGPSOn(){
        try {
            if (!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage(R.string.gps_deshabiliatdo)
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                int pid = android.os.Process.myPid();
                                android.os.Process.killProcess(pid);
                                System.exit(0);


                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }
    private void setUpMapIfNeeded() {
             if (mMap == null) {
                // Try to obtain the map from the SupportMapFragment.
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .getMap();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {

        super.onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        EliminarUsuarioCommand eliminarUsuario = new EliminarUsuarioCommand();
        global.getManejadorServidor().agregarElemento(eliminarUsuario);
    }

    @Override //Usar Notificaciones despues
    public void onBackPressed() {
        try {
            new AlertDialog.Builder(this)
                    .setTitle("ATENCION")
                    .setMessage("Realmente desea salir?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }).create().show();
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    private void setUpMap() {
        try {


            if (latLng != null) {
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .getMap();

                global.getManejadorInterfaz().setContexto(this);
                global.getManejadorInterfaz().setMmap(mMap);
                global.getManejadorInterfaz().DibujarMarcador(new Marcador(latLng,"Usted esta aqui!","IC_MI_UBICACION"));


            }
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }
    //Metodo para suscribirse al pedido de ubicacion que se le solicita al servicio. Cuando se consiga
    //un resultado, el broadcast lo recibira
    public void suscribirseUbicacion() {
        try {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    latLng = intent.getParcelableExtra("latLong");
                    setUpMap();
                    desuscribirseUbicacion(broadcastReceiver);
                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter("resultado"));
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    //Metodo para avisarle a la capa del servicio, que no queremos recibir mas la informacion de la ubicacion.
    public void desuscribirseUbicacion(BroadcastReceiver b){
        try {
            unregisterReceiver(b);
            capaServicio.desvincularBroadcast(this);
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            return super.onCreateOptionsMenu(menu);
        }catch (Exception e){Log.LOGGER.severe(e.toString());
        return  false;}
    }

    //PopUp con Spinner para refinar la visualizacion de los cortes existentes
    public void clickItemCortesDistancia(MenuItem item) {
        try {
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.visualizar_cortes, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


  
        final SeekBar seekBar = (SeekBar) popupView.findViewById(R.id.seekBar1);
        final TextView textViewSeekBar = (TextView)popupView.findViewById(R.id.textViewSeekBar);
        textViewSeekBar.setText("Marque la distancia deseada: " + seekBar.getProgress() + "/" + seekBar.getMax());

        Spinner spinner = (Spinner) popupView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.visualizar_cortes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
                        String seleccionado = parent.getItemAtPosition(position).toString();
                        if (seleccionado.equals("Distancia")) {
                            seekBar.setVisibility(View.VISIBLE);
                            textViewSeekBar.setVisibility(View.VISIBLE);
                            seekBar.setOnSeekBarChangeListener(
                                    new SeekBar.OnSeekBarChangeListener() {
                                        int progress = 0;

                                        @Override
                                        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                                            progress = progresValue;
                                        }

                                        @Override
                                        public void onStartTrackingTouch(SeekBar seekBar) {
                                            // Do something here, if you want to do anything at the start of touching the seekbar
                                        }

                                        @Override
                                        public void onStopTrackingTouch(SeekBar seekBar) {
                                            textViewSeekBar.setText("Marque la distancia deseada: " + progress + "/" + seekBar.getMax());
                                            distanciaPedidoCortes = progress;
                                        }
                                    });
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(popupView.getContext(), "Nada seleccionado ", Toast.LENGTH_SHORT).show();
                    }
                });

        //BOTON CERRRAR POPUP
        Button botonCerrar = (Button) popupView.findViewById(R.id.boton_cerrar);
        botonCerrar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        //BOTON  ENVIAR
        Button botonEnviar = (Button) popupView.findViewById(R.id.boton_enviar);
        botonEnviar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                pedidoCortesVisualizar(distanciaPedidoCortes);
                popupWindow.dismiss();
            }
        });
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }


    //PopUp con Spinner para Elegir el la linea de colectivos
    public void clickItemColectivos(MenuItem item) {
        try {
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.visualizar_colectivos, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            Spinner spinner = (Spinner) popupView.findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.visualizar_colectivos, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
                            seleccionado = parent.getItemAtPosition(position).toString();
                        }

                        public void onNothingSelected(AdapterView<?> parent) {
                            Toast.makeText(popupView.getContext(), "Nada seleccionado ", Toast.LENGTH_SHORT).show();
                        }
                    });

            //BOTON CERRRAR POPUP
            Button botonCerrar = (Button) popupView.findViewById(R.id.boton_cerrar);
            botonCerrar.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupWindow.dismiss();
                }
            });

            //BOTON  ENVIAR
            Button botonEnviar = (Button) popupView.findViewById(R.id.boton_enviar);
            botonEnviar.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pedidoColectivos(seleccionado);
                    popupWindow.dismiss();
                }
            });
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }



    //PopUp con Para ver el Saldo
    public void clickSaldo(MenuItem item) {
        try {
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.obtener_saldo, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            popupWindow.setFocusable(true);
            popupWindow.update();
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            final EditText input = (EditText) popupView.findViewById(R.id.editTextSaldo);
            input.requestFocus();


            // input.setShowSoftInputOnFocus(true);// NECESITA CAMBIAR NIVEL DE LA API a 21

            //BOTON CERRRAR POPUP
            Button botonCerrar = (Button) popupView.findViewById(R.id.boton_cerrar);
            botonCerrar.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupWindow.dismiss();
                }
            });

            //BOTON  ENVIAR
            Button botonEnviar = (Button) popupView.findViewById(R.id.boton_enviar);
            botonEnviar.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                   //validador de numero SUMO
                    if (global.getValidador().chequeoCodigoSUMO(input.getText().toString())) {
                        pedidoSaldo(input.getText().toString());
                        popupWindow.dismiss();
                    }
                    else{
                        global.getManejadorInterfaz().mostrarNotificacionTexto("Código inválido. Por favor, ingrese un código de 8 dígitos.");

                    }

                    pedidoSaldo(input.getText().toString());
                     popupWindow.dismiss();
                }
            });
        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    public void  pedidoSaldo(String tarjeta) {
        try {
            //ARMAR EL COMMAND DE PEDIR SALDO
            PedirSaldoCommand pedidoSaldo = new PedirSaldoCommand(tarjeta);

            pedidoSaldo.setManejadorInterfaz(global.getManejadorInterfaz());
            global.getManejadorServidor().agregarElemento(pedidoSaldo);

        }   catch (Exception e){Log.LOGGER.severe(e.toString());}

    }



    //Solicita al servidor, los cortes para visualizarlos en el mapa. Envia la distancia deseada a mostrar desde la ubicacion del usuario.
    public void pedidoCortesVisualizar (int distancia){
        try {

            LatLng ubicacion = CapaServicio.retornarUltimaUbicacionConocida();
            //ARMAR EL COMMAND DE PEDIRCORTE
            PedirCorteCommand pedidoCortes = new PedirCorteCommand(ubicacion.latitude,ubicacion.longitude,distancia);

            pedidoCortes.setManejadorInterfaz(global.getManejadorInterfaz());
            global.getManejadorServidor().agregarElemento(pedidoCortes);

        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }


    public void clickSimularAlarma(MenuItem item) {
        try {
            Global.instance().getManejadorRutinas().setContext(this);
            PedirPrediccionRutinaAlarma pedirPrediccionRutinasCommad = new PedirPrediccionRutinaAlarma();
            pedirPrediccionRutinasCommad.setUsuario("soledc");//preferencia.getUsuario(this));
            pedirPrediccionRutinasCommad.setFechaConsulta(Calendar.getInstance());
            pedirPrediccionRutinasCommad.setPuntoActual(CapaServicio.retornarUltimaUbicacionConocida());
            pedirPrediccionRutinasCommad.setContext(context);
            Global.instance().getManejadorServidor().agregarElemento(pedirPrediccionRutinasCommad);





        }catch (Exception e)
             {Log.LOGGER.severe(e.toString()); }

    }
            //Solicita al servidor los parquimetros almacenados en la base para mostrarlos en el mapa
    public void clickItemParquimetros(MenuItem item) {
        try {


            PedirParquimetrosCommad pedirParquimetros = new PedirParquimetrosCommad();
            pedirParquimetros.setManejadorInterfaz(global.getManejadorInterfaz());
            global.getManejadorServidor().agregarElemento(pedirParquimetros);


            //----prueba alarma
 /*           Global.instance().getManejadorRutinas().setContext(this);
            PedirPrediccionRutinaAlarma pedirPrediccionRutinasCommad = new PedirPrediccionRutinaAlarma();
            pedirPrediccionRutinasCommad.setUsuario("soledc");//preferencia.getUsuario(this));
            pedirPrediccionRutinasCommad.setFechaConsulta(Calendar.getInstance());
            pedirPrediccionRutinasCommad.setPuntoActual(CapaServicio.retornarUltimaUbicacionConocida());
            pedirPrediccionRutinasCommad.setContext(context);
            Global.instance().getManejadorServidor().agregarElemento(pedirPrediccionRutinasCommad);



*/

        }catch (Exception e)
        {Log.LOGGER.severe(e.toString());}
    }

     /*-------------------------------------INICIO ALARMA----------------------------------------------------*/
/*
    private BroadcastReceiver broadcastReceiverRutina = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Long horaNueva = intent.getLongExtra("hora", 0); //cambiar a Calendar
            int pos = intent.getIntExtra("posicion",0);
        //    horaAlarma = horaNueva;
            global.getManejadorInterfaz().mostrarNotificacionTexto("sono");
            unregisterReceiver(broadcastReceiverRutina);
            establecerAlarma(horaNueva,pos);

        }
    };


    private void establecerAlarma(Long when, int pos){
        registerReceiver(broadcastReceiverRutina, new IntentFilter("receive_rutina"));
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent  = new Intent(this, MyReceiver.class);
        intent.putExtra("posicion", pos);//id de tabla seria ahora
        PendingIntent pIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        manager.set(AlarmManager.RTC, when, pIntent);//ver si va rtc_wakeup
        global.getManejadorInterfaz().mostrarNotificacionTexto("alarma seteada la 1era vez: " + date.toString());

    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
  */  /*-------------------------------------FIN ALARMA----------------------------------------------------*/
    //Solicita al servidor los colectivos, version inicial solo el azul
    public void pedidoColectivos(String seleccionado) {
        try {

            PedirColectivosCommad pedirColectivos = new PedirColectivosCommad(seleccionado);
            pedirColectivos.setManejadorInterfaz(global.getManejadorInterfaz());
            global.getManejadorServidor().agregarElemento(pedirColectivos);


        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }




    //Solicita al servidor los puntos de carga almacenados en la base para mostrarlos en el mapa
    public void clickItemPuntosCarga(MenuItem item) {
        try {

            PedirPuntosCargaCommand pedirPuntosCargaCommand = new PedirPuntosCargaCommand();
            pedirPuntosCargaCommand.setManejadorInterfaz(global.getManejadorInterfaz());

            global.getManejadorServidor().agregarElemento(pedirPuntosCargaCommand);


        }catch (Exception e){Log.LOGGER.severe(e.toString());}
    }



   //Interfaz y funcionamiento de la accion: Reportar corte
   public void clickItem(MenuItem item) {

        try {
            GrupoObject myObjects;//Items de la grid para manejar si esta seleccionado o no
            GrupoObject myObjectsUbicacion;//Items de la grid para manejar si esta seleccionado o no

            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.reoportar_corte,
                    null);
            final PopupWindow popupWindow = new PopupWindow(popupView,
                    ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            //GridView
            GridView gridView, gridViewUbicacion;
            //Items de la Grid Corte
            String[] textItem = new String[] { "Choque", "Protesta"};
            final int[] imagenItem = new int[] { Iconos.getIcono("Choque"),Iconos.getIcono("Protesta")};
            int[] imagenItemSeleccionado = new int[] { Iconos.getIcono("IC_CHOQUE_GREEN"),Iconos.getIcono("IC_PROTESTA_GREEN")};
            myObjects = new GrupoObject(textItem,imagenItem,imagenItemSeleccionado);
            gridView = (GridView) popupView.findViewById(R.id.gridView);
            // Set custom adapter (GridAdapter) to gridview
            adapterView=new PersonalizableGridAdapter(this, myObjects);
            //Items de la Grid Ubicacion
            String[] textItemUbicacion = new String[] { "Actual", "Otra..."};
            int[] imagenItemUbicacion = new int[] { Iconos.getIcono("IC_TRACKER"), Iconos.getIcono("IC_OTRA_UBICACION")};
            int[] imagenItemSeleccionadoUbicacion = new int[] { Iconos.getIcono("IC_TRACKER_GREEN"), Iconos.getIcono("IC_OTRA_UBICACION_GREEN")};

            myObjectsUbicacion = new GrupoObject(textItemUbicacion,imagenItemUbicacion,imagenItemSeleccionadoUbicacion);//Grupo de items de la grilla para llevar cual esta seleccoinado
            // Get gridview object from xml file
            gridViewUbicacion = (GridView) popupView.findViewById(R.id.gridViewUbicacion);
             adapterViewUbicacion=new PersonalizableGridAdapter(this, myObjectsUbicacion);

            gridView.setAdapter(adapterView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    adapterView.click(position);//Avisa al adapter

                }
            });
            gridViewUbicacion.setAdapter(adapterViewUbicacion);

            gridViewUbicacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    adapterViewUbicacion.click(position);//Avisa al adapter
                    if (adapterViewUbicacion.getNombreIconoSeleccionado()=="Otra..."){
                        dialogGoogleSearchPlace= new google_place_search();  //fragmentGooglSearchPlace.showDialog();
                        dialogGoogleSearchPlace.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
                        dialogGoogleSearchPlace.show(getFragmentManager(),"dialogo");

                    }

                }
            });

            //BOTON CERRRAR POPUP
            Button botonCerrar = (Button) popupView
                    .findViewById(R.id.boton_cerrar);
            botonCerrar.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupWindow.dismiss();
                }
            });

            //BOTON  ENVIAR
            Button botonEnviar = (Button) popupView
                    .findViewById(R.id.boton_enviar);
            botonEnviar.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int tipoCorte;
                    String descripcion;
                    if (adapterView.cumpleSeleccion() && adapterViewUbicacion.cumpleSeleccion()){


                       String nombreIcono = adapterView.getNombreIconoSeleccionado();


                        if (adapterView.getSeleccionado().getNombre().equals("Protesta")){
                            descripcion="Protesta";
                        }
                        else{
                            descripcion="Choque";
                        }
                        LatLng ubicacion=new LatLng(0,0);
                        if (adapterViewUbicacion.getSeleccionado().getNombre().equals("Actual")){
                            ubicacion= CapaServicio.retornarUltimaUbicacionConocida();
                        }
                        else{

                            ubicacion=ubicacionActual;


                        }


                        //SE PODRIAN SACAR LAS FECHAS Y QUE SE CARGUEN DIRECTO DLE SERVER, PORQUE SINO LAS DEBERIA SETEAR EL USER
                        Corte corte = new Corte(0,ubicacion.latitude,ubicacion.longitude,"2015-02-24 15:21:09","2015-02-24 15:21:09","2015-02-24 17:21:09",descripcion,/*tipoCorte*/nombreIcono);


                        AgregarCorteCommand agregarCorte = new AgregarCorteCommand();
                        agregarCorte.setCorte(corte);
                        agregarCorte.setManejadorInterfaz(global.getManejadorInterfaz());
                        global.getManejadorServidor().agregarElemento(agregarCorte);
                          popupWindow.dismiss();}
                    else{

                        Toast.makeText(getApplicationContext(),"Debe seleccionar al menos uno",Toast.LENGTH_LONG).show();
                    }
                }
            });


            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


        }
        catch (Exception e){Log.LOGGER.severe(e.toString());
        int i =3;
        }


    }
    //----------------------------RUTINAS-----------------------------

    //BORRAR
/*    public void clickItemVisualizarRutinas(MenuItem item) {
    //    Toast.makeText(this, "Ver Rutinas", Toast.LENGTH_SHORT).show();

      try{
            Calendar fecha = Calendar.getInstance();
            fecha.set(2015,10,13);
            PedirRutinasCommad pedirRutinasCommad = new PedirRutinasCommad();
            pedirRutinasCommad.setManejadorInterfaz(global.getManejadorInterfaz());
            pedirRutinasCommad.setUsuario(preferencia.getUsuario(this));
            pedirRutinasCommad.setFechaConsulta(fecha);
      //      pedirRutinasCommad.setArchivoCache(archivoCache); ahora creo no es necesario porq se almacenan en la bd interna
            global.getManejadorServidor().agregarElemento(pedirRutinasCommad);
/*
        //MODIFICADO PARA ARMAR CAPTURA DE PANTALLA SOLAMENTE
          calendar = Calendar.getInstance();
          calendar.setTimeInMillis(System.currentTimeMillis());
          calendar.set(Calendar.HOUR_OF_DAY, 8);
          calendar.set(Calendar.MINUTE, 40);
          calendar.set(Calendar.SECOND, 00);
          RutinaG rutina1 = new RutinaG();
          rutina1.setOrigenRutina(new LatLng(-37.325838, -59.139321));
          rutina1.setDestinoRutina(new LatLng(-37.321451, -59.082156));
          rutina1.setFecha(calendar);
          rutina1.setNombre("Trabajo");
          calendar = Calendar.getInstance();
          calendar.setTimeInMillis(System.currentTimeMillis());
          calendar.set(Calendar.HOUR_OF_DAY, 17);
          calendar.set(Calendar.MINUTE, 45);
          calendar.set(Calendar.SECOND, 00);
          RutinaG rutina2 = new RutinaG();
          rutina2.setOrigenRutina(new LatLng(-37.321451, -59.082156));
          rutina2.setDestinoRutina(new LatLng(-37.310357, -59.155722));
          rutina2.setFecha(calendar);
          rutina2.setNombre("Ingles lunes");
          calendar = Calendar.getInstance();
          calendar.setTimeInMillis(System.currentTimeMillis());
          calendar.set(Calendar.HOUR_OF_DAY, 19);
          calendar.set(Calendar.MINUTE, 40);
          calendar.set(Calendar.SECOND, 00);
          RutinaG rutina3 = new RutinaG();
          rutina3.setOrigenRutina(new LatLng(-37.310357, -59.155722));
          rutina3.setDestinoRutina(new LatLng(-37.319782, -59.140606));
          rutina3.setFecha(calendar);
          rutina3.setNombre("Gimnasio lunes");
          calendar = Calendar.getInstance();
          calendar.setTimeInMillis(System.currentTimeMillis());
          calendar.set(Calendar.HOUR_OF_DAY, 21);
          calendar.set(Calendar.MINUTE, 15);
          calendar.set(Calendar.SECOND, 00);
          RutinaG rutina4 = new RutinaG();
          rutina4.setOrigenRutina(new LatLng(-37.319782, -59.140606));
          rutina4.setDestinoRutina(new LatLng(-37.325838, -59.139321));
          rutina4.setFecha(calendar);
          rutina4.setNombre("Casa");

          ArrayList<RutinaG> rutina = new ArrayList<>();
          rutina.add(0,rutina1);
          rutina.add(1,rutina2);
          rutina.add(2,rutina3);
          rutina.add(3,rutina4);
          global.getManejadorInterfaz().cambioActivityRutinas(rutina,"soledcr");

*/

/*Gianno
          LoginRutinaCommand loginrutina = new LoginRutinaCommand();
          global.getManejadorServidor().agregarElemento(loginrutina);


          CargarRutinaCommand cargarrutina = new CargarRutinaCommand();
          global.getManejadorServidor().agregarElemento(cargarrutina);


          EntrenarRutinaCommand entrenarrutina = new EntrenarRutinaCommand();
          global.getManejadorServidor().agregarElemento(entrenarrutina);

          PredecirRutinaCommand predecirRutina = new PredecirRutinaCommand(CapaServicio.retornarUltimasUbicaciones());
          predecirRutina.setManejadorInterfaz(global.getManejadorInterfaz());
          global.getManejadorServidor().agregarElemento(predecirRutina);

*/

 /*       }
      catch (Exception e){
        Log.LOGGER.severe(e.toString());
        }



    }

*/
    public void clasificarRutinasPorFecha(MenuItem item){

        DatePickerFragmentRangoPruebas myDialogFragment = new DatePickerFragmentRangoPruebas();
        myDialogFragment.show(getFragmentManager(), "DatePickerRutina");
        myDialogFragment.setOnDateSetListener(MapsActivity.this);


    }



    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
     //   String fechaRutina = dayOfMonth+"-"+monthOfYear+"-"+year;
        Calendar fecha=Calendar.getInstance();
        fecha.set(year, monthOfYear, dayOfMonth);
 //       String month = fecha.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        try {
/*

            PuntosEstadiaCommand puntoEstadia = new PuntosEstadiaCommand(null,fecha);
            puntoEstadia.setManejadorInterfaz(global.getManejadorInterfaz());
            global.getManejadorServidor().agregarElemento(puntoEstadia);
*/
            PedirRutinasCommad pedirRutinasCommad = new PedirRutinasCommad(); //agregra la fecha
            pedirRutinasCommad.setManejadorInterfaz(global.getManejadorInterfaz());
            pedirRutinasCommad.setUsuario(preferencia.getUsuario(this));
            pedirRutinasCommad.setFechaConsulta(fecha);
            global.getManejadorServidor().agregarElemento(pedirRutinasCommad);

        } catch (Exception e){

            Log.LOGGER.severe(e.toString());
        }

    //    Toast.makeText(this, fechaRutina, Toast.LENGTH_LONG).show();
    }

    public void  clickNuevoPuntoEstadia(MenuItem item){


        Global.instance().getManejadorInterfaz().mostrarNotificacionTexto("Presione durante unos segundos en el mapa, el lugar del nuevo punto de estadía");
        Global.instance().getManejadorInterfaz().limpiarMapa();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
            @Override
            public void onMapLongClick(LatLng point) {
                Log.LOGGER.severe("Map clicked [" + point.latitude + " / " + point.longitude + "]");
             //   Toast.makeText(MapsActivity.this, "Ubicación [" + point.latitude + " / " + point.longitude + "]", Toast.LENGTH_LONG).show();
                Global.instance().getManejadorInterfaz().agregarMarkerCliqueable(point);
                mMap.setOnMapLongClickListener(null);

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        mMap.setOnMarkerClickListener(null);
                        AgregarPuntoEstadia agregar = new AgregarPuntoEstadia(marker.getPosition());
                        agregar.show(getFragmentManager(), "dialogPuntoEstadia");
                        archivoCache.borrarCache();
                        return true;
                    }
                });


            }
        });

    }

   /* @Override
    public boolean onMarkerClick(Marker marker) {

     //   if (marker.equals(marcadorEstadia)) {
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.fragment_agregar_punto_estadia, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            popupWindow.setFocusable(true);
            popupWindow.update();
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            //BOTON CERRRAR POPUP
            Button botonCerrar = (Button) popupView.findViewById(R.id.boton_cerrar);
            botonCerrar.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            //BOTON  ENVIAR
          /*      Button botonEnviar = (Button) popupView.findViewById(R.id.boton_enviar);
                botonEnviar.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                     /*   if (global.getValidador().chequeoCodigoSUMO(input.getText().toString())) {
                            pedidoSaldo(input.getText().toString());
                            popupWindow.dismiss();
                        } else {
                            global.getManejadorInterfaz().mostrarNotificacionTexto("Código inválido. Por favor, ingrese un código de 8 dígitos.");

                        }

                        pedidoSaldo(input.getText().toString());
                        *//*
                        popupWindow.dismiss();
                    }
                });
            */
 //           return true;
     //   }
     //   return false;
 //   }

    public void clickProximaActividad(MenuItem item){
        try {

            PedirPrediccionRutinaCommand pedirPrediccionRutinasCommad = new PedirPrediccionRutinaCommand();
            pedirPrediccionRutinasCommad.setManejadorInterfaz(global.getManejadorInterfaz());
            pedirPrediccionRutinasCommad.setUsuario(preferencia.getUsuario(this));
            pedirPrediccionRutinasCommad.setFechaConsulta(Calendar.getInstance());
         //   Toast.makeText(this,"fecha current "+Calendar.getInstance().getTime().toString(),Toast.LENGTH_LONG).show();
            pedirPrediccionRutinasCommad.setPuntoActual(CapaServicio.retornarUltimaUbicacionConocida());
            global.getManejadorServidor().agregarElemento(pedirPrediccionRutinasCommad);

/*-------------------LAYOUT PARA MANDAR FEEDBACK AL SERVER SOBRE LA PROXIMA ACTIVIDAD DADA----------------------------------
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.feedback_proxima_actividad, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            popupWindow.setFocusable(true);
            popupWindow.update();
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);




            // input.setShowSoftInputOnFocus(true);// NECESITA CAMBIAR NIVEL DE LA API a 21

            //BOTON CERRRAR POPUP
            Button botonCerrar = (Button) popupView.findViewById(R.id.boton_no);
            botonCerrar.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupWindow.dismiss();
                }
            });

            //BOTON  ENVIAR
            Button botonEnviar = (Button) popupView.findViewById(R.id.boton_si);
            botonEnviar.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();

                }



            });
*/

  /*

                            LoginRutinaCommand loginrutina = new LoginRutinaCommand();
                            global.getManejadorServidor().agregarElemento(loginrutina);


                           CargarRutinaCommand cargarrutina = new CargarRutinaCommand();
                            global.getManejadorServidor().agregarElemento(cargarrutina);


                            EntrenarRutinaCommand entrenarrutina = new EntrenarRutinaCommand();
                            global.getManejadorServidor().agregarElemento(entrenarrutina);

                            PredecirRutinaCommand predecirRutina = new PredecirRutinaCommand(CapaServicio.retornarUltimasUbicaciones());
                            predecirRutina.setManejadorInterfaz(global.getManejadorInterfaz());
                            global.getManejadorServidor().agregarElemento(predecirRutina);

*/

          /*  BuscadorRutinasCommand buscarRutina = new BuscadorRutinasCommand();
            global.getManejadorServidor().agregarElemento(buscarRutina);


            GetActividadCommand puntosRutina = new GetActividadCommand();
            global.getManejadorServidor().agregarElemento(puntosRutina);

            Toast.makeText(this, "Entrenar Rutina", Toast.LENGTH_SHORT).show();*/
        } catch (Exception e){

            Log.LOGGER.severe(e.toString());
        }





    }
/*
    public void clickItemEntrenarRutina(MenuItem item){
        try {


            LoginRutinaCommand loginrutina = new LoginRutinaCommand();
            global.getManejadorServidor().agregarElemento(loginrutina);


            CargarRutinaCommand cargarrutina = new CargarRutinaCommand();
            global.getManejadorServidor().agregarElemento(cargarrutina);


            EntrenarRutinaCommand entrenarrutina = new EntrenarRutinaCommand();
            global.getManejadorServidor().agregarElemento(entrenarrutina);

            PredecirRutinaCommand predecirRutina = new PredecirRutinaCommand(CapaServicio.retornarUltimasUbicaciones());
            predecirRutina.setManejadorInterfaz(global.getManejadorInterfaz());

            global.getManejadorServidor().agregarElemento(predecirRutina);

          /*  BuscadorRutinasCommand buscarRutina = new BuscadorRutinasCommand();
            global.getManejadorServidor().agregarElemento(buscarRutina);


            GetActividadCommand puntosRutina = new GetActividadCommand();
            global.getManejadorServidor().agregarElemento(puntosRutina);*/
/*
            Toast.makeText(this, "Entrenar Rutina", Toast.LENGTH_SHORT).show();
        } catch (Exception e){

            Log.LOGGER.severe(e.toString());
        }
    }
*/
    //-------------------------FIN RUTINAS-----------------------------

    public void clickVerPuntosEstadia(MenuItem item){

        PuntosEstadiaCommand puntosEstadiaCommand = new PuntosEstadiaCommand();
        puntosEstadiaCommand.setManejadorInterfaz(global.getManejadorInterfaz());
        puntosEstadiaCommand.setUsuario(preferencia.getUsuario(this));
        puntosEstadiaCommand.setArchivoCache(archivoCache);

        global.getManejadorServidor().agregarElemento(puntosEstadiaCommand);




    }
    //----------------------CALCULO RUTA-------------------------------

    public ArrayList<Corte> obtenerDatosCache(){
        ArrayList<Corte> puntos;
        ManejadorParser manejadorParser = new ManejadorParser();
        byte[] bytes;
        StringBuffer stringBuffer = archivoCache.leerCache();
        if (stringBuffer != null ){
            bytes  = stringBuffer.toString().getBytes();
            InputStream salida = new ByteArrayInputStream(bytes);
            puntos = manejadorParser.LeerCorte(salida);
            return puntos;
        }
        else{
            //pedir llenar puntosEstadia al servidor y llenar cache
            ConsultarPuntosEstadiaCacheCommand consulta = new ConsultarPuntosEstadiaCacheCommand();
            consulta.setUsuario(preferencia.getUsuario(this));
            consulta.setArchivoCache(archivoCache);
            consulta.setAsyncRespuesta(MapsActivity.this);
            global.getManejadorServidor().agregarElemento(consulta);

        }

       return null;
    }

    public void clickItemCalculoRuta(MenuItem item){

        //consulta de puntos de interes por si no estan en cache(rutinas)
   /*     ConsultarPuntosEstadiaCacheCommand consultarRutinasCommand = new ConsultarPuntosEstadiaCacheCommand();//no va masssssssssss

        consultarRutinasCommand.setUsuario(preferencia.getUsuario(MapsActivity.this));
        consultarRutinasCommand.setArchivoCache(archivoCache);
        consultarRutinasCommand.setAsyncRespuesta(MapsActivity.this);
        global.getManejadorServidor().agregarElemento(consultarRutinasCommand);
*/


        puntosEstadiaUsuario = obtenerDatosCache();

        //inflar el layout visualizar ruta solicitada
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.visualizar_ruta_solicitada, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        //Origen
        RadioGroup radioGroupOrigen = (RadioGroup)popupView.findViewById(R.id.radioGroupOrigen);
        final RadioButton radioButtonUbicacionActual = (RadioButton)popupView.findViewById(R.id.radioButtonUbicacionActual);
        final RadioButton radioButtonEligeUbicacionOrigen = (RadioButton)popupView.findViewById(R.id.radioButtonUbicacionElige);
        final TextView origenIngresado = (TextView)popupView.findViewById(R.id.origenIngresado); //INVISIBLE

        //Destino
        RadioGroup radioGroupDestino = (RadioGroup)popupView.findViewById(R.id.radioGroupDestino);
        final RadioButton radioButtonIngreseUbicacionDestino = (RadioButton)popupView.findViewById(R.id.ingreseDestino);
        final RadioButton radioButtonUbicacionPuntoConocidos = (RadioButton)popupView.findViewById(R.id.puntosConocidos);

        final Spinner spinnerPuntosConocidos = (Spinner)popupView.findViewById(R.id.spinnerPuntosConocidos);
        final TextView destinoIngresado = (TextView)popupView.findViewById(R.id.destinoIngresado);


        final LatLng[] origenCoordenadas = {null};
        final LatLng[] destinoCoordenadas = {null};


        radioGroupOrigen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId) {
                    case R.id.radioButtonUbicacionActual:
                        if (radioButtonUbicacionActual.isChecked()) {

                            origenIngresado.setVisibility(View.INVISIBLE);
                            //levantar ubicaicon actual del usuario
                            try {
                                origenCoordenadas[0] = CapaServicio.retornarUltimaUbicacionConocida();
                            }
                            catch (Exception e){
                                Log.LOGGER.severe(e.toString());
                            }

                        }
                            break;
                    case R.id.radioButtonUbicacionElige:
                        if (radioButtonEligeUbicacionOrigen.isChecked()){
                            seleccionUbicacion = ORIGEN;

                            try {
                                //abrir el google_palces_fragment
                                dialogGoogleSearchPlace = new google_place_search();  //fragmentGooglSearchPlace.showDialog();
                                dialogGoogleSearchPlace.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
                                dialogGoogleSearchPlace.show(getFragmentManager(), "dialogo");


                                respuestaOrigen = new BroadcastReceiver() {
                                    @Override
                                    public void onReceive(Context context, Intent intent) {
                                        if (ubicacionActual != null) {
                                            //    origen[0] = placeActual;

                                            origenCoordenadas[0] = (LatLng) intent.getExtras().get("latLng");
                                            origenIngresado.setText("Origen ingresado: " + intent.getExtras().get("direccion"));
                                            origenIngresado.setVisibility(View.VISIBLE);
                                            ubicacionActual = null;
                                            //desuscribirse broadcast
                                            desuscribirsePlace(respuestaOrigen);
                                            seleccionUbicacion = VER_UBICACION;

                                        }
                                    }
                                };
                                registerReceiver(respuestaOrigen, new IntentFilter("receive_origen"));
                            }
                            catch (Exception e){
                                Log.LOGGER.severe(e.toString());
                            }

                        }
                            break;
                }
            }
        });


        radioGroupDestino.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.puntosConocidos:
                        if (radioButtonUbicacionPuntoConocidos.isChecked()) {
                            destinoIngresado.setVisibility(View.INVISIBLE);

                            try {

                                int cantidadPuntosEstadia = puntosEstadiaUsuario.size();
                                String[] puntos = new String[cantidadPuntosEstadia];
                                if ( cantidadPuntosEstadia != 0){
                                    for (int i = 0;i<cantidadPuntosEstadia;i++){
                                        puntos[i] = puntosEstadiaUsuario.get(i).getDescripcion();
                                    }
                                }
                                ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter<CharSequence>(MapsActivity.this,android.R.layout.simple_spinner_item, puntos);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinnerPuntosConocidos.setAdapter(spinnerArrayAdapter);

                                spinnerPuntosConocidos.setVisibility(View.VISIBLE);
                                //setear destino

                                //ACCION DEL SPINNER
                                spinnerPuntosConocidos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
                                        //usar este valor de destino
                                        destinoCoordenadas[0] = puntosEstadiaUsuario.get(position).getLatLng();
                                    }

                                    public void onNothingSelected(AdapterView<?> parent) {
                                        Toast.makeText(popupView.getContext(), "Nada seleccionado ", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            } catch (Exception e) {
                                Log.LOGGER.severe(e.toString());
                            }
                        }

                        break;

                    case R.id.ingreseDestino:
                        if (radioButtonIngreseUbicacionDestino.isChecked()) {

                            spinnerPuntosConocidos.setVisibility(View.INVISIBLE);
                            seleccionUbicacion = DESTINO;

                            try {
                                //abrir el google_palces_fragment
                                dialogGoogleSearchPlace = new google_place_search();  //fragmentGooglSearchPlace.showDialog();
                                dialogGoogleSearchPlace.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
                                dialogGoogleSearchPlace.show(getFragmentManager(), "dialogo");


                                 respuestaDestino = new BroadcastReceiver() {
                                    @Override
                                    public void onReceive(Context context, Intent intent) {
                                        if (ubicacionActual != null) {

                                            destinoCoordenadas[0] = (LatLng) intent.getExtras().get("latLng");
                                            destinoIngresado.setText("Destino ingresado: " + intent.getExtras().get("direccion"));
                                            destinoIngresado.setVisibility(View.VISIBLE);
                                            ubicacionActual = null;
                                            //desuscribirse broadcast

                                            desuscribirsePlace(respuestaDestino);

                                            seleccionUbicacion = VER_UBICACION;
                                        }
                                    }
                                };
                                registerReceiver(respuestaDestino, new IntentFilter("receive_destino"));
                            } catch (Exception e) {
                                Log.LOGGER.severe(e.toString());
                            }
                        }
                        break;
                }
            }
        });



        //BOTON CERRRAR POPUP
        Button botonCerrar = (Button) popupView.findViewById(R.id.boton_cerrar);
        botonCerrar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        //BOTON  ENVIAR
        Button botonEnviar = (Button) popupView.findViewById(R.id.boton_enviar);
        botonEnviar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcularRuta(origenCoordenadas[0], destinoCoordenadas[0]);
                origenCoordenadas[0] = null;
                destinoCoordenadas[0] = null;
                popupWindow.dismiss();
            }
        });
    }


    public void desuscribirsePlace(BroadcastReceiver b){
        try {
            unregisterReceiver(b);
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }
    }
    private void calcularRuta(LatLng origenCoordenadas, LatLng destinoCoordenadas) {

       // preparar command
        try {
            Punto origen = new Punto(origenCoordenadas.latitude, origenCoordenadas.longitude, "");
            Punto destino = new Punto(destinoCoordenadas.latitude, destinoCoordenadas.longitude, "");

            PedirRutaCommand pedirRutaCommand = new PedirRutaCommand();
            pedirRutaCommand.setManejadorInterfaz(global.getManejadorInterfaz());
            pedirRutaCommand.setOrigen(origen);
            pedirRutaCommand.setDestino(destino);
            global.getManejadorServidor().agregarElemento(pedirRutaCommand);
        }
        catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }


    }



    //---------------------FIN CALCULO RUTA----------------------------
    //En este metodo se devuelve el lugar seleccionado en el fragment de de googgle_place_search
    public  void LugarSeleccionado(Place lugar){

        try {

            if (dialogGoogleSearchPlace != null) {
                dialogGoogleSearchPlace.dismiss();
            }

            placeActual = lugar;
            ubicacionActual = new LatLng(0, 0);
            ubicacionActual = lugar.getLatLng();


            switch (seleccionUbicacion){
                case(ORIGEN):{
                    Intent notifyIntent = new Intent("receive_origen");
                    notifyIntent.putExtra("direccion", lugar.getAddress());
                    notifyIntent.putExtra("latLng",ubicacionActual);
                    sendBroadcast(notifyIntent);
                }
                break;
                case(DESTINO):{
                    Intent notifyIntent = new Intent("receive_destino");
                    notifyIntent.putExtra("direccion", lugar.getAddress());
                    notifyIntent.putExtra("latLng",ubicacionActual);
                    sendBroadcast(notifyIntent);
                }
                case(VER_UBICACION):{
                    global.getManejadorInterfaz().DibujarMarcador(new Marcador(ubicacionActual, lugar.getAddress().toString(), "IC_TRACKER"));
                }
                break;

            }




        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }

    }


    public Context getContext() {

        try {
            return context;
        }catch (Exception e){Log.LOGGER.severe(e.toString());
        return  null;}
    }

    @Override
    public void finObtencionPECache(ArrayList<Corte> puntos) {
        puntosEstadiaUsuario = puntos;
    }


    @Override
    public void rutinaIngresada(RutinaG rutinaEnviar) {
        //verrrrrrrrrrrrrrr
    }
}
