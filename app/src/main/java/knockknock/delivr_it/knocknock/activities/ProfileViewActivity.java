package knockknock.delivr_it.knocknock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.nispok.snackbar.Snackbar;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.managers.UserInformationStorageManager;
import knockknock.delivr_it.knocknock.models.UserInformation;
import knockknock.delivr_it.knocknock.models.UserInformationDTO;

public class ProfileViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adress_information_layout);
        loadUserInformationToUI();
    }

    public void showOrders(View view) {
        Intent intent = new Intent(ProfileViewActivity.this, PlacedOrdersActivity.class);
        startActivity(intent);
        finish();
    }


    private void loadUserInformationToUI() {
        UserInformation userInformation = UserInformationStorageManager.getUserInformation(getBaseContext());
        if (userInformation == null)
            return;
        ((EditText) findViewById(R.id.name)).setText(userInformation.getUserName());
        ((EditText) findViewById(R.id.phone_number)).setText(userInformation.getPrimaryPhoneNumber());
        ((EditText) findViewById(R.id.alternate_phone_number)).setText(userInformation.getSecondaryPhoneNumber());
        ((EditText) findViewById(R.id.address)).setText(userInformation.getAddress());
        ((EditText) findViewById(R.id.email)).setText(userInformation.getEmail());
    }

    private UserInformationDTO getUserDetailsFromUI() {
        UserInformationDTO userInformation = new UserInformationDTO();

        userInformation.userName = ((EditText) findViewById(R.id.name)).getText().toString();
        userInformation.primaryPhoneNumber = ((EditText) findViewById(R.id.phone_number)).getText().toString();
        userInformation.secondaryPhoneNumber = ((EditText) findViewById(R.id.alternate_phone_number)).getText().toString();
        userInformation.address = ((EditText) findViewById(R.id.address)).getText().toString();
        userInformation.email = ((EditText) findViewById(R.id.email)).getText().toString();

        return userInformation;
    }

    public void saveDeliveryInformation(View view) {
        UserInformationDTO userInformationDTO = getUserDetailsFromUI();
        if (userInformationDTO.areFieldsValid())
            UserInformationStorageManager.setUserInformation(getBaseContext(), userInformationDTO);
        else
            Snackbar.with(getBaseContext()).text("Incomplete information.").actionLabel("OK").show(this);
    }
}
