package transporte.appbase;


import java.util.ArrayList;

import transporte.appbase.Ruta.Corte;
import transporte.appbase.Rutinas.RutinaG;

public interface AsyncRespuesta {

     void finObtencionPECache(ArrayList<Corte> puntos);

}
