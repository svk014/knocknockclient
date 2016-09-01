package knockknock.delivr_it.knocknock.managers;

import java.util.ArrayList;
import java.util.List;

import knockknock.delivr_it.knocknock.R;
import knockknock.delivr_it.knocknock.models.MainMenuItem;


public class MainMenuItemsManager {
    private List<MainMenuItem> mainMenuItems;

    public MainMenuItemsManager() {
        this.mainMenuItems = new ArrayList<>();
    }

    public List<MainMenuItem> getMainMenuItems() {
        loadMainMenuItems();
        return mainMenuItems;
    }

    private void loadMainMenuItems() {
        MainMenuItem mainMenuItem = new MainMenuItem("Food", "default_food_image.jpg", "Food");
        mainMenuItems.add(mainMenuItem);
        mainMenuItem = new MainMenuItem("Groceries", "default_grocery_image.jpg", "Grocery");
        mainMenuItems.add(mainMenuItem);
        mainMenuItem = new MainMenuItem("Stationary", "default_stationary_image.jpg", "Stationary");
        mainMenuItems.add(mainMenuItem);
        mainMenuItem = new MainMenuItem("Cosmetics", "default_cosmetics_image.jpg", "Cosmetics");
        mainMenuItems.add(mainMenuItem);
//        mainMenuItem = new MainMenuItem("Contact Us", "default_contact_us_image.jpg", "Contact Us");
//        mainMenuItems.add(mainMenuItem);
        mainMenuItem = new MainMenuItem("About KnocKnock", "default_about_us_image.jpg", "About Us");
        mainMenuItems.add(mainMenuItem);
    }

}
