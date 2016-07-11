package knockknock.delivr_it.knocknock.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.SliderLayoutManager;
import knockknock.delivr_it.knocknock.TextSliderViewManager;
import knockknock.delivr_it.knocknock.tasks.OfferRetrievalTask;

public class MainActivity extends AppCompatActivity {

    private SliderLayout dailyOffersSliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new OfferRetrievalTask(MainActivity.this).execute();
        displayDailyOffersOnSliderLayout();


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
