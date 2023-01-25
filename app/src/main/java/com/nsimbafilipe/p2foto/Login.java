package com.nsimbafilipe.p2foto;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Login extends AppCompatActivity {

    private Base_Dados dados;
    List<Utilizador> utilizador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("P2foto", MODE_PRIVATE,null);

            sqLiteDatabase.execSQL("CREATE TABLE Album  (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL unique, " +
                    "data_ TEXT, " +
                    "utilizador INTEGER , " +//utilizador que criou o album|unico
                    "associados TEXT, " +//id utilizadores poderam ter acesso ao album
                    "id_foto INTEGER" +//tabela fotos
                    ");");
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + "Album" + " ORDER BY id " + "ASC" + ";", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                if(cursor.getInt(0)==0) {
                    Log.i("meuLog", "tabela esta vazia");
                }else
                    Log.i("meuLog", "tabela esta com elementos");
            }else Log.i("meuLog", "nao existe tabela ");
            while (cursor!=null) {

                Long s = cursor.getLong(cursor.getColumnIndex("id"));
                Log.i("meuLog", "id" + s + "");
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e ){
            e.printStackTrace();

        }

        Button cadastrar= findViewById(R.id.Cadastrar_login);
        Button login= findViewById(R.id.entrar_login);

        EditText email_log= findViewById(R.id.email_login);
        EditText senha_log=findViewById(R.id.senha_login);
        dados = new Base_Dados(this);
        utilizador = dados.listar_utilizador("ASC");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email= email_log.getText().toString();
                String senha= senha_log.getText().toString();
                int id;

                for (int i=0;i<utilizador.size();i++){
                    if (email.equals(utilizador.get(i).getEmail()) && senha.equals(utilizador.get(i).getSenha())){
                        id= utilizador.get(i).getId();
                        Toast.makeText(Login.this, "id: "+id, Toast.LENGTH_SHORT).show();
                        //id= utilizador.get(i).getId();
                        Intent i1 = new Intent(Login.this,Menu_principal.class);
                        i1.putExtra("usuario",id);
                        startActivity(i1);
                    }
                }
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUtilizador();
            }
        });


    }

    public void addUtilizador(){

        dados = new Base_Dados(this);

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View visao = getLayoutInflater().inflate(R.layout.interface_utilizador,null);
        final EditText nome= visao.findViewById(R.id.nome_utilizador);
        final EditText email= visao.findViewById(R.id.email_utilizador);
        final  EditText senha= visao.findViewById(R.id.senha_utilizador);
        builder.setView(visao);
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Utilizador u = new Utilizador();
                u.setNome(nome.getText().toString());
                u.setEmail(email.getText().toString());
                u.setSenha(senha.getText().toString());

                dados.inserir_utilizador(u);


                Toast.makeText(Login.this,"O nome e "+ dados.listar_utilizador("ASC").size(), Toast.LENGTH_SHORT).show();


            }
        });
        builder.setNegativeButton("Anular", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

}