package com.andevindo.andevindonetworking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by -Andevindo- on 10/8/2015.
 */
class VolleySingleton {

    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static VolleySingleton initialize(Context context) {
        if(sInstance == null){
            sInstance = new VolleySingleton(context);
        }
        return sInstance;
    }

    public static VolleySingleton getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
