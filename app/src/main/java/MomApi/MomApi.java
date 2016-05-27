package MomApi;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.richou.mom.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robin on 25/05/2016.
 */
public class MomApi {
    private RequestQueue queue;
    private String mainUrl;

    public MomApi(Context c) {
        queue = Volley.newRequestQueue(c);
        mainUrl = c.getString(R.string.MOM_SERVER);

        Log.d("@", mainUrl);
    }


    private void request(int m, String res, final HashMap<String, String> params, RequestCallback c) {
        // Request a string response from the provided URL..
        final HashMap<String, String> mParams = params;
        //MomApiCallback mac = new MomApiCallback(c);
        Log.d("@", mParams.toString());
        JSONPostRequest stringRequest = new JSONPostRequest(m,
                                                            mainUrl+res,
                                                            params,
                                                            c,
                                                            c);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getUser(int UserId, RequestCallback c) {
        request(Request.Method.GET, "/user/" + UserId, new HashMap<String, String>(), c);
    }

    public void login(String email, String pass, RequestCallback c) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("email", email);
        p.put("password", pass);
        request(Request.Method.POST, "/sign_in/", p, c);
    }
}
