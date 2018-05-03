package transporte.appbase.Interfaz;

import java.util.ArrayList;
import transporte.appbase.Ruta.ConjuntoTrazas;


/**
 * Created by soled_000 on 30/5/2016.
 */
public class DibujableConjuntoTraza implements Dibujable {

    private ArrayList<DibujableTraza> conjuntoTrazas;

    public DibujableConjuntoTraza(ConjuntoTrazas trazas) {
        conjuntoTrazas = new ArrayList<>();
        for (int i = 0; i<trazas.size();i++){
            conjuntoTrazas.add(new DibujableTraza(trazas.get(i)));
        }
    }
    @Override
    public void Accept(Dibujador dibujador) {
        dibujador.dibujarConjuntoTrazas(this);
    }

    public ArrayList<DibujableTraza> getConjuntoTrazas(){
        return conjuntoTrazas;
    }
}
