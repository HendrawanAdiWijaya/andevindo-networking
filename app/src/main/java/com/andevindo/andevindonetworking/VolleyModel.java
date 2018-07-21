package com.andevindo.andevindonetworking;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private Map<String, String> mParameters;
    private Map<String, String> mHeaders;
    private HttpEntity mHttpEntity;
    private NetworkMethod mNetworkMethod;
    private MultipartEntityBuilder mMultipartEntityBuilder;
    private boolean mIsUsingHeader;
    private String mExtraUrl;
    private boolean mIsUsingParameter;
    private boolean mIsUsingLaravelWebService;

    public boolean isUsingLaravelWebService() {
        return mIsUsingLaravelWebService;
    }

    /* private VolleyModel(ParameterBuilder parameterBuilder) {
        mUrl += parameterBuilder.mUrl;
        mExtraUrl = parameterBuilder.mUrl;
        mParameters = parameterBuilder.mParameters;
        mHeaders = parameterBuilder.mHeaders;
        mNetworkMethod = parameterBuilder.mNetworkMethod;
        mIsUsingHeader = parameterBuilder.isUsingHeader;
        mIsUsingParameter = true;
    }*/

    private VolleyModel(ParameterBuilder parameterBuilder) {
        mUrl += parameterBuilder.mUrl;
        mIsUsingLaravelWebService = parameterBuilder.mIsUsingLaravelWebService;
        mExtraUrl = parameterBuilder.mUrl;
        mHttpEntity = parameterBuilder.mHttpEntity;
        mMultipartEntityBuilder = parameterBuilder.mMultipartEntityBuilder;
        mParameters = parameterBuilder.mParameters;
        mHeaders = parameterBuilder.mHeaders;
        mNetworkMethod = parameterBuilder.mNetworkMethod;
        mIsUsingHeader = parameterBuilder.isUsingHeader;
        mIsUsingParameter = false;
    }

    public boolean isUsingHeader() {
        return mIsUsingHeader;
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
            if (mParameters != null) {
                int index = 0;
                int parameterSize = mParameters.size();
                for (Map.Entry<String, String> parameter : mParameters.entrySet()) {
                    if (index == 0) {
                        mUrl += "?";
                    }
                    try {
                        mUrl += parameter.getKey() + "=" + URLEncoder.encode(parameter.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        mUrl += parameter.getKey() + "=" + parameter.getValue();
                    }

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
        if (mParameters != null)
            for (Map.Entry<String, String> parameter : mParameters.entrySet()) {
                mMultipartEntityBuilder.addTextBody(parameter.getKey(), parameter.getValue());
            }
        return mMultipartEntityBuilder.build();
    }

    public Map<String, String> getParameters() {
        if (mNetworkMethod == NetworkMethod.GET)
            return null;
        else
            return mParameters;
    }

    void addParameterLocal(String key, String value) {
        if (mParameters == null)
            mParameters = new HashMap<>();
        mParameters.put(key, value);
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
        if (value != null) {
            FileBody fileBody = new FileBody(value);
            mMultipartEntityBuilder.addPart(key, fileBody);
        }
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    /*public static class ParameterBuilder {
        private Map<String, String> mParameters;
        private Map<String, String> mHeaders;
        private String mUrl;
        private NetworkMethod mNetworkMethod = NetworkMethod.GET;
        private boolean isUsingHeader;
        //private int[] mResponseCodes = new int[1];

        public ParameterBuilder(String url) {
            mUrl = url;
        }

        public ParameterBuilder addParameterLocal(String key, String value) {
            if (mParameters == null)
                mParameters = new HashMap<>();
            mParameters.put(key, value);
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
    }*/

    public static class ParameterBuilder {
        private MultipartEntityBuilder mMultipartEntityBuilder;
        private HttpEntity mHttpEntity;
        private Map<String, String> mHeaders;
        private Map<String, String> mParameters;
        private String mUrl;
        private NetworkMethod mNetworkMethod = NetworkMethod.POST;
        private boolean isUsingHeader;
        private boolean mIsUsingLaravelWebService;

        public ParameterBuilder setNetworkMethod(NetworkMethod networkMethod, boolean isUsingLaravelWebService) {
            mNetworkMethod = networkMethod;
            mIsUsingLaravelWebService = isUsingLaravelWebService;
            if (networkMethod == NetworkMethod.PUT && isUsingLaravelWebService)
                addParameter("_method", "PUT");
            else if (networkMethod == NetworkMethod.DELETE && isUsingLaravelWebService)
                addParameter("_method", "DELETE");
            return this;
        }

        public ParameterBuilder(String url) {
            mUrl = url;
            mMultipartEntityBuilder = MultipartEntityBuilder.create();
        }

        public ParameterBuilder addParameter(String key, File file) {
            if (file != null) {
                FileBody fileBody = new FileBody(file);
                mMultipartEntityBuilder.addPart(key, fileBody);
            }
            return this;
        }

        public ParameterBuilder addParameterLocal(String key, String value) {
            if (mParameters == null)
                mParameters = new HashMap<>();
            mParameters.put(key, value);
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
            mHttpEntity = mMultipartEntityBuilder.build();
            return new VolleyModel(this);
        }
    }

}
