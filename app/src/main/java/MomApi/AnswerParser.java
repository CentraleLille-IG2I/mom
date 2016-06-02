package MomApi;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.richou.mom.R;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import MomApi.Model.Invitation;

/**
 * Created by Robin on 29/05/2016.
 */
public class AnswerParser <T> implements Response.Listener<JSONObject>, Response.ErrorListener{
    protected RequestCallback<T> callback;
    private Class<T> kek;

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
            callback.onError(MomErrors.UNKNOW_ERROR);
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
