package transporte.appbase.Rutinas;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Global;
import transporte.appbase.Parser.Parser;
import transporte.appbase.R;
import transporte.appbase.Servidor.AgregarPuntoEstadiaCommand;
import transporte.appbase.google_place_search;


public class AgregarPuntoEstadia extends DialogFragment implements DatePickerFragment.OnDateSetListener, TimePickerFragment.OnTimeSetListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int POS_FECHA = 0;
    private static final int POS_HORA = 1;
    private static final int CODE = 1;


    private String mParam1;
    private String mParam2;

    private LatLng puntoEstadiaCoordenadas = new LatLng(0, 0);

    private String fechaInicio, fechaFin = null;
    private String horaInicio, horaFin;

    private TextView fechaInicioSeleccionada;
    private TextView horaInicioSeleccionada;
    private TextView horaFinSeleccionada;
    private TextView fechaFinSeleccionada;
 //   private TextView destinoSeleccionado;
    private EditText nombreRutina;

    private LatLng puntoSeleccionado;
    private DialogFragment dialogGoogleSearchPlace;


    private String delimitador = "[ ]";

    private OnFragmentInteractionListener mListener;
 /*   private ToggleButton chk_lunes;
    private ToggleButton chk_martes;
    private ToggleButton chk_miercoles;
    private ToggleButton chk_jueves;
    private ToggleButton chk_viernes;
    private ToggleButton chk_sabado;
    private ToggleButton chk_domingo;
*/
    private HashMap seleccionDiasRutina;
    private static final String HORA_INICIO = "inicioH";
    private static final String HORA_FIN = "finH";
    private static final String FECHA_INICIO = "inicioF";
    private static final String FECHA_FIN = "finF";
    private String seleccionado;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarPuntoEstadia.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarPuntoEstadia newInstance(String param1, String param2) {
        LatLng p = new LatLng(0,0);
        AgregarPuntoEstadia fragment = new AgregarPuntoEstadia(p);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public AgregarPuntoEstadia(LatLng puntoSeleccionado) {

        seleccionDiasRutina = new HashMap();
        this.puntoSeleccionado = puntoSeleccionado;

    }

 /*   public AgregarPuntoEstadia(RutinaG rutinaEditar, Context context/*, String direccion) {*/
  //      seleccionDiasRutina = new HashMap();
 //       this.rutinaEditar = rutinaEditar;
    //    this.contextoEditar = context;
        //   this.direccionEditar = direccion;


//   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    //    global = new Global();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View myInflatedView = inflater.inflate(R.layout.fragment_agregar_punto_estadia, container, false);
        //nuevo
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        Button buttonNombre = (Button) myInflatedView.findViewById(R.id.nombre_estadia);
        buttonNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarNombre();
            }
        });

                /*
                        Button buttonDestino = (Button) myInflatedView.findViewById(R.id.destino_rutina);
                        buttonDestino.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                seleccionarLugar();
                            }
                        });
*/
        Button botonFechaI = (Button)myInflatedView.findViewById(R.id.fecha_inicio_estadia);
        botonFechaI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionado = FECHA_INICIO;
                showDatePickerDialog(v);
            }
        });

   //     Button botonHoraI = (Button)myInflatedView.findViewById(R.id.hora_inicio_estadia);
   //     botonHoraI.setOnClickListener(new View.OnClickListener(){

        Button botonHoraI = (Button) myInflatedView.findViewById(R.id.hora_inicio_estadia);
        botonHoraI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  seleccionado = HORA_INICIO;
                  showTimePickerDialog(v);
            }
        });

        Button botonFechaF = (Button)myInflatedView.findViewById(R.id.fecha_fin_estadia);
        botonFechaF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionado = FECHA_FIN;
                showDatePickerDialog(v);
            }
        });

        Button botonHoraF = (Button)myInflatedView.findViewById(R.id.hora_fin_estadia);
        botonHoraF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionado = HORA_FIN;
                showTimePickerDialog(v);
            }
        });

        nombreRutina =(EditText)myInflatedView.findViewById(R.id.nombre_descripcion);
        fechaInicioSeleccionada = (TextView)myInflatedView.findViewById(R.id.fechaInicioSeleccionada);
        horaInicioSeleccionada = (TextView)myInflatedView.findViewById(R.id.horaInicioSeleccionada);
        fechaFinSeleccionada = (TextView)myInflatedView.findViewById(R.id.fechaFinSeleccionada);
        horaFinSeleccionada = (TextView)myInflatedView.findViewById(R.id.horaFinSeleccionada);


     /*
        if (rutinaEditar != null) {
            nombreRutina.setVisibility(View.VISIBLE);
            //   nombreRutina.setText(rutinaEditar.getNombreRutina());GIANNO
            nombreRutina.setText(rutinaEditar.getNombre());

            destinoSeleccionado.setVisibility(TextView.VISIBLE);
        //    destinoSeleccionado.setText(rutinaEditar.getDireccionDestino()); //nuevo porque viene en la rutinaEditar
          //  puntoEstadiaCoordenadas = rutinaEditar.getDestinoRutina();
            destinoSeleccionado.setText(rutinaEditar.getpDestino());

            //    String[] palabrasSeparadas = rutinaEditar.getFecha().split(delimitador);GIANNO
            //String[] palabrasSeparadas = rutinaEditar.getFecha().getTime().toString();
        
      //      String fechaInicio =  rutinaEditar.getFechaInicio();
      //      String fechaFin =  rutinaEditar.getFechaInicio();

            fechaInicioSeleccionada.setVisibility(TextView.VISIBLE);
            fechaInicioSeleccionada.setText("Fecha: " + Parser.Fecha(rutinaEditar.getFechaInicio().getTime()));
            //  fechaRutina = palabrasSeparadas[POS_FECHA];

            horaInicioSeleccionada.setVisibility(TextView.VISIBLE);
            horaInicioSeleccionada.setText("Hora: " + Parser.HoraFecha(rutinaEditar.getFechaInicio().getTime()));
            horaRutina = Parser.HoraFecha(rutinaEditar.getFechaInicio().getTime());


        }*/
/*
        chk_lunes = (ToggleButton) myInflatedView.findViewById(R.id.chk_lunes);
        chk_lunes.setOnCheckedChangeListener(toggleButtonChangeListener);
        chk_martes = (ToggleButton) myInflatedView.findViewById(R.id.chk_martes);
        chk_martes.setOnCheckedChangeListener(toggleButtonChangeListener);
        chk_miercoles = (ToggleButton) myInflatedView.findViewById(R.id.chk_miercoles);
        chk_miercoles.setOnCheckedChangeListener(toggleButtonChangeListener);
        chk_jueves = (ToggleButton) myInflatedView.findViewById(R.id.chk_jueves);
        chk_jueves.setOnCheckedChangeListener(toggleButtonChangeListener);
        chk_viernes = (ToggleButton) myInflatedView.findViewById(R.id.chk_viernes);
        chk_viernes.setOnCheckedChangeListener(toggleButtonChangeListener);
        chk_sabado = (ToggleButton) myInflatedView.findViewById(R.id.chk_sabado);
        chk_sabado.setOnCheckedChangeListener(toggleButtonChangeListener);
        chk_domingo = (ToggleButton) myInflatedView.findViewById(R.id.chk_domingo);
        chk_domingo.setOnCheckedChangeListener(toggleButtonChangeListener);

        CheckBox checkBox = (CheckBox) myInflatedView.findViewById(R.id.repeat);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    chk_lunes.setVisibility(View.VISIBLE);
                    chk_martes.setVisibility(View.VISIBLE);
                    chk_miercoles.setVisibility(View.VISIBLE);
                    chk_jueves.setVisibility(View.VISIBLE);
                    chk_viernes.setVisibility(View.VISIBLE);
                    chk_sabado.setVisibility(View.VISIBLE);
                    chk_domingo.setVisibility(View.VISIBLE);
                } else {
                    chk_lunes.setVisibility(View.INVISIBLE);
                    chk_martes.setVisibility(View.INVISIBLE);
                    chk_miercoles.setVisibility(View.INVISIBLE);
                    chk_jueves.setVisibility(View.INVISIBLE);
                    chk_viernes.setVisibility(View.INVISIBLE);
                    chk_sabado.setVisibility(View.INVISIBLE);
                    chk_domingo.setVisibility(View.INVISIBLE);
                }

            }
        });
*/
        //BOTON CERRRAR
        Button botonCerrar = (Button) myInflatedView.findViewById(R.id.boton_cerrar);
        botonCerrar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AgregarPuntoEstadia.this.dismiss();
                Global.instance().getManejadorInterfaz().limpiarMapa();
            }
        });

        //BOTON  ENVIAR
        Button botonEnviar = (Button) myInflatedView.findViewById(R.id.boton_enviar_rutina);
        botonEnviar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean nombreVacio = Global.instance().getValidador().chequeoCampoVacio(nombreRutina.getText().toString());
                if (nombreVacio) {
                    nombreRutina.setError("Este campo no puede estar vacío");
                    nombreRutina.setVisibility(View.VISIBLE);

                }
            /*    boolean destinoVacio = global.getValidador().chequeoCampoVacio(destinoSeleccionado.getText().toString());
                if (destinoVacio) {
                    destinoSeleccionado.setError("Este campo no puede estar vacío");
                    destinoSeleccionado.setVisibility(View.VISIBLE);
                }
            */  boolean fechaInicioVacia = Global.instance().getValidador().chequeoCampoVacio(fechaInicioSeleccionada.getText().toString());
                if (fechaInicioVacia) {
                    fechaInicioSeleccionada.setError("Este campo no puede estar vacío");
                    fechaInicioSeleccionada.setVisibility(View.VISIBLE);
                }
                boolean horaInicioVacia = Global.instance().getValidador().chequeoCampoVacio(horaInicioSeleccionada.getText().toString());
                if (horaInicioVacia){
                    horaInicioSeleccionada.setError("Este campo no puede estar vacío");
                    horaInicioSeleccionada.setVisibility(View.VISIBLE);
                }
                boolean fechaFinVacia = Global.instance().getValidador().chequeoCampoVacio(fechaFinSeleccionada.getText().toString());
                if (fechaFinVacia) {
                    fechaFinSeleccionada.setError("Este campo no puede estar vacío");
                    fechaFinSeleccionada.setVisibility(View.VISIBLE);
                }
                boolean horaFinVacia = Global.instance().getValidador().chequeoCampoVacio(horaFinSeleccionada.getText().toString());
                if (horaFinVacia){
                    horaFinSeleccionada.setError("Este campo no puede estar vacío");
                    horaFinSeleccionada.setVisibility(View.VISIBLE);
                }

                if (!nombreVacio && !fechaInicioVacia && !horaInicioVacia && !fechaFinVacia && !horaFinVacia) {
                    //-----------------------ARMADO OBJETO PUNTO ESTADIA PARA ENVIAR---------
                    String fechaI = fechaInicio+" "+horaInicio;
                    String fechaF = fechaFin+" "+horaFin;
                    PuntoEstadia puntoEstadia = new PuntoEstadia(puntoSeleccionado,nombreRutina.getText().toString(),fechaI,fechaF);
                    AgregarPuntoEstadia.this.dismiss();


                    //comand de envio de punto de estadia al servidor
                try{
                    AgregarPuntoEstadiaCommand agregarPuntoEstadiaCommand = new AgregarPuntoEstadiaCommand();
                    agregarPuntoEstadiaCommand.setManejadorInterfaz(Global.instance().getManejadorInterfaz());
                    agregarPuntoEstadiaCommand.setPuntoEstadia(puntoEstadia);
                    Global.instance().getManejadorServidor().agregarElemento(agregarPuntoEstadiaCommand);
                }
                catch (Exception e){
                    Log.LOGGER.severe(e.toString());}
                }


            }
        });
        return myInflatedView;
    }

    final CompoundButton.OnCheckedChangeListener toggleButtonChangeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.isChecked()) {
                seleccionDiasRutina.put(buttonView.getId(), buttonView.getText());
            } else {
                seleccionDiasRutina.remove(buttonView.getId());
            }
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public void insertarNombre() {
        nombreRutina.setVisibility(View.VISIBLE);
        //levantar el valor del nombre


    }

    //--------------------Comunicacion con DialogFragment Fecha y hora-------------------
    public void showTimePickerDialog(View v) {
        TimePickerFragment myDialogFragment = new TimePickerFragment();
        myDialogFragment.show(getFragmentManager(), "TimePicker");
        myDialogFragment.setOnTimeSetListener(this);
    }


    public void showDatePickerDialog(View v) {
        DatePickerFragment myDialogFragment = new DatePickerFragment();
        myDialogFragment.show(getFragmentManager(), "DatePicker");
        myDialogFragment.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        java.text.SimpleDateFormat df1 = new java.text.SimpleDateFormat("MM");
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        String month = df1.format(c.getTime());

        switch (seleccionado) {
            case (FECHA_INICIO): {
                fechaInicioSeleccionada.setText("Fecha: " + dayOfMonth + "/" + month + "/" + year);
                fechaInicioSeleccionada.setVisibility(TextView.VISIBLE);
                fechaInicio = month + "/" + dayOfMonth + "/" +  year;
                }
            break;
            case (FECHA_FIN): {
                fechaFinSeleccionada.setText("Fecha: " + dayOfMonth + "/" + month + "/" + year);
                fechaFinSeleccionada.setVisibility(TextView.VISIBLE);
                fechaFin =  month + "/" + dayOfMonth + "/" +  year;
            }
            break;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hora, int minutos) {
    //    horaInicioSeleccionada.setText("Hora: " + hora + ":" + minutos);
    //    horaInicioSeleccionada.setVisibility(TextView.VISIBLE);
    //    horaRutina = hora+":" + minutos;

        switch (seleccionado){
            case(HORA_INICIO): {
                horaInicioSeleccionada.setText("Hora: " + hora + ":" + minutos);
                horaInicioSeleccionada.setVisibility(TextView.VISIBLE);
                horaInicio = hora + ":" + minutos + ":00"  ;
            } break;
            case (HORA_FIN):{
                horaFinSeleccionada.setText("Hora: " + hora + ":" + minutos);
                horaFinSeleccionada.setVisibility(TextView.VISIBLE);
                horaFin = hora + ":" + minutos+ ":00";
            }break;
        }

    }
//--------------------FIN Comunicacion con DialogFragment Fecha y hora-------------------

    public void seleccionarLugar() {
        dialogGoogleSearchPlace = new google_place_search();  //fragmentGooglSearchPlace.showDialog();
        dialogGoogleSearchPlace.setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
        dialogGoogleSearchPlace.setTargetFragment(AgregarPuntoEstadia.this, CODE);
        dialogGoogleSearchPlace.show(getFragmentManager(), "dialogo");
    }

    //para mandarle la info al activity maps y usar los datos
    public interface OnFragmentInteractionListener {

        void rutinaIngresada(RutinaG rutinaEnviar);

    }

    public void respuestaPlace(Place destino) {
     //   destinoSeleccionado.setText(destino.getAddress());
     //   destinoSeleccionado.setVisibility(TextView.VISIBLE);
        //setea el LatLng para crear el objeto Rutina.
        puntoEstadiaCoordenadas = destino.getLatLng();
    }


}


