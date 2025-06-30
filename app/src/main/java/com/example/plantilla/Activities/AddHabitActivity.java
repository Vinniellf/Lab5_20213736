package com.example.plantilla.Activities;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.plantilla.Beans.Habit;
import com.example.plantilla.HabitPrefs;
import com.example.plantilla.Notification.NotificationHelper;
import com.example.plantilla.R;
import com.example.plantilla.databinding.ActivityAddHabitBinding;
import com.example.plantilla.databinding.ActivityHabitsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddHabitActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etFrecuencia, etHora, etFecha;
    private AutoCompleteTextView etCategoria;
    private MaterialButton btnGuardar;

    private HabitPrefs prefs;
    private Calendar calendario = Calendar.getInstance();
    private ActivityAddHabitBinding binding;

    private long fechaInicioMillis;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        binding = ActivityAddHabitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefs = new HabitPrefs(this);


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Usar el método tradicional
            }
        });


        binding.etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSelectorFecha();
            }
        });

        binding.etHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSelectorHora();
            }
        });

        binding.btnGuardar.setOnClickListener(v -> {
            String nombre    = binding.etNombre.getText().toString().trim();
            String categoria = binding.etCategoria.getText().toString().trim();
            int    frecuencia;
            try { frecuencia = Integer.parseInt(binding.etFrecuencia.getText().toString().trim()); }
            catch (NumberFormatException e){ frecuencia = 0; }

            if(nombre.isEmpty() || categoria.isEmpty() || frecuencia==0){
                Toast.makeText(this,"Completa todos los campos",Toast.LENGTH_SHORT).show();
                return;
            }

            fechaInicioMillis = calendario.getTimeInMillis();
            Habit nuevo = new Habit(nombre,categoria,frecuencia,fechaInicioMillis);
            prefs.agregar(nuevo);            // ✅ persiste en SharedPreferences
            NotificationHelper.createNotificationChannels(this);
            Toast.makeText(this,"Hábito guardado",Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(this, HabitsActivity.class);
            startActivity(intent);

        });

    }

    private void mostrarSelectorHora() {
        final Calendar c1 = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
            calendario.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendario.set(Calendar.MINUTE, minute);
            calendario.set(Calendar.SECOND, 0);

            c1.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c1.set(Calendar.MINUTE, minute);
            c1.set(Calendar.SECOND, 0);
            binding.etHora.setText(android.text.format.DateFormat.format("HH:mm", c1));
        };
        new TimePickerDialog(AddHabitActivity.this, timeSetListener,
                calendario.get(Calendar.HOUR_OF_DAY), calendario.get(Calendar.MINUTE), true).show();

    }

    private void mostrarSelectorFecha() {
        final Calendar c2 = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, month);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            c2.set(Calendar.YEAR, year);
            c2.set(Calendar.MONTH, month);
            c2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            binding.etFecha.setText(android.text.format.DateFormat.format("dd/MM/yyyy", c2));

        };

        new DatePickerDialog(AddHabitActivity.this, dateSetListener,
                calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();
    }
}