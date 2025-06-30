package com.example.plantilla.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.plantilla.NotificationWorker;
import com.example.plantilla.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.TimeUnit;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private TextInputEditText etNombre, etMensaje, etHoras;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs     = getSharedPreferences(getString(R.string.pref_name), MODE_PRIVATE);

        etNombre  = findViewById(R.id.etNombre);
        etMensaje = findViewById(R.id.etMensaje);
        etHoras   = findViewById(R.id.etHoras);

        // precargar valores
        etNombre.setText(prefs.getString("nombre","Usuario"));
        etMensaje.setText(prefs.getString("mensaje", getString(R.string.default_msg)));
        etHoras.setText(String.valueOf(prefs.getInt("horas", 4)));   // por defecto cada 4 h

        findViewById(R.id.btnGuardar).setOnClickListener(v -> guardar());
    }

    private void guardar() {

        String nombre   = etNombre.getText().toString().trim();
        String mensaje  = etMensaje.getText().toString().trim();
        int horas;

        try {
            horas = Integer.parseInt(etHoras.getText().toString().trim());
        } catch (NumberFormatException e) {
            horas = 0;   // fuerza validación
        }

        if (horas < 1 || horas > 24) {
            Toast.makeText(this, "Introduce un número de horas entre 1 y 24", Toast.LENGTH_SHORT).show();
            return;
        }

        prefs.edit()
                .putString("nombre", nombre)
                .putString("mensaje", mensaje)
                .putInt   ("horas",  horas)
                .apply();

        programarWorker(horas);

        Toast.makeText(this, "Configuración guardada 😊", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void programarWorker(int intervalHours) {

        // cancela trabajos previos, si los hubiera
        WorkManager.getInstance(this).cancelAllWorkByTag("motivation");

        long intervalo = TimeUnit.HOURS.toMillis(intervalHours);

        PeriodicWorkRequest req = new PeriodicWorkRequest
                .Builder(NotificationWorker.class, intervalHours, TimeUnit.HOURS)
                .addTag("motivation")
                .setInitialDelay(intervalo, TimeUnit.MILLISECONDS)  // primera vez después de X horas
                .build();

        WorkManager.getInstance(this).enqueue(req);
    }
}

