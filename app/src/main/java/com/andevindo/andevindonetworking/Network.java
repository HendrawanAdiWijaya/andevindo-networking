package com.andevindo.andevindonetworking;

import android.content.Context;

import java.util.Map;

/**
 * Created by heendher on 11/3/2016.
 */
public class Network {

    private static int sSOCKET_TIME_OUT = NetworkConfig.SOCKET_TIME_OUT;
    private static int sRETRIES = NetworkConfig.RETRIES;

    private static String sAPI_ADDRESS = NetworkConfig.API_ADDRESS;

    private static Map<String, String> sHEADER = NetworkConfig.HEADER;

    private static Network sINSTANCE;

    public static Network setNetworkConfiguration(NetworkConfiguration networkConfiguration){
        sSOCKET_TIME_OUT = networkConfiguration.getSocketTimeOut();
        sRETRIES = networkConfiguration.getRetries();
        sAPI_ADDRESS = networkConfiguration.getAPIAddress();
        sHEADER = networkConfiguration.getHeaders();
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

    public static Map<String, String> getHeader(){
        return sHEADER;
    }

    public static int getSocketTimeOut() {
        return sSOCKET_TIME_OUT;
    }

    public static int getRetries() {
        return sRETRIES;
    }

    public static String getAPIAddress() {
        return sAPI_ADDRESS;
    }
}
