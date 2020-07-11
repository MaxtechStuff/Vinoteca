package com.example.vinoteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class BD_Vinos {
    /* Estructura Tabla */
    public static final String CAMPO_NOMBRE = "NOMBRE";
    public static final String CAMPO_DENOMINACION = "DENOMINACION_ORIGEN";
    public static final String CAMPO_TIPO = "TIPO";
    public static final String CAMPO_PROBADO = "PROBADO";
    public static final String CAMPO_VALORACION = "VALORACION";
    public static final String TAG = "BD_VINOS";

    /* Datos Base datos */
    public static final String BD_NOMBRE = "Base_Datos_Vinos";
    public static final String BD_NOMBRE_TABLA = "VINOS";
    public static final Integer BD_VER = 2;


    public static final String BD_CREATE =
            "create table " + BD_NOMBRE_TABLA + " ("
                    + CAMPO_DENOMINACION + " integer primary key autoincrement, "
                    + CAMPO_NOMBRE + " text not null, "
                    + CAMPO_TIPO + " text not null, "
                    + CAMPO_PROBADO + " text not null, "
                    + CAMPO_VALORACION + " text not null"
                    +")";

    private final Context contexto;
    private AyudaBD ayuda;
    private SQLiteDatabase BD;

    public BD_Vinos(Context con){
        this.contexto = con;
        Log.w(TAG, "creando ayuda...");
        ayuda = new AyudaBD(contexto);
    }

    public BD_Vinos abre () throws SQLException{
        Log.w(TAG, " abriendo base de datos");
        BD = ayuda.getWritableDatabase();
        return this;
    }

    public void cierra(){
        ayuda.close();
    }

    public Cursor obtenerVinos(){
        return BD.query(BD_NOMBRE_TABLA, new String[]
                {CAMPO_NOMBRE, CAMPO_DENOMINACION, CAMPO_TIPO, CAMPO_PROBADO, CAMPO_VALORACION},
                null, null, null, null, null, null);
    }

    public String obtenerNombre(String denominacion){
        String nombre;
        Cursor c = BD.query(BD_NOMBRE_TABLA, new String[]
                        {CAMPO_NOMBRE},
                CAMPO_DENOMINACION + " =?", new String[]{denominacion}, null, null, null);
        c.moveToFirst();
        nombre = c.getString(0);
        return nombre;
    }

    public String obtenerTipo(String denominacion){
        String tipo;
        Cursor c = BD.query(BD_NOMBRE_TABLA, new String[]
                        {CAMPO_TIPO},
                CAMPO_DENOMINACION + " =?", new String[]{denominacion}, null, null, null);
        c.moveToFirst();
        tipo = c.getString(0);
        return tipo;
    }

    public String obtenerProbado(String denominacion){
        String probado;
        Cursor c = BD.query(BD_NOMBRE_TABLA, new String[]
                        {CAMPO_PROBADO},
                CAMPO_DENOMINACION + " =?", new String[]{denominacion}, null, null, null);
        c.moveToFirst();
        probado = c.getString(0);
        return probado;
    }

    public String obtenerValoracion(String denominacion){
        String valoracion;
        Cursor c = BD.query(BD_NOMBRE_TABLA, new String[]
                        {CAMPO_VALORACION},
                CAMPO_DENOMINACION + " =?", new String[]{denominacion}, null, null, null);
        c.moveToFirst();
        valoracion = c.getString(0);
        return valoracion;
    }

    public void actualizaVino(String nombre, String denominacion, String tipo, String probado, String valoracion){
        Log.w("App","Nombre " + nombre);
        Log.w("App","Denominacion: " + denominacion);
        Log.w("App","Tipo " + tipo);
        Log.w("App","Probado " + probado);
        Log.w("App","Valoracion " + valoracion);

        /* UPDATE comments SET comment='Esto es un comentario actualizado por el método execSQL()' WHERE user='Digital Learning'*/
        BD.execSQL("UPDATE "+ BD_NOMBRE_TABLA + " SET " + CAMPO_NOMBRE + " = '" + nombre + "' where " + CAMPO_DENOMINACION + " = '"+ denominacion+ "'");
        BD.execSQL("UPDATE "+ BD_NOMBRE_TABLA + " SET " + CAMPO_TIPO + " = '" + tipo + "' where " + CAMPO_DENOMINACION + " = '"+ denominacion+ "'");
        BD.execSQL("UPDATE "+ BD_NOMBRE_TABLA + " SET " + CAMPO_PROBADO + " = '" + probado + "' where " + CAMPO_DENOMINACION + " = '"+ denominacion+ "'");
        BD.execSQL("UPDATE "+ BD_NOMBRE_TABLA + " SET " + CAMPO_VALORACION + " = " + valoracion + " where " + CAMPO_DENOMINACION + " = '"+ denominacion+ "'");
    }

    public long insertaVino(String nombre, String tipo, String probado, String valoracion){
        ContentValues initialValues = new ContentValues();
        initialValues.put(CAMPO_NOMBRE, nombre);
        initialValues.put(CAMPO_TIPO, tipo);
        initialValues.put(CAMPO_PROBADO, probado);
        initialValues.put(CAMPO_VALORACION, valoracion);

        Log.w("App","Nombre " + nombre);
        Log.w("App","Tipo: " + tipo);
        Log.w("App","Probado " + probado);
        Log.w("App","Valoracion " + valoracion);

        return BD.insert(BD_NOMBRE_TABLA, null, initialValues);
    }

    public void borraVino(String denominacion){
        String CADENA = "DELETE FROM "+ BD_NOMBRE_TABLA + " WHERE " + CAMPO_DENOMINACION + " = '" + denominacion + "'";
        BD.execSQL(CADENA);
    }

    public class AyudaBD extends SQLiteOpenHelper{
        public AyudaBD(Context con) {
            super(con, BD_NOMBRE, null, BD_VER);
            Log.w(TAG, "contructor de ayuda");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                Log.w(TAG, "creando base de datos " + BD_CREATE);
                db.execSQL(BD_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Actualizando base de datos de la version " + oldVersion + " a la versión " + newVersion + ". Todos los datos serán borrados.");
            BD.execSQL("DROP TABLE IF EXISTS " + BD_NOMBRE_TABLA);
            onCreate(BD);
        }
    }
}
