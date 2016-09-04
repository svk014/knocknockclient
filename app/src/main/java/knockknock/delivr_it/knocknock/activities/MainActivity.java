package knockknock.delivr_it.knocknock.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLinearLayout;

import java.io.File;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.adapters.ItemListAdapter;
import knockknock.delivr_it.knocknock.adapters.MainActivityMenuAdapter;
import knockknock.delivr_it.knocknock.managers.AboutUsStorageManager;
import knockknock.delivr_it.knocknock.managers.CartStorageManager;
import knockknock.delivr_it.knocknock.managers.ItemStorageManager;
import knockknock.delivr_it.knocknock.managers.MainMenuItemsManager;
import knockknock.delivr_it.knocknock.managers.SliderLayoutManager;
import knockknock.delivr_it.knocknock.managers.TextSliderViewManager;
import knockknock.delivr_it.knocknock.models.Item;
import knockknock.delivr_it.knocknock.tasks.AboutUsUpdateTask;
import knockknock.delivr_it.knocknock.tasks.ItemUpdateRetrievalTask;
import knockknock.delivr_it.knocknock.tasks.OfferRetrievalTask;
import knockknock.delivr_it.knocknock.tasks.UpdateDatabaseTask;

public class MainActivity extends AppCompatActivity {

    private EditText searchBar;
    private View search_view;
    private List<Item> searchedItems;

    private SliderLayout dailyOffersSliderLayout;
    private ExpandableLinearLayout expandableLinearLayout;
    private static boolean activityStarted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();
        inflateMainMenuItems();
        startOfferRetrieval();
        startDatabaseCheck();
        setupSearchBar();
        displayDailyOffersOnSliderLayout();
        updateAboutUs();
    }

    private void updateAboutUs() {
        new AboutUsUpdateTask(getBaseContext()).execute();
    }

    private void init() {
        expandableLinearLayout = (ExpandableLinearLayout) findViewById(R.id.expandable_container);
    }

    private void startDatabaseCheck() {
        new UpdateDatabaseTask(getApplicationContext()).checkAndUpdate();
    }

    private void inflateMainMenuItems() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_menu);

        MainMenuItemsManager mainMenuItemsManager = new MainMenuItemsManager();
        MainActivityMenuAdapter mainActivityMenuAdapter = new MainActivityMenuAdapter(MainActivity.this, mainMenuItemsManager.getMainMenuItems());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mainActivityMenuAdapter);
    }

    private void setupSearchBar() {
        searchBar = (EditText) findViewById(R.id.search_bar);
        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    search_view = findViewById(R.id.custom_search_view);
                    search_view.setVisibility(View.VISIBLE);
                }
            }
        });

        searchBar.addTextChangedListener(getTextWatcher());
    }

    @NonNull
    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 2)
                    showItems(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }


    private void startOfferRetrieval() {
        new OfferRetrievalTask(MainActivity.this).execute();
    }

    public void displayDailyOffersOnSliderLayout() {
        dailyOffersSliderLayout = (SliderLayout) findViewById(R.id.slider);

        SliderLayoutManager sliderLayoutManager = new SliderLayoutManager(MainActivity.this, dailyOffersSliderLayout);

        List<TextSliderView> textSliderViews = new TextSliderViewManager(MainActivity.this).textSlider();
        sliderLayoutManager.addSliderToLayout(textSliderViews);
        sliderLayoutManager.configureSliderLayout();
    }

    @Override
    protected void onStop() {
        dailyOffersSliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onResume() {
        dailyOffersSliderLayout.startAutoCycle();
        startOfferRetrieval();
        startItemUpdateRetrieval();
        super.onResume();
    }

    private void startItemUpdateRetrieval() {
        new ItemUpdateRetrievalTask(getBaseContext()).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void closeCustomSearchView(View view) {
        searchBar.clearFocus();
        search_view.setVisibility(View.INVISIBLE);

    }

    public void refreshActivity() {
        finish();
        startActivity(getIntent());
    }

    public void startItemListActivity(View view) {
        String tag = view.getTag().toString();
        if (tag.equals("Contact Us")) {
            return;
        }
        if (tag.equals("About Us")) {
            showAboutKnocKnockPopUp();
            return;
        }
        Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
        intent.putExtra("category", tag);
        startActivity(intent);

    }

    private void showAboutKnocKnockPopUp() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.about_knocknock_popup);
        ImageView aboutUsImage = (ImageView) dialog.findViewById(R.id.about_us);

        String localPath = AboutUsStorageManager.getAboutUsImage(getBaseContext());
        if (localPath != null) {
            Picasso.with(getBaseContext()).load(new File(localPath)).into(aboutUsImage);

            dialog.show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart) {
            Intent intent = new Intent(MainActivity.this, OrderViewActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.profile) {
            Intent intent = new Intent(MainActivity.this, ProfileViewActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.alerts) {
            Intent intent = new Intent(MainActivity.this, NotificationDisplayActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private void showItems(String searchItem) {
        RecyclerView searchView = (RecyclerView) findViewById(R.id.search_items_list);

        ItemListAdapter searchedItemsAdapter = new ItemListAdapter(getBaseContext(), ItemStorageManager.getAllItemsFor(getBaseContext(), searchItem));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchView.setLayoutManager(layoutManager);
        searchView.setItemAnimator(new DefaultItemAnimator());
        searchView.setAdapter(searchedItemsAdapter);
    }

    public void removeItemFromCart(View view) {
        String id = view.getTag().toString();
        View parentView = (View) (view.getParent());
        TextView quantityTextView = (TextView) parentView.findViewById(R.id.order_item_quantity);
        int quantity = Integer.parseInt(quantityTextView.getText().toString());
        if (quantity > 0) {
            quantity--;
            if (quantity == 0)
                CartStorageManager.removeItemFromCart(getApplicationContext(), id);
            else
                CartStorageManager.storeItemInCart(getApplicationContext(), id, quantity);
            quantityTextView.setText(quantity + "");
        }
    }

    public void addItemToCart(View view) {
        String id = view.getTag().toString();
        View parentView = (View) (view.getParent());
        TextView quantityTextView = (TextView) parentView.findViewById(R.id.order_item_quantity);

        int quantity = Integer.parseInt(quantityTextView.getText().toString());
        quantity++;
        CartStorageManager.storeItemInCart(getApplicationContext(), id, quantity);
        quantityTextView.setText(quantity + "");

    }

    public void showOfferDescription(String offerTitle, String offerDescription) {
        TextView offerDescriptionTextView = (TextView) findViewById(R.id.offer_description);
        TextView offerTitleTextView = (TextView) findViewById(R.id.offer_title);
        offerDescriptionTextView.setText(offerDescription);
        offerTitleTextView.setText(offerTitle);
        expandableLinearLayout.expand();
    }

    public void hideOfferDetails(View view) {
        expandableLinearLayout.collapse();
    }
}
