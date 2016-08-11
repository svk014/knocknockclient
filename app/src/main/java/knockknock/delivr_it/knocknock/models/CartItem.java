package knockknock.delivr_it.knocknock.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CartItem extends RealmObject {
    @PrimaryKey
    private String itemId;
    private int quantity;
    private long orderIdentifier;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(long orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }
}
