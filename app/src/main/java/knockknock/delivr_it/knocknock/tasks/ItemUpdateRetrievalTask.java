package knockknock.delivr_it.knocknock.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ItemUpdateRetrievalTask extends AsyncTask<Void, Void, JSONArray> {

    private Context context;

    public ItemUpdateRetrievalTask(Context context) {
        this.context = context;
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet("http://knock-knock-server-0.herokuapp.com/itemUpdate/all");

            HttpResponse response = httpclient.execute(httppost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");
            String result11 = sb.toString();
            return new JSONArray(result11);
        } catch (Exception ignored) {
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONArray itemUpdates) {
        try {
            new ItemUpdateWebToStorageTask(context).updateItems(itemUpdates);
            Toast.makeText(context, "Done getting updates", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Could not get updates", Toast.LENGTH_SHORT).show();
        }
    }
}
