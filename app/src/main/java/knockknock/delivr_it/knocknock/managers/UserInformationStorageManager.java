package knockknock.delivr_it.knocknock.managers;

import android.content.Context;

import io.realm.Realm;
import knockknock.delivr_it.knocknock.models.UserInformation;
import knockknock.delivr_it.knocknock.models.UserInformationDTO;

public class UserInformationStorageManager {

    public static UserInformation getUserInformation(Context context) {
        Realm realm = Realm.getInstance(context);
        return realm.where(UserInformation.class).findFirst();
    }

    public static void setUserInformation(Context context, UserInformationDTO userInformationDTO) {
        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();
        UserInformation userInformation = new UserInformation();
        userInformation.set_id("1");
        userInformation.setUserName(userInformationDTO.userName);
        userInformation.setPrimaryPhoneNumber(userInformationDTO.primaryPhoneNumber);
        userInformation.setSecondaryPhoneNumber(userInformationDTO.secondaryPhoneNumber);
        userInformation.setEmail(userInformationDTO.email);
        userInformation.setAddress(userInformationDTO.address);

        realm.copyToRealmOrUpdate(userInformation);
        realm.commitTransaction();
    }
}
