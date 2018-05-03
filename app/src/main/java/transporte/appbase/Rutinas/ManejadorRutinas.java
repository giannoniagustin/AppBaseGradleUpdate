package transporte.appbase.Rutinas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import transporte.appbase.DBMananger.BdHelperRutinas;
import transporte.appbase.DBMananger.DataBaseManagerRutinas;
import transporte.appbase.ManejadorServidor;

/**
 * Created by soled_000 on 7/6/2016.
 */
public class ManejadorRutinas {

    private BdHelperRutinas bdHelperRutinas;
    private Context context;
    private ManejadorServidor manejadorServidor;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

    public final static String TARJETA_MEMORIA = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    public final static String RAIZ_APP = TARJETA_MEMORIA + "/AppBase";
    public final static String BD_Rutinas = RAIZ_APP + "/BD/" + BdHelperRutinas.DB_NAME;

    public final static int MINUTOS_CONSULTA = -2;

    public ManejadorRutinas() {
        bdHelperRutinas = new BdHelperRutinas(context, BD_Rutinas); //ver este new
        //manejadorServidor = Global.instance().getManejadorServidor();
    }

    public void setContext(Context c) {
        this.context = c;
    }

    public void solicitarRutinasPorDia() {
        //preparar command de pedido al servidor
        // manejadorServidor.agregarElemento(command);
        eliminarTodasRutinas();

    }

    public void almacenarRutinasBD(ContentValues registro) {
        bdHelperRutinas.insertTabla(BD_Rutinas, registro);
    }

    public void reemplazarRutina(RutinaG rutina, String id) {
        //hacer para usarlo cuando se pide una rutina

        ContentValues registro = prepararRegistroRutina(rutina);
        bdHelperRutinas.updateTabla(DataBaseManagerRutinas.TABLE_NAME, registro, DataBaseManagerRutinas.CN_ID + " ='" + id + "'");

    }

    public ContentValues prepararRegistroRutina(RutinaG rutina) {
        ContentValues registro = new ContentValues();
        registro.put("nombre", rutina.getNombre());
        registro.put("fechaInicio", rutina.getFechaInicio().getTime().toString());
        registro.put("fechaFin", rutina.getFechaFin().getTime().toString());
        registro.put("origen", rutina.getpOrigen());
        registro.put("destino", rutina.getpDestino());
        registro.put("puntoOrigenLatitud", rutina.getOrigenRutina().latitude);
        registro.put("puntoOrigenLongitud", rutina.getOrigenRutina().longitude);
        registro.put("puntoDestinoLatitud", rutina.getDestinoRutina().latitude);
        registro.put("puntoDestinoLongitud", rutina.getDestinoRutina().longitude);
        registro.put("trazas", rutina.getTrazas().conjuntoTrazasToString());
        return registro;
    }

    public void procesarRespuestaServidor(RutinaG rutina) { //suponiendo que le paso 1 rutina

        bdHelperRutinas = new BdHelperRutinas(context, BD_Rutinas);
        bdHelperRutinas.openBd();
        ContentValues registro = prepararRegistroRutina(rutina);
        bdHelperRutinas.insertTabla(DataBaseManagerRutinas.TABLE_NAME, registro);
        bdHelperRutinas.closeBd();
    }

    public Long getProximoHorarioRutina(/*int id*/Calendar horario) {
        String data;
  /*      Cursor resultado = bdHelperRutinas.consultaTabla("SELECT * FROM " + DataBaseManagerRutinas.TABLE_NAME +
                " WHERE " + DataBaseManagerRutinas.CN_ID + " ='" + id + "'"); //id seria nuemeo de fila!
        if (resultado.moveToNext()) {
            data = bdHelperRutinas.getStringColumna(resultado, DataBaseManagerRutinas.CN_FECHA_FIN); //VEEEEEEEEEEEEEEER aca va la fecha fin o inicio ver
*/

            try {
           //     Date d = dateFormat.parse(data);
           //     Calendar cal = Calendar.getInstance();
           //     cal.setTime(d);
           //     cal.add(Calendar.MINUTE, MINUTOS_CONSULTA); //resta los minutos establecidos
                int min = horario.getTime().getMinutes();
                horario.add(horario.MINUTE, MINUTOS_CONSULTA);
                System.out.println("minutos fin " + horario.getTime().getMinutes());
                System.out.println("minutos resta " + MINUTOS_CONSULTA);
            //    System.out.println("minutos alarma" + cal.getTime().toString());
                System.out.println("minutos alarma" + horario.getTime().toString());
            //    return (cal.getTime().getTime());
                return (horario.getTime().getTime());
            } catch (/*ParseException e*/Exception e) {
                e.printStackTrace();
            }

    //    }
        return null;
    }

    public int cantidadRutinasDia() {
        return Integer.parseInt(String.valueOf(bdHelperRutinas.cantidadFilas()));
    }

    public int getIdInicial() {
        int data = 0;
        Cursor resultado = bdHelperRutinas.consultaTabla("SELECT * FROM " + DataBaseManagerRutinas.TABLE_NAME +
                " ORDER BY ROWID ASC LIMIT 1");
        if (resultado.moveToNext()) {
            data = bdHelperRutinas.getIntColumna(resultado, DataBaseManagerRutinas.CN_ID);
        }
        return data;
    }

    public boolean existeId(int i) {
        Cursor resultado = bdHelperRutinas.consultaTabla("SELECT * FROM " + DataBaseManagerRutinas.TABLE_NAME +
                " WHERE " + DataBaseManagerRutinas.CN_ID + " ='" + i + "'");
        if (resultado.moveToNext()) {
            return true;
        }
        return false;
    }

    public void eliminarTodasRutinas() {
        bdHelperRutinas = new BdHelperRutinas(context, BD_Rutinas);
        bdHelperRutinas.openBd();
        bdHelperRutinas.eliminarTabla();
        bdHelperRutinas.closeBd();
    }

    public int getUltimoID() {
        int data = -1;
        //SELECT * FROM `corte` WHERE idCorte = (SELECT MAX(idCorte) as number FROM corte)
        Cursor id = bdHelperRutinas.consultaTabla("SELECT MAX(" + DataBaseManagerRutinas.CN_ID + ") as "+DataBaseManagerRutinas.CN_ID+" FROM " + DataBaseManagerRutinas.TABLE_NAME);
        if (id.moveToFirst()){
            data = bdHelperRutinas.getIntColumna(id, DataBaseManagerRutinas.CN_ID);
        }
    //    Cursor resultado = bdHelperRutinas.consultaTabla("SELECT * FROM " + DataBaseManagerRutinas.TABLE_NAME + " WHERE "+DataBaseManagerRutinas.CN_ID + "= "+ id.getColumnIndex(DataBaseManagerRutinas.CN_ID ));
    //    if (resultado.moveToNext()) {
    //        data = bdHelperRutinas.getIntColumna(resultado, DataBaseManagerRutinas.CN_ID);
    //    }
    return data;
    }

    public boolean esFechaMenor(Calendar calendar) {
        int a = calendar.getTime().getDay();
        int b = calendar.getTime().getHours();
        int c = calendar.getTime().getMinutes();
        Calendar ahora = Calendar.getInstance();
        int jj = ahora.getTime().getDay();
        int h = ahora.getTime().getHours();
        int j = ahora.getTime().getMinutes();
        boolean menor = calendar.before(ahora);//calendar es anterior a ahora
        return (menor);
    }
}

