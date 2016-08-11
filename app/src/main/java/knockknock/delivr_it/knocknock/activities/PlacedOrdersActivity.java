package knockknock.delivr_it.knocknock.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.adapters.PlacedOrderListAdapter;
import knockknock.delivr_it.knocknock.managers.OrderStorageManager;

public class PlacedOrdersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placed_orders);
        displayPlacedOrders();
    }

    private void displayPlacedOrders() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.placed_orders_list);
        PlacedOrderListAdapter placedOrderListAdapter = new PlacedOrderListAdapter(OrderStorageManager.getAllOrders(getBaseContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(placedOrderListAdapter);
    }
}
