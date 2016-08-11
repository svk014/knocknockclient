package knockknock.delivr_it.knocknock.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import knockknock.delivr_it.knocknock.R;

public class ProfileViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adress_information_layout);
    }

    public void showOrders(View view) {
        Intent intent = new Intent(ProfileViewActivity.this, PlacedOrdersActivity.class);
        startActivity(intent);
        finish();
    }
}
