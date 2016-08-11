package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import knockknock.delivr_it.knocknock.models.Order;

public class OrderStorageManager {
    public static void storeOrder(Context context, JSONObject placedOrder) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();
        Order order = realm.createOrUpdateObjectFromJson(Order.class, placedOrder);
        realm.commitTransaction();
    }

    public static List<Order> getAllOrders(Context context) {
        Realm realm = Realm.getInstance(context);
        return realm.where(Order.class).findAll();
    }
}