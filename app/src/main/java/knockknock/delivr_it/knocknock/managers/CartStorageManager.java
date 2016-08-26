package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import knockknock.delivr_it.knocknock.adapters.CartItemAdapter;
import knockknock.delivr_it.knocknock.models.CartItem;

public class CartStorageManager {

    public static void storeItemInCart(Context context, String id, int quantity) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        CartItem cartItem = new CartItem();
        cartItem.setItemId(id);
        cartItem.setQuantity(quantity);
        cartItem.setOrderIdentifier(System.currentTimeMillis());
        realm.copyToRealmOrUpdate(cartItem);

        realm.commitTransaction();
    }

    public static void removeItemFromCart(Context context, String id) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        RealmResults<CartItem> cartItemToDelete = realm.where(CartItem.class).equalTo("itemId", id).findAll();
        cartItemToDelete.clear();

        realm.commitTransaction();
    }

    public static int getItemsInCartCountForId(Context context, String id) {
        Realm realm = Realm.getInstance(context);

        RealmResults<CartItem> cartItem = realm.where(CartItem.class).equalTo("itemId", id).findAll();
        return cartItem.size() == 0 ? 0 : cartItem.first().getQuantity();
    }

    public static List<CartItem> getItemsInCart(Context context) {
        Realm realm = Realm.getInstance(context);
        cleanRedundantOrders(context);
        return realm.where(CartItem.class).findAll();
    }

    private static void cleanRedundantOrders(Context context) {
        Realm realm = Realm.getInstance(context);
        long orderIdentifierClient = getOrderIdentifier(context);
        boolean orderAleadyExists = OrderStorageManager.orderExistsForClientOrderIdentifier(
                context,
                orderIdentifierClient);
        if (orderAleadyExists) {
            removeAllFromCart(context);
        }
    }

    private static void removeAllFromCart(Context context) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();

        RealmResults<CartItem> cartItemToDelete = realm.where(CartItem.class).findAll();
        cartItemToDelete.clear();

        realm.commitTransaction();
    }

    public static long getOrderIdentifier(Context context) {
        Realm realm = Realm.getInstance(context);
        Number orderIdentifier = realm.where(CartItem.class).max("orderIdentifier");
        if (orderIdentifier == null)
            return 0;
        return orderIdentifier.longValue();
    }
}
