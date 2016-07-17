package knockknock.delivr_it.knocknock.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cachapa.expandablelayout.ExpandableLinearLayout;

import knockknock.delivr_it.knocknock.R;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.HolderView> {

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_row, parent, false);
        return new HolderView(inflatedView);
    }

    @Override
    public void onBindViewHolder(final HolderView holder, int position) {
        holder.expandableLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expandableLinearLayout.toggle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class HolderView extends RecyclerView.ViewHolder {
        ExpandableLinearLayout expandableLinearLayout;

        public HolderView(View itemView) {
            super(itemView);
            expandableLinearLayout = (ExpandableLinearLayout) itemView.findViewById(R.id.expandable_container);

        }
    }
}
