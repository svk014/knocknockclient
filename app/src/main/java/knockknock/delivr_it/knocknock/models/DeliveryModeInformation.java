package knockknock.delivr_it.knocknock.models;

public class DeliveryModeInformation {
    public String deliveryType;
    public String deliveryDay;
    public String deliveryTime;

    public DeliveryModeInformation(String deliveryType, String deliveryDay, String deliveryTime){
        this.deliveryType = deliveryType;
        this.deliveryDay = deliveryDay;
        this.deliveryTime = deliveryTime;
    }

}
