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
        return realm.where(CartItem.class).findAll();
    }

    public static long getOrderIdentifier(Context context) {
        Realm realm = Realm.getInstance(context);
        return realm.where(CartItem.class).max("orderIdentifier").longValue();
    }
}
