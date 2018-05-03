package transporte.appbase.DBMananger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by soled_000 on 7/6/2016.
 */
public class BdHelperRutinas extends SQLiteOpenHelper {

    public static final String DB_NAME = "rutinasPorDia.sql";
    private static final int DB_SCHEME_VERSION = 1;
    private SQLiteDatabase bd;

    public BdHelperRutinas(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    public BdHelperRutinas(Context context,String bd_name) {
        super(context, bd_name, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DataBaseManagerRutinas.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertTabla(String tabla, ContentValues registro)
    {
        bd=this.getWritableDatabase();
        bd.insert(tabla,null, registro);

    }
    public Cursor consultaTabla(String consulta){
        openBd();
        Cursor fila = bd.rawQuery(consulta, null);
        return fila;
    }

    public void updateTabla(String tabla,ContentValues registro ,String where){
        openBd();
        bd.update(tabla, registro, where, null);
        bd.close();
    }

    public void openBd(){
        bd=this.getWritableDatabase();}

    public void closeBd(){ bd.close();}


    public int getIntColumna(Cursor resultado,String Columna){

        return resultado.getInt(resultado.getColumnIndex(Columna));
    }
    public String getStringColumna(Cursor resultado,String Columna){

        return resultado.getString(resultado.getColumnIndex(Columna));
    }


    public Long getLongColumna(Cursor resultado, String columna) {
        return resultado.getLong(resultado.getColumnIndex(columna));
    }

    public long cantidadFilas(){
        bd = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(bd, DB_NAME);
        bd.close();
        return cnt;
    }
    public void eliminarTabla(){
        openBd();
       bd.delete(DataBaseManagerRutinas.TABLE_NAME,null,null);
    }
}
