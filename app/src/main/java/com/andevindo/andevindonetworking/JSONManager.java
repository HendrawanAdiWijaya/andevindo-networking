package com.andevindo.andevindonetworking;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
/**
 * Created by heendher on 8/1/2016.
 */
public class JSONManager {

    public static String getJSONFromAsset(Context context, String assetName) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(assetName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static boolean checkCode(JSONObject jsonObject, int codePattern) {
        int code = 0;
        try {
            code = jsonObject.getInt("kode");

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        if (code == codePattern)
            return true;
        else
            return false;
    }

}
