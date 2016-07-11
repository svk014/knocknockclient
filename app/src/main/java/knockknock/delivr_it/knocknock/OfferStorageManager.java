package knockknock.delivr_it.knocknock;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
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
}
