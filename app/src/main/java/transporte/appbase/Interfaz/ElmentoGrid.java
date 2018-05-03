package transporte.appbase.Interfaz;

import java.util.Vector;

/**
 * Created by Agustin on 21/07/2015.
 */
public abstract class ElmentoGrid {
    protected String nombre;


    public abstract Vector<GridObjeto> getSelecionados(Vector<GridObjeto> grupoObjetos);
    public abstract void seleccionarElemento(int item);
    public abstract void deseleccionarElemento(int item);

    public abstract int cantidadSeleccionados();
}
