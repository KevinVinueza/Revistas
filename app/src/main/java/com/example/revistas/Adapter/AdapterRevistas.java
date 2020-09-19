package com.example.revistas.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.revistas.Model.revistas;
import com.example.revistas.R;

import java.util.ArrayList;
import java.util.List;

public  class AdapterRevistas extends RecyclerView.Adapter<AdapterRevistas.ViewHolderRevista> implements View.OnClickListener{

        private ArrayList<revistas> listaRevistas;
        Context context;
        private int posicio = RecyclerView.NO_POSITION;
        private View.OnClickListener listener;
        TextView titulo, descripcion;
        ImageView portad;

        public AdapterRevistas(ArrayList<revistas> listaRevist, Context context) {
                this.listaRevistas = listaRevist;
                this.context = context;
        }

        @NonNull
        @Override
        public AdapterRevistas.ViewHolderRevista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_revista,null,false);
                view.setOnClickListener(this);
                return new ViewHolderRevista(view);
        }
        public void setOnClickListener(View.OnClickListener listener){
                this.listener=listener;
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterRevistas.ViewHolderRevista holder, int position) {
                try {


                        titulo.setText(listaRevistas.get(position).getTitulo());
                        descripcion.setText(Html.fromHtml(listaRevistas.get(position).getDescripcion()));
                        Glide.with(context)
                                .load(listaRevistas.get(position).getPortada())
                                .into(portad);
                        holder.itemView.setSelected(posicio == position);

                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        @Override
        public int getItemCount() {
                return listaRevistas.size();
        }

        @Override
        public void onClick(View view) {
                if(listener!=null)
                {
                        listener.onClick(view);
                }
        }

        public class ViewHolderRevista extends RecyclerView.ViewHolder {

                public ViewHolderRevista(@NonNull View itemView) {
                        super(itemView);

                        titulo = (TextView)itemView.findViewById(R.id.titulo);
                        descripcion = (TextView)itemView.findViewById(R.id.descrip);
                        portad = (ImageView)itemView.findViewById(R.id.img);
                }
        }
}
