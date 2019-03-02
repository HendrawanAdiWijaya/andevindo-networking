package com.andevindo.andevindonetworking;

import org.json.JSONArray;
import org.json.JSONObject;

public class NetworkResponse {

    private int mCode;
    private JSONObject mDataAsObject;
    private JSONArray mDataAsArray;
    private String mMessage;
    private VolleyResponseStatus mVolleyResponseStatus;

    public NetworkResponse(int code, Object data, String message, VolleyResponseStatus volleyResponseStatus) {
        mCode = code;
        if (data==null){
            mDataAsObject = null;
            mDataAsArray = null;
        }
        else if (data instanceof JSONObject)
            mDataAsObject = ((JSONObject) data);
        else
            mDataAsArray = ((JSONArray) data);
        mMessage = message;
        mVolleyResponseStatus = volleyResponseStatus;
    }

    public VolleyResponseStatus getVolleyResponseStatus() {
        return mVolleyResponseStatus;
    }

    public void setVolleyResponseStatus(VolleyResponseStatus volleyResponseStatus) {
        mVolleyResponseStatus = volleyResponseStatus;
    }

    public JSONObject getDataAsObject() {
        return mDataAsObject;
    }

    public void setDataAsObject(JSONObject dataAsObject) {
        mDataAsObject = dataAsObject;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
