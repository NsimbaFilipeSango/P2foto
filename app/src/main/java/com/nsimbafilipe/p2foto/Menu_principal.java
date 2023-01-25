package com.nsimbafilipe.p2foto;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.nsimbafilipe.p2foto.databinding.ActivityMenuPrincipalBinding;

import java.util.List;

public class Menu_principal extends AppCompatActivity {

    private List<Album> listaAlbum;

    private List<Utilizador> listaUtilizador;

    Base_Dados helper;
    private RecyclerView albumRecy;
    private AlbumAdapter adapter;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMenuPrincipalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();

        int id_usuario = extra.getInt("usuario");
        binding = ActivityMenuPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_principal);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTarefa();
            }
        });

        helper = new Base_Dados(this);

        listaAlbum = helper.getList("ASC",id_usuario);
        Toast.makeText(Menu_principal.this, "QTD ELEMENTOS: "+listaAlbum.size(), Toast.LENGTH_SHORT).show();

        albumRecy = findViewById(R.id.UtilizadorRecy);
        albumRecy.setHasFixedSize(true);

        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        albumRecy.setLayoutManager(lin);

        adapter = new AlbumAdapter(listaAlbum);
        albumRecy.setAdapter(adapter);

    }

    public void addTarefa(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View alertview = getLayoutInflater().inflate(R.layout.cadastrar_album,null);
        final EditText nome= alertview.findViewById(R.id.Nome_Album);
        final EditText data= alertview.findViewById(R.id.Data_Album);

        builder.setView(alertview);
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //
                Bundle extra = getIntent().getExtras();//serve para passar os dados de uma tela para a outra

                int id_usuario = extra.getInt("usuario");
                Album album_ = new Album();//=  data.getParcelableExtra("contato");
                album_.setNome(nome.getText().toString());
                album_.setData(data.getText().toString());
                album_.setUtilizador(id_usuario);

                helper.inserirContact(album_);
                listaAlbum = helper.getList("ASC",id_usuario);
                Toast.makeText(Menu_principal.this, "id: "+id_usuario, Toast.LENGTH_SHORT).show();
                adapter = new AlbumAdapter(listaAlbum);
                albumRecy.setAdapter(adapter);

                /*SQLiteDatabase db = bd.getWritableDatabase();
                ContentValues values= new ContentValues();

                values.clear();
                values.put(Contrato.Columns.TAREFA,tarefa.getText().toString());
                values.put(Contrato.Columns.PRAZO,praso.getText().toString());
                db.insertWithOnConflict(Contrato.TABLE,null,values,SQLiteDatabase.CONFLICT_IGNORE);
                UpdateUI();
            */

            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_principal);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}