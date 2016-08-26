package knockknock.delivr_it.knocknock.activities;

import android.app.Application;
import android.util.Log;

import com.onesignal.OneSignal;

public class KnocKnock extends Application {

    public static String oneSignalUserId;
    public static String masterAppId;

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                oneSignalUserId = userId;
                Log.d("user-id", userId);
            }
        });
    }
}
