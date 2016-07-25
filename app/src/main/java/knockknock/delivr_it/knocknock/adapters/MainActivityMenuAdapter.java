package knockknock.delivr_it.knocknock.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.models.MainMenuItem;

public class MainActivityMenuAdapter extends RecyclerView.Adapter<MainActivityMenuAdapter.HolderView> {
    private Context context;
    private List<MainMenuItem> mainMenuItems;
    View inflatedView;

    public MainActivityMenuAdapter(Context context, List<MainMenuItem> mainMenuItems) {
        this.context = context;
        this.mainMenuItems = mainMenuItems;
    }

    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_individual_item, parent, false);
        return new HolderView(inflatedView);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(mainMenuItems.get(position).getBackgroundDrawable());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        holder.imageView.setImageBitmap(bitmap);
        holder.menuTitle.setText(mainMenuItems.get(position).getItemTitle());
    }

    @Override
    public int getItemCount() {
        return mainMenuItems.size();
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
