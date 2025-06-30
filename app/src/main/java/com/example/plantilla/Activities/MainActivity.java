package com.example.plantilla.Activities;

import static com.example.plantilla.Notification.NotificationHelper.CHANNEL_EJERCICIO;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.plantilla.Activities.SettingsActivity;
import com.example.plantilla.Beans.Habit;
import com.example.plantilla.HabitPrefs;
import com.example.plantilla.Notification.HabitNotificationHelper;
import com.example.plantilla.Notification.NotificationHelper;
import com.example.plantilla.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String FOTO_FILE = "foto_usuario.jpg";
    private TextView tvSaludo, tvMensaje;
    private ImageView ivFoto;
    private SharedPreferences prefs;

    private final ActivityResultLauncher<String> pickImage =
            registerForActivityResult(new ActivityResultContracts.GetContent(), this::guardarFoto);

    // Lanzador para galería
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) pickImage.launch("image/*");
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences(getString(R.string.pref_name), MODE_PRIVATE);

        tvSaludo  = findViewById(R.id.tvSaludo);
        tvMensaje = findViewById(R.id.tvMensaje);
        ivFoto    = findViewById(R.id.ivFoto);

        HabitPrefs prefs = new HabitPrefs(this);
        List<Habit> listaHabitos = prefs.leer();

        if (!listaHabitos.isEmpty()) {
            for (Habit habit : listaHabitos) {
                HabitNotificationHelper.scheduleNotification(this, habit);
            }
        }

        NotificationHelper.createNotificationChannels(this);

        findViewById(R.id.btnConfig).setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));

        // “Ver mis hábitos” solo abre un placeholder
        findViewById(R.id.btnHabitos).setOnClickListener(v ->
                startActivity(new Intent(this, HabitsActivity.class))); // TODO

        ivFoto.setOnClickListener(v -> abrirGaleria());

        refrescarUI();
    }

    @Override protected void onResume() {
        super.onResume();
        refrescarUI();       // por si se cambió en ConfigActivity
    }

    /* ---------------- utilidades ------------------- */

    private void refrescarUI() {
        String nombre   = prefs.getString("nombre", "Usuario");
        String mensaje  = prefs.getString("mensaje", getString(R.string.default_msg));

        tvSaludo.setText("¡Hola, " + nombre + "!");
        tvMensaje.setText(mensaje);
        cargarFotoSiExiste();
    }

    private void abrirGaleria() {
        if (Build.VERSION.SDK_INT >= 33) {
            // En Android 13+ pedimos READ_MEDIA_IMAGES
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED) {
                pickImage.launch("image/*");
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            // En Android 12 o anterior pedimos READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                pickImage.launch("image/*");
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }


    private void guardarFoto(@NonNull Uri uri) {
        try (InputStream in = getContentResolver().openInputStream(uri);
             FileOutputStream out = openFileOutput(FOTO_FILE, MODE_PRIVATE)) {
            byte[] buffer = new byte[2048];
            int len;
            while ((len = in.read(buffer)) != -1) out.write(buffer, 0, len);
            cargarFotoSiExiste();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarFotoSiExiste() {
        File f = new File(getFilesDir(), FOTO_FILE);
        if (f.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
            ivFoto.setImageBitmap(bmp);
        } else {
            ivFoto.setImageResource(R.drawable.image_icon); // placeholder
        }
    }


}
