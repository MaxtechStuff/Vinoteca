package com.example.vinoteca;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinoteca.interfaces.OnRecyclerViewLongItemClickListener;

import java.util.ArrayList;


public class AdaptadorVinos extends RecyclerView.Adapter<AdaptadorVinos.ViewHolderVinos> {

    ArrayList<Vino> listaVinos;
    OnRecyclerViewLongItemClickListener itemLongClickListener;

    public AdaptadorVinos(ArrayList<Vino> listaVinos) {
        this.listaVinos = listaVinos;
    }

    @Override
    public ViewHolderVinos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vinos, null, false);
        return new ViewHolderVinos(view);
    }

    public void remove(Vino c){
        listaVinos.remove(c);
    }

    @Override
    public void onBindViewHolder(ViewHolderVinos holder, int position) {
        holder.nombre.setText(listaVinos.get(position).getNombre());
        holder.denominacion.setText("DO " + listaVinos.get(position).getDenominacionOrigen());
    }

    public void setOnItemLongClickListener(OnRecyclerViewLongItemClickListener listener) { //Long Click Listener
        this.itemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return listaVinos.size();
    }

    public class ViewHolderVinos extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView nombre, denominacion;

        public ViewHolderVinos(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtNombreVino);
            denominacion = (TextView) itemView.findViewById(R.id.txtDenominacionVino);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if (itemLongClickListener != null) {
                itemLongClickListener.onItemLongClick(v, getAdapterPosition());//con getAdapterPosition le pasamos la posición dentro del array de objetos
            }
            return true;
        }
    }


}
