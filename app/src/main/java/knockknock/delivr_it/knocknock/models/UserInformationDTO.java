package knockknock.delivr_it.knocknock.models;

public class UserInformationDTO {
    public String userName = "";
    public String primaryPhoneNumber = "";
    public String secondaryPhoneNumber = "";
    public String address = "";
    public String email = "";
    public String oneSingalId = "";

    public boolean areFieldsValid() {
        return !(userName.equals("")
                || primaryPhoneNumber.equals("")
                || secondaryPhoneNumber.equals("")
                || address.equals("")
                || email.equals(""));
    }

}
