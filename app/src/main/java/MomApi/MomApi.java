package MomApi;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.richou.mom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import MomApi.Model.Event;
import MomApi.Model.EventStatus;
import MomApi.Model.User;

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

    private <T extends Response.Listener<JSONObject> & Response.ErrorListener> void request (int m, String res, final HashMap<String, String> params, T c) {
        // Request a string response from the provided URL..
        final HashMap<String, String> mParams = params;
        Log.d("@", mParams.toString());
        Log.d("@", mainUrl + res);
        JSONPostRequest stringRequest = new JSONPostRequest(m,
                                                            mainUrl+res,
                                                            params,
                                                            c,
                                                            c);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public User getUser(int userId) throws ExecutionException, InterruptedException, JSONException {
        RequestFuture<JSONObject> synchronListener = RequestFuture.newFuture();
        request(Request.Method.GET, "/user/" + userId, new HashMap<String, String>(), synchronListener);

        JSONObject answer = synchronListener.get();
        return new User(answer.getInt("pk" ),
                        answer.getString("first_name"),
                        answer.getString("last_name"),
                        answer.getString("email"),
                        answer.getString("phone_number"));
    }

    public void getEventStatuses(int eventId, RequestCallback<List<EventStatus>> callback) {
        request(Request.Method.GET, "/event/"+eventId+"/statuses/", new HashMap<String, String>(), new AnswerParser<List<EventStatus>>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", response.toString());
                JSONArray list = null;
                List<EventStatus> ret = new ArrayList<EventStatus>();

                try {
                    list = response.getJSONArray("statuses");

                    for (int i=0; i<list.length(); i++) {
                        Log.d("@", list.getJSONObject(i).toString());
                        ret.add(new EventStatus(list.getJSONObject(i)));
                    }
                    this.callback.onSuccess(ret);
                } catch (JSONException e) {
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void getUserEvents(int userId, RequestCallback<List<Event>> callback) {
        request(Request.Method.GET, "/user/"+userId+"/events/", new HashMap<String, String>(), new AnswerParser<List<Event>>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", response.toString());
                JSONArray list = null;
                List<Event> ret = new ArrayList<Event>();

                try {
                    list = response.getJSONArray("events");

                    for (int i=0; i<list.length(); i++) {
                        Log.d("@", list.getJSONObject(i).toString());
                        ret.add(new Event(list.getJSONObject(i)));
                    }
                    this.callback.onSuccess(ret);
                } catch (JSONException e) {
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void login(String email, String pass, RequestCallback<Integer> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("email", email);
        p.put("password", pass);

        request(Request.Method.POST, "/sign_in/", p, new AnswerParser<Integer>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("success")) {
                        this.callback.onSuccess(response.getInt("pk_user"));
                    }
                } catch (JSONException e) {
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void createEvent() {}
}
