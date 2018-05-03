package transporte.appbase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import transporte.appbase.Archivos.Log;
import transporte.appbase.Interfaz.GridObjeto;
import transporte.appbase.Interfaz.GrupoObject;

/**
 * Created by Agust�n on 17/07/2015.
 */

public class PersonalizableGridAdapter extends BaseAdapter {
    private Context context;
    private final GrupoObject myObjects;
    private int seleccionado;

    //Constructor to initialize values
    public PersonalizableGridAdapter(Context context, GrupoObject myObjects) {

        this.context = context;

              this.myObjects = myObjects;
              this.seleccionado = -1;


        }



    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return myObjects.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(int position, View convertView, ViewGroup parent) {
        try {

            // LayoutInflator to call external grid_item.xml file

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(context);

                // get layout from grid_item.xml ( Defined Below )

                gridView = inflater.inflate(R.layout.grid_item, null);

                // set value into textview

                TextView textView = (TextView) gridView
                        .findViewById(R.id.grid_item_label);

                textView.setText(myObjects.getObjeto(position).getNombre()); // textView.setText(gridValues[position]);//TEXT VIEW NOMBRE DE ICONO

                // set image based on selected text

                ImageView imageView = (ImageView) gridView
                        .findViewById(R.id.grid_item_image);
                imageView.setImageResource(myObjects.getObjeto(position).getImagen());


            } else {


                gridView = convertView;
                ImageView imageView = (ImageView) gridView
                        .findViewById(R.id.grid_item_image);
                if (!myObjects.getObjeto(position).isSeleccionado()) {
                    imageView.setImageResource(myObjects.getObjeto(position).getImagen());
                } else {
                    imageView.setImageResource(myObjects.getObjeto(position).getImagen_seleccionada());

                }


            }


            return gridView;
        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());
            return null;
        }
    }

    public void click(int position){
        try {
//Selecciona el objeto de la grid y notificar a si mismo para que actualice la pantalla
            seleccionado = position;
            myObjects.seleccionarElemento(position);
            this.notifyDataSetChanged();
        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());

        }

    }
    public boolean cumpleSeleccion(){
 try {
     return myObjects.cumpleSeleccion();
 }catch  (Exception e) {
     Log.LOGGER.severe(e.toString());
     return  false;

 }

    }

    public GridObjeto getSeleccionado() {
        try {


            return myObjects.getObjeto(seleccionado);
        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());
            return null;
        }
    }



    public String getNombreIconoSeleccionado(){
        try {
            return myObjects.getObjeto(seleccionado).getNombre();
        }catch  (Exception e) {
            Log.LOGGER.severe(e.toString());
            return null;

        }
    }



}