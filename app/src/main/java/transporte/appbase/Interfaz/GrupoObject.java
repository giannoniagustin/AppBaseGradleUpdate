package transporte.appbase.Interfaz;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Agustn on 22/07/2015.
 */
public class GrupoObject extends ElmentoGrid {
    private ArrayList<GridObjeto> myObjects;
    private int anteriorSeleccionado;
    private int minimoSeleccionado;

    public GrupoObject( String[] textItem,int[] imagenItem, int[] imagenItemSeleccionado) {
        myObjects = new ArrayList<>();
        minimoSeleccionado=1;
        anteriorSeleccionado=0;
        for (int i=0;i < textItem.length;i++) {//Crea los itmes
            myObjects.add(new GridObjeto(textItem[i],imagenItem[i],imagenItemSeleccionado[i],false,i));
        }
    }

    @Override
    public Vector<GridObjeto> getSelecionados(Vector<GridObjeto> grupo) {
        grupo=new Vector<>();

        for (GridObjeto o:myObjects) {
            o.getSelecionados(grupo);
            
        }
        return grupo;
        
    }

    @Override
    public void seleccionarElemento(int item) {
        myObjects.get(anteriorSeleccionado).setSeleccionado(false);//Deselecciono el item
        myObjects.get(item).setSeleccionado(true);//Selecciono el item
        anteriorSeleccionado=item;//Actualizo el ultimo seleccionado
    }

    @Override
    public void deseleccionarElemento(int item) {//Deselecciona todos los elementos menos el que se la pasa por parametro


    }

  

    @Override
    public int cantidadSeleccionados() {

        int cant=0;
        for (GridObjeto o:myObjects) {
            cant+=o.cantidadSeleccionados();

        }

        return cant;
    }
    public int size(){ return  myObjects.size();}
    public GridObjeto getObjeto(int i){ return  myObjects.get(i);}

    public boolean cumpleSeleccion(){


        return  (this.cantidadSeleccionados() >= minimoSeleccionado);
    }
}
