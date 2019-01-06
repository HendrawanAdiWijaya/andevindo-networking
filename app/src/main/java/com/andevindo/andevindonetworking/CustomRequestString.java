package com.andevindo.andevindonetworking;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;


/**
 * Created by -Andevindo- on 10/8/2015.
 */
class CustomRequestString extends Request<String> {

    private Response.Listener<String> mListener;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;
    private HttpEntity mHttpEntity;
    private boolean mIsDebugOn;
    private int mMethod;

    public CustomRequestString(int method, String url, Map<String, String> headers, Map<String, String> params,
                               HttpEntity httpEntity, Response.Listener<String> responseListener, Response.ErrorListener errorListener, boolean isDebugOn) {
        super(method, url, errorListener);
        mListener = responseListener;
        mParams = params;
        mHttpEntity = httpEntity;
        mHeaders = headers;
        mIsDebugOn = isDebugOn;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mHttpEntity != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                mHttpEntity.writeTo(bos);
                return bos.toByteArray();
            } catch (IOException e) {
                VolleyLog.e("" + e);
                return null;
            } catch (OutOfMemoryError e) {
                VolleyLog.e("" + e);
                return null;
            }
        }
        return super.getBody();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mHeaders != null)
            return mHeaders;
        else
            return super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        if (mHttpEntity != null)
            return mHttpEntity.getContentType().getValue();
        else
            return super.getBodyContentType();
    }

   /* @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams != null)
            return mParams;
        else
            return super.getParams();
    }*/

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        Log.d("SerResponse", "OnDeliver");
        if (mIsDebugOn && error != null && error.networkResponse != null && error.networkResponse.data != null) {
            try {
                Log.d("ServerResponse", new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers)));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d("ServerResponse", new String(error.networkResponse.data));
            } catch (NullPointerException e) {
                Log.d("ServerResponse", "Null");
            }
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }
}
