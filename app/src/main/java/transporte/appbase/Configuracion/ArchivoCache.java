package transporte.appbase.Configuracion;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import transporte.appbase.Archivos.Log;
import transporte.appbase.DBMananger.BdHelperRutinas;


public class ArchivoCache {

    private static final String FILE_CACHE = "Cache_Puntos_Estadia.txt";
    public final static String TARJETA_MEMORIA=android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    public final static String RAIZ_APP=TARJETA_MEMORIA+"/AppBase";
    public final static String CACHE_PUNTOS_ESTADIA=RAIZ_APP+"/"+FILE_CACHE;


    private static final long HORAS = 1800000; //1hr = 3600000miliseg, puse media hr
    protected Context c;
    private StringBuffer cache = new StringBuffer(); //ojoooo

    public void setContext(Context c){
        this.c = c;
    }

    public void escribirCache(String content) throws IOException {

        File file;
        FileOutputStream outputStream;

    //    file = new File(c.getCacheDir(), FILE_CACHE);
        file = new File(RAIZ_APP,FILE_CACHE);

        try{
        outputStream = new FileOutputStream(file);
        outputStream.write(content.getBytes());
        outputStream.close();


         /*    else{
                StringBuffer sB = leerCache();

                StringBuffer a = cache;
                a.deleteCharAt(cache.length()-1);
                a = a.append( ",");
                a = a.append(content);

                char aaa = a.charAt(a.length() - 1);
                char bbb = cache.charAt(cache.length()-1);

                cache = a;
                file = new File(c.getCacheDir(), FILE_CACHE);

                outputStream = new FileOutputStream(file);
                cache.append(content.toString());
                outputStream.write(cache.toString().getBytes());
                outputStream.write(content.getBytes());
                outputStream.close();
               }
*/

        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
    }

    public StringBuffer leerCache(){
        BufferedReader input;
        File file;
        try {

        //    file = new File(c.getCacheDir(),FILE_CACHE);
            file = new File(RAIZ_APP,FILE_CACHE);
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line;
            cache = new StringBuffer();
            while ((line = input.readLine()) != null) {
                cache.append(line);
            }

            return cache;


        } catch (Exception e) {
            Log.LOGGER.severe(e.toString());
        }
        return null;
    }

    public int getCacheLength(){
       return  cache.length();
    }

    public void testCacheado() {
      cache = new StringBuffer();
        try {
            File cacheFile = new File(/*c.getCacheDir()*/RAIZ_APP, FILE_CACHE);
            if ((System.currentTimeMillis() - cacheFile.lastModified()) < HORAS) {

                BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(cacheFile)));
                String line;
                while ((line = input.readLine()) != null) {
                    cache.append(line);
                }

                input.close();


            } else
                cacheFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrarCache(){
        File cacheFile = new File(/*c.getCacheDir()*/RAIZ_APP, FILE_CACHE);
        cacheFile.delete();
    }
}
