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

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import MomApi.Model.Event;
import MomApi.Model.EventStatus;
import MomApi.Model.Invitation;
import MomApi.Model.User;

/**
 * Created by Robin on 25/05/2016.
 */
public class MomApi {
    private RequestQueue queue;
    private String mainUrl;

    static private CookieManager manager = null;

    public MomApi(Context c) {
        if (manager==null) {
            Log.d("@", "cookie manager renewed");
            manager = new CookieManager();
            CookieHandler.setDefault(manager);
        }

        queue = Volley.newRequestQueue(c);
        mainUrl = c.getString(R.string.MOM_SERVER);

        Log.d("@", mainUrl);
    }

    private <T extends Response.Listener<JSONObject> & Response.ErrorListener> void request (int m, String res, final HashMap<String, String> params, T c) {
        // Request a string response from the provided URL..
        final HashMap<String, String> mParams = params;
        HttpCookie cookie = getCookie();
        Log.d("@", "cookie : "+cookie);
        if (cookie!=null)
            mParams.put(cookie.getName(), cookie.getValue());

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

    private HttpCookie getCookie() {
        for (HttpCookie c : manager.getCookieStore().getCookies()) {
            if (c.getName().equals("sessionid"))
                return c;
        }
        return null;
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
                    Collections.sort(ret);
                    /*Collections.sort(ret, new Comparator<EventStatus>() {
                        @Override
                        public int compare(EventStatus lhs, EventStatus rhs) {
                            DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:S", Locale.ENGLISH);
                            try {
                                return format.parse(lhs.getCreationDate()).compareTo(format.parse(rhs.getCreationDate()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return 0;
                            }
                        }
                    });*/
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

    public void createEvent(String eventName, String eventDate, String eventPlace, String eventDescription, RequestCallback<Event> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("name", eventName);
        p.put("description", eventDescription);
        p.put("date", eventDate);
        p.put("place", eventPlace);

        request(Request.Method.POST, "/event/create", p, new AnswerParser<Event>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("@", response.toString());
                    this.callback.onSuccess(new Event(response));
                } catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void register(String firstName, String lastName, String email, String phone, String password, RequestCallback<Integer> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("first_name", firstName);
        p.put("last_name", lastName);
        p.put("email", email);
        p.put("phone_number", phone);
        p.put("password", password);
        request(Request.Method.POST, "/register", p, new AnswerParser<Integer>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("@", response.toString());
                    this.callback.onSuccess(new Integer(response.getInt("pk_user")));
                } catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void getEventInvitations(Event event, RequestCallback<List<Invitation>> callback) {
        request(Request.Method.GET, "/event/"+event.getId()+"/invitations", new HashMap<String, String>(), new AnswerParser<List<Invitation>>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                List<Invitation> result = new ArrayList<>();
                try {
                    Log.d("@", response.toString());
                    JSONArray invitations = response.getJSONArray("invitations");
                    for(int i = 0 ; i<invitations.length() ; ++i) {
                        JSONObject invitation = invitations.getJSONObject(i);
                        JSONObject user = invitation.getJSONObject("user_invited");
                        Invitation.Status s;
                        switch(invitation.getString("status")) {
                            case "A": s = Invitation.Status.ACCEPTED; break;
                            case "P": s = Invitation.Status.PENDING; break;
                            case "R": s = Invitation.Status.REFUSED; break;
                            default: throw new JSONException("Status is inconsistent");
                        }
                        result.add(new Invitation(invitation.getInt("pk"),
                                s,
                                invitation.getString("content"),
                                invitation.getString("date_created"),
                                invitation.getInt("pk_event"),
                                invitation.getInt("pk_user_created_by"),
                                new User(user.getInt("pk"), user.getString("first_name"), user.getString("last_name")),
                                invitation.getInt("pk_rank")));
                    }
                } catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
                this.callback.onSuccess(result);
            }
        });
    }

    public void getUserByEmail(String email, RequestCallback<User> callback) {
        HashMap<String, String> p = new HashMap<>();

        p.put("email", email);
        request(Request.Method.POST, "/user/search", p, new AnswerParser<User>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", "getUserByEmail: "+response);
                try {
                    JSONObject user = response.getJSONObject("user");
                    if (!user.isNull("pk")) {
                        callback.onSuccess(new User(user.getInt("pk"),
                                user.getString("first_name"),
                                user.getString("last_name")));
                    } else {
                        callback.onSuccess(null);
                    }
                }
                catch(JSONException e) {
                    Log.e("@", e.getMessage());
                    callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }
}
