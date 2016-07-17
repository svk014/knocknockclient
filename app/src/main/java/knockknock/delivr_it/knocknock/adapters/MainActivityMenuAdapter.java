package knockknock.delivr_it.knocknock.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import knockknock.delivr_it.knocknock.R;

public class MainActivityMenuAdapter extends RecyclerView.Adapter<MainActivityMenuAdapter.HolderView> {
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_individual_item, parent, false);
        return new HolderView(inflatedView);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class HolderView extends RecyclerView.ViewHolder {

        TextView menuTitle;
        ImageView imageView;

        public HolderView(View itemView) {
            super(itemView);
            menuTitle = (TextView) itemView.findViewById(R.id.text);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
