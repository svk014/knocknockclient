package knockknock.delivr_it.knocknock.models;

public class MainMenuItem {
    private final String itemTitle;
    private final String backgroundDrawable;

    public MainMenuItem(String itemTitle, String backgroundDrawable) {
        this.itemTitle = itemTitle;
        this.backgroundDrawable = backgroundDrawable;
    }

    public String getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public String getItemTitle() {
        return itemTitle;
    }
}
