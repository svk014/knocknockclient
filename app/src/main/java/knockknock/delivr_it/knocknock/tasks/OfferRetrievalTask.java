package knockknock.delivr_it.knocknock.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import knockknock.delivr_it.knocknock.activities.MainActivity;

public class OfferRetrievalTask extends AsyncTask<Void, Void, JSONArray> {

    private MainActivity context;

    public OfferRetrievalTask(MainActivity context) {
        this.context = context;
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet("https://knock-knock-server-0.herokuapp.com/offer/all");

            HttpResponse response = httpclient.execute(httppost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");
            String result11 = sb.toString();
            return new JSONArray(result11);
        } catch (Exception e) {

        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONArray offers) {
        Toast.makeText(context, "Done getting offers", Toast.LENGTH_SHORT).show();
        try {
            new OfferImageDownloaderTask(context).downloadAndStoreImages(offers);
        } catch (JSONException e) {
        }
    }
}
