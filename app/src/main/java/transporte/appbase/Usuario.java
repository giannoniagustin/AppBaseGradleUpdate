package transporte.appbase;



public class Usuario {

    private String nombreUsuario;
    private String eMail;
    private String contrasenia1; //ENCRIPTAR CONTRASENIAS EN BASE DE DATOS!!
//    private String contrasenia2;
//    private String usuarioRedSocial;


    public Usuario(String eMail, String nombreUsuario, String contrasenia1) {
        this.nombreUsuario = nombreUsuario;
        this.eMail = eMail;
        this.contrasenia1 = contrasenia1;
    }

    public Usuario(String nombreUsuario, String contrasenia1) {

        this.nombreUsuario = nombreUsuario;
        this.contrasenia1 = contrasenia1;
        this.eMail = "";
    }

    public Usuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia1 = "";
        this.eMail = "";

    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setContrasenia1(String contrasenia1) {
        this.contrasenia1 = contrasenia1;
    }


    public String getNombreUsuario() {

        return nombreUsuario;
    }

    public String geteMail() {
        return eMail;
    }

    public String getContrasenia1() {
        return contrasenia1;
    }

 /*   public void envioDatosServidor(Context context, String url) { //hacer command para estpo tmb
        try {

            ArrayList<NameValuePair> parametrosConcatenar;
            parametrosConcatenar = new ArrayList<NameValuePair>();
            parametrosConcatenar.add(new BasicNameValuePair("usuario", getNombreUsuario()));
            parametrosConcatenar.add(new BasicNameValuePair("password", getContrasenia1()));
            parametrosConcatenar.add(new BasicNameValuePair("mail", geteMail()));

            HashMap<String, Object> parametros;
            parametros = new HashMap<String, Object>();
            parametros.put("url", url);
            parametros.put("parametrosConsulta", parametrosConcatenar);
//            ConsultaServidorGenerica controlLoginRegistro = new LoginRegistroUsuarioServidor(context);
 //           controlLoginRegistro.setValor((AsyncRespuesta) context);
//            controlLoginRegistro.execute(parametros, null, null);
        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());

        }

    }
    */

}
