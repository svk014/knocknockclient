package knockknock.delivr_it.knocknock.tasks;

import android.content.Context;
import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import knockknock.delivr_it.knocknock.activities.ItemListActivity;
import knockknock.delivr_it.knocknock.activities.MainActivity;
import knockknock.delivr_it.knocknock.managers.CartStorageManager;
import knockknock.delivr_it.knocknock.managers.FileStorageManager;
import knockknock.delivr_it.knocknock.managers.ItemStorageManager;
import knockknock.delivr_it.knocknock.managers.OfferStorageManager;
import knockknock.delivr_it.knocknock.managers.UpdateVersionStorageManager;
import knockknock.delivr_it.knocknock.models.Order;

public class ItemUpdateWebToStorageTask {
    private Context context;
    private ItemListActivity itemListActivity;

    public ItemUpdateWebToStorageTask(Context context, ItemListActivity itemListActivity) {
        this.context = context;
        this.itemListActivity = itemListActivity;
    }

    public void updateItems(JSONArray itemUpdates) throws Exception {
        int updateVersionPreUpdate = UpdateVersionStorageManager.getCurrentUpdateVersion(context);

        for (int i = 0; i < itemUpdates.length(); i++) {
            final JSONObject jsonObject = itemUpdates.getJSONObject(i);
            String id = jsonObject.getString("id");

            int currentUpdateVersion = UpdateVersionStorageManager.getCurrentUpdateVersion(context);

            if (currentUpdateVersion >= Integer.parseInt(id)) {
                continue;
            }

            String update_type = jsonObject.getString("update_type");
            if (update_type.equals("update") || update_type.equals("create")) {
                updateOrCreate(jsonObject);
            }
            if (update_type.equals("delete")) {
                delete(jsonObject);
            }

            UpdateVersionStorageManager.setCurrentUpdateVersion(context, Integer.parseInt(id));

        }

        int updateVersionPostUpdate = UpdateVersionStorageManager.getCurrentUpdateVersion(context);

        updateUIIfNecessary(updateVersionPreUpdate, updateVersionPostUpdate);
    }

    public void updateUIIfNecessary(int updateVersionPreUpdate, int updateVersionPostUpdate) {
        if (itemListActivity != null && updateVersionPostUpdate > updateVersionPreUpdate)
            itemListActivity.inflateMainMenuItems();
    }

    private void delete(JSONObject jsonObject) throws JSONException {
        String jsonItemString = jsonObject.getString("item_json");

        JSONObject itemUpdate = new JSONObject(jsonItemString);
        String id = itemUpdate.getString("id");

        ItemStorageManager.deleteItemById(context, id);
        CartStorageManager.removeItemFromCart(context, id);

    }

    public void updateOrCreate(JSONObject jsonObject) throws JSONException {
        String jsonItemString = jsonObject.getString("item_json");

        JSONObject itemUpdate = new JSONObject(jsonItemString);
        itemUpdate.put("image_local_path", "<empty>");

        ItemStorageManager.storeItem(context, itemUpdate);
    }
}
