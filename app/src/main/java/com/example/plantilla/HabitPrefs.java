package com.example.plantilla;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.plantilla.Beans.Habit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HabitPrefs {

    private static final String FILE = "habitos_prefs";
    private static final String KEY  = "habitos_json";

    /** Gson con formateo ISO 8601 para java.util.Date */
    private final Gson   gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();
    private final Type   tipoLista = new TypeToken<List<Habit>>(){}.getType();
    private final SharedPreferences sp;

    public HabitPrefs(Context ctx){
        sp = ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    /* Lee la lista persistente (vacía si aún no existe) */
    public List<Habit> leer(){
        String json = sp.getString(KEY, "[]");
        return gson.fromJson(json, tipoLista);
    }

    /* Sobrescribe la lista completa */
    private void guardar(List<Habit> lista){
        sp.edit().putString(KEY, gson.toJson(lista, tipoLista)).apply();
    }

    /* === Operaciones de conveniencia === */

    /** Agrega un hábito nuevo y lo guarda */
    public void agregar(Habit h){
        List<Habit> l = leer();
        l.add(h);
        guardar(l);
    }

    /** Elimina por posición (o implementa por ID/nombre) */
    public void eliminar(int index){
        List<Habit> l = leer();
        if(index>=0 && index<l.size()){
            l.remove(index);
            guardar(l);
        }
    }

    /** Reemplaza toda la lista (útil tras editar) */
    public void reemplazar(List<Habit> nueva){
        guardar(nueva);
    }
}
