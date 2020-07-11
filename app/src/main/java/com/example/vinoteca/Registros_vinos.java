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

public class Registros_vinos extends AppCompatActivity {
    Button aceptar, borrar;
    EditText txtNombre, txtDenominacion;
    RadioButton tinto, blanco;
    CheckBox probado;
    RatingBar puntuacion;

    ImageView imagen;
    Bitmap imagenNueva;
    String URL = "https://www.catadelvino.com/uploads/que-significa-la-categoria-de-vino-de-mesa-4917-1.jpg";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_vinos);

        CargaImagen cargaImagen = new CargaImagen();
        cargaImagen.execute(URL);

        aceptar = (Button) findViewById(R.id.btn_registra_aceptar);
        imagen = findViewById(R.id.imageView);

        txtNombre = (EditText) findViewById(R.id.txtVinoNombre);
        tinto = (RadioButton) findViewById(R.id.btn_tinto);
        blanco = (RadioButton) findViewById(R.id.btn_blanco);
        probado = (CheckBox) findViewById(R.id.cb_probado);
        puntuacion = (RatingBar) findViewById(R.id.barra_valoracion);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = " ";
                String tipoTinto, tipoBlanco, tipoValor = " ";
                String Valoracion;
                String isprobado;

                nombre = txtNombre.getText().toString();
                tipoTinto = String.valueOf(tinto.isChecked());
                tipoBlanco = String.valueOf(blanco.isChecked());
                Valoracion = String.valueOf(puntuacion.getRating());
                isprobado = String.valueOf(probado.isChecked());

                if(tipoTinto.equalsIgnoreCase("true")){
                    tipoValor = "Tinto";
                }else if (tipoBlanco.equalsIgnoreCase("true")){
                    tipoValor = "Blanco";
                }

                if(tipoValor.equalsIgnoreCase(" ") || Valoracion.equalsIgnoreCase("0.0") || nombre.equalsIgnoreCase("") || nombre.equalsIgnoreCase( " ") || nombre == null){
                    Toast.makeText(Registros_vinos.this, "Tienes que rellenar todos los datos para realizar el registro!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Registros_vinos.this, "|Nombre: " + nombre
                            +"| Tipo: " + tipoValor
                            +"| Probado: " + isprobado
                            +"| valoracion: " + Valoracion, Toast.LENGTH_LONG).show();

                    BD_Vinos BD = new BD_Vinos(getBaseContext());
                    BD.abre();
                    Log.w("App", txtNombre.getText().toString()+ " " +tipoValor+ " " +String.valueOf(probado.isChecked()) + " " + String.valueOf(puntuacion.getRating()));
                    BD.insertaVino(txtNombre.getText().toString(), tipoValor, String.valueOf(probado.isChecked()), String.valueOf(puntuacion.getRating()));
                    BD.cierra();

                    Intent intent = new Intent();
                    intent.setClass(Registros_vinos.this, MainActivity.class);
                    startActivity(intent);
            }
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
