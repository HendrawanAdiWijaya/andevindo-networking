package com.andevindo.andevindonetworking;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by heendher on 11/3/2016.
 */

public class VolleyListener {

    public interface VolleySuccessListener {
        void onSuccess(JSONObject jsonObject, String tag);
        void onOtherResponse(JSONObject jsonObject, String tag);

    }

    public interface VolleyResponseListener{
        void doWork(JSONObject jsonObject, int responseCode);
    }

    public interface VolleyErrorListener {

        void onNetworkError(String tag);
        void onServerError(String tag);
        void onParseError();

    }

    public interface VolleyErrorGlobalListener{
        void onErrorGlobalListener(String tag);
    }

}
