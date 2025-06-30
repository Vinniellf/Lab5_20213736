package com.example.plantilla.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantilla.Adapters.HabitAdapter;
import com.example.plantilla.Beans.Habit;
import com.example.plantilla.HabitPrefs;
import com.example.plantilla.R;
import com.example.plantilla.databinding.ActivityHabitsBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HabitsActivity extends AppCompatActivity {

    private ActivityHabitsBinding binding;
    private HabitPrefs prefs;
    private List<Habit> listHabitos = new ArrayList<>();
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        binding = ActivityHabitsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.listaHabitaciones;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        CargarData();
        if (listHabitos.isEmpty()) {
            binding.NoHayHabitos.setVisibility(View.VISIBLE);
        } else {
            binding.NoHayHabitos.setVisibility(View.GONE);
        }
        HabitAdapter adapter = new HabitAdapter(listHabitos, this, new HabitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Habit habit = listHabitos.get(position);
                /*mostrarPopupSeleccion(habitacion);*/
            }

            @Override
            public void onSeleccionCambio() {
                /*verificarSeleccion();*/
            }
        });
        recyclerView.setAdapter(adapter);


        binding.btnAdd.setOnClickListener(v ->  {
            finish();
            Intent intent = new Intent(this, AddHabitActivity.class);
            startActivity(intent);
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Usar el método tradicional
            }
        });


    }

    private void CargarData() {
        HabitPrefs prefs = new HabitPrefs(this);
        listHabitos = prefs.leer();

    }

    public void mostrarTextoNoHayHabitos() {
        TextView tvNoHayHabitos = findViewById(R.id.NoHayHabitos);
        tvNoHayHabitos.setVisibility(View.VISIBLE);
    }


}