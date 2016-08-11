package knockknock.delivr_it.knocknock.tasks;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import knockknock.delivr_it.knocknock.managers.CartStorageManager;
import knockknock.delivr_it.knocknock.managers.ItemStorageManager;
import knockknock.delivr_it.knocknock.models.CartItem;
import knockknock.delivr_it.knocknock.models.DeliveryModeInformation;
import knockknock.delivr_it.knocknock.models.UserInformationDTO;

public class BuildOrderTask {
    public JSONObject buildOrderJson(Context context, UserInformationDTO userInformation, DeliveryModeInformation deliveryModeInformation) throws Exception {
        List<CartItem> itemsInCart = CartStorageManager.getItemsInCart(context);
        long order_identifier = CartStorageManager.getOrderIdentifier(context);

        JSONObject orderJson = new JSONObject();
        orderJson.put("order_identifier_client", order_identifier);

        JSONArray cartItemsJsonArray = new JSONArray();
        for (CartItem cartItem : itemsInCart) {
            JSONObject cartItemJson = new JSONObject();
            cartItemJson.put("item_id", cartItem.getItemId());
            cartItemJson.put("quantity", cartItem.getQuantity());
            cartItemJson.put("price_per_unit", ItemStorageManager.getPriceForId(context, cartItem.getItemId()));

            cartItemsJsonArray.put(cartItemJson);
        }

        orderJson.put("orders", cartItemsJsonArray);
        orderJson.put("user_information", new Gson().toJson(userInformation));
        orderJson.put("delivery_information", new Gson().toJson(deliveryModeInformation));
        return orderJson;
    }
}
