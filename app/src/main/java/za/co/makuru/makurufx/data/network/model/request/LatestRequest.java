package za.co.makuru.makurufx.data.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestRequest {

    @SerializedName("app_id")
    @Expose
    private String AppId;

    public LatestRequest(String appId) {
        AppId = appId;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }
}
