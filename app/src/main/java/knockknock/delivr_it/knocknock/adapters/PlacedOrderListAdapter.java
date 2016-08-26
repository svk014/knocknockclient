package knockknock.delivr_it.knocknock.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.models.Order;

public class PlacedOrderListAdapter extends RecyclerView.Adapter<PlacedOrderListAdapter.HolderView> {
    private List<Order> orders;

    public PlacedOrderListAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.placed_orders_list_row, parent, false);
        return new HolderView(inflatedView);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        holder.orderId.setText("Order # " + orders.get(position).getOrder_id());
        holder.timeOfOrder.setText("Placed on\n" + orders.get(position).getOrder_time());
        holder.orderStatus.setText(orders.get(position).getOrder_status());
        holder.itemView.setTag(orders.get(position).getOrder_id());
        int totalPrice = Integer.parseInt(orders.get(position).getTotal());
        String priceText = "Price to be confirmed.";
        if (totalPrice > 0)
            priceText = "Total : Rs. " + totalPrice + "";
        holder.totalCost.setText(priceText);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView totalCost;
        TextView timeOfOrder;
        TextView orderStatus;
        View itemView;

        public HolderView(View itemView) {
            super(itemView);
            this.itemView = itemView;
            orderId = (TextView) itemView.findViewById(R.id.order_id);
            totalCost = (TextView) itemView.findViewById(R.id.total_cost);
            timeOfOrder = (TextView) itemView.findViewById(R.id.order_date_time);
            orderStatus = (TextView) itemView.findViewById(R.id.order_status);
        }
    }
}
