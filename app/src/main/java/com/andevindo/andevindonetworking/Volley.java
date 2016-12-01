package com.andevindo.andevindonetworking;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by heendher on 11/3/2016.
 */
public class Volley {

    private static Volley ourInstance = new Volley();


    public static Builder with(Context context) {
        return new Builder(context);
    }

    private Volley() {
    }

    public Volley(Context context, String tag, VolleyModel volleyModel,
                  Response.Listener<JSONObject> listener, VolleyListener.VolleySuccessListener successListener, VolleyListener.VolleyErrorListener errorListener,
                  VolleyListener.VolleyErrorGlobalListener globalListener, NetworkConfiguration networkConfiguration) {

        new VolleyRequest(context).sendRequest(tag, volleyModel, listener, successListener, errorListener, globalListener, networkConfiguration);

    }

    public static class Builder {


        private Context mContext;


        public Builder(Context context) {
            mContext = context;
        }

        public API setAPI(VolleyModel volleyModel) {
            return new API(volleyModel, mContext);
        }


    }

    public static class API {

        private VolleyListener.VolleySuccessListener mVolleySuccessListener = new VolleyListener.VolleySuccessListener() {
            @Override
            public void onSuccess(JSONObject jsonObject, String tag) {

            }

            @Override
            public void onOtherResponse(JSONObject jsonObject, String tag) {

            }
        };
        private Response.Listener<JSONObject> mJSONObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        };
        private VolleyListener.VolleyErrorListener mVolleyErrorListener = new VolleyListener.VolleyErrorListener() {
            @Override
            public void onNetworkError(String tag) {

            }

            @Override
            public void onServerError(String tag) {

            }

            @Override
            public void onParseError() {

            }
        };
        private VolleyListener.VolleyErrorResponseListener mVolleyErrorResponseListener = new VolleyListener.VolleyErrorResponseListener() {
            @Override
            public void onResponseNotFound() {

            }

            @Override
            public void onNetworkError(String tag) {

            }

            @Override
            public void onServerError(String tag) {

            }

            @Override
            public void onParseError() {

            }
        };
        private VolleyListener.VolleyErrorGlobalListener mVolleyErrorGlobalListener = new VolleyListener.VolleyErrorGlobalListener() {
            @Override
            public void onErrorGlobalListener(String tag) {

            }
        };
        private VolleyModel mVolleyModel = new VolleyModel.ParameterBuilder("").build();
        private NetworkConfiguration mNetworkConfiguration = new NetworkConfiguration.Builder().build();
        private String mTag;
        private Context mContext;

        public API(VolleyModel volleyModel, Context context) {
            mVolleyModel = volleyModel;
            mContext = context;
        }

        public API setMethod(NetworkMethod networkMethod) {
            mVolleyModel.setNetworkMethod(networkMethod);
            return this;
        }

        public API setSuccessListener(final VolleyListener.VolleyResponseListener listener) {
            mJSONObjectListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int code = response.getInt("kode");
                        boolean isCodeFounded = false;
                        for (int i = 0; i < mVolleyModel.getResponseCodes().length; i++) {
                            if (code==mVolleyModel.getResponseCodes()[i]) {
                                listener.doWork(response, code);
                                isCodeFounded = true;
                                break;
                            }
                        }
                        if (!isCodeFounded)
                            mVolleyErrorResponseListener.onResponseNotFound();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mVolleyErrorListener.onParseError();
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

        public Volley go() {
            return new Volley(mContext, mTag, mVolleyModel, mJSONObjectListener, mVolleySuccessListener, mVolleyErrorListener, mVolleyErrorGlobalListener, mNetworkConfiguration);
        }

    }

}
