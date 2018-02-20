package com.andevindo.andevindonetworking;

import java.util.Map;

/**
 * Created by heendher on 11/3/2016.
 */

public class NetworkConfiguration {

    private int mSocketTimeOut;
    private int mRetries;
    private String mAPIAddress;
    private Map<String, String> mHeaders;

    private NetworkConfiguration(Builder builder){
        mSocketTimeOut = builder.mSocketTimeOut;
        mRetries = builder.mRetries;
        mAPIAddress = builder.mAPIAddress;
        mHeaders = builder.mHeaders;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public int getSocketTimeOut() {
        return mSocketTimeOut;
    }

    public void setSocketTimeOut(int socketTimeOut) {
        mSocketTimeOut = socketTimeOut;
    }

    public int getRetries() {
        return mRetries;
    }

    public void setRetries(int retries) {
        mRetries = retries;
    }

    public String getAPIAddress() {
        return mAPIAddress;
    }

    public void setAPIAddress(String APIAddress) {
        mAPIAddress = APIAddress;
    }

    public static class Builder{

        private int mSocketTimeOut = Network.getSocketTimeOut();
        private int mRetries = Network.getRetries();
        private String mAPIAddress = Network.getAPIAddress();
        private Map<String, String> mHeaders = null;

        public Builder() {
        }

        public Builder(String APIAddress) {
            mAPIAddress = APIAddress;
        }

        public Builder setHeaders(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public Builder setSocketTimeOut(int socketTimeOut){
            mSocketTimeOut = socketTimeOut;
            return this;
        }

        public Builder setRetries(int retries){
            mRetries = retries;
            return this;
        }

        public NetworkConfiguration build(){
            return new NetworkConfiguration(this);
        }
    }

}
