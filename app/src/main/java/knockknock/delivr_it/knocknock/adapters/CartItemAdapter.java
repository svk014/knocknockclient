package knockknock.delivr_it.knocknock.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.managers.ItemStorageManager;
import knockknock.delivr_it.knocknock.models.CartItem;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private Context context;
    private List<CartItem> cartItems;

    public CartItemAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView;
        if (viewType == 1) {
            inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout_invoice, parent, false);
            return new InvoiceViewHolder(inflatedView);
        }
        inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout_single_cart_item, parent, false);
        return new CartItemViewHolder(inflatedView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            InvoiceViewHolder invoiceViewHolder = (InvoiceViewHolder) holder;
            invoiceViewHolder.totalCostWithoutDeliveryCharge.setText("₹ " + calculateTotalPrice());
            return;
        }
        CartItemViewHolder item = (CartItemViewHolder) holder;
        String itemId = cartItems.get(position).getItemId();

        setItemName(item, itemId);
        setItemImage(item, itemId);
        addTagsToButtons(item, position);
        setItemOrderQuantityAndPrice(item, position, itemId);
    }

    private void setItemImage(CartItemViewHolder item, String itemId) {
        String imageFilePath = ItemStorageManager.getImageForId(context, itemId);
        Picasso.with(context).load(new File(imageFilePath)).placeholder(R.drawable.default_food_image)
                .into(item.cartItemImage);
    }

    public void setItemName(CartItemViewHolder item, String itemId) {
        item.cartItemName.setText(ItemStorageManager.getNameForId(context, itemId));
    }

    private void addTagsToButtons(CartItemViewHolder holder, int position) {
        holder.addButton.setTag(cartItems.get(position).getItemId());
        holder.removeButton.setTag(cartItems.get(position).getItemId());
    }

    private void setItemOrderQuantityAndPrice(CartItemViewHolder holder, int position, String itemId) {
        holder.itemOrderQunatity.setText("" + cartItems.get(position).getQuantity());
        int quantity = cartItems.get(position).getQuantity();
        String price = ItemStorageManager.getPriceForId(context, itemId);
        int totalPrice = Integer.parseInt(price) * quantity;
        holder.cartItemPrice.setText("₹ " + totalPrice);
    }

    @Override
    public int getItemCount() {
        return cartItems.size() == 0 ? 0 : cartItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() - 1 ? 1 : 0;
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            int quantity = item.getQuantity();
            String itemId = item.getItemId();
            String price = ItemStorageManager.getPriceForId(context, itemId);

            totalPrice += quantity * Integer.parseInt(price);

        }
        return totalPrice;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    public class CartItemViewHolder extends ViewHolder {

        ImageView addButton;
        ImageView removeButton;
        ImageView cartItemImage;
        TextView cartItemPrice;
        TextView cartItemName;
        TextView itemOrderQunatity;

        public CartItemViewHolder(View view) {
            super(view);
            cartItemImage = (ImageView) view.findViewById(R.id.cart_item_image);
            cartItemName = (TextView) view.findViewById(R.id.cart_item_name);
            cartItemPrice = (TextView) view.findViewById(R.id.cart_item_total_price);
            addButton = (ImageView) itemView.findViewById(R.id.add_to_cart);
            removeButton = (ImageView) itemView.findViewById(R.id.remove_from_cart);
            itemOrderQunatity = (TextView) itemView.findViewById(R.id.order_item_quantity);
        }
    }

    public class InvoiceViewHolder extends ViewHolder {

        TextView totalCostWithoutDeliveryCharge;

        public InvoiceViewHolder(View view) {
            super(view);
            totalCostWithoutDeliveryCharge = (TextView) view.findViewById(R.id.order_total_price_without_delivery_charge);
        }
    }
}
