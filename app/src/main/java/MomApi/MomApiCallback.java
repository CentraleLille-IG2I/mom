package MomApi;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Robin on 25/05/2016.
 */
public class MomApiCallback implements Response.Listener<JSONObject>, Response.ErrorListener{
    private RequestCallback callback;

    public MomApiCallback(RequestCallback c) {
        callback = c;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("@", "err:" + error.getMessage());
    }

    @Override
    public void onResponse(JSONObject  response) {
        // Display the first 500 characters of the response string.
        Log.d("@", "?");
        Log.d("@", response.toString());
        //try {
            //JSONObject jo = new JSONObject(response);
            //callback.callback(response);
        //} catch (JSONException e) {
        //    e.printStackTrace();
        //}
    }
}
