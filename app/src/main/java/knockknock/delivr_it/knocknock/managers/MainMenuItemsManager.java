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
        MainMenuItem mainMenuItem = new MainMenuItem("Food", "default_food_image.jpg");
        mainMenuItems.add(mainMenuItem);
        mainMenuItem = new MainMenuItem("Groceries", "default_grocery_image.jpg");
        mainMenuItems.add(mainMenuItem);
        mainMenuItem = new MainMenuItem("Stationary", "default_stationary_image.jpg");
        mainMenuItems.add(mainMenuItem);
        mainMenuItem = new MainMenuItem("Cosmetics", "default_cosmetics_image.jpg");
        mainMenuItems.add(mainMenuItem);
    }

}
