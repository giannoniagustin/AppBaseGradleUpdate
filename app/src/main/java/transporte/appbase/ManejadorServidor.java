package transporte.appbase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import java.util.*;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Servidor.Server;
import transporte.appbase.Servidor.ServidorCommand;


public class ManejadorServidor{

    private java.util.Queue queueB;
    private Context context;
    private BroadcastReceiver conexionInternet;
    private Server server;

    public ManejadorServidor(){
        queueB = new ArrayDeque();// agregar un COMPARABLE para el tema de la PRIORIDAD PriorityQueue
        server = new Server();
    }

    public void setContexto(Context c){
        this.context = c;
    //    inicializarBroadcast(); //REVISAR ACAAAAAAAAAAAAAAAAAAAAA PARA ACTIVAR LA EJECUCION DE LOS COMMANDS CUANDO SE RECONOCE INTERNET
    }

    //Cuando se activa INTERNET comienza la ejecucion de los commands
    private void inicializarBroadcast() {
        //frecuencia de ejecucion solo cuando hay conexion a internet
        conexionInternet = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean conectado = intent.getBooleanExtra("conexion",false);
                while ((conectado) &&(queueB.iterator().hasNext())){
                    ejecutarTarea();
                }
            }

        };
        context.registerReceiver(conexionInternet, new IntentFilter("receive_conection"));
    }

    public void agregarElemento(ServidorCommand elem){
        try {


            queueB.add(elem);
            ejecutarTarea();
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }
    }

    public Object sacarElemento(){
        return (queueB.poll()); //retorna null si la cola esta vacia
    }


    public void ejecutarTarea(){    //VER FRECUENCIA despierta a ejecutar cuando llega un elemento nuevo...
        try {
            ServidorCommand c = (ServidorCommand) sacarElemento();
            c.setServer(server);
            c.Ejecutar();
        }catch (Exception e){
            Log.LOGGER.severe(e.toString());
        }



    }
    public int cantidadElementos(){
        return queueB.size();
    }

    public Server getServer(){
        return server;
    }


    
}
