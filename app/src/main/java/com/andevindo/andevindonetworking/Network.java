package com.andevindo.andevindonetworking;

import android.content.Context;

/**
 * Created by heendher on 11/3/2016.
 */
public class Network {

    private static int sSOCKET_TIME_OUT = NetworkConfig.SOCKET_TIME_OUT;
    private static int sRETRIES = NetworkConfig.RETRIES;

    private static int sSUCCESS_CODE = NetworkConfig.SUCCESS_CODE;
    private static int sFAILED_CODE = NetworkConfig.FAILED_CODE;

    private static String sAPI_ADDRESS = NetworkConfig.API_ADDRESS;
    private static String sJSON_KEY_RESULT = NetworkConfig.JSON_KEY_RESULT;

    private static Network sINSTANCE;

    public static Network setNetworkConfiguration(NetworkConfiguration networkConfiguration){
        sSOCKET_TIME_OUT = networkConfiguration.getSocketTimeOut();
        sRETRIES = networkConfiguration.getRetries();
        sSUCCESS_CODE = networkConfiguration.getSuccessCode();
        sAPI_ADDRESS = networkConfiguration.getAPIAddress();
        return sINSTANCE;
    }

    public static Network getInstance(Context context) {
        if (sINSTANCE ==null){
            sINSTANCE = new Network(context);
        }
        return sINSTANCE;
    }

    private Network(Context context) {
        VolleySingleton.initialize(context);
    }

    public static int getSocketTimeOut() {
        return sSOCKET_TIME_OUT;
    }

    public static int getRetries() {
        return sRETRIES;
    }

    public static int getSuccessCode() {
        return sSUCCESS_CODE;
    }

    public static String getAPIAddress() {
        return sAPI_ADDRESS;
    }

    public static String getJSONKeyResult() {
        return sJSON_KEY_RESULT;
    }
}
