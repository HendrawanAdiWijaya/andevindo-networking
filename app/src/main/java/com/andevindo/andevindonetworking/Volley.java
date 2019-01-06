package com.andevindo.andevindonetworking;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by heendher on 11/3/2016.
 */
public class Volley{

    public static API setAPI(VolleyModel volleyModel) {
        return new API(volleyModel);
    }

    private Volley() {
    }

    public Volley(String tag, VolleyModel volleyModel,
                  Response.Listener<JSONObject> listener, VolleyListener.VolleySuccessListener successListener, VolleyListener.VolleyErrorListener errorListener,
                  VolleyListener.VolleyErrorGlobalListener globalListener, NetworkConfiguration networkConfiguration,
                  boolean isDebugOn, ProgressListener progressListener) {

        new VolleyRequest().sendRequest(tag, volleyModel, listener, successListener, errorListener, globalListener, networkConfiguration, isDebugOn, progressListener);

    }

    public static class API {

        private VolleyListener.VolleySuccessListener mVolleySuccessListener;
        private Response.Listener<JSONObject> mJSONObjectListener;
        private VolleyListener.VolleyErrorListener mVolleyErrorListener;
        private VolleyListener.VolleyErrorResponseListener mVolleyErrorResponseListener;
        private VolleyListener.VolleyErrorGlobalListener mVolleyErrorGlobalListener;
        private VolleyModel mVolleyModel = new VolleyModel.ParameterBuilder("").build();
        private NetworkConfiguration mNetworkConfiguration = new NetworkConfiguration.Builder().build();
        private String mTag;
        private boolean mIsDebugOn;
        private ProgressListener mProgressListener;

        public API(VolleyModel volleyModel) {
            mVolleyModel = volleyModel;
        }

        public API setMethod(NetworkMethod networkMethod) {
            mVolleyModel.setNetworkMethod(networkMethod);
            return this;
        }

        public API setRawJSONListener(final VolleyListener.VolleyRawJSONListener listener){
            mJSONObjectListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.doWork(response, mTag);
                }
            };
            return this;
        }

        public API setSuccessListener(final VolleyListener.VolleyResponseListener listener) {
            mJSONObjectListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int code = response.getInt("kode");
                        listener.doWork(response, code, mTag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mVolleyErrorListener.onParseError(mTag);
                    }
                }
            };

            return this;
        }

        public API setSuccessListener(VolleyListener.VolleySuccessListener listener) {
            mVolleySuccessListener = listener;
            return this;
        }

        public API setErrorListener(VolleyListener.VolleyErrorListener listener) {
            mVolleyErrorListener = listener;
            return this;
        }

        public API setErrorListener(VolleyListener.VolleyErrorResponseListener listener) {
            mVolleyErrorResponseListener = listener;
            mVolleyErrorListener = new VolleyListener.VolleyErrorListener() {
                @Override
                public void onNetworkError(String tag) {
                    mVolleyErrorResponseListener.onNetworkError(tag);
                }

                @Override
                public void onServerError(String tag) {
                    mVolleyErrorResponseListener.onServerError(tag);
                }

                @Override
                public void onParseError(String tag) {
                    mVolleyErrorResponseListener.onParseError(tag);
                }
            };
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

        public API setDebugOn(boolean isDebugOn){
            mIsDebugOn = isDebugOn;
            return this;
        }

        public API setProgressListener(ProgressListener progressListener){
            mProgressListener = progressListener;
            return this;
        }

        public Volley go() {
            return new Volley(mTag, mVolleyModel, mJSONObjectListener, mVolleySuccessListener, mVolleyErrorListener, mVolleyErrorGlobalListener, mNetworkConfiguration, mIsDebugOn, mProgressListener);
        }

    }

}
