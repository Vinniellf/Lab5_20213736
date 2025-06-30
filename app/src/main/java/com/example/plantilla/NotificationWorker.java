package com.example.plantilla;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.plantilla.R;

public class NotificationWorker extends Worker {

    public NotificationWorker(@NonNull Context ctx, @NonNull WorkerParameters params) {
        super(ctx, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        SharedPreferences prefs = getApplicationContext()
                .getSharedPreferences(getApplicationContext().getString(R.string.pref_name), Context.MODE_PRIVATE);
        String nombre  = prefs.getString("nombre", "¡Hola!");
        String mensaje = prefs.getString("mensaje",
                getApplicationContext().getString(R.string.default_msg));

        NotificationManager nm =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String chId = "habitos_ch";
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            nm.createNotificationChannel(new NotificationChannel(
                    chId, "Recordatorios de hábitos",
                    NotificationManager.IMPORTANCE_DEFAULT));
        }

        NotificationCompat.Builder nb = new NotificationCompat.Builder(getApplicationContext(), chId)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentTitle(nombre)
                .setContentText(mensaje)
                .setAutoCancel(true);

        nm.notify(1, nb.build());
        return Result.success();
    }
}
