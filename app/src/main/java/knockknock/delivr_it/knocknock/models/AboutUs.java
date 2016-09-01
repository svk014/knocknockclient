package knockknock.delivr_it.knocknock.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AboutUs extends RealmObject {
    @PrimaryKey
    private int aboutUsImageVersion;
    private String localPath;

    public int getAboutUsImageVersion() {
        return aboutUsImageVersion;
    }

    public void setAboutUsImageVersion(int aboutUsImageVersion) {
        this.aboutUsImageVersion = aboutUsImageVersion;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
