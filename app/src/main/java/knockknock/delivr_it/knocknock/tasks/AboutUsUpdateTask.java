package knockknock.delivr_it.knocknock.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import knockknock.delivr_it.knocknock.managers.AboutUsStorageManager;
import knockknock.delivr_it.knocknock.managers.FileStorageManager;

public class AboutUsUpdateTask extends AsyncTask<Void, Void, Integer> {

    Context context;

    public AboutUsUpdateTask(Context context) {
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("https://raw.githubusercontent.com/svk014/knocknockrawassets/master/about-us/image-version.txt");

            HttpResponse response = httpclient.execute(httpGet);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
            String result11 = reader.readLine();
            return Integer.parseInt(result11);
        } catch (Exception ignored) {

        }

        return 0;
    }

    @Override
    protected void onPostExecute(Integer imageVersion) {
        if (imageVersion > AboutUsStorageManager.getCurrentImageVersion(context)) {
            storeImage(imageVersion);
        }
    }

    private void storeImage(final int imageVersion) {
        Picasso.with(context).load("https://raw.githubusercontent.com/svk014/knocknockrawassets/master/about-us/about_us_page.png")
                .transform(new Transformation() {
                               @Override
                               public Bitmap transform(Bitmap source) {
                                   String image_local_path = FileStorageManager.storeImageInFileSystem(context, "aboutUs", System.currentTimeMillis() + "", source);
                                   AboutUsStorageManager.setCurrentImageVersion(context, imageVersion, image_local_path);
                                   return source;
                               }

                               @Override
                               public String key() {
                                   return null;
                               }
                           }

                ).

                fetch();
    }
}
