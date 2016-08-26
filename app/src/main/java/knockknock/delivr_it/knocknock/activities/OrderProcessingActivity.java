package knockknock.delivr_it.knocknock.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.managers.ItemStorageManager;
import knockknock.delivr_it.knocknock.managers.OrderStorageManager;
import knockknock.delivr_it.knocknock.models.Order;

public class OrderProcessingActivity extends AppCompatActivity {
    private Order order;
    private TextView orderDetails;
    private TextView deliveryDetails;
    private TextView itemNames;
    private TextView itemPrices;
    private TextView currentOrderStatus;
    private TextView total;
    private TextView charges;
    private TextView estimatedDelivery;


    HashMap<String, String> nextStatusMapping = new HashMap<>();
    HashMap<String, String> statusButtonMapping = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expanded_order);
        String orderId = getIntent().getExtras().getString("order_id");

        order = OrderStorageManager.getOrderForId(getBaseContext(), orderId);

        init();
        showOrderDetails();
    }

    private void init() {
        orderDetails = (TextView) findViewById(R.id.order_details);
        deliveryDetails = (TextView) findViewById(R.id.delivery_details);
        itemNames = (TextView) findViewById(R.id.item_names);
        itemPrices = (TextView) findViewById(R.id.item_prices);
        currentOrderStatus = (TextView) findViewById(R.id.order_status);
        charges = (TextView) findViewById(R.id.charges);
        total = (TextView) findViewById(R.id.total);
        estimatedDelivery = (TextView) findViewById(R.id.estimated_delivery);

    }

    private void showOrderDetails() {
        try {
            orderDetails.setText(buildOrderDetails());
            deliveryDetails.setText(buildDeliveryDetails());
            itemNames.setText(buildItemNameDetails());
            itemPrices.setText(buildItemPriceQuantityDetails());
            currentOrderStatus.setText(order.getOrder_status());
            total.setText("Rs. " + calculateTotalWithCharges("0"));
            int totalCharges = Integer.parseInt(order.getTotal()) - calculateTotalWithCharges("0");
            charges.setText(totalCharges + "");
            estimatedDelivery.setText(order.getEstimated_delivery());

        } catch (Exception ignored) {
        }
    }

    private String buildItemPriceQuantityDetails() throws Exception {
        String s = "";

        JSONArray jsonArray = new JSONArray(order.getOrders());
        for (int keyIndex = 0; keyIndex < jsonArray.length(); keyIndex++) {
            JSONObject jsonObject = jsonArray.getJSONObject(keyIndex);
            s += jsonObject.getString("quantity") + " * " + jsonObject.getString("price_per_unit") + "\n";
        }

        return s;
    }

    private String buildDeliveryDetails() throws Exception {
        String s = "";

        JSONObject userDetails = new JSONObject(order.getUser_information());
        s += userDetails.getString("userName") + "\n";
        s += userDetails.getString("address") + "\n";
        s += userDetails.getString("primaryPhoneNumber") + "\t\t\t";
        s += userDetails.getString("secondaryPhoneNumber") + "\n";
        s += userDetails.getString("email") + "\n";

        return s;
    }

    private String buildOrderDetails() throws Exception {
        String s = "Order # " + order.getOrder_id() + "\n";

        JSONObject deliveryDetails = new JSONObject(order.getDelivery_information());
        s += deliveryDetails.getString("deliveryType") + "\n";
        if (!deliveryDetails.getString("deliveryDay").equals("not applicable"))
            s += deliveryDetails.getString("deliveryDay") + "\n";
        if (!deliveryDetails.getString("deliveryDay").equals("not applicable"))
            s += deliveryDetails.getString("deliveryTime") + "\n";

        return s;
    }

    private String buildItemNameDetails() throws Exception {
        String s = "";

        JSONArray jsonArray = new JSONArray(order.getOrders());
        for (int keyIndex = 0; keyIndex < jsonArray.length(); keyIndex++) {
            JSONObject jsonObject = jsonArray.getJSONObject(keyIndex);
            String itemName = ItemStorageManager.getNameForId(getBaseContext(), jsonObject.getString("item_id"));
            s += itemName + "\n";
        }

        return s;
    }

    private int calculateTotalWithCharges(String charges) throws Exception {
        charges = charges.equals("") ? "0" : charges;
        int total = 0;
        JSONArray jsonArray = new JSONArray(order.getOrders());
        for (int keyIndex = 0; keyIndex < jsonArray.length(); keyIndex++) {
            JSONObject jsonObject = jsonArray.getJSONObject(keyIndex);
            total += Integer.parseInt(jsonObject.getString("quantity")) * Integer.parseInt(jsonObject.getString("price_per_unit"));
        }
        return total + Integer.parseInt(charges);
    }


}
