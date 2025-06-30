package com.example.plantilla.Notification;

import android.content.Context;
import android.content.SharedPreferences;

public class MotivationPreferences {

    private static final String PREF_NAME = "motivation_prefs";
    private static final String KEY_MOTIVATIONAL_MESSAGE = "motivational_message";
    private static final String KEY_NOTIFICATION_INTERVAL = "notification_interval"; // Horas

    public static void saveMotivationalMessage(Context context, String message, int interval) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_MOTIVATIONAL_MESSAGE, message);
        editor.putInt(KEY_NOTIFICATION_INTERVAL, interval);
        editor.apply();
    }

    public static String getMotivationalMessage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(KEY_MOTIVATIONAL_MESSAGE, "¡Sigue adelante!");
    }

    public static int getNotificationInterval(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(KEY_NOTIFICATION_INTERVAL, 24); // Default: cada 24 horas
    }
}
