package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import knockknock.delivr_it.knocknock.models.AboutUs;

public class AboutUsStorageManager {
    public static int getCurrentImageVersion(Context context) {
        Realm realm = Realm.getInstance(context);
        RealmResults<AboutUs> aboutUsObjects = realm.where(AboutUs.class).findAll();

        if (aboutUsObjects.size() == 0)
            return 0;
        else
            return aboutUsObjects.get(0).getAboutUsImageVersion();
    }

    public static void setCurrentImageVersion(Context context, int newImageVersion, String imageLocation) {
        Realm realm = Realm.getInstance(context);
        RealmResults<AboutUs> aboutUsObjects = realm.where(AboutUs.class).findAll();

        realm.beginTransaction();

        aboutUsObjects.clear();

        AboutUs newAboutUsObject = new AboutUs();
        newAboutUsObject.setAboutUsImageVersion(newImageVersion);
        newAboutUsObject.setLocalPath(imageLocation);

        realm.copyToRealm(newAboutUsObject);

        realm.commitTransaction();
    }

    public static String getAboutUsImage(Context context) {
        Realm realm = Realm.getInstance(context);
        RealmResults<AboutUs> aboutUsObjects = realm.where(AboutUs.class).findAll();
        if (aboutUsObjects.size() == 0)
            return null;
        return aboutUsObjects.first().getLocalPath();
    }
}
