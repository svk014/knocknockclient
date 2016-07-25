package knockknock.delivr_it.knocknock.tasks;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import knockknock.delivr_it.knocknock.managers.BaseDatabaseVersionStorageManager;
import knockknock.delivr_it.knocknock.managers.ItemStorageManager;

public class ItemStorageTask {
    private Context context;

    ItemStorageTask(Context context) {

        this.context = context;
    }

    public void store(JSONArray items, int baseDbVersion) {

        for (int i = 0; i < items.length(); i++) {
            try {
                JSONObject item = items.getJSONObject(i);
                item.put("image_local_path", "<empty>");
                ItemStorageManager.storeItem(context, item);
            } catch (JSONException ignored) {
            }
        }

        if (ItemStorageManager.confirmItemsStored(context, items.length())) {
            BaseDatabaseVersionStorageManager.setCurrentDatabaseVersion(context, baseDbVersion);
        }
    }

}
