package com.andevindo.andevindonetworking;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by heendher on 11/3/2016.
 */
public class Volley {

    public static API setAPI(VolleyModel volleyModel) {
        return new API(volleyModel);
    }

    private Volley() {
    }

    public Volley(String tag, VolleyModel volleyModel,
                  Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener, NetworkConfiguration networkConfiguration,
                  boolean isDebugOn, ProgressListener progressListener) {

        new VolleyRequest().sendRequest(tag, volleyModel, successListener, errorListener, networkConfiguration, isDebugOn, progressListener);

    }

    public static class API {

        private MutableLiveData<NetworkResponse> mNetworkResponseMutableLiveData = new MutableLiveData<>();
        private VolleyListener.VolleySuccessListener mVolleySuccessListener;
        private Response.Listener<JSONObject> mSuccessListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Object dataObject = getData(response);
                    int code = getCode(response);
                    String message = getMessage(response);
                    if (mVolleySuccessListener != null) {
                        mVolleySuccessListener.response(new NetworkResponse(code, dataObject, message, VolleyResponseStatus.SUCCESS), mTag);
                    }

                    if (mVolleyGlobalListener != null) {
                        mVolleyGlobalListener.response(new NetworkResponse(code, dataObject, message, VolleyResponseStatus.SUCCESS), mTag, null);
                    }

                    if (mVolleySuccessRawJSONListener != null) {
                        mVolleySuccessRawJSONListener.response(response, mTag);
                    }

                    mNetworkResponseMutableLiveData.setValue(new NetworkResponse(code, dataObject, message, VolleyResponseStatus.SUCCESS));

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (mVolleyErrorListener != null) {
                        mVolleyErrorListener.onParseError(mTag);
                    }

                    if (mVolleyErrorGlobalListener != null) {
                        mVolleyErrorGlobalListener.onErrorGlobalListener(mTag);
                    }

                    if (mVolleyGlobalListener != null) {
                        mVolleyGlobalListener.response(new NetworkResponse(-1, null, null, VolleyResponseStatus.SERVER_ERROR), mTag, null);
                    }

                    mNetworkResponseMutableLiveData.setValue(new NetworkResponse(-1, null, null, VolleyResponseStatus.SERVER_ERROR));
                }


            }
        };
        private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyResponseStatus volleyResponseStatus;
                if (VolleyErrorMessage.isConnectionProblem(error)){
                    volleyResponseStatus = VolleyResponseStatus.NETWORK_ERROR;
                }else{
                    volleyResponseStatus = VolleyResponseStatus.SERVER_ERROR;
                }

                if (mVolleyGlobalListener != null) {
                    mVolleyGlobalListener.response(new NetworkResponse(-1, null, null, volleyResponseStatus), mTag, null);
                }

                if (mVolleyErrorListener!=null){
                    if (volleyResponseStatus.equals(VolleyResponseStatus.NETWORK_ERROR)){
                        mVolleyErrorListener.onNetworkError(mTag);
                    }else{
                        mVolleyErrorListener.onServerError(mTag);
                    }
                }

                if (mVolleyErrorGlobalListener!=null){
                    mVolleyErrorGlobalListener.onErrorGlobalListener(mTag);
                }

                mNetworkResponseMutableLiveData.setValue(new NetworkResponse(-1, null, null, volleyResponseStatus));
            }
        };
        private VolleyListener.VolleyErrorListener mVolleyErrorListener;
        private VolleyListener.VolleyErrorGlobalListener mVolleyErrorGlobalListener;
        private VolleyListener.VolleyGlobalListener mVolleyGlobalListener;
        private VolleyListener.VolleySuccessRawJSONListener mVolleySuccessRawJSONListener;
        private VolleyModel mVolleyModel = new VolleyModel.ParameterBuilder("").build();
        private NetworkConfiguration mNetworkConfiguration = new NetworkConfiguration.Builder().build();
        private String mTag;
        private boolean mIsDebugOn;
        private ProgressListener mProgressListener;

        private Object getData(JSONObject jsonObject) throws JSONException {
            return jsonObject.get("data");
        }

        private int getCode(JSONObject jsonObject) throws JSONException {
            return jsonObject.getInt("code");
        }

        private String getMessage(JSONObject jsonObject) throws JSONException {
            return jsonObject.getString("message");
        }

        public API(VolleyModel volleyModel) {
            mVolleyModel = volleyModel;
        }

        public API setMethod(NetworkMethod networkMethod) {
            mVolleyModel.setNetworkMethod(networkMethod);
            return this;
        }

        public API setRawJSONListener(final VolleyListener.VolleySuccessRawJSONListener listener) {
            mVolleySuccessRawJSONListener = listener;
            return this;
        }

        public API setSuccessListener(final VolleyListener.VolleySuccessListener listener) {
            mVolleySuccessListener = listener;

            return this;
        }

        public API setGlobalListener(final VolleyListener.VolleyGlobalListener globalListener) {
            mVolleyGlobalListener = globalListener;

            return this;
        }

        public API setErrorListener(VolleyListener.VolleyErrorListener listener) {
            mVolleyErrorListener = listener;
            return this;
        }

        public API setErrorGlobalLister(VolleyListener.VolleyErrorGlobalListener listener) {
            mVolleyErrorGlobalListener = listener;
            return this;
        }

        public API setNetworkConfiguration(NetworkConfiguration networkConfiguration) {
            mNetworkConfiguration = networkConfiguration;
            return this;
        }

        public API setTag(String tag) {
            mTag = tag;
            return this;
        }

        public API setDebugOn(boolean isDebugOn) {
            mIsDebugOn = isDebugOn;
            return this;
        }

        public API setProgressListener(ProgressListener progressListener) {
            mProgressListener = progressListener;
            return this;
        }

        public Volley go() {
            return new Volley(mTag, mVolleyModel, mSuccessListener, mErrorListener, mNetworkConfiguration, mIsDebugOn, mProgressListener);
        }

        public LiveData<NetworkResponse> goAsLiveData(){
            new Volley(mTag, mVolleyModel, mSuccessListener, mErrorListener, mNetworkConfiguration, mIsDebugOn, mProgressListener);
            return mNetworkResponseMutableLiveData;
        }

    }

}
