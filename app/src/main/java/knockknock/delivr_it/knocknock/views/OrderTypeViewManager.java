package knockknock.delivr_it.knocknock.views;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.activities.OrderViewActivity;
import knockknock.delivr_it.knocknock.models.DeliveryModeInformation;
import picker.ugurtekbas.com.Picker.Picker;

public class OrderTypeViewManager {


    public static void makeDeliveryTypeSelectionOnUI(OrderViewActivity orderViewActivity, HashMap<String, ToggleButton> toggleButtonHashMap,
                                                     HashMap<String, TextView> textViewHashMap,
                                                     HashMap<String, Integer> layoutHashMap,
                                                     String tag) {

        int deliveryModeLayoutToRender = 0;
        for (String key : toggleButtonHashMap.keySet()) {
            ToggleButton toggleButton = toggleButtonHashMap.get(key);
            toggleButton.setChecked(key.equals(tag));
//            TextView textView = textViewHashMap.get(key);
//            int color = key.equals(tag) ? Color.WHITE : Color.BLACK;
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
        inflateSpinner(parent, deliveryModeLayoutToRender);
    }

    public static DeliveryModeInformation getSelectedDeliveryMode(Activity activity, HashMap<String, ToggleButton> toggleButtonHashMap) {
        String deliveryMode = "";

        for (String key : toggleButtonHashMap.keySet()) {
            deliveryMode = toggleButtonHashMap.get(key).isChecked() ? key : deliveryMode;
        }

        return new DeliveryModeInformation(deliveryMode, getDeliveryDay(activity, deliveryMode), getDeliveryTime(activity, deliveryMode));
    }

    private static String getDeliveryTime(Activity activity, String deliveryMode) {
        if (deliveryMode.equals("delayed")) {
            Picker deliverDatePicker = (Picker) activity.findViewById(R.id.delivery_time_picker);
            return deliverDatePicker.getCurrentHour() + " : " + deliverDatePicker.getCurrentMin();
        }
        return "not applicable";
    }

    private static String getDeliveryDay(Activity activity, String deliveryMode) {
        if (deliveryMode.equals("delayed")) {
            Spinner deliverDaySpinner = (Spinner) activity.findViewById(R.id.delivery_day_spinner);
            return deliverDaySpinner.getSelectedItem().toString();
        }
        return "not applicable";
    }

    private static void inflateSpinner(View parent, int deliveryType) {

        if (deliveryType != R.layout.order_layout_delayed_delivery)
            return;
        Spinner sortMethodSelector = (Spinner) parent.findViewById(R.id.delivery_day_spinner);
        List<String> sortCategories = new ArrayList<>();
        sortCategories.add("Today");
        sortCategories.add("Tomorrow");
        sortCategories.add("Day after tomorrow");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(parent.getContext(), android.R.layout.simple_spinner_item, sortCategories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sortMethodSelector.setAdapter(dataAdapter);
    }
}
