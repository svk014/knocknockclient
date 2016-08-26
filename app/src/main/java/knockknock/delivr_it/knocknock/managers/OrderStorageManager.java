package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
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
        return realm.where(Order.class).findAllSorted("order_id", false);
    }


    public static boolean orderExistsForClientOrderIdentifier(Context context, long orderIdentifierClient) {
        Realm realm = Realm.getInstance(context);
        RealmResults<Order> orders = realm.where(Order.class).equalTo("order_identifier_client", orderIdentifierClient + "").findAll();
        return orders.size() != 0;
    }

    public static Order getOrderForId(Context context, String orderId) {
        Realm realm = Realm.getInstance(context);
        return realm.where(Order.class).equalTo("order_id", orderId).findAll().first();
    }
}