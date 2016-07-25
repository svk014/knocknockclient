package knockknock.delivr_it.knocknock.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.cachapa.expandablelayout.ExpandableLinearLayout;

import java.util.HashMap;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.views.OrderTypeViewManager;

public class OrderViewActivity extends AppCompatActivity {
    ExpandableLinearLayout expandableLinearLayout;
    TextView buttonTextView;
    HashMap<String, ToggleButton> toggleButtonHashMap;
    HashMap<String, TextView> textViewHashMap;
    HashMap<String, Integer> layoutHashMap;

    View inflatedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        addConfirmOrderView();
        init();
    }

    private void init() {
        expandableLinearLayout = (ExpandableLinearLayout) findViewById(R.id.expandable_container);
        buttonTextView = (TextView) findViewById(R.id.button_text);

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
        ViewGroup parent = (ViewGroup) findViewById(R.id.expandable_section);
        inflatedView = getLayoutInflater().inflate(R.layout.order_layout_confirm_shipping_and_order, parent, false);
        parent.addView(inflatedView, 0);
    }


    public void toggleFinaliseOrderButtonLayout(View view) {
        expandableLinearLayout.toggle();
        String buttonText = expandableLinearLayout.isExpanded() ? "CLOSE" : "PLACE ORDER";
        buttonTextView.setText(buttonText);
    }

    public void setDeliveryType(View view) {
        String tag = (String) view.getTag();
        OrderTypeViewManager.makeDeliveryTypeSelectionOnUI(this, toggleButtonHashMap, textViewHashMap, layoutHashMap, tag);
    }

}
