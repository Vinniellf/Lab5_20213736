package com.example.plantilla.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.content.Context;

import androidx.core.app.ActivityCompat;

import com.example.plantilla.Beans.Habit;

public class NotificationHelper {

    public static final String CHANNEL_EJERCICIO = "Ejercicio";
    public static final String CHANNEL_ALIMENTACION = "Alimentación";
    public static final String CHANNEL_SUENO = "Sueño";
    public static final String CHANNEL_PROFESIONAL = "Profesional";


    public static void createNotificationChannels(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Creando el canal para Ejercicio
            NotificationChannel ejercicioChannel = new NotificationChannel(
                    CHANNEL_EJERCICIO,
                    "Ejercicio",
                    NotificationManager.IMPORTANCE_HIGH
            );
            ejercicioChannel.setDescription("Notificaciones para hábitos de ejercicio");
            ejercicioChannel.enableVibration(true);

            // Creando el canal para Alimentación
            NotificationChannel alimentacionChannel = new NotificationChannel(
                    CHANNEL_ALIMENTACION,
                    "Alimentación",
                    NotificationManager.IMPORTANCE_LOW
            );
            alimentacionChannel.setDescription("Notificaciones para hábitos de alimentación");
            alimentacionChannel.enableVibration(true);

            // Creando el canal para Sueño
            NotificationChannel suenoChannel = new NotificationChannel(
                    CHANNEL_SUENO,
                    "Sueño",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            suenoChannel.setDescription("Notificaciones para hábitos de sueño");
            suenoChannel.enableVibration(true);

            // Creando el canal para Lectura
            NotificationChannel profesionalChannel = new NotificationChannel(
                    CHANNEL_PROFESIONAL,
                    "Lectura",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            profesionalChannel.setDescription("Notificaciones para hábitos profesionales");
            profesionalChannel.enableVibration(true);

            // Registrar los canales
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(ejercicioChannel);
            manager.createNotificationChannel(alimentacionChannel);
            manager.createNotificationChannel(suenoChannel);
            manager.createNotificationChannel(profesionalChannel);
        }

    }
}
