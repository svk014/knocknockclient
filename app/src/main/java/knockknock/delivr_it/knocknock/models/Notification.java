package knockknock.delivr_it.knocknock.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Notification extends RealmObject {
    @PrimaryKey
    private Long timeOfReceipt;
    private String notificationText;

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public Long getTimeOfReceipt() {
        return timeOfReceipt;
    }

    public void setTimeOfReceipt(Long timeOfReceipt) {
        this.timeOfReceipt = timeOfReceipt;
    }
}
