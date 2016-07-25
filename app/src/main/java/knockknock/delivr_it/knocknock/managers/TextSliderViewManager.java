package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.models.Offer;

public class TextSliderViewManager {

    private Context context;

    public TextSliderViewManager(Context context) {
        this.context = context;
    }

    public List<TextSliderView> textSlider() {

        ArrayList<TextSliderView> textSliderViews = new ArrayList<>();
        List<Offer> offers = OfferStorageManager.retrieveStoredOffers(context);

        for (Offer offer : offers) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description(offer.getOffer_title())
                    .image(new File(offer.getImage_local_path()))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            textSliderViews.add(textSliderView);
        }

        if (textSliderViews.size() == 0) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description("Loading offers")
                    .image(R.drawable.default_food_image)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            textSliderViews.add(textSliderView);
        }

        return textSliderViews;
    }
}
