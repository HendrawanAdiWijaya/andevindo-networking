package com.andevindo.andevindonetworking;

/**
 * Created on   : 2/15/2018
 * Developed by : Hendrawan Adi Wijaya
 * Github       : https://github.com/andevindo
 * Website      : http://www.andevindo.com
 */

public class NetworkAPIResponse {

    //General
    public static final int SUCCESS = 1;
    public static final int NULL = 2;
    public static final int PARAMETER_MISSING = 3;
    public static final int UNAUTHENTICATED = 7;

    //User
    public static final int EMAIL_ALREADY_REGISTERED = 21;
    public static final int USER_NOT_ACTIVATED_YET = 22;
    public static final int EMAIL_NOT_FOUND = 23;
    public static final int WRONG_PASSWORD = 24;

}
