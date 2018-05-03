package transporte.appbase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import transporte.appbase.Parser.Parser;
import transporte.appbase.Rutinas.AdapterListener;
//import transporte.appbase.Rutinas.Rutina;
import transporte.appbase.Rutinas.RutinaG;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<RutinaG> mDataset;
    private AdapterListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nombreRutina;
        public TextView direccion;
        public TextView horaInicioRutina;
        public TextView horaFinRutina;
        private ImageButton editarRutina;
        private ImageButton verRutinaMapa;


        public ViewHolder(View v) {
            super(v);
            nombreRutina = (TextView) v.findViewById(R.id.nombre_rutina);
            direccion = (TextView) v.findViewById(R.id.direccion_rutina);
            horaInicioRutina = (TextView) v.findViewById(R.id.hora_inicio_rutina);
            horaFinRutina = (TextView) v.findViewById(R.id.hora_fin_rutina);
            editarRutina = (ImageButton) v.findViewById(R.id.boton_editar_rutina);
            verRutinaMapa = (ImageButton) v.findViewById(R.id.boton_vista_mapa_rutina);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<RutinaG> myDataset,AdapterListener adapterListener) {
        this.mDataset = myDataset;
        this.listener = adapterListener;
     }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false); //layout de cardUI
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //    rutinaSeleccionada = mDataset.get(position);

        holder.nombreRutina.setText(mDataset.get(position).getNombre());
     //   holder.nombreRutina.setText("Trazas: "+ mDataset.get(position).getTrazas().size());//para ver la cantidad de trazas
        holder.direccion.setText(mDataset.get(position).getpOrigen()+" - "+mDataset.get(position).getpDestino());

        int hora = mDataset.get(position).getFechaInicio().getTime().getHours();
        int minutos = mDataset.get(position).getFechaInicio().getTime().getMinutes();
        holder.horaInicioRutina.setText(String.valueOf(hora)+":"+String.valueOf(minutos));

        int horafin = mDataset.get(position).getFechaFin().getTime().getHours();
        int minutofins = mDataset.get(position).getFechaFin().getTime().getMinutes();
        holder.horaFinRutina.setText(String.valueOf(horafin)+":"+String.valueOf(minutofins));

        final int posicionSeleccionada = holder.getAdapterPosition();
        holder.editarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editar rutina especifica
                if (listener != null){
                    listener.onItemClickEditarRutina(posicionSeleccionada);
                }
            }
        });

        holder.verRutinaMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ver rutina especifica en mapa
                if (listener != null){
                    listener.onItemClickPosicionRutina(posicionSeleccionada);
                }

            }
        });

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
