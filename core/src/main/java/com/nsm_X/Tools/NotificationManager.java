package com.nsm_X.Tools;

public class NotificationManager {
    private static String lastNotification = "";
    private static boolean notificationActive = false;

    // Méthode pour enregistrer une notification
    public static void setLastNotification(String message) {
        lastNotification = message;
        notificationActive = true;
    }

    // Méthode pour récupérer la dernière notification
    public static String getLastNotification() {
        return lastNotification;
    }

    // Vérifier si une notification est active
    public static boolean isNotificationActive() {
        return notificationActive;
    }

    // Réinitialiser la notification
    public static void resetNotification() {
        notificationActive = false;
    }
}
