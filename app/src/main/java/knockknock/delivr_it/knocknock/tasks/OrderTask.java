package knockknock.delivr_it.knocknock.tasks;

import android.app.Activity;
import android.os.AsyncTask;

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

import knockknock.delivr_it.knocknock.activities.OrderViewActivity;
import knockknock.delivr_it.knocknock.managers.OrderStorageManager;

public class OrderTask {

    private Activity activity;

    public OrderTask(Activity activity) {
        this.activity = activity;
    }

    public void placeOrder(JSONObject orderJson) {
        new OrderAsyncTask(activity, orderJson.toString()).execute();
    }

    public class OrderAsyncTask extends AsyncTask<Void, Void, String> {

        private Activity activity;
        private String orderJson;

        OrderAsyncTask(Activity activity, String orderJson) {
            this.activity = activity;
            this.orderJson = orderJson;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://knock-knock-server-0.herokuapp.com/order/newOrder");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("order", orderJson));

                showLoadingAnimation();

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

        private void showLoadingAnimation() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((OrderViewActivity) activity).startLoadSpinner();
                }
            });
        }

        @Override
        protected void onPostExecute(String response) {
            if (response == null || response.equals("order previously placed")) {
                ((OrderViewActivity) activity).stopLoadSpinner();
                ((OrderViewActivity) activity).toastFailedToPlaceOrder();
            } else {

                try {
                    JSONObject placedOrder = new JSONObject(response);
                    OrderStorageManager.storeOrder(activity, placedOrder);
                    ((OrderViewActivity) activity).sendNotification("Order #" + placedOrder.getString("order_id"));
                    activity.finish();
                } catch (JSONException ignored) {
                }
            }
        }
    }
}
