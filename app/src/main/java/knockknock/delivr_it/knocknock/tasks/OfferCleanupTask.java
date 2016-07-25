package knockknock.delivr_it.knocknock.tasks;

import org.json.JSONArray;

import knockknock.delivr_it.knocknock.activities.MainActivity;
import knockknock.delivr_it.knocknock.managers.OfferStorageManager;

public class OfferCleanupTask {
    private MainActivity mainActivityInstance;

    public OfferCleanupTask(MainActivity mainActivityInstance) {
        this.mainActivityInstance = mainActivityInstance;
    }

    public void deleteOutdatedOffers(JSONArray offers) {
        try {
            OfferStorageManager.deleteOffers(mainActivityInstance, offers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
