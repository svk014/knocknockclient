package knockknock.delivr_it.knocknock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.nispok.snackbar.Snackbar;
import com.onesignal.OneSignal;

import net.cachapa.expandablelayout.ExpandableLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.managers.UserInformationStorageManager;
import knockknock.delivr_it.knocknock.models.UserInformation;
import knockknock.delivr_it.knocknock.models.UserInformationDTO;
import knockknock.delivr_it.knocknock.tasks.VerifyCodeTask;

public class RegistrationActivity extends AppCompatActivity {

    ExpandableLinearLayout expandableLinearLayout;
    private String phoneNumber;
    private EditText verificationCodeEditText;
    private EditText registrationPhoneNumberEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        init();
    }

    private void init() {
        expandableLinearLayout = (ExpandableLinearLayout) findViewById(R.id.verification_container);
        verificationCodeEditText = (EditText) findViewById(R.id.verification_code);
        registrationPhoneNumberEditText = (EditText) findViewById(R.id.registration_phone_number);

        expandableLinearLayout.collapse();
        registrationPhoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                expandableLinearLayout.collapse();
                phoneNumber = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void requestVerificationCode(View view) {
        String phoneNumber = registrationPhoneNumberEditText.getText().toString();
        if (phoneNumber.length() < 10) {
            Snackbar.with(getBaseContext()).text("Phone number must be 10 digits long.").actionLabel("OK").show(this);
            return;
        }
        requestVerificationCodeFromKnocKnock(phoneNumber);
    }

    private void requestVerificationCodeFromKnocKnock(String phoneNumber) {
        findViewById(R.id.spin_kit_request).setVisibility(View.VISIBLE);
        try {
            JSONObject oneSignalDeliveryObject = new JSONObject("{'contents': {'en':'" + phoneNumber + "'}, 'include_player_ids': ['"
                    + KnocKnock.masterAppId
                    + "'], 'headings': {'en':'Verify Number'}}");
            OneSignal.postNotification(oneSignalDeliveryObject, getNotificationPostHandler());
        } catch (JSONException e) {
            findViewById(R.id.spin_kit_request).setVisibility(View.GONE);
        }
    }


    public void verifyCode(View view) {
        String verificationCode = verificationCodeEditText.getText().toString();
        if (verificationCode.length() < 6) {
            Snackbar.with(getBaseContext()).text("Verification code must be 6 digits long.").actionLabel("OK").show(this);
            return;
        }
        new VerifyCodeTask(this, phoneNumber, verificationCode).execute();
    }

    public void savePhoneNumberAndProceed(String phoneNumber) {
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.primaryPhoneNumber = phoneNumber;
        UserInformationStorageManager.setUserInformation(getBaseContext(), userInformationDTO);
        startMainApp();
    }

    private void startMainApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void invalidateUi() {
        findViewById(R.id.spin_kit_verify).setVisibility(View.GONE);
    }

    @NonNull
    public OneSignal.PostNotificationResponseHandler getNotificationPostHandler() {
        return new OneSignal.PostNotificationResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.spin_kit_request).setVisibility(View.GONE);
                        expandableLinearLayout.expand();
                        Snackbar.with(getBaseContext()).text("Please wait an SMS containing a 6 digit verification code.")
                                .show(RegistrationActivity.this);
                    }
                });
            }

            @Override
            public void onFailure(JSONObject response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.with(getBaseContext()).text("Please try again.").actionLabel("OK")
                                .show(RegistrationActivity.this);
                        findViewById(R.id.spin_kit_request).setVisibility(View.GONE);
                    }
                });
            }
        };
    }

    public void showVerificationLoadingAnimation() {
        findViewById(R.id.spin_kit_verify).setVisibility(View.VISIBLE);
    }
}
