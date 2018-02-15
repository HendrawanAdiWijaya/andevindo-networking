package com.andevindo.andevindonetworking;

import android.content.Context;
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
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;


/**
 * Created by -Andevindo- on 10/8/2015.
 */
class CustomRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> mListener;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;
    private HttpEntity mHttpEntity;
    private boolean mIsDebugOn;

    public CustomRequest(int method, String url, Map<String, String> headers, Map<String, String> params,
                         HttpEntity httpEntity, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener, boolean isDebugOn) {
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

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mParams != null)
            return mParams;
        else
            return super.getParams();
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (mIsDebugOn){
            Log.d("ServerResponse", volleyError.networkResponse.toString());
        }
        return super.parseNetworkError(volleyError);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            if (mIsDebugOn){
                Log.d("ServerResponse", response.toString());
            }
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }


}
