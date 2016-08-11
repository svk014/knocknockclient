package knockknock.delivr_it.knocknock.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.activities.OrderViewActivity;
import knockknock.delivr_it.knocknock.models.CartItem;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private List<CartItem> cartItems;

    public CartItemAdapter(List<CartItem> cartItems) {
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
            return;
        }
        CartItemViewHolder item = (CartItemViewHolder) holder;
        item.cartItemName.setText("ABCD");
        addTagsToButtons(item, position);
        setItemOrderQuantity(item, position);
    }

    private void addTagsToButtons(CartItemViewHolder holder, int position) {
        holder.addButton.setTag(cartItems.get(position).getItemId());
        holder.removeButton.setTag(cartItems.get(position).getItemId());
    }

    private void setItemOrderQuantity(CartItemViewHolder holder, int position) {
        holder.itemOrderQunatity.setText("" + cartItems.get(position).getQuantity());
        holder.cartItemPrice.setText("₹ " + cartItems.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return cartItems.size() == 0 ? 0 : cartItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() - 1 ? 1 : 0;
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

        public InvoiceViewHolder(View view) {
            super(view);
        }
    }
}
