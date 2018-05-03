package transporte.appbase.DBMananger;

/**
 * Created by soled_000 on 7/6/2016.
 */
public class DataBaseManagerRutinas {

    public static final String TABLE_NAME="rutinasPorDia";

    public static  final String CN_ID="_id";
    public static  final String CN_NAME="nombre";
    public static  final String CN_FECHA_INICIO="fechaInicio";
    public static  final String CN_FECHA_FIN="fechaFin";
    public static  final String CN_ORIGEN ="origen";
    public static  final String CN_DESTINO ="destino";
    public static  final String CN_PUNTO_ORIGEN_LAT ="puntoOrigenLatitud";
    public static  final String CN_PUNTO_ORIGEN_LONG ="puntoOrigenLongitud";
    public static  final String CN_PUNTO_DESTINO_LAT ="puntoDestinoLatitud";
    public static  final String CN_PUNTO_DESTINO_LONG ="puntoDestinoLongitud";
    public static  final String CN_TRAZAS = "trazas";


    public static final String CREATE_TABLE="create table "+TABLE_NAME+" ("
            +CN_ID+" integer primary key autoincrement, "
            +CN_NAME+" text not null,"
            +CN_FECHA_INICIO+" text,"
            +CN_FECHA_FIN+" text,"
            +CN_ORIGEN +" text,"
            +CN_DESTINO +" text,"
            +CN_PUNTO_ORIGEN_LAT+" real,"
            +CN_PUNTO_ORIGEN_LONG+" real,"
            +CN_PUNTO_DESTINO_LAT+" real,"
            +CN_PUNTO_DESTINO_LONG+" real,"
            +CN_TRAZAS +" text );";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}