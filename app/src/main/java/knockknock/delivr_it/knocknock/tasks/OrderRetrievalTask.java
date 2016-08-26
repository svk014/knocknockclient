package knockknock.delivr_it.knocknock.tasks;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import knockknock.delivr_it.knocknock.activities.PlacedOrdersActivity;
import knockknock.delivr_it.knocknock.managers.OrderStorageManager;
import knockknock.delivr_it.knocknock.managers.UserInformationStorageManager;
import knockknock.delivr_it.knocknock.models.UserInformation;

public class OrderRetrievalTask extends AsyncTask<Void, Void, JSONArray> {

    PlacedOrdersActivity placedOrdersActivity;

    public OrderRetrievalTask(PlacedOrdersActivity placedOrdersActivity) {
        this.placedOrdersActivity = placedOrdersActivity;
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://knock-knock-server-0.herokuapp.com/order/getOrdersForPhone");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            UserInformation userInformation = UserInformationStorageManager.getUserInformation(placedOrdersActivity);
            nameValuePairs.add(new BasicNameValuePair("phone", userInformation.getPrimaryPhoneNumber()));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
            StringBuilder everything = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                everything.append(line);
            }
            return new JSONArray(everything.toString());
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                OrderStorageManager.storeOrder(placedOrdersActivity, jsonObject);
            }
            placedOrdersActivity.refreshList();
        } catch (Exception ignored) {
        }
    }
}
