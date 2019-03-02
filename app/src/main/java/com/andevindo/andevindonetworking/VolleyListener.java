package com.andevindo.andevindonetworking;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by heendher on 11/3/2016.
 */

public class VolleyListener{

    public interface VolleySuccessRawJSONListener {
        void response(JSONObject jsonObjectRaw, String tag);
    }

    public interface VolleySuccessListener {
        void response(JSONObject jsonObjectData, int responseCode, String status, String tag);
    }

    public interface VolleyErrorListener {

        void onNetworkError(String tag);
        void onServerError(String tag);
        void onParseError(String tag);

    }

    public interface VolleyErrorGlobalListener{
        void onErrorGlobalListener(String tag);
    }

    public interface VolleyGlobalListener{
        void response(JSONObject jsonObjectData, int code, String status, String tag, VolleyResponseStatus generalError, VolleyError rawError);
    }
}
