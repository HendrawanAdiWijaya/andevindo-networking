package com.andevindo.andevindonetworking;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by -H- on 11/12/2015.
 */
class VolleyErrorMessage {

    public static boolean isConnectionProblem(VolleyError error){
        if (error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError) {
            return true;
        }else
            return false;
    }
}
