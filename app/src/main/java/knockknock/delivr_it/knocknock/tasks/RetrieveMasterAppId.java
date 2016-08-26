package knockknock.delivr_it.knocknock.tasks;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import knockknock.delivr_it.knocknock.activities.KnocKnock;
import knockknock.delivr_it.knocknock.activities.SplashActivity;

public class RetrieveMasterAppId extends AsyncTask<Void, Void, String> {

    private SplashActivity splashActivity;

    public RetrieveMasterAppId(SplashActivity splashActivity) {
        this.splashActivity = splashActivity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://knock-knock-server-0.herokuapp.com/order/masterAppId");

            HttpResponse response = httpclient.execute(httpGet);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
            StringBuilder everything = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                everything.append(line);
            }
            return everything.toString();
        } catch (Exception ignored) {
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        KnocKnock.masterAppId = result;
        splashActivity.startNextActivity();
    }
}
