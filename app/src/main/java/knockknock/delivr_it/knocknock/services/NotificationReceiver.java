package knockknock.delivr_it.knocknock.services;


import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import knockknock.delivr_it.knocknock.managers.NotificationStorageManager;

public class NotificationReceiver extends NotificationExtenderService {
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {

//        Toast.makeText(getApplicationContext(), receivedResult.payload.body, Toast.LENGTH_LONG).show();
        NotificationStorageManager.storeNotification(getApplicationContext(), receivedResult.payload.body);
        // Return true to stop the notification from displaying.
        return false;

    }
}
