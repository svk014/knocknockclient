package knockknock.delivr_it.knocknock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.adapters.PlacedOrderListAdapter;
import knockknock.delivr_it.knocknock.managers.OrderStorageManager;
import knockknock.delivr_it.knocknock.tasks.OrderRetrievalTask;

public class PlacedOrdersActivity extends AppCompatActivity {
    private PlacedOrderListAdapter placedOrderListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placed_orders);
        displayPlacedOrders();
        new OrderRetrievalTask(this).execute();
    }

    private void displayPlacedOrders() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.placed_orders_list);
        placedOrderListAdapter = new PlacedOrderListAdapter(OrderStorageManager.getAllOrders(getBaseContext()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(placedOrderListAdapter);
    }

    public void refreshList() {
        placedOrderListAdapter.notifyDataSetChanged();
    }

    public void showOrderDetails(View view) {
        Intent intent = new Intent(this, OrderProcessingActivity.class);
        String orderId = view.getTag().toString();
        intent.putExtra("order_id", orderId);
        startActivity(intent);
        finish();
    }
}
