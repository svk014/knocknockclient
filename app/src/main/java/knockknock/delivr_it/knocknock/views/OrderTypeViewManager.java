package knockknock.delivr_it.knocknock.views;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.activities.OrderViewActivity;

public class OrderTypeViewManager {


    public static void makeDeliveryTypeSelectionOnUI(OrderViewActivity orderViewActivity, HashMap<String, ToggleButton> toggleButtonHashMap,
                                                     HashMap<String, TextView> textViewHashMap,
                                                     HashMap<String, Integer> layoutHashMap,
                                                     String tag) {

        int deliveryModeLayoutToRender = 0;
        for (String key : toggleButtonHashMap.keySet()) {
            ToggleButton toggleButton = toggleButtonHashMap.get(key);
            toggleButton.setChecked(key.equals(tag));
            TextView textView = textViewHashMap.get(key);
            int color = key.equals(tag) ? Color.WHITE : Color.BLACK;
            textView.setTextColor(color);
            if (key.equals(tag)) {
                deliveryModeLayoutToRender = layoutHashMap.get(key);
            }
        }
        inflateDeliveryTypeInformationOnUI(orderViewActivity, deliveryModeLayoutToRender);
    }

    public static void inflateDeliveryTypeInformationOnUI(OrderViewActivity orderViewActivity, int deliveryModeLayoutToRender) {
        ViewGroup parent = (ViewGroup) orderViewActivity.findViewById(R.id.order_type_information_container);
        View inflatedView = orderViewActivity.getLayoutInflater().inflate(deliveryModeLayoutToRender, parent, false);

        parent.removeAllViews();
        parent.addView(inflatedView, 0);
    }

}
