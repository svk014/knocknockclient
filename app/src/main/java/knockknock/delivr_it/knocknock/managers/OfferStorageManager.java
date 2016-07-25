package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import knockknock.delivr_it.knocknock.models.Offer;

public class OfferStorageManager {

    public static void storeOffer(Context context, JSONObject offerJSON) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        Offer offer = realm.createObjectFromJson(Offer.class, offerJSON);
        realm.commitTransaction();
    }

    public static boolean offerExists(Context context, String id) {
        Realm realm = Realm.getInstance(context);
        RealmResults<Offer> results = realm.where(Offer.class).equalTo("id", id).findAll();
        return results.size() != 0;
    }

    public static List<Offer> retrieveStoredOffers(Context context) {
        Realm realm = Realm.getInstance(context);
        return realm.where(Offer.class).findAll();
    }

    public static boolean deleteOffers(Context context, JSONArray offers) throws Exception {
        Realm realm = Realm.getInstance(context);
        int i = 0;
        RealmQuery<Offer> where = realm.where(Offer.class);
        for (; i < offers.length() - 1; i++) {
            where.notEqualTo("id", offers.getJSONObject(i).getString("id")).or();
        }
        RealmResults<Offer> itemsToDelete = where.notEqualTo("id", offers.getJSONObject(i).getString("id")).findAll();

        realm.beginTransaction();
        itemsToDelete.clear();
        realm.commitTransaction();

        return true;
    }
}
