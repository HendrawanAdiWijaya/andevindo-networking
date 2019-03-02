package com.andevindo.andevindonetworking;

import org.json.JSONObject;

public class NetworkResponse {

    private int mCode;
    private JSONObject mData;
    private String mMessage;
    private VolleyResponseStatus mVolleyResponseStatus;

    public NetworkResponse(int code, JSONObject data, String message, VolleyResponseStatus volleyResponseStatus) {
        mCode = code;
        mData = data;
        mMessage = message;
        mVolleyResponseStatus = volleyResponseStatus;
    }

    public VolleyResponseStatus getVolleyResponseStatus() {
        return mVolleyResponseStatus;
    }

    public void setVolleyResponseStatus(VolleyResponseStatus volleyResponseStatus) {
        mVolleyResponseStatus = volleyResponseStatus;
    }

    public JSONObject getData() {
        return mData;
    }

    public void setData(JSONObject data) {
        mData = data;
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
