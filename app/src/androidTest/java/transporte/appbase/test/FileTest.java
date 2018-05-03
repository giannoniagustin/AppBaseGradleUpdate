package transporte.appbase.test;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.SmallTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import transporte.appbase.Archivos.ControladorArchivos;
import transporte.appbase.DBMananger.BdHelper;
import transporte.appbase.DBMananger.DataBaseMananger;
import transporte.appbase.MapsActivity;
import transporte.appbase.Util.WifiChangeReceiver;
import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class FileTest {

    private ControladorArchivos controladorArchivos;
    private MapsActivity mapsActivity;
    private Context contexto;
    private DataBaseMananger dataBaseMananger;
    private BdHelper bdHelper;
    private WifiChangeReceiver wifiChangeReceiver;
    private String nombreArchivo;


    @Rule
    public final ActivityTestRule<MapsActivity> activityTestRule = new ActivityTestRule<>(MapsActivity.class);

    @Test//no necesario en este test
    public void useActivityInTest() {

        Activity activity = activityTestRule.getActivity();

    }
    @Before
    public void setUp() throws Exception {

      /*  contexto = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");
        controladorArchivos = new ControladorArchivos(contexto);
        dataBaseMananger = new DataBaseMananger();
        wifiChangeReceiver = new WifiChangeReceiver();
        nombreArchivo = "TrackUbicacionEnvio.zip";*/
    }

    @Test
    public void checkPreconditions() {
       // assertThat(mapsActivity, notNullValue());
        // Check that Instrumentation was correctly injected in setUp()
      //  assertThat(getInstrumentation(), notNullValue());
    }

    @Test //test wifi conexion
    public void conexionInternet() {
        boolean esperado = true;
        boolean respuesta = wifiChangeReceiver.getWifiConectado(contexto);
        assertEquals(esperado, respuesta);
    }

    @Test //Envio archivo
    public void envioArchivoServidor() {
        String nombreArchivo = "TrackUbicacionEnvio.zip";
        String ruta = "storage/emulated/0";
     //   controladorArchivos.enviarArchivoServidor(nombreArchivo, ruta); //CAMBIAR

    }
    @Test //test archivo enviado
    public void chequeoEnvioArchivoServidor() {
        int esperado = 1;
        bdHelper = new BdHelper(contexto,"aiptu.sql");
        bdHelper.OpenBd();
        bdHelper.getReadableDatabase();

        Cursor resultado = bdHelper.ConsultaTabla("SELECT *  FROM " + DataBaseMananger.TABLE_NAME +
                " WHERE " + DataBaseMananger.CN_NAME + " ='" + nombreArchivo + "'");

        if (resultado.moveToNext()){
            int respuesta = resultado.getInt(resultado.getColumnIndex(DataBaseMananger.CN_ENVIADO));
            try{
                assertEquals(esperado, respuesta);
            }catch (Exception e){
                String m = "diferente assertEquals";}
            }

        bdHelper.close();
    }

/*  @After
    public void tearDown() throws Exception {
        super.tearDown();
   }
*/

}

