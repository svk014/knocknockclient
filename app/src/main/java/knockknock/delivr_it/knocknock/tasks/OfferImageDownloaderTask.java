package knockknock.delivr_it.knocknock.tasks;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import knockknock.delivr_it.knocknock.OfferStorageManager;
import knockknock.delivr_it.knocknock.activities.MainActivity;

public class OfferImageDownloaderTask {
    private MainActivity mainActivity;

    public OfferImageDownloaderTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void downloadAndStoreImages(JSONArray offers) throws JSONException {
        for (int i = 0; i < offers.length(); i++) {
            final JSONObject jsonObject = offers.getJSONObject(i);
            String image_url = jsonObject.getString("image_url");
            String id = jsonObject.getString("id");

            boolean offerExists = OfferStorageManager.offerExists(mainActivity, id);

            if (offerExists)
                continue;

            Picasso.with(mainActivity).load(image_url).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    try {
                        String image_local_path = storeImageInFile(mainActivity, "offers", System.currentTimeMillis() + "", source);
                        jsonObject.put("image_local_path", image_local_path);
                    } catch (JSONException e) {
                    }
                    saveOfferToDatabase(jsonObject);
                    return source;
                }

                @Override
                public String key() {
                    return null;
                }
            }).fetch();
            mainActivity.displayDailyOffersOnSliderLayout();

        }

    }

    public void saveOfferToDatabase(JSONObject jsonObject) {

        OfferStorageManager.storeOffer(mainActivity, jsonObject);

    }

    private String storeImageInFile(Context context, final String imageDir, final String imageName, Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE);
        final File myImageFile = new File(directory, imageName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myImageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return myImageFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
