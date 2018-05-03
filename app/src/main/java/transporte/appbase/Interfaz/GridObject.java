package transporte.appbase.Interfaz;


public class GridObject {

    private String nombre;
    private int imagen;
    private int imagen_seleccionada;
    private boolean seleccionado;

    public GridObject(String nombre, int imagen,int imagen_seleccionada, boolean seleccionado) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.seleccionado = seleccionado;
        this.imagen_seleccionada=imagen_seleccionada;
    }

    public int getImagen_seleccionada() {
        return imagen_seleccionada;
    }

    public void setImagen_seleccionada(int imagen_seleccionada) {
        this.imagen_seleccionada = imagen_seleccionada;
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

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
