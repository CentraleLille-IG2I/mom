package MomApi;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by Robin on 25/05/2016.
 */
public interface RequestCallback extends Response.Listener<JSONObject>, Response.ErrorListener{
}
