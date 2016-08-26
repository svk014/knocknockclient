package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import knockknock.delivr_it.knocknock.models.Item;

public class ItemStorageManager {

    public static void storeItem(Context context, JSONObject itemJSON) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        Item item = realm.createOrUpdateObjectFromJson(Item.class, itemJSON);

        realm.commitTransaction();
    }

    public static void updateItemLocalImagePath(Context context, String itemId, String image_local_path) {
        Realm realm = Realm.getInstance(context);
        Item itemToBeUpdated = realm.where(Item.class).equalTo("id", itemId).findAll().first();
        realm.beginTransaction();
        itemToBeUpdated.setImage_local_path(image_local_path);
        realm.commitTransaction();
    }

    public static boolean confirmItemsStored(Context context, int count) {
        Realm realm = Realm.getInstance(context);
        RealmResults<Item> allItems = realm.where(Item.class).findAll();
        return allItems.size() == count;
    }

    public static List<Item> getAllItemsForCategory(Context context, String category, List<String> sections, String vegOnly, String inStockOnly) {
        Realm realm = Realm.getInstance(context);
        RealmQuery<Item> itemsQuery = realm.where(Item.class).equalTo("category", category).beginGroup();

        for (String section : sections) {
            itemsQuery.equalTo("section", section).or();
        }

        return itemsQuery.isEmpty("id").endGroup().beginGroup().equalTo("vegetarian", vegOnly)
                .or().equalTo("vegetarian", "na").or().equalTo("vegetarian", "true").endGroup()
                .beginGroup().equalTo("in_stock", inStockOnly).or().equalTo("in_stock", "true")
                .endGroup().findAll();
    }

    public static List<String> getAllSectionsForCategory(Context context, String category) {
        Realm realm = Realm.getInstance(context);
        RealmResults<Item> allItems = realm.where(Item.class).equalTo("category", category).findAll();

        HashSet<String> sections = new HashSet<>();
        for (Item item : allItems) {
            sections.add(item.getSection());
        }
        return new ArrayList<>(sections);
    }

    public static String getPriceForId(Context context, String itemId) {
        Realm realm = Realm.getInstance(context);

        return realm.where(Item.class).equalTo("id", itemId).findAll().first().getPrice();
    }

    public static List<Item> getAllItemsFor(Context context, String input) {
        Realm realm = Realm.getInstance(context);
        return realm.where(Item.class)
                .contains("item_name", input, false)
                .or().contains("section", input, false)
                .or().contains("category", input, false)
                .findAll();
    }

    public static String getNameForId(Context context, String itemId) {
        Realm realm = Realm.getInstance(context);
        return realm.where(Item.class).equalTo("id", itemId).findAll().first().getItem_name();
    }

    public static String getImageForId(Context context, String itemId) {
        Realm realm = Realm.getInstance(context);
        return realm.where(Item.class).equalTo("id", itemId).findFirst().getImage_local_path();
    }

    public static void deleteItemById(Context context, String itemId) {
        Realm realm = Realm.getInstance(context);
        RealmResults<Item> itemsToDelete = realm.where(Item.class).equalTo("id", itemId).findAll();

        realm.beginTransaction();
        itemsToDelete.clear();
        realm.commitTransaction();
    }

    public static String getStockStatusForId(Context context, String itemId) {
        Realm realm = Realm.getInstance(context);
        Item id = realm.where(Item.class).equalTo("id", itemId).findFirst();
        if (id == null)
            return "false";
        return id.getIn_stock();
    }
}
