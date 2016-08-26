package knockknock.delivr_it.knocknock.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.adapters.NotificationListAdapter;
import knockknock.delivr_it.knocknock.managers.NotificationStorageManager;

public class NotificationDisplayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_list);
        setupNotifications();
    }

    private void setupNotifications() {
        RecyclerView notificationsList = (RecyclerView) findViewById(R.id.notifications_list);

        NotificationListAdapter notificationListAdapter = new NotificationListAdapter(NotificationStorageManager.getAllNotifications(getBaseContext()));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        notificationsList.setLayoutManager(mLayoutManager);
        notificationsList.setItemAnimator(new DefaultItemAnimator());
        notificationsList.setAdapter(notificationListAdapter);
    }


}
