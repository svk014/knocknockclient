package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;
import knockknock.delivr_it.knocknock.models.Item;

public class ItemStorageManager {

    public static void storeItem(Context context, JSONObject itemJSON) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        Item item = realm.createObjectFromJson(Item.class, itemJSON);
        realm.commitTransaction();
    }

    public static boolean confirmItemsStored(Context context, int count) {
        Realm realm = Realm.getInstance(context);
        RealmResults<Item> allItems = realm.where(Item.class).findAll();
        return allItems.size() == count;
    }
}
