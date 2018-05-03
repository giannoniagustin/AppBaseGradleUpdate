package transporte.appbase.Interfaz;

import transporte.appbase.Rutinas.RutinaG;

/**
 * Created by soled_000 on 04/11/2015.
 */
public class DibujableRutina implements Dibujable {

    private RutinaG rutina;
    private MarcadorRutina marcadorRutinaOrigen;
    private MarcadorRutina marcadorRutinaDestino;

    public DibujableRutina(/*MarcadorRutina mRutina*/RutinaG rutina) {
        this.rutina = rutina;
        this.marcadorRutinaOrigen = new MarcadorRutina(rutina.getOrigenRutina(),rutina.getpOrigen(),"IC_RUTA",rutina.getFechaInicio().getTime().toString());
        this.marcadorRutinaDestino = new MarcadorRutina(rutina.getDestinoRutina(),rutina.getpDestino(),"IC_RUTA",rutina.getFechaFin().getTime().toString()); //seteada hora de fin de la rutina, pero no es la hora que llega al destino

    }
    @Override
    public void Accept(Dibujador dibujador) {
        dibujador.dibujarRutina(this);
    }
    public MarcadorRutina getMarcadorRutinaOrigen() {
        return marcadorRutinaOrigen;
    }
    public MarcadorRutina getMarcadorRutinaDestino() {
        return marcadorRutinaDestino;
    }
    public RutinaG getRutina(){
        return rutina;
    }
}
