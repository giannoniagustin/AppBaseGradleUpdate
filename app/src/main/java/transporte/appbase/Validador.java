package transporte.appbase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by soled_000 on 26/06/2015.
 */
public class Validador {

    private static final String EMAIL_ADDRESS = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final int LONGITUD_SUMO = 8;
    private Pattern pattern;
    private Matcher matcher;

    public boolean chequeoEmail(String email) {

        pattern = Pattern.compile(EMAIL_ADDRESS);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean chequeoContrasenia(String contrasenia) {
        return contrasenia != null && contrasenia.length() > 6;
    }

    public boolean chequeoContraseniasIguales(String contr1, String contr2) {
        return contr1.equals(contr2);
    }

    public boolean chequeoCodigoSUMO(String codigo){
        if (codigo.length() == LONGITUD_SUMO) {
            return true;
        }
        return false;
    }

    public boolean chequeoCampoVacio(String campo){
        if (campo.length()==0)
            return true;
        return false;
    }

}
