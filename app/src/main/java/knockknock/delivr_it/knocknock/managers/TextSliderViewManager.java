package knockknock.delivr_it.knocknock.managers;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.activities.MainActivity;
import knockknock.delivr_it.knocknock.models.Offer;

public class TextSliderViewManager {

    private MainActivity mainActivity;

    public TextSliderViewManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<TextSliderView> textSlider() {

        ArrayList<TextSliderView> textSliderViews = new ArrayList<>();
        List<Offer> offers = OfferStorageManager.retrieveStoredOffers(mainActivity);

        for (final Offer offer : offers) {
            TextSliderView textSliderView = new TextSliderView(mainActivity);
            textSliderView
                    .description(offer.getOffer_title())
                    .image(new File(offer.getImage_local_path()))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            textSliderViews.add(textSliderView);
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    mainActivity.showOfferDescription(offer.getOffer_title(), offer.getOffer_text());
                }
            });
        }

        if (textSliderViews.size() == 0) {
            TextSliderView textSliderView = new TextSliderView(mainActivity);
            textSliderView
                    .description("Loading offers")
                    .image(R.drawable.default_food_image)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            textSliderViews.add(textSliderView);
        }

        return textSliderViews;
    }
}
