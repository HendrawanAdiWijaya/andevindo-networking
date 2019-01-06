package com.andevindo.andevindonetworking;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by heendher on 11/3/2016.
 */

public class VolleyListener{

    public interface VolleyRawJSONListener<T>{
        void doWork(T t, String tag);
    }

    public interface VolleySuccessListener {
        void onSuccess(JSONObject jsonObject, String tag);
        void onOtherResponse(JSONObject jsonObject, String tag);

    }

    public interface VolleyResponseListener<T>{
        void doWork(T t, int responseCode, String tag);
    }

    public interface VolleyErrorListener {

        void onNetworkError(String tag);
        void onServerError(String tag);
        void onParseError(String tag);

    }

    public interface VolleyErrorResponseListener extends VolleyErrorListener{
        void onResponseNotFound(String tag);
    }

    public interface VolleyErrorGlobalListener{
        void onErrorGlobalListener(String tag);
    }

}
