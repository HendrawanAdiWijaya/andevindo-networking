package com.andevindo.andevindonetworking;

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
    private MultipartEntityBuilder mMultipartEntityBuilder;
    private boolean mIsUsingHeader;
    private String mExtraUrl;
    private boolean mIsUsingParameter;

    private VolleyModel(ParameterBuilder parameterBuilder) {
        mUrl += parameterBuilder.mUrl;
        mExtraUrl = parameterBuilder.mUrl;
        mParameter = parameterBuilder.mParameter;
        mHeaders = parameterBuilder.mHeaders;
        mNetworkMethod = parameterBuilder.mNetworkMethod;
        mIsUsingHeader = parameterBuilder.isUsingHeader;
        mIsUsingParameter = true;
    }

    private VolleyModel(MultiPartEntityBuilder multiPartEntityBuilder) {
        mUrl += multiPartEntityBuilder.mUrl;
        mExtraUrl = multiPartEntityBuilder.mUrl;
        mHttpEntity = multiPartEntityBuilder.mHttpEntity;
        mMultipartEntityBuilder = multiPartEntityBuilder.mMultipartEntityBuilder;
        mHeaders = multiPartEntityBuilder.mHeaders;
        mNetworkMethod = multiPartEntityBuilder.mNetworkMethod;
        mIsUsingHeader = multiPartEntityBuilder.isUsingHeader;
        mIsUsingParameter = false;
    }

    public boolean isUsingHeader() {
        return mIsUsingHeader;
    }

    public void addString(String key, String value) {
        mMultipartEntityBuilder.addTextBody(key, value);
        mHttpEntity = mMultipartEntityBuilder.build();
    }

    public void setCustomBaseURL(String baseURL) {
        mUrl = baseURL;
        mUrl += mExtraUrl;
    }

    public NetworkMethod getNetworkMethod() {
        return mNetworkMethod;
    }

    public void setNetworkMethod(NetworkMethod networkMethod) {
        mNetworkMethod = networkMethod;
    }

    public String getUrl() {
        if (mNetworkMethod == NetworkMethod.GET) {
            if (mParameter != null) {
                int index = 0;
                int parameterSize = mParameter.size();
                for (Map.Entry<String, String> parameter : mParameter.entrySet()) {
                    if (index == 0) {
                        mUrl += "?";
                    }
                    mUrl += parameter.getKey() + "=" + parameter.getValue();

                    if (index++ != parameterSize - 1) {
                        mUrl += "&";
                    }
                }
            }
            return mUrl;
        } else {
            return mUrl;
        }

    }

    public HttpEntity getHttpEntity() {
        return mHttpEntity;
    }

    public Map<String, String> getParameter() {
        if (mNetworkMethod == NetworkMethod.GET)
            return null;
        else
            return mParameter;
    }

    public void setParameter(Map<String, String> parameter) {
        mParameter = parameter;
        mMultipartEntityBuilder = null;
    }

    public void setParameter(MultiPartEntityBuilder multiPartEntityBuilder) {
        multiPartEntityBuilder = multiPartEntityBuilder;
        mParameter = null;
    }

    void addParameterLocal(String key, String value) {
        if (mIsUsingParameter) {
            if (mParameter == null)
                mParameter = new HashMap<>();
            mParameter.put(key, value);
        } else {
            mMultipartEntityBuilder.addTextBody(key, value);
            mHttpEntity = mMultipartEntityBuilder.build();
        }
    }

    public void addParameter(String key, String value) {
        addParameterLocal(key, value);
    }

    public void addParameter(String key, int value) {
        addParameterLocal(key, value + "");
    }

    public void addParameter(String key, float value) {
        addParameterLocal(key, value + "");
    }

    public void addParameter(String key, double value) {
        addParameterLocal(key, value + "");
    }

    public void addParameter(String key, boolean value) {
        addParameterLocal(key, value + "");
    }

    public void addParameter(String key, File value) {
        addParameterLocal(key, value + "");
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public static class ParameterBuilder {
        private Map<String, String> mParameter;
        private Map<String, String> mHeaders;
        private String mUrl;
        private NetworkMethod mNetworkMethod = NetworkMethod.GET;
        private boolean isUsingHeader;
        //private int[] mResponseCodes = new int[1];

        public ParameterBuilder(String url) {
            mUrl = url;
        }

        public ParameterBuilder addParameterLocal(String key, String value) {
            if (mParameter == null)
                mParameter = new HashMap<>();
            mParameter.put(key, value);
            return this;
        }

        public ParameterBuilder setNetworkMethod(NetworkMethod networkMethod) {
            mNetworkMethod = networkMethod;
            return this;
        }

        public ParameterBuilder addParameter(String key, String value) {
            return addParameterLocal(key, value);
        }

        public ParameterBuilder addParameter(String key, int value) {
            return addParameterLocal(key, value + "");
        }

        public ParameterBuilder addParameter(String key, float value) {
            return addParameterLocal(key, value + "");
        }

        public ParameterBuilder addParameter(String key, double value) {
            return addParameterLocal(key, value + "");
        }

        public ParameterBuilder addParameter(String key, boolean value) {
            return addParameterLocal(key, value + "");
        }

        public ParameterBuilder setUsingHeader(boolean usingHeader) {
            isUsingHeader = usingHeader;
            return this;
        }

        public ParameterBuilder setHeaders(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public VolleyModel build() {
            return new VolleyModel(this);
        }
    }

    public static class MultiPartEntityBuilder {
        private MultipartEntityBuilder mMultipartEntityBuilder;
        private HttpEntity mHttpEntity;
        private Map<String, String> mHeaders;
        private String mUrl;
        private NetworkMethod mNetworkMethod = NetworkMethod.POST;
        private boolean isUsingHeader;

        public MultiPartEntityBuilder(String url) {
            mUrl = url;
            mMultipartEntityBuilder = MultipartEntityBuilder.create();
        }

        public MultiPartEntityBuilder addParameter(String key, File file) {
            if (file != null) {
                FileBody fileBody = new FileBody(file);
                mMultipartEntityBuilder.addPart(key, fileBody);
            } else {
                mMultipartEntityBuilder.addPart(key, null);
            }
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, String value) {
            mMultipartEntityBuilder.addTextBody(key, value);
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, int value) {
            mMultipartEntityBuilder.addTextBody(key, value + "");
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, boolean value) {
            mMultipartEntityBuilder.addTextBody(key, value + "");
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, float value) {
            mMultipartEntityBuilder.addTextBody(key, value + "");
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, double value) {
            mMultipartEntityBuilder.addTextBody(key, value + "");
            return this;
        }

        public MultiPartEntityBuilder setUsingHeader(boolean usingHeader) {
            isUsingHeader = usingHeader;
            return this;
        }

        public MultiPartEntityBuilder setHeaders(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public VolleyModel build() {
            mHttpEntity = mMultipartEntityBuilder.build();
            return new VolleyModel(this);
        }
    }

}
