package knockknock.delivr_it.knocknock.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import net.cachapa.expandablelayout.ExpandableLinearLayout;

import java.io.File;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.managers.CartStorageManager;
import knockknock.delivr_it.knocknock.managers.FileStorageManager;
import knockknock.delivr_it.knocknock.managers.ItemStorageManager;
import knockknock.delivr_it.knocknock.models.Item;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.HolderView> {

    private Context context;
    private List<Item> items;

    public ItemListAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_row, parent, false);
        return new HolderView(inflatedView);
    }

    @Override
    public void onBindViewHolder(final HolderView holder, final int position) {

        displayPrices(holder, position);
        displayVegOrNonVeg(holder, position);
        displayDescription(holder, position);
        addTagsToButtons(holder, position);

        holder.expandableLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expandableLinearLayout.toggle();
                if (holder.expandableLinearLayout.isExpanded()) {
                    downloadSaveAndShowImage(holder.itemImage, items.get(position).getId(), items.get(position).getImage_url(), items.get(position).getImage_local_path());
                    int unitsInCart = CartStorageManager.getItemsInCartCountForId(context, items.get(position).getId());
                    holder.itemOrderQunatity.setText(unitsInCart + "");
                }
            }
        });

        holder.setIsRecyclable(false);
    }

    private void addTagsToButtons(HolderView holder, int position) {
        holder.addButton.setTag(items.get(position).getId());
        holder.removeButton.setTag(items.get(position).getId());
    }

    private void displayDescription(HolderView holder, int position) {
        holder.itemSectionTextView.setText(items.get(position).getSection());
    }

    private void displayVegOrNonVeg(HolderView holder, int position) {
        if (items.get(position).getVegetarian().equals("true")) {
            holder.itemVegOrNonVegTextView.setText("Veg");
            holder.itemVegOrNonVegTextView.setBackgroundResource(R.drawable.green_background);
        }
        if (items.get(position).getVegetarian().equals("false")) {
            holder.itemVegOrNonVegTextView.setText("Non-Veg");
            holder.itemVegOrNonVegTextView.setBackgroundResource(R.drawable.red_background);
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayPrices(HolderView holder, int position) {
        holder.itemNameTextView.setText(items.get(position).getItem_name());

        int priceAfterDiscount = (int) (Integer.parseInt(items.get(position).getPrice()) * (1 - Double.parseDouble(items.get(position).getDiscount()) / 100.0));
        holder.itemDiscountedPrice.setText("₹ " + priceAfterDiscount);

        if (items.get(position).getDiscount().equals("0")) {
            holder.itemActualPrice.setVisibility(View.GONE);
        } else {
            String actualPrice = "  ₹ " + items.get(position).getPrice() + "  ";
            holder.itemActualPrice.setText(actualPrice);
            holder.itemActualPrice.setPaintFlags(holder.itemActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    private void downloadSaveAndShowImage(ImageView itemImage, final String itemId, String image_url, String image_local_path) {

        Picasso picasso = Picasso.with(context);

        if (image_local_path.equals("<empty>")) {
            picasso.load(image_url).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    String image_local_path = FileStorageManager.storeImageInFileSystem(context, "items", System.currentTimeMillis() + "", source);
                    ItemStorageManager.updateItemLocalImagePath(context, itemId, image_local_path);
                    return source;
                }

                @Override
                public String key() {
                    return null;
                }
            })
                    .placeholder(R.drawable.default_food_image)
                    .into(itemImage);
        } else {
            picasso.load(new File(image_local_path)).into(itemImage);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        ExpandableLinearLayout expandableLinearLayout;
        TextView itemNameTextView;
        TextView itemSectionTextView;
        TextView itemVegOrNonVegTextView;
        TextView itemActualPrice;
        TextView itemDiscountedPrice;
        ImageView itemImage;
        ImageView addButton;
        ImageView removeButton;
        TextView itemOrderQunatity;

        public HolderView(View itemView) {
            super(itemView);
            expandableLinearLayout = (ExpandableLinearLayout) itemView.findViewById(R.id.expandable_container);
            itemNameTextView = (TextView) itemView.findViewById(R.id.item_name);
            itemSectionTextView = (TextView) itemView.findViewById(R.id.item_section);
            itemVegOrNonVegTextView = (TextView) itemView.findViewById(R.id.item_veg_or_non_veg);
            itemActualPrice = (TextView) itemView.findViewById(R.id.item_actual_price);
            itemDiscountedPrice = (TextView) itemView.findViewById(R.id.item_discounted_price);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            addButton = (ImageView) itemView.findViewById(R.id.add_to_cart);
            removeButton = (ImageView) itemView.findViewById(R.id.remove_from_cart);
            itemOrderQunatity = (TextView) itemView.findViewById(R.id.order_item_quantity);
        }
    }
}