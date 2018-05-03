package transporte.appbase.Interfaz;

import transporte.appbase.Ruta.Corte;

/**
 * Created by Agustín on 07/10/2015.
 */
public class DibujableCorte implements Dibujable {
    private Corte corte;

    public DibujableCorte(Corte corte) {
        this.corte = corte;
    }

    @Override
    public void Accept(Dibujador dibujador) {

        dibujador.DibujarCorte(this);

    }

    public Corte getCorte() {
        return corte;
    }
}
