package knockknock.delivr_it.knocknock.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import knockknock.delivr_it.knocknock.managers.OrderStorageManager;

public class OrderTask {

    private Context context;

    public OrderTask(Context context) {
        this.context = context;
    }

    public void placeOrder(JSONObject orderJson) {
        new OrderAsyncTask(context, orderJson.toString()).execute();
    }

    public class OrderAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private String orderJson;

        OrderAsyncTask(Context context, String orderJson) {
            this.context = context;
            this.orderJson = orderJson;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://10.133.127.65:5000/order/newOrder");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("order", orderJson));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httpPost);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
                StringBuilder everything = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    everything.append(line);
                }
                return everything.toString();
            } catch (Exception ignored) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (!response.equals("order previously placed")) {

                try {
                    JSONObject placedOrder = new JSONObject(response);
                    OrderStorageManager.storeOrder(context, placedOrder);
                } catch (JSONException ignored) {
                }
            }
        }
    }
}
