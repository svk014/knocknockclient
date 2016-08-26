package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import knockknock.delivr_it.knocknock.models.Notification;

public class NotificationStorageManager {
    public static void storeNotification(Context context, String notificationText) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();
        Notification notification = new Notification();
        notification.setNotificationText(notificationText);
        notification.setTimeOfReceipt(System.currentTimeMillis());
        realm.copyToRealmOrUpdate(notification);
        realm.commitTransaction();
    }

    public static List<Notification> getAllNotifications(Context context) {
        Realm realm = Realm.getInstance(context);
        return realm.where(Notification.class).findAllSorted("timeOfReceipt", false);
    }
}
