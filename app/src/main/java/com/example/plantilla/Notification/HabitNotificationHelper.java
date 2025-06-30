package com.example.plantilla.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.plantilla.Beans.Habit;
import com.example.plantilla.R;

public class HabitNotificationHelper {

    public static void scheduleNotification(Context context, Habit habit) {
        // Crea un Intent para disparar la notificación
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("habit_name", habit.getNombre());
        intent.putExtra("habit_action", "Realizar " + habit.getNombre()); // Acción sugerida
        intent.putExtra("habit_categoria", habit.getCategoria());

        if(habit.getCategoria().equals("Ejercicio")){
            intent.putExtra("habit_icon", R.drawable.ic_ejercicio_icon); // Definir un icono representativo
        } else if(habit.getCategoria().equals("Alimentación")){
            intent.putExtra("habit_icon", R.drawable.ic_alimentacion_icon); // Definir un icono representativo
        } else if(habit.getCategoria().equals("Sueño")){
            intent.putExtra("habit_icon", R.drawable.ic_sueno_icon); // Definir un icono representativo

        } else if(habit.getCategoria().equals("Profesional")){
            intent.putExtra("habit_icon", R.drawable.ic_profesional_icon); // Definir un icono representativo
        }

        // Crear un PendingIntent para la notificación
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Configura AlarmManager para emitir la notificación
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long tiempoInicial = habit.getFechaInicio();  // desde cuándo inicia
        long interval = habit.getFrecuencia() * 60 * 60 * 1000; // Frecuencia en horas
        long triggerTime = tiempoInicial + interval;  // Tiempo de disparo del primer recordatorio

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                tiempoInicial,
                interval,
                pendingIntent);
    }


}
