package knockknock.delivr_it.knocknock.managers;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.List;

import knockknock.delivr_it.knocknock.activities.MainActivity;

public class SliderLayoutManager {

    private MainActivity mainActivity;
    private SliderLayout sliderLayout;

    public SliderLayoutManager(MainActivity mainActivity, SliderLayout sliderLayout) {
        this.mainActivity = mainActivity;
        this.sliderLayout = sliderLayout;
    }

    public void configureSliderLayout() {
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mainActivity.hideOfferDetails(null);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void addSliderToLayout(List<TextSliderView> textSliderViews) {
        for (TextSliderView textSliderView : textSliderViews)
            sliderLayout.addSlider(textSliderView);
    }

    public int getCurrentSliderPosition() {
        return sliderLayout.getCurrentPosition();
    }
}
