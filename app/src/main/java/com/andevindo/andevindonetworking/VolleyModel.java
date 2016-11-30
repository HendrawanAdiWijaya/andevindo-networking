package com.andevindo.andevindonetworking;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;

/**
 * Created by -H- on 2/20/2016.
 */
public class VolleyModel<T extends NetworkModel> {

    private String mUrl = Network.getAPIAddress();
    private Map<String, String> mParameter;
    private Map<String, String> mHeaders;
    private HttpEntity mHttpEntity;
    private NetworkMethod mNetworkMethod;
    private boolean mIsList = false;
    private BaseAPIListener mBaseAPIListener;
    private int[] mResponseCodes = new int[1];


    private VolleyModel(ParameterBuilder parameterBuilder) {
        mUrl += parameterBuilder.mUrl;
        mParameter = parameterBuilder.mParameter;
        mHeaders = parameterBuilder.mHeaders;
        mNetworkMethod = parameterBuilder.mNetworkMethod;
        mResponseCodes = parameterBuilder.mResponseCodes;
    }

    private VolleyModel(MultiPartEntityBuilder multiPartEntityBuilder) {
        mUrl += multiPartEntityBuilder.mUrl;
        mHttpEntity = multiPartEntityBuilder.mHttpEntity;
        mHeaders = multiPartEntityBuilder.mHeaders;
        mNetworkMethod = multiPartEntityBuilder.mNetworkMethod;
        mResponseCodes = multiPartEntityBuilder.mResponseCodes;
    }

    public int[] getResponseCodes() {
        return mResponseCodes;
    }

    public NetworkMethod getNetworkMethod() {
        return mNetworkMethod;
    }

    public void setNetworkMethod(NetworkMethod networkMethod) {
        mNetworkMethod = networkMethod;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = mUrl + url;
    }

    public HttpEntity getHttpEntity(){
        return mHttpEntity;
    }

    public Map<String, String> getParameter() {
        return mParameter;
    }

    public void setParameter(Map<String, String> parameter) {
        mParameter = parameter;
    }

    public void addParameter(String key, String value){
        if (mParameter==null)
            mParameter = new HashMap<>();
        mParameter.put(key, value);
    }

    public void setHeaders(Map<String, String> headers){
        mHeaders = headers;
    }

    public Map<String, String> getHeaders(){
        return mHeaders;
    }

    public static class ParameterBuilder {
        private Map<String, String> mParameter;
        private Map<String, String> mHeaders;
        private String mUrl;
        private NetworkMethod mNetworkMethod = NetworkMethod.GET;
        private int[] mResponseCodes = new int[1];

        public ParameterBuilder(String url){
            mUrl = url;
        }

        public ParameterBuilder(String url, NetworkMethod networkMethod) {
            mUrl = url;
            mNetworkMethod = networkMethod;
        }

        public ParameterBuilder setNetworkMethod(NetworkMethod networkMethod) {
            mNetworkMethod = networkMethod;
            return this;
        }

        public ParameterBuilder addParameter(String key, String value){
            if (mParameter==null)
                mParameter = new HashMap<>();
            mParameter.put(key, value);
            return this;
        }

        public ParameterBuilder setHeaders(Map<String, String> headers){
            mHeaders = headers;
            return this;
        }

        public ParameterBuilder setResponseCodes(int... responseCodes){
            mResponseCodes= responseCodes;
            return this;
        }

        public VolleyModel build(){
            return new VolleyModel(this);
        }
    }

    public static class MultiPartEntityBuilder {
        private MultipartEntityBuilder mMultipartEntityBuilder;
        private HttpEntity mHttpEntity;
        private Map<String, String> mHeaders;
        private String mUrl = Network.getAPIAddress();
        private NetworkMethod mNetworkMethod = NetworkMethod.POST;
        private int[] mResponseCodes = new int[1];

        public MultiPartEntityBuilder(String url){
            mUrl = url;
            mMultipartEntityBuilder = MultipartEntityBuilder.create();
        }

        public MultiPartEntityBuilder addFile(String key, File file){
            FileBody fileBody = new FileBody(file);
            mMultipartEntityBuilder.addPart(key, fileBody);
            return this;
        }

        public MultiPartEntityBuilder addString(String key, String value){
            mMultipartEntityBuilder.addTextBody(key, value);
            return this;
        }

        public MultiPartEntityBuilder setHeaders(Map<String, String> headers){
            mHeaders = headers;
            return this;
        }

        public MultiPartEntityBuilder setResponseCodes(int... responseCodes){
            mResponseCodes= responseCodes;
            return this;
        }

        public VolleyModel build(){
            mHttpEntity = mMultipartEntityBuilder.build();
            return new VolleyModel(this);
        }
    }

}
