package knockknock.delivr_it.knocknock.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.cachapa.expandablelayout.ExpandableLinearLayout;

import org.json.JSONObject;

import java.util.HashMap;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.adapters.CartItemAdapter;
import knockknock.delivr_it.knocknock.managers.CartStorageManager;
import knockknock.delivr_it.knocknock.models.DeliveryModeInformation;
import knockknock.delivr_it.knocknock.models.UserInformationDTO;
import knockknock.delivr_it.knocknock.tasks.BuildOrderTask;
import knockknock.delivr_it.knocknock.tasks.OrderTask;
import knockknock.delivr_it.knocknock.views.OrderTypeViewManager;

public class OrderViewActivity extends AppCompatActivity {
    ExpandableLinearLayout expandableLinearLayout;
    TextView buttonTextView;
    HashMap<String, ToggleButton> toggleButtonHashMap;
    HashMap<String, TextView> textViewHashMap;
    HashMap<String, Integer> layoutHashMap;
    CartItemAdapter cartItemAdapter;
    View inflatedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        init();
        addCartItems();
    }

    private void init() {
        expandableLinearLayout = (ExpandableLinearLayout) findViewById(R.id.expandable_container);
        buttonTextView = (TextView) findViewById(R.id.button_text);
    }

    public void inflateOrderProcessingView(int viewType) {
        if (viewType == 0) {
            addNoOrderView();
        } else {
            addConfirmOrderView();
        }
    }

    private void addNoOrderView() {
        addViewToParent(R.layout.order_layout_no_order, R.id.expandable_section);
    }


    private void addCartItems() {
        cartItemAdapter = new CartItemAdapter(CartStorageManager.getItemsInCart(getBaseContext()));

        RecyclerView cartItemsContainer = (RecyclerView) findViewById(R.id.cart_items);

        cartItemsContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cartItemsContainer.setItemAnimator(new DefaultItemAnimator());
        cartItemsContainer.setAdapter(cartItemAdapter);
    }

    private void initConfirmOrderView() {

        ToggleButton standardDelivery = (ToggleButton) inflatedView.findViewById(R.id.standard_delivery_toggle_button);
        ToggleButton expressDelivery = (ToggleButton) inflatedView.findViewById(R.id.express_delivery_toggle_button);
        ToggleButton delayedDelivery = (ToggleButton) inflatedView.findViewById(R.id.delayed_delivery_toggle_button);

        TextView standardDeliveryText = (TextView) inflatedView.findViewById(R.id.standard_delivery_text);
        TextView expressDeliveryText = (TextView) inflatedView.findViewById(R.id.express_delivery_text);
        TextView delayedDeliveryText = (TextView) inflatedView.findViewById(R.id.delayed_delivery_text);


        toggleButtonHashMap = new HashMap<>();
        toggleButtonHashMap.put("standard", standardDelivery);
        toggleButtonHashMap.put("express", expressDelivery);
        toggleButtonHashMap.put("delayed", delayedDelivery);

        textViewHashMap = new HashMap<>();
        textViewHashMap.put("standard", standardDeliveryText);
        textViewHashMap.put("express", expressDeliveryText);
        textViewHashMap.put("delayed", delayedDeliveryText);

        layoutHashMap = new HashMap<>();
        layoutHashMap.put("standard", R.layout.order_layout_standard_delivery);
        layoutHashMap.put("express", R.layout.order_layout_express_delivery);
        layoutHashMap.put("delayed", R.layout.order_layout_delayed_delivery);
    }

    private void addConfirmOrderView() {
        addViewToParent(R.layout.order_layout_confirm_shipping_and_order, R.id.expandable_section);
        initConfirmOrderView();
    }

    private void addViewToParent(int layoutToAdd, int parentId) {
        ViewGroup parent = (ViewGroup) findViewById(parentId);
        inflatedView = getLayoutInflater().inflate(layoutToAdd, parent, false);
        parent.removeAllViews();
        parent.addView(inflatedView, 0);
    }


    public void toggleFinaliseOrderButtonLayout(View view) {
        if (!expandableLinearLayout.isExpanded())
            inflateOrderProcessingView(cartItemAdapter.getItemCount());
        expandableLinearLayout.toggle();
        String buttonText = expandableLinearLayout.isExpanded() ? "CLOSE" : "PROCEED TO CHECKOUT";
        buttonTextView.setText(buttonText);
    }

    public void setDeliveryType(View view) {
        String tag = (String) view.getTag();
        OrderTypeViewManager.makeDeliveryTypeSelectionOnUI(this, toggleButtonHashMap, textViewHashMap, layoutHashMap, tag);
    }

    public void removeItemFromCart(View view) {
        String id = view.getTag().toString();
        View parentView = (View) (view.getParent());
        TextView quantityTextView = (TextView) parentView.findViewById(R.id.order_item_quantity);
        int quantity = Integer.parseInt(quantityTextView.getText().toString());
        if (quantity > 0) {
            quantity--;
            if (quantity == 0)
                CartStorageManager.removeItemFromCart(getApplicationContext(), id);
            else
                CartStorageManager.storeItemInCart(getApplicationContext(), id, quantity);
            cartItemAdapter.notifyDataSetChanged();
        }
    }

    public void addItemToCart(View view) {
        String id = view.getTag().toString();
        View parentView = (View) (view.getParent());
        TextView quantityTextView = (TextView) parentView.findViewById(R.id.order_item_quantity);

        int quantity = Integer.parseInt(quantityTextView.getText().toString());
        quantity++;
        CartStorageManager.storeItemInCart(getApplicationContext(), id, quantity);
        cartItemAdapter.notifyDataSetChanged();

    }

    public void confirmVerifyAddressAndPlaceOrder(View view) {
        try {
            DeliveryModeInformation deliveryModeInformation = new DeliveryModeInformation("express", "tomorrow", "12:50");
            UserInformationDTO userInformation = new UserInformationDTO();

            userInformation.userName = "Name";
            userInformation.primaryPhoneNumber = "Phone";
            userInformation.secondaryPhoneNumber = "Phone";
            userInformation.address = "Address\"";
            userInformation.email = "email";

            JSONObject jsonObject = new BuildOrderTask().buildOrderJson(getBaseContext(), userInformation, deliveryModeInformation);
            new OrderTask(getBaseContext()).placeOrder(jsonObject);
        } catch (Exception ignored) {
        }
    }
}
