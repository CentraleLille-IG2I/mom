package MomApiPackage;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.richou.mom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import MomApiPackage.Model.Rank;
import MomApiPackage.Model.Event;
import MomApiPackage.Model.EventStatus;
import MomApiPackage.Model.Invitation;
import MomApiPackage.Model.Task;
import MomApiPackage.Model.TaskComment;
import MomApiPackage.Model.TaskItem;
import MomApiPackage.Model.User;

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
        Log.d("@", "cookie : " + cookie);
        /*if (cookie!=null)
            mParams.put(cookie.getName(), cookie.getValue());*/

        Log.d("@", "params : "+mParams.toString());
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

    public void getUser(int userId, RequestCallback<User> callback)  {
        request(Request.Method.GET, "/user/"+userId, new HashMap<String, String>(), new AnswerParser<User>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", response.toString());
                try {
                    this.callback.onSuccess(new User(response));
                } catch (JSONException e1) {
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
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
                    this.callback.onSuccess(ret);
                } catch (JSONException e) {
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void getUserEvents(User user, RequestCallback<List<Event>> callback) {
        request(Request.Method.GET, "/user/" + user.getId() + "/events/", new HashMap<String, String>(), new AnswerParser<List<Event>>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", response.toString());
                JSONArray list = null;
                List<Event> ret = new ArrayList<Event>();

                try {
                    list = response.getJSONArray("events");

                    for (int i = 0; i < list.length(); i++) {
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

    public void createEventStatus(Event event, String message, RequestCallback<EventStatus> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("content", message);
        p.put("pk_event", ""+event.getId());

        request(Request.Method.POST, "/status/create", p, new AnswerParser<EventStatus>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("@", response.toString());
                    this.callback.onSuccess(new EventStatus(response));
                } catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void getEventTask(Event event, RequestCallback<List<Task>> callback) {
        request(Request.Method.GET, "/event/"+event.getId()+"/tasks", new HashMap<String, String>(), new AnswerParser<List<Task>>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", response.toString());
                JSONArray list = null;
                List<Task> ret = new ArrayList<Task>();

                try {
                    list = response.getJSONArray("tasks");

                    for (int i = 0; i < list.length(); i++) {
                        Log.d("@", list.getJSONObject(i).toString());
                        ret.add(new Task(list.getJSONObject(i)));
                    }
                    this.callback.onSuccess(ret);
                }
                catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void createTask(Event event, String name, String description, RequestCallback<Task> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("name", name);
        p.put("description", description);
        p.put("pk_event", ""+event.getId());

        request(Request.Method.POST, "/task/create", p, new AnswerParser<Task>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("@", response.toString());
                    this.callback.onSuccess(new Task(response));
                } catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void getTaskItems(Task task, RequestCallback<List<TaskItem>> callback) {
        request(Request.Method.GET, "/task/"+task.getId()+"/items", new HashMap<String, String>(), new AnswerParser<List<TaskItem>>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", response.toString());
                JSONArray list = null;
                List<TaskItem> ret = new ArrayList<TaskItem>();

                try {
                    list = response.getJSONArray("items");

                    for (int i = 0; i < list.length(); i++) {
                        Log.d("@", list.getJSONObject(i).toString());
                        ret.add(new TaskItem(list.getJSONObject(i)));
                    }
                    this.callback.onSuccess(ret);
                }
                catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void getTaskComments(Task task, RequestCallback<List<TaskComment>> callback) {
        request(Request.Method.GET, "/task/"+task.getId()+"/comments", new HashMap<String, String>(), new AnswerParser<List<TaskComment>>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", response.toString());
                JSONArray list = null;
                List<TaskComment> ret = new ArrayList<TaskComment>();

                try {
                    list = response.getJSONArray("comments");

                    for (int i = 0; i < list.length(); i++) {
                        Log.d("@", list.getJSONObject(i).toString());
                        ret.add(new TaskComment(list.getJSONObject(i)));
                    }
                    this.callback.onSuccess(ret);
                }
                catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void getTaskUsers(Task task, RequestCallback<List<User>> callback) {
        request(Request.Method.GET, "/task/"+task.getId()+"/users", new HashMap<String, String>(), new AnswerParser<List<User>>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", response.toString());
                JSONArray list = null;
                List<User> ret = new ArrayList<User>();

                try {
                    list = response.getJSONArray("users");

                    for (int i = 0; i < list.length(); i++) {
                        Log.d("@", list.getJSONObject(i).toString());
                        ret.add(new User(list.getJSONObject(i)));
                    }
                    this.callback.onSuccess(ret);
                }
                catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void addUserInTask(User user, Task task, RequestCallback<Integer> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("pk_user", "" + user.getId());

        request(Request.Method.POST, "/task/" + task.getId() + "/add_user", p, new AnswerParser<Integer>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("@", response.toString());
                    new Task(response);
                    this.callback.onSuccess(1);
                } catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void addTaskComment(Task task, String comment, RequestCallback<Integer> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("pk_task", "" + task.getId());
        p.put("content", comment);

        request(Request.Method.POST, "/comment/create", p, new AnswerParser<Integer>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("@", response.toString());
                    new TaskComment(response);
                    this.callback.onSuccess(1);
                } catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void addTaskItem(Task task, String name, RequestCallback<Integer> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("pk_task", "" + task.getId());
        p.put("name", name);

        request(Request.Method.POST, "/task_item/create", p, new AnswerParser<Integer>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("@", response.toString());
                    new TaskItem(response);
                    this.callback.onSuccess(1);
                } catch (JSONException e) {
                    Log.d("@", e.toString());
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void editTaskItem(TaskItem taskItem, RequestCallback<Integer> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("task_item_pk", "" + taskItem.getId());
        p.put("completed", "" + taskItem.isCompleted());
        p.put("name", taskItem.getName());

        request(Request.Method.POST, "/task_item/edit", p, new AnswerParser<Integer>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("@", response.toString());
                    new TaskItem(response);
                    this.callback.onSuccess(1);
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
                Log.d("@", "getUserByEmail: " + response);
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

    public void getEventRanks(Event event, RequestCallback<List<Rank>> callback) {
        request(Request.Method.GET, "/event/"+event.getId()+"/ranks", new HashMap<String, String>(), new AnswerParser<List<Rank>>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@", "getEventRanks: "+response.toString());
                List<Rank> result = new ArrayList<>();
                try {
                    JSONArray ranks = response.getJSONArray("ranks");
                    JSONObject rank;

                    for (int i=0; i<ranks.length(); i++) {
                        rank = ranks.getJSONObject(i);
                        result.add(new Rank(rank));
                    }
                    this.callback.onSuccess(result);
                } catch (JSONException e) {
                    e.getMessage();
                    this.callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }

    public void createInvitation(Event event, User user, Rank rank, String message, RequestCallback<Invitation> callback) {
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("pk_event", String.valueOf(event.getId()));
        p.put("pk_user", String.valueOf(user.getId()));
        p.put("pk_rank", String.valueOf(rank.getId()));
        p.put("content", message);

        request(Request.Method.POST, "/invitation/create", p, new AnswerParser<Invitation>(callback) {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("@", "createInvitation: " + response.toString());
                    callback.onSuccess(new Invitation(response.getInt("pk"),
                            Invitation.getStatusFromString(response.getString("status")),
                            response.getString("content"),
                            response.getString("date_created"),
                            response.getInt("pk_event"),
                            response.getInt("pk_user_created_by"),
                            null,
                            response.getInt("pk_rank")
                    ));
                } catch (JSONException e) {
                    Log.e("@", e.getMessage());
                    callback.onError(MomErrors.MALFORMED_DATA);
                }
            }
        });
    }
}
