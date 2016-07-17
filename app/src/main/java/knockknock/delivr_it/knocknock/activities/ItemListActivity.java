package knockknock.delivr_it.knocknock.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.adapters.ItemListAdapter;

public class ItemListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_lists);

        inflateMainMenuItems();
    }

    private void inflateMainMenuItems() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_menu);
        ItemListAdapter itemListAdapter = new ItemListAdapter();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(itemListAdapter);
    }
}
