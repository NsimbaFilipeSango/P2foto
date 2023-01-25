package com.nsimbafilipe.p2foto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Base_Dados extends SQLiteOpenHelper {
    private  static  final int ACTUAL=1;
    private  static  final  String TABELA = "utilizador";
    private  static  final String DATABASE = "P2foto";

    public Base_Dados(Context context) {
        super(context, DATABASE, null, ACTUAL);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String sql="CREATE TABLE "+TABELA+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nome TEXT NOT NULL,"+
                "email TEXT,"+
                "senha TEXT" +
                ");";
        sqLiteDatabase.execSQL(sql);


    }

    //==================================|Lista de album|==============================================
    //=====================================|inserir em album|============================================
    public void inserirContact(Album c) {

        ContentValues values = new ContentValues();
        values.put("nome", c.getNome());
        values.put("data_", c.getData());
        values.put("utilizador", c.getUtilizador());
        values.put("associados", c.getId_associacao());
        values.put("id_foto", c.getId_foto());
        getWritableDatabase().insert("Album", null, values);
    }
//===================================================================================================

    public List<Album> getList(String order,int usuario) {
        List<Album> contatos = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM  Album  where utilizador="+ usuario+" ORDER BY nome " + order + ";", null);
        //Cursor cursor1 = getReadableDatabase().rawQuery("SELECT a.id , a.nome, a.data_, a.utilizador, a.associados, a.id_foto FROM album a, ( select b.utilizador from album c, adcionar_album b WHERE c.id=b.album) u WHERE  a.utilizador="+usuario+" or u.utilizador="+usuario+" ORDER BY a.nome " + order + ";", null);

        while (cursor.moveToNext()) {
            Album c = new Album();
            Log.i("meuLog","--------------|Album|------------------- ");
            Log.i("meuLog","id"+cursor.getLong(cursor.getColumnIndex("utilizador")));
            c.setId(cursor.getInt(cursor.getColumnIndex("id")));
            c.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            c.setData(cursor.getString(cursor.getColumnIndex("data_")));
            c.setUtilizador(cursor.getInt(cursor.getColumnIndex("utilizador")));
            c.setId_associacao(cursor.getString(cursor.getColumnIndex("associados")));
            c.setId_foto(cursor.getLong(cursor.getColumnIndex("id_foto")));
            //c.setId(cursor.getLong(cursor.getColumnIndex("id_usuario")));
            contatos.add(c);
        }

        cursor.close();

        return contatos;
    }
//===================================================================================================


    public void inserir_utilizador(Utilizador utilizador){
        ContentValues valor = new ContentValues();
        valor.put("nome",utilizador.getNome());
        valor.put("email",utilizador.getEmail());
        valor.put("senha",utilizador.getSenha());
        getWritableDatabase().insert(TABELA,null,valor);
    }

    public  List<Utilizador> listar_utilizador(String c){

        List<Utilizador>listar = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " +TABELA +" ORDER BY id ASC ",null);
        String nome="Erro";

        while (cursor.moveToNext()){

            Utilizador ut = new Utilizador();

            ut.setId(cursor.getInt(cursor.getColumnIndex("id")));
            ut.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            ut.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            ut.setEmail(cursor.getString(cursor.getColumnIndex("email")));
             //nome= cursor.getString(cursor.getColumnIndex("nome"));
           // Long id= cursor.getLong(cursor.getColumnIndex("id"));
            //Log.i("ns","ver_id: "+cursor.getLong(cursor.getColumnIndex("id")));
            listar.add(ut);
        }
        return listar;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
