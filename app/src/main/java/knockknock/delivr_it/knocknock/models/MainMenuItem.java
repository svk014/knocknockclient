package knockknock.delivr_it.knocknock.models;

public class MainMenuItem {
    private final String itemTitle;
    private final String backgroundDrawable;
    private String category;

    public MainMenuItem(String itemTitle, String backgroundDrawable, String category) {
        this.itemTitle = itemTitle;
        this.backgroundDrawable = backgroundDrawable;
        this.category = category;
    }

    public String getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getCategory() {
        return category;
    }
}
