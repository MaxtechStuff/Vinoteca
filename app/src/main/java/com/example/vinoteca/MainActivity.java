package com.example.vinoteca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.vinoteca.interfaces.OnRecyclerViewLongItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler;
    AdaptadorVinos adaptador;
    ArrayList<Vino> array_vinos;
    BD_Vinos BD;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        toolbar.setTitle("Vinos");
        setSupportActionBar(toolbar);

        BD = new BD_Vinos(this);
        BD.abre();
        cargaListaVinos();
    }
    public void cargaListaVinos(){
        array_vinos = new ArrayList<Vino>();
        Cursor c = BD.obtenerVinos();
        if(c == null || c.getCount() == 0){
            Toast.makeText(this, "Tabla vacía", Toast.LENGTH_SHORT).show();
        }else{
            int i = 0;
            if (c.moveToFirst()){
                do{
                    array_vinos.add(new Vino(c.getString(0), c.getString(1)));
                    i++;
                }while(c.moveToNext());

            }
        }
        recycler = findViewById(R.id.main_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorVinos(array_vinos);
        recycler.setAdapter(adaptador);

        registerForContextMenu(recycler);
        adaptador.setOnItemLongClickListener(new OnRecyclerViewLongItemClickListener() {
            @Override
            public void onItemLongClick(View view, int p) {
                position = p;
                openContextMenu(view);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        Activity activity= (Activity) v.getContext();
        MenuInflater inflater =activity.getMenuInflater();
        inflater.inflate(R.menu.menu_popup, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String nombre, denominacion, tipo, valoracion, probado;
        Vino vi = array_vinos.get(position);
        switch(item.getItemId()) {
            case R.id.popupBorrar:
                BD.borraVino(vi.getDenominacionOrigen());//borramos de la base de datos
                adaptador.remove(vi);//quitamos el dato de la lista del adaptador
                adaptador.notifyDataSetChanged();//recargamos el listview
                break;
            case R.id.popupModificar :
                denominacion = vi.getDenominacionOrigen();
                nombre = BD.obtenerNombre(denominacion);
                tipo = BD.obtenerTipo(denominacion);
                probado = BD.obtenerProbado(denominacion);
                valoracion = BD.obtenerValoracion(denominacion);

                Log.w("DATOS OBTENIDOS", nombre + " " + denominacion + " " + tipo + " "+ probado + " " + valoracion  + " " );

                Intent intent = new Intent(this, Modifica_vinos.class);
                intent.putExtra("Nombre",nombre);
                intent.putExtra("Denominacion","DO " + denominacion);
                intent.putExtra("Tipo", tipo);
                intent.putExtra("Probado", probado);
                intent.putExtra("Valoracion", valoracion);

                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.registro_vino:
                Intent intent = new Intent(this, Registros_vinos.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    public void onResume(){
        super.onResume();
        BD.abre();
        cargaListaVinos();
    }

    public void onStop(){
        super.onStop();
        BD.cierra();
    }

}
