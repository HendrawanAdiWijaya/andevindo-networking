package com.andevindo.andevindonetworking;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by -Andevindo- on 10/8/2015.
 */
public class VolleyRequest {
    private RequestQueue mRequestQueue;
    private CustomRequestJson mCustomRequestJson;

    public VolleyRequest() {
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
    }

    public void sendRequest(final String tag, final VolleyModel volleyModel,
                            final Response.Listener<JSONObject> successlistener,
                            final Response.ErrorListener errorListener,
                            NetworkConfiguration networkConfiguration,
                            boolean isDebugOn, boolean isUploadErrorLog,
                            ProgressListener progressListener) {
        int method;
        if (volleyModel.getNetworkMethod() == NetworkMethod.POST
                || (volleyModel.getNetworkMethod() == NetworkMethod.PUT && volleyModel.isUsingLaravelWebService())
                || (volleyModel.getNetworkMethod() == NetworkMethod.DELETE && volleyModel.isUsingLaravelWebService()))
            method = Request.Method.POST;
        else if (volleyModel.getNetworkMethod() == NetworkMethod.GET)
            method = Request.Method.GET;
        else if (volleyModel.getNetworkMethod() == NetworkMethod.PUT) {
            method = Request.Method.POST;
        }else
            method = Request.Method.POST;
        Map<String, String> header = null;
        if (volleyModel.isUsingHeader()){
            if (volleyModel.getHeaders()==null){
                header = networkConfiguration.getHeaders();
            }else{
                header = volleyModel.getHeaders();
            }
        }
        mCustomRequestJson = new CustomRequestJson(method, volleyModel.getUrl(), header,
                volleyModel.getParameters(), volleyModel.getHttpEntity(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (successlistener != null) {
                    successlistener.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorListener!=null){
                    errorListener.onErrorResponse(error);
                }
            }
        }, isDebugOn, isUploadErrorLog, progressListener);

        mCustomRequestJson.setRetryPolicy(new DefaultRetryPolicy(networkConfiguration.getSocketTimeOut(),
                networkConfiguration.getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mCustomRequestJson.setTag(tag);
        mRequestQueue.add(mCustomRequestJson);

    }

    public void cancelAllRequest(String tag) {
        if (mCustomRequestJson != null)
            mRequestQueue.cancelAll(tag);

    }
}
