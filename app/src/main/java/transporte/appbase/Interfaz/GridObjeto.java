package transporte.appbase.Interfaz;

import java.util.Vector;

/**
 * Created by Agustn on 22/07/2015.
 */
public class GridObjeto extends  ElmentoGrid {
    private String nombre;
    private int imagen;
    private int imagen_seleccionada;
    private boolean seleccionado;
    private int nroItem;

    public int getNroItem() {
        return nroItem;
    }

    public void setNroItem(int nroItem) {
        this.nroItem = nroItem;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getImagen_seleccionada() {
        return imagen_seleccionada;
    }

    public void setImagen_seleccionada(int imagen_seleccionada) {
        this.imagen_seleccionada = imagen_seleccionada;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public GridObjeto(String s, int i, int i1, boolean b,int pNroItem) {

        nombre=s;
        imagen=i;
        imagen_seleccionada=i1;
        seleccionado=b;
        nroItem=pNroItem;

    }


    @Override
    public Vector<GridObjeto> getSelecionados(Vector<GridObjeto> grupoObjetos) {

        if  (this.seleccionado){

            grupoObjetos.add(this);
        }
        return grupoObjetos;
    }

    @Override
    public void seleccionarElemento(int item) {




    }

    @Override
    public void deseleccionarElemento(int item) {

    }

    @Override
    public int cantidadSeleccionados() {
        if(this.seleccionado){
            return 1;
        }

        return 0;
    }
}
