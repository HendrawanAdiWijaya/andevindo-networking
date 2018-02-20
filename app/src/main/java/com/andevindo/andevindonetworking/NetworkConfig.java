package com.andevindo.andevindonetworking;

import java.util.Map;

/**
 * Created by -H- on 12/16/2015.
 */
public class NetworkConfig {

    protected final static int SOCKET_TIME_OUT = 9000;
    public final static int RETRIES = 0;

    public final static int SUCCESS_CODE = 1;
    public final static int FAILED_CODE = 100;

    public final static String JSON_KEY_RESULT = "data";

    public static String PREFERENCES_NAME = "com.andevindo";
    public static String API_ADDRESS = "andevindo.com";
    public static Map<String, String> HEADER = null;
}
