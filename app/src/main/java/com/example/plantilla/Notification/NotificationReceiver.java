package com.example.plantilla.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.plantilla.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String habitName = intent.getStringExtra("habit_name");
        String action = intent.getStringExtra("habit_action");
        String categoria = intent.getStringExtra("habit_categoria");

        String channelId = "";
        String mensaje = "¡Hoy es un gran día para tu hábito!";
        int icono = R.drawable.ic_launcher_foreground;


    }
}
