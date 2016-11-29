package com.andevindo.andevindonetworking;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by heendher on 11/9/2016.
 */

public abstract class NetworkModel {

    public abstract void objectFromJSON(JSONObject jsonObject);
    public abstract void listFromJSON(JSONObject jsonObject);

}
