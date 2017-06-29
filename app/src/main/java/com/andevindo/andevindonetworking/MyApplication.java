package com.andevindo.andevindonetworking;

import android.app.Application;

/**
 * Created on   : 6/29/2017
 * Developed by : Hendrawan Adi Wijaya
 * Github       : https://github.com/andevindo
 * Website      : http://www.andevindo.com
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VolleySingleton.initialize(this);
    }
}
