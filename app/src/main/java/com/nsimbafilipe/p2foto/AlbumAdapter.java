package com.nsimbafilipe.p2foto;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>{

    private List<Album> listaContato;
    public AlbumAdapter  (List<Album> lista){
        listaContato=lista;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celula_lista_album,viewGroup,false);
        return new AlbumViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder albumViewHolder, int i) {
        Album c = listaContato.get(i);
        albumViewHolder.nome.setText(c.getNome());
        albumViewHolder.data.setText(c.getData());
    }

    @Override
    public int getItemCount() {

        return listaContato.size();
    }


    static class AlbumViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        TextView data;

        public AlbumViewHolder(View v) {
            super(v);
           nome = v.findViewById(R.id.nome_list_album);
           data= v.findViewById(R.id.data_list_album);

        }
    }
}
