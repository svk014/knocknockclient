package knockknock.delivr_it.knocknock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.SliderLayoutManager;
import knockknock.delivr_it.knocknock.TextSliderViewManager;
import knockknock.delivr_it.knocknock.adapters.MainActivityMenuAdapter;
import knockknock.delivr_it.knocknock.tasks.OfferRetrievalTask;

public class MainActivity extends AppCompatActivity {

    private EditText searchBar;
    private View search_view;


    private SliderLayout dailyOffersSliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflateMainMenuItems();
        startOfferRetrieval();
        setupSearchBar();
        displayDailyOffersOnSliderLayout();


    }

    private void inflateMainMenuItems() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_menu);
        MainActivityMenuAdapter mainActivityMenuAdapter = new MainActivityMenuAdapter();

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
    }


    private void startOfferRetrieval() {
        new OfferRetrievalTask(MainActivity.this).execute();
    }

    public void displayDailyOffersOnSliderLayout() {
        dailyOffersSliderLayout = (SliderLayout) findViewById(R.id.slider);

        SliderLayoutManager sliderLayoutManager = new SliderLayoutManager(dailyOffersSliderLayout);


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
        super.onResume();
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
        Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
        startActivity(intent);
    }
}
