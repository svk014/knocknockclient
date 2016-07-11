package knockknock.delivr_it.knocknock;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.List;

public class SliderLayoutManager {

    private SliderLayout sliderLayout;

    public SliderLayoutManager(SliderLayout sliderLayout) {
        this.sliderLayout = sliderLayout;
    }

    public void configureSliderLayout() {
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
    }

    public void addSliderToLayout(List<TextSliderView> textSliderViews) {
        for (TextSliderView textSliderView : textSliderViews)
            sliderLayout.addSlider(textSliderView);
    }
}
