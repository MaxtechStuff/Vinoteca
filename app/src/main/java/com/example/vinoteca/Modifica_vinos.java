package com.example.vinoteca;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class Modifica_vinos extends AppCompatActivity {

    Button aceptar, borrar;
    EditText txtNombre, txtDenominacion;
    RadioButton tinto, blanco;
    CheckBox probado;
    RatingBar puntuacion;

    ImageView imagen;
    Bitmap imagenNueva;
    String URL = "https://www.catadelvino.com/uploads/que-significa-la-categoria-de-vino-de-mesa-4917-1.jpg";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualiza_vinos);
        CargaImagen cargaImagen = new CargaImagen();
        cargaImagen.execute(URL);

        aceptar = (Button) findViewById(R.id.btn_aceptar_actualiza);
        borrar = (Button) findViewById(R.id.btn_borrar_modifica);
        imagen = findViewById(R.id.imageView_actualiza);

        txtDenominacion = (EditText) findViewById(R.id.txt_denominacionOrigen_actualiza);
        txtNombre = (EditText) findViewById(R.id.txtVinoNombre_acualiza);
        tinto = (RadioButton) findViewById(R.id.btn_tinto_actualiza);
        blanco = (RadioButton) findViewById(R.id.btn_blanco_actualiza);
        probado = (CheckBox) findViewById(R.id.cb_probado_actualiza);
        puntuacion = (RatingBar) findViewById(R.id.barra_valoracion_actualiza);

        Intent intent = getIntent();
        final String cadena_intent = intent.getStringExtra("Denominacion");
        if(intent!=null){
            txtNombre.setText(intent.getStringExtra("Nombre"));
            txtDenominacion.setText(intent.getStringExtra("Denominacion"));

            if (intent.getStringExtra("Tipo").equalsIgnoreCase("tinto") == true) {
                tinto.setChecked(true);
                blanco.setChecked(false);
            }else{
                tinto.setChecked(false);
                blanco.setChecked(true);
            }


            if(intent.getStringExtra("Probado").equalsIgnoreCase("true")){
                probado.setChecked(true);
            }else{
                probado.setChecked(false);
            }
            String puntos = intent.getStringExtra("Valoracion");
            Float p = Float.parseFloat(puntos);
            puntuacion.setRating(p);
        }

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo =" ", isvaloracion = " ", isprobado= " ";
                if(tinto.isChecked()== true){
                    tipo = "tinto";
                }else{
                    tipo = "blanco";
                }

                if (probado.isChecked() == true ){
                    isprobado = "true";
                }else if(probado.isChecked() == false){
                    isprobado = "false";
                }
                isvaloracion = String.valueOf(puntuacion.getRating());

                BD_Vinos BD = new BD_Vinos(getBaseContext());
                BD.abre();
                String deno = txtDenominacion.getText().toString();


                Log.w("App1","Nombre " + txtNombre.getText().toString());
                Log.w("App1", "Denominacion: " + deno.substring(3,4));
                Log.w("App1","tipo: " + tipo);
                Log.w("App1","probado " + isprobado);
                Log.w("App1","Valoracion " + isvaloracion);

                Log.w("AppDATOS", txtNombre.getText().toString() + deno.substring(3,4) + tipo + isprobado);
                BD.actualizaVino(txtNombre.getText().toString(), deno.substring(3,4), tipo, isprobado, isvaloracion);
                BD.cierra();
                Intent intent = new Intent();
                intent.setClass(Modifica_vinos.this, MainActivity.class);
                startActivity(intent);
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadena = " ";
                Intent inte = getIntent();
                cadena = inte.getStringExtra("Denominacion");
                BD_Vinos BD = new BD_Vinos((getBaseContext()));
                BD.abre();
                BD.borraVino(cadena.substring(3,4));
                BD.cierra();

                Intent intent2 = new Intent();
                intent2.setClass(Modifica_vinos.this, MainActivity.class);
                startActivity(intent2);
            }
        });
    }

    public class CargaImagen extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... param){
            Bitmap bitmap = null;
            try{
                InputStream input = new java.net.URL(param[0]).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result){
            imagen.setImageBitmap(result);
        }
    }
}
