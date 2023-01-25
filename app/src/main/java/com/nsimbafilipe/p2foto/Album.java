package com.nsimbafilipe.p2foto;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {

    private String nome;
    private String data;
    private int utilizador;
    private String id_associacao;
    private Long id_foto;
    private  Integer id;

    public Album(){

    }
    protected  Album(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);
        setNome(data[0]);
        setData(data[1]);
        setUtilizador(Integer.parseInt(data[2]));
        setId_associacao(data[3]);
        setId_foto(Long.parseLong(data[4]));
        setId(Integer.parseInt(data[5]));

    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(int utilizador) {
        this.utilizador = utilizador;
    }

    public String getId_associacao() {
        return id_associacao;
    }

    public void setId_associacao(String id_associacao) {
        this.id_associacao = id_associacao;
    }

    public Long getId_foto() {
        return id_foto;
    }

    public void setId_foto(Long id_foto) {
        this.id_foto = id_foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray( new String[]{
                getNome(),
                getData(),
                getUtilizador()+"",
                getId_associacao(),
                getId_foto()+"",
                getId()+""
        });

    }
}
