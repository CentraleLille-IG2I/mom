package MomApiPackage;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Robin on 29/05/2016.
 */
public class AnswerParser <T> implements Response.Listener<JSONObject>, Response.ErrorListener{
    protected RequestCallback<T> callback;

    public AnswerParser(RequestCallback<T> callback) {
        this.callback = callback;

        //kek = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("@", error.toString());
        if (error.networkResponse != null && error.networkResponse.statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED)
            callback.onError(MomErrors.HTTP_401);
        else if (error instanceof NoConnectionError || error instanceof TimeoutError)
            callback.onError(MomErrors.NETWORK_UNAVAILABLE);
        else
            callback.onError(MomErrors.UNKNOWN_ERROR);
    }

    @Override
    public void onResponse(JSONObject response) {
        /*try {
            callback.onSuccess( kek.getConstructor(new Class<?>[] { String.class }).newInstance(response)  );
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/
    }
}
