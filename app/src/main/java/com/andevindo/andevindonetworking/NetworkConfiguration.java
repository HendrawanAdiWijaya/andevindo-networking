package com.andevindo.andevindonetworking;

/**
 * Created by heendher on 11/3/2016.
 */

public class NetworkConfiguration {

    private int mSocketTimeOut;
    private int mRetries;
    private int mSuccessCode;
    private String mAPIAddress;
    private String mJSONKeyResult;

    private NetworkConfiguration(Builder builder){
        mSocketTimeOut = builder.mSocketTimeOut;
        mRetries = builder.mRetries;
        mSuccessCode = builder.mSuccessCode;
        mAPIAddress = builder.mAPIAddress;
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

    public int getSuccessCode() {
        return mSuccessCode;
    }

    public void setSuccessCode(int successCode) {
        mSuccessCode = successCode;
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
        private int mSuccessCode = Network.getSuccessCode();
        private String mAPIAddress = Network.getAPIAddress();
        private String mJSONKeyResult = Network.getJSONKeyResult();

        public Builder() {
        }

        public void setJSONKeyResult(String JSONKeyResult) {
            mJSONKeyResult = JSONKeyResult;
        }

        public Builder setSocketTimeOut(int socketTimeOut){
            mSocketTimeOut = socketTimeOut;
            return this;
        }

        public Builder setRetries(int retries){
            mRetries = retries;
            return this;
        }

        public Builder setSuccessCode(int successCode){
            mSuccessCode = successCode;
            return this;
        }

        public Builder setAPIAddress(String apiAddress){
            mAPIAddress = apiAddress;
            return this;
        }

        public NetworkConfiguration build(){
            return new NetworkConfiguration(this);
        }
    }

}
