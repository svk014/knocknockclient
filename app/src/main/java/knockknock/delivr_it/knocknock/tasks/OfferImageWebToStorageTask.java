package knockknock.delivr_it.knocknock.tasks;

import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import knockknock.delivr_it.knocknock.managers.OfferStorageManager;
import knockknock.delivr_it.knocknock.activities.MainActivity;
import knockknock.delivr_it.knocknock.managers.FileStorageManager;

public class OfferImageWebToStorageTask {
    private MainActivity mainActivity;

    public OfferImageWebToStorageTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private int offers_processed = 0;

    public void downloadAndStoreImages(final JSONArray offers) throws Exception {
        for (int i = 0; i < offers.length(); i++) {
            final JSONObject jsonObject = offers.getJSONObject(i);
            String image_url = jsonObject.getString("image_url");
            String id = jsonObject.getString("id");

            boolean offerExists = OfferStorageManager.offerExists(mainActivity, id);

            if (offerExists) {
                offers_processed++;
                continue;
            }

            Picasso.with(mainActivity).load(image_url).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    try {
                        String image_local_path = FileStorageManager.storeImageInFileSystem(mainActivity, "offers", System.currentTimeMillis() + "", source);
                        jsonObject.put("image_local_path", image_local_path);
                    } catch (JSONException ignored) {
                    }
                    saveOfferToDatabase(jsonObject);
                    offers_processed++;
                    if (offers_processed == offers.length())
                        mainActivity.refreshActivity();

                    return source;
                }

                @Override
                public String key() {
                    return null;
                }
            }).fetch();

        }

    }

    public void saveOfferToDatabase(JSONObject jsonObject) {

        OfferStorageManager.storeOffer(mainActivity, jsonObject);

    }

}
