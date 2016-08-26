package knockknock.delivr_it.knocknock.tasks;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import knockknock.delivr_it.knocknock.activities.RegistrationActivity;

public class VerifyCodeTask extends AsyncTask<Void, Void, String> {
    RegistrationActivity registrationActivity;
    private final String phoneNumber;
    private final String verificationCode;

    public VerifyCodeTask(RegistrationActivity registrationActivity, String phoneNumber, String verificationCode) {
        this.registrationActivity = registrationActivity;
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://knock-knock-server-0.herokuapp.com/order/verifyPhoneNumber");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("phoneNumber", phoneNumber));
            nameValuePairs.add(new BasicNameValuePair("verificationCode", verificationCode));

            registrationActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    registrationActivity.showVerificationLoadingAnimation();
                }
            });

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
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("success")) {
            registrationActivity.savePhoneNumberAndProceed(phoneNumber);
        } else {
            registrationActivity.invalidateUi();
        }
    }
}
