package transporte.appbase.Servidor;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import transporte.appbase.Archivos.ElementoArchivo;
import transporte.appbase.Archivos.Log;
import transporte.appbase.Configuracion.ArchivoCache;
import transporte.appbase.Ruta.Corte;
import transporte.appbase.Rutinas.PuntoEstadia;
import transporte.appbase.Rutinas.RutinaG;
import transporte.appbase.Usuario;
import transporte.appbase.Util.Adaptador;


/**
 * Created by Agustín on 06/10/2015.
 */
public class Server {


    private static final String IP="intranet.isistan.unicen.edu.ar";//"192.168.1.10";
    //private static final String URL_REPORTAR_CORTE ="http://200.5.106.52/AIPTU/guardar_corte.php";
    private static final String URL_REPORTAR_CORTE ="http://"+IP+":8080/nextplace/ReportarCorteServlet";

  //  private static final String URL_PEDIDO_CORTES ="http://200.5.106.52/AIPTU/obtener_cortes.php";
    private static final String URL_PEDIDO_CORTES ="http://"+IP+":8080/nextplace/ObtenerCortesServlet";

  //  private static final String URL_LOGIN_USUARIO_TWITTER ="http://200.5.106.52/AIPTU/iniciar_sesion_twitter.php";
    private static final String URL_LOGIN_USUARIO_TWITTER ="http://"+IP+":8080/nextplace/LoginTwitterServlet";

  //  private static final String URL_LOGIN_USUARIO ="http://200.5.106.52/AIPTU/iniciar_sesion.php";
    private static final String URL_LOGIN_USUARIO ="http://"+IP+":8080/nextplace/login";

 //   private static final String URL_REGISTRO_USUARIO ="http://200.5.106.52/AIPTU/crear_cuenta.php";
    private static final String URL_REGISTRO_USUARIO ="http://"+IP+":8080/nextplace/RegistroUsuarioServlet";

 //   private static final String URL_SERVIDOR = "http://200.5.106.52/AIPTU/enviar_archivo.php";
    private static final String URL_SERVIDOR = "http://"+IP+":8080/nextplace/AlmacenarArchivoServlet";

    private static final String URL_USUARIO_REGISTRADO = "http://"+IP+":8080/nextplace/LoginRegistradoServlet";

    private static final String URL_USUARIO_ELIMINAR_REGISTRO = "http://"+IP+":8080/nextplace/logout";

    private static final String URL_CONSULTA_CORTE = "http://"+IP+":8080/nextplace/ConsultaPrueba2Servlet";

    private static final String URL_PREDICCION_RUTINA ="http://"+IP+":8080/nextplace/PredecirProximaRutinaServlet";

   private static final String URL_PEDIDO_PARQUIMETROS  = "http://"+IP+":8080/nextplace/ObtenerParquimetrosServlet";
   //TENDRIA QUE IR ESTA EN ALGUN MOMENTO private static final String URL_PEDIDO_PARQUIMETROS  = "http://www.gpssumo.com/parquimetros/get_PA/25";

    private static final String URL_RUTINAS_POR_DIA = "http://"+IP+":8080/nextplace/ObtenerRutinasPorDiaServlet";
    private static final String URL_CALCULAR_RUTA = "http://"+IP+":8080/nextplace/ObtenerRutaSinCortes"; //"http://maps.googleapis.com/maps/api/directions/json?origin=";
    private static final String URL_ENVIAR_PUNTO_ESTADIA = "http://"+IP+":8080/nextplace/AlmacenarPuntoEstadiaServlet";
    private static final String URL_PEDIDO_PUNTOS_ESTADIA="http://"+IP+":8080/nextplace/ObtenerPuntosEstadiaServlet";

   //pasar todo a servicio web como el saldo
    private static final String URL_PEDIDO_SALDO = "http://www.gpssumo.com/movimientos/get_saldo_app/";
    private static final String URL_PEDIDO_PUNTOS_CARGA ="http://200.5.106.52/AIPTU/obtener_puntos_carga.php";
    private static final String URL_PEDIDO_COLECTIVOS = "http://gpssumo.com/ajax/ebus_dev/get/";
    public static final String HASH_ID_PEDIDOCOLECTIVO = "faa8f91f9b9fbc077ac44ca18aaa7b97";

    private static final String URL_LOGIN_RUTINA="http://"+IP+":8080/nextplace/login";
    private static final String URL_CARGAR_RUTINA="http://"+IP+":8080/nextplace/files/upload";
    private static final String URL_ENTRENAR_RUTINA="http://"+IP+":8080/nextplace/files/train";
    private static final String URL_BUSCAR_RUTINA="http://"+IP+":8080/nextplace/activityfinder";
    private static final String URL_GET_ACTIVIDAD ="http://"+IP+":8080/nextplace/get-info/activity";
    private static final String URL_GET_PREDICCION ="http://"+IP+":8080/nextplace/predict/string";



    private static final String URL_ENVIAR_RUTINA="http://200.5.106.52/AIPTU/enviar_rutina.php";




    private static final String URL_ELIMINAR_RUTINA = "http://200.5.106.52/AIPTU/eliminar_rutina.php";

    private HttpClient client ;


    public Server() {
     //   this.client = new DefaultHttpClient();


    }

    public HttpClient getClientUsuario(){
        return client;
    }
    public void setClient(HttpClient client){
        this.client = client;
    }

    public void EnviarCorte(Corte corte,/*Context context,*/AgregarCorteCommand agregarCorteCommand){
        try {

            ArrayList<NameValuePair> parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("latitud", String.valueOf(corte.getLatitude())));
            parametrosConcatenar.add(new BasicNameValuePair("longitud", String.valueOf(corte.getLongitude())));

            parametrosConcatenar.add(new BasicNameValuePair("descripcion", corte.getDescripcion()));
            parametrosConcatenar.add(new BasicNameValuePair("nombreIcono", corte.getNombreIcono()));
            parametrosConcatenar.add(new BasicNameValuePair("fechaCreacion", String.valueOf(corte.getFechaCreacion())));
            parametrosConcatenar.add(new BasicNameValuePair("fechaInicio", String.valueOf(corte.getFechaInicio())));
            parametrosConcatenar.add(new BasicNameValuePair("fechaFin", String.valueOf(corte.getFechaFin())));


            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_REPORTAR_CORTE);
            parametros.put("parametrosConsulta", parametrosConcatenar);


        //    ConsultaServerGenerica consultaServerGenerica = new ConsultaServerGenerica(agregarCorteCommand);
        //    consultaServerGenerica.execute(parametros, null,null);

            //TOMCAT
            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(agregarCorteCommand,client);
            consultaServerGenerica.execute(parametros, null, null);


        }catch (Exception e){ Log.LOGGER.severe(e.toString());}

    }

    public void PedirCorte(Double latitude,Double longitude, int distancia, PedirCorteCommand pedirCorteCommand) {
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            parametrosPost.add(new BasicNameValuePair("latitudOrigen", Double.toString(latitude)));
            parametrosPost.add(new BasicNameValuePair("longitudOrigen", Double.toString(longitude)));
            parametrosPost.add(new BasicNameValuePair("distancia", Integer.toString(distancia)));
            parametros.put("parametrosConsulta", parametrosPost);
            parametros.put("url", URL_PEDIDO_CORTES);

        //    ConsultaServidorGenerica pedidoCortes = new PedidoCortesServidor(pedirCorteCommand);
        //    pedidoCortes.execute(parametros, null, null);

       //     ConsultaServerGenerica consultaServerGenerica = new ConsultaServerGenerica(pedirCorteCommand);
         //   consultaServerGenerica.execute(parametros, null,null);

            //TOMCAT
            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(pedirCorteCommand,client);
            consultaServerGenerica.execute(parametros, null, null);

        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

   //PEDIR SALDO
    public void PedirSaldo(String tarjeta, PedirSaldoCommand pedirSaldoCommand) {
        try {

            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();

            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("url", URL_PEDIDO_SALDO + tarjeta);
            parametros.put("parametrosConsulta", parametrosConcatenar);
            ConsultaServerGenerica pedidoSaldo = new ConsultaServerGenerica(pedirSaldoCommand);
            pedidoSaldo.execute(parametros, null, null);

        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    //enviarUsuario
    public void enviarUsuarioTwitter(Usuario usuario, EnviarUsuarioTwitterCommand enviarUsuarioTwitterCommand) {
        try {

            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("user", usuario.getNombreUsuario()));

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_LOGIN_USUARIO_TWITTER);
            parametros.put("parametrosConsulta", parametrosConcatenar);

            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(enviarUsuarioTwitterCommand,client);
            consultaServerGenerica.execute(parametros, null, null);

        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());

        }

    }

    //Modificada para acceder al server Tomcat :)
    public void enviarUsuarioLogin(Usuario usuario, EnviarUsuarioLoginCommand enviarUsuarioLoginCommand){
        try {

            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("user", usuario.getNombreUsuario()));
            parametrosConcatenar.add(new BasicNameValuePair("pwd", usuario.getContrasenia1()));
        //    parametrosConcatenar.add(new BasicNameValuePair("mail", usuario.geteMail()));

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_LOGIN_USUARIO);
            parametros.put("parametrosConsulta", parametrosConcatenar);

       //     ConsultaServerGenerica consultaServerGenerica = new ConsultaServerGenerica(enviarUsuarioLoginCommand);
       //     consultaServerGenerica.execute(parametros, null,null);

            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(enviarUsuarioLoginCommand,client);
            consultaServerGenerica.execute(parametros, null, null);


        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());

        }

    }

    public void enviarUsuarioRegistro(Usuario usuario, EnviarUsuarioRegistroCommand enviarUsuarioRegistroCommand){
        try {

            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("user", usuario.getNombreUsuario()));
            parametrosConcatenar.add(new BasicNameValuePair("pwd", usuario.getContrasenia1()));
            parametrosConcatenar.add(new BasicNameValuePair("eMail", usuario.geteMail()));
                    //ver twitter parametro

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_REGISTRO_USUARIO);
            parametros.put("parametrosConsulta", parametrosConcatenar);


      //      ConsultaServerGenerica consultaServerGenerica = new ConsultaServerGenerica(enviarUsuarioRegistroCommand);
       //     consultaServerGenerica.execute(parametros, null, null);

            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(enviarUsuarioRegistroCommand,client);
            consultaServerGenerica.execute(parametros, null, null);

        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());

        }

    }


    public void pedirParquimetros(PedirParquimetrosCommad pedirCorteCommand){
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            parametros.put("parametrosConsulta", parametrosPost);
          //  parametros.put("url", URL_PEDIDO_PARQUIMETROS); SACADO PARA PROBAR OTRA COSA
            parametros.put("url", URL_PEDIDO_PARQUIMETROS);

            //    ConsultaServidorGenerica pedidoParquimetros = new PedidoParquimetrosServidor(pedirCorteCommand);
        //    pedidoParquimetros.execute(parametros, null, null);

            ConsultaServidorTomcat consultaServerTomcat = new ConsultaServidorTomcat(pedirCorteCommand,client);
            consultaServerTomcat.execute(parametros, null,null);

        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }



    public void pedirColectivos(PedirColectivosCommad pedirColectivosCommand, String linea){
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();

            parametros.put("parametrosConsulta", parametrosPost);
            //String dir = URL_PEDIDO_COLECTIVOS + (new Date().getTime()) + "/0";
            String dir = URL_PEDIDO_COLECTIVOS + "faa8f91f9b9fbc077ac44ca18aaa7b97" + "/0";
            parametros.put("url", dir);
            //MEJORAR MANEJOS DE CONSTANTES EN CODIGO
            parametros.put("linea", linea);

            ConsultaServerGPSSumo consultaServerGenerica = new ConsultaServerGPSSumo(pedirColectivosCommand);
            consultaServerGenerica.execute(parametros, null,null);

        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    public void pedirPuntosCarga(PedirPuntosCargaCommand pedirPuntosCarga){
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            parametros.put("parametrosConsulta", parametrosPost);
            parametros.put("url", URL_PEDIDO_PUNTOS_CARGA);

       //     ConsultaServidorGenerica pedidoParquimetros = new PedidoPuntosCargaServidor(pedirPuntosCarga);
        //    pedidoParquimetros.execute(parametros, null, null);

            ConsultaServerGenerica consultaServerGenerica = new ConsultaServerGenerica(pedirPuntosCarga);
            consultaServerGenerica.execute(parametros, null,null);

        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }


    public void enviarArchivoGeneral(ElementoArchivo archivo,EnviarArchivoCommand enviarArchivoCommand, String nombreUsuario){
        //Preparar parametros

        try {
        //    contexto=pcontexto;

            Log.LOGGER.info("Archivo a enviar: " + archivo.getNombre());
            //llamado asincronico
       //     Preferencias preferencia=new Preferencias();
        //    String nombreUsuario = preferencia.getUsuario(contexto);
            Log.LOGGER.info("Usuario archivo: " + nombreUsuario);

            Log.LOGGER.info("Path archivo: " + archivo.getPath());
            //       this.usuarioActual.add((new BasicNameValuePair("nombreUsuario", nombreUsuario)));

            ArrayList<NameValuePair> parametrosConcatenar = new ArrayList<NameValuePair>();
        //    parametrosConcatenar.add(new BasicNameValuePair("usuario", nombreUsuario));
            parametrosConcatenar.add(new BasicNameValuePair("nombreArchivo", archivo.getNombre()));
        //    parametrosConcatenar.add(new BasicNameValuePair("rutaArchivo",archivo.getPath()));

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_SERVIDOR);
            parametros.put("rutaArchivo",archivo.getPath());
            parametros.put("nombreArchivo", archivo.getNombre());
            parametros.put("parametrosEnvio", parametrosConcatenar);


       //     SubirArchivosServidor enviarArchivo = new SubirArchivosServidor(enviarArchivoCommand);
        //    enviarArchivo.execute(parametros, null, null);


            SubirArchivosServidorTomcat enviarArchivo = new SubirArchivosServidorTomcat(enviarArchivoCommand, client);
            enviarArchivo.execute(parametros, null, null);

        }catch (Exception e){
            Log.LOGGER.severe(e.toString()+"Archivo "+archivo.getNombre()+"Path: "+archivo.getPath());}

    }
    public void loginRutina(String valor,LoginRutinaCommand command){

        try {
            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("username", "user"));
            parametrosConcatenar.add(new BasicNameValuePair("password", "user"));


            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_LOGIN_RUTINA);
            parametros.put("parametrosConsulta", parametrosConcatenar);

            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(command,client);
            consultaServerGenerica.execute(parametros, null,null);

        }
        catch (Exception e){Log.LOGGER.severe(e.toString());}



    }


    public void CargarRutina(String s, CargarRutinaCommand cargarRutinaCommand) {
        try {
            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("all", "false"));
            parametrosConcatenar.add(new BasicNameValuePair("", ""));//NO VA


            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_CARGAR_RUTINA);
            parametros.put("parametrosConsulta", parametrosConcatenar);

          /*  ConsultaServerGenerica consultaServerGenerica = new ConsultaServerGenerica(cargarRutinaCommand);
            consultaServerGenerica.execute(parametros, null,null);*/
            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(cargarRutinaCommand,client);
            consultaServerGenerica.execute(parametros, null, null);

        }
        catch (Exception e){Log.LOGGER.severe(e.toString());}





    }

    public void entrenarRutina(String s, EntrenarRutinaCommand entrenarRutinaCommand) {

        try {
            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("", ""));
            parametrosConcatenar.add(new BasicNameValuePair("", ""));//NO VA


            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_ENTRENAR_RUTINA);
            parametros.put("parametrosConsulta", parametrosConcatenar);

       /*     ConsultaServerGenerica consultaServerGenerica = new ConsultaServerGenerica(entrenarRutinaCommand);
            consultaServerGenerica.execute(parametros, null,null);*/
            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(entrenarRutinaCommand, client);
            consultaServerGenerica.execute(parametros, null, null);

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());

        }
    }
    //MODIFICAR
        public void enviarRutina(RutinaG rutina, EnviarRutinaCommand enviarRutinaCommand){
            try {
/*
                ArrayList<NameValuePair> parametrosConcatenar = new ArrayList<NameValuePair>();
                parametrosConcatenar.add(new BasicNameValuePair("idRutina", String.valueOf(rutina.getIdRutina()))); //nuevoo
                parametrosConcatenar.add(new BasicNameValuePair("nombreRutina", rutina.getNombre()));
                parametrosConcatenar.add(new BasicNameValuePair("longitudOrigen", String.valueOf(rutina.getOrigenRutina().longitude)));
                parametrosConcatenar.add(new BasicNameValuePair("latitudOrigen", String.valueOf(rutina.getOrigenRutina().latitude)));
                parametrosConcatenar.add(new BasicNameValuePair("longitudDestino", String.valueOf(rutina.getDestinoRutina().longitude)));
                parametrosConcatenar.add(new BasicNameValuePair("latitudDestino", String.valueOf(rutina.getDestinoRutina().latitude)));
            //    parametrosConcatenar.add(new BasicNameValuePair("direccionDestino", rutina.getDireccionDestino())); OJO ACA

                // ojo no   parametrosConcatenar.add(new BasicNameValuePair("nombreIcono", corte.getNombreIcono()));

                parametrosConcatenar.add(new BasicNameValuePair("fechaInicio",  String.valueOf(rutina.getFechaInicio())/* + rutina.getHoraRutina()*///));
 /*               parametrosConcatenar.add(new BasicNameValuePair("fechaFin",  String.valueOf(rutina.getFechaFin())));
                parametrosConcatenar.add(new BasicNameValuePair("nombreUsuario",enviarRutinaCommand.getNombreUsuario()));


                HashMap<String, Object> parametros;
                parametros = new HashMap<String, Object>();
                parametros.put("url", URL_ENVIAR_RUTINA);
                parametros.put("parametrosConsulta", parametrosConcatenar);


                ConsultaServerGenerica consultaServerGenerica = new ConsultaServerGenerica(enviarRutinaCommand);
                consultaServerGenerica.execute(parametros, null,null);
                */
            }catch (Exception e){ Log.LOGGER.severe(e.toString());}


        }


    public void buscarRutinas(String s, BuscadorRutinasCommand buscadorRutinasCommand)  {

        try{

        ArrayList<NameValuePair> parametrosConcatenar;
        parametrosConcatenar = new ArrayList<NameValuePair>();
    HashMap<String, Object> parametros;
        parametros = new HashMap<String, Object>();
        parametros.put("url", URL_BUSCAR_RUTINA);
        parametros.put("parametrosConsulta", parametrosConcatenar);
        ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(buscadorRutinasCommand, client);
        consultaServerGenerica.execute(parametros, null, null);

    } catch (Exception e) {
        Log.LOGGER.severe(e.toString());

    }

    }

    public void getActividad(String idRutina, GetActividadCommand getActividadCommand) {
        try{

            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("id","35"));//ATENCION HARDCODE!!!!!
            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_GET_ACTIVIDAD);
            parametros.put("parametrosConsulta", parametrosConcatenar);
            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(getActividadCommand, client);
            consultaServerGenerica.execute(parametros, null, null);

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());

        }


    }
    //va con fecha actual y posicion actual
    public void pedirPrediccionRutina(/*PedirPrediccionRutinaCommand*/RutinasCommand pedirRutinasCommad/*, ArchivoCache archivoCache*/){
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            parametrosPost.add(new BasicNameValuePair("nombreUsuario",pedirRutinasCommad.getNombreUsuario()));
            parametrosPost.add(new BasicNameValuePair("fechaActual", String.valueOf(pedirRutinasCommad.getFechaConsulta().getTime().getTime())));
            parametrosPost.add(new BasicNameValuePair("puntoActualLatitud",String.valueOf(pedirRutinasCommad.getPuntoActual().latitude)));
            parametrosPost.add(new BasicNameValuePair("puntoActualLongitud",String.valueOf(pedirRutinasCommad.getPuntoActual().longitude)));
            parametros.put("parametrosConsulta", parametrosPost);
            parametros.put("url", URL_PREDICCION_RUTINA);

            ConsultaServidorTomcat consultaServerRutina = new ConsultaServidorTomcat(pedirRutinasCommad,client/*,archivoCache*/);
            consultaServerRutina.execute(parametros, null, null);



        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    //va con fecha calendar seleccionada
    public void pedirRutinasPorDia(PedirRutinasCommad pedirRutinasCommad){
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            parametrosPost.add(new BasicNameValuePair("nombreUsuario",pedirRutinasCommad.getNombreUsuario()));
            parametrosPost.add(new BasicNameValuePair("fechaActual",String.valueOf(pedirRutinasCommad.getFechaConsulta().getTime().getTime())));
            parametros.put("parametrosConsulta", parametrosPost);
            parametros.put("url", URL_RUTINAS_POR_DIA);

            ConsultaServidorTomcat consultaServerRutina = new ConsultaServidorTomcat(pedirRutinasCommad,client/*,archivoCache*/);
            consultaServerRutina.execute(parametros, null, null);



        }   catch (Exception e){Log.LOGGER.severe(e.toString());}

    }

    public void pedirCalculoRuta(PedirRutaCommand pedirRutaCommand){
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            parametrosPost.add(new BasicNameValuePair("latitudOrigen",String.valueOf(pedirRutaCommand.getOrigen().getLatitude())));
            parametrosPost.add(new BasicNameValuePair("longitudOrigen",String.valueOf(pedirRutaCommand.getOrigen().getLongitude())));
            parametrosPost.add(new BasicNameValuePair("latitudDestino",String.valueOf(pedirRutaCommand.getDestino().getLatitude())));
            parametrosPost.add(new BasicNameValuePair("longitudDestino",String.valueOf(pedirRutaCommand.getDestino().getLongitude())));
            parametros.put("parametrosConsulta", parametrosPost);
            parametros.put("url", URL_CALCULAR_RUTA);

            ConsultaServidorTomcat consultaServerRutina = new ConsultaServidorTomcat(pedirRutaCommand,client);
            consultaServerRutina.execute(parametros, null, null);



        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
       /* try {
          HashMap<String, Object> parametros = new HashMap<>();

           String latitudOrigen = Double.toString(pedirRutaCommand.getOrigen().getLatitude());
            String longitudOrigen = Double.toString(pedirRutaCommand.getOrigen().getLongitude());
            String latitudDestino = Double.toString(pedirRutaCommand.getDestino().getLatitude());
            String longitudDestino = Double.toString(pedirRutaCommand.getDestino().getLongitude());

            String urlCompleta = URL_CALCULAR_RUTA +latitudOrigen+","+longitudOrigen+"&destination="+latitudDestino+","+longitudDestino;
            parametros.put("url", urlCompleta);

            ObtenerRutaGoogle camino = new ObtenerRutaGoogle(pedirRutaCommand);
            camino.execute(parametros, null, null);


        }
        catch(Exception e){
            Log.LOGGER.severe(e.toString());
        }*/
    }


    public void eliminarRutina(EliminarRutinaCommand eliminarRutinaCommand ){
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosConcatenar = new ArrayList<>();
            parametrosConcatenar.add(new BasicNameValuePair("idRutina", String.valueOf((eliminarRutinaCommand.getRutina()).getIdRutina())));
            parametros.put("parametrosConsulta", parametrosConcatenar);
            parametros.put("url", URL_ELIMINAR_RUTINA);

            ConsultaServerGenerica consultaServerGenerica = new ConsultaServerGenerica(eliminarRutinaCommand);
            consultaServerGenerica.execute(parametros, null,null);



        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

    public void lisrArchivos(String s, ListarArchivosCommand listarArchivosCommand) {

    }
    public void pedirPuntosEstadia(PuntosEstadiaCommand puntosEstadiaCommand,ArchivoCache archivoCache) {
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            parametros.put("parametrosConsulta", parametrosPost);
            parametros.put("url", URL_PEDIDO_PUNTOS_ESTADIA);

            ConsultaPuntosEstadiaTomcat consultaPuntosEstadia = new ConsultaPuntosEstadiaTomcat(puntosEstadiaCommand,client,archivoCache);
            consultaPuntosEstadia.execute(parametros, null, null);

       //     ConsultaServidorTomcat consultaServerRutina = new ConsultaServidorTomcat(puntosEstadiaCommand,client);
        //    consultaServerRutina.execute(parametros, null, null);


        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }

/*
    public void predecirRutina(String s, PredecirRutinaCommand predecirRutinaCommand) {


        try {

            HashMap<String, Object> parametros = new HashMap<>();
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            Adaptador adaptador=new Adaptador();

            Gson gson=new Gson();
            parametrosPost.add(new BasicNameValuePair("event", gson.toJson(predecirRutinaCommand.getUltimasUbicaciones())));

            String fecha =adaptador.formatoFecha(Calendar.getInstance().getTime(),"d");
            parametrosPost.add(new BasicNameValuePair("fecha",fecha));

            parametros.put("parametrosConsulta", parametrosPost);
            parametros.put("url", URL_GET_PREDICCION);

            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(predecirRutinaCommand, client);
            consultaServerGenerica.execute(parametros, null, null);



        }   catch (Exception e){Log.LOGGER.severe(e.toString());}
    }
*/
    public void enviarUsuarioRegistrado(Usuario usuario, EnviarUsuarioRegistradoCommand enviarUsuarioRegistradoCommand) {
        try {

            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("user", usuario.getNombreUsuario()));

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_USUARIO_REGISTRADO);
            parametros.put("parametrosConsulta", parametrosConcatenar);



            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(enviarUsuarioRegistradoCommand,client);
            consultaServerGenerica.execute(parametros, null, null);

        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());

        }
    }

    public void eliminarUsuarioServer(EliminarUsuarioCommand eliminarUsuarioCommand) {
        ArrayList<NameValuePair> parametrosConcatenar;
        parametrosConcatenar = new ArrayList<NameValuePair>();
    //    parametrosConcatenar.add(new BasicNameValuePair("user", usuario.getNombreUsuario()));

        HashMap<String, Object> parametros;
        parametros = new HashMap<String, Object>();
        parametros.put("url", URL_USUARIO_ELIMINAR_REGISTRO);
        parametros.put("parametrosConsulta", parametrosConcatenar);



        ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(eliminarUsuarioCommand,client);
        consultaServerGenerica.execute(parametros, null, null);

    }

    public void enviarPuntoEstadia(AgregarPuntoEstadiaCommand agregarPuntoEstadiaCommand) {
        try {
            PuntoEstadia punto = agregarPuntoEstadiaCommand.getPuntoEstadia();
            ArrayList<NameValuePair> parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("nombre", punto.getNombre()));
            parametrosConcatenar.add(new BasicNameValuePair("fechaInicio", punto.getFechaInicio()));
            parametrosConcatenar.add(new BasicNameValuePair("fechaFin",  punto.getFechaFin()));
            parametrosConcatenar.add(new BasicNameValuePair("latitud", String.valueOf(punto.getPunto().latitude)));
            parametrosConcatenar.add(new BasicNameValuePair("longitud", String.valueOf(punto.getPunto().longitude)));

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", URL_ENVIAR_PUNTO_ESTADIA);
            parametros.put("parametrosConsulta", parametrosConcatenar);
            //TOMCAT
            ConsultaServidorTomcat consultaServerGenerica = new ConsultaServidorTomcat(agregarPuntoEstadiaCommand,client);
            consultaServerGenerica.execute(parametros, null, null);


        }catch (Exception e){ Log.LOGGER.severe(e.toString());}
    }
}



