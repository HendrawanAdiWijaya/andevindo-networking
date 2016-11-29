package com.andevindo.andevindonetworking;

/**
 * Created by heendher on 11/2/2016.
 */

public interface VolleyErrorPresenter {

    void onNetworkError(String tag);
    void onServerError(String tag);

}
