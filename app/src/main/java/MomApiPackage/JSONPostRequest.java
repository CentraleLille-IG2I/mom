package MomApiPackage;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
;import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * Created by Robin on 27/05/2016.
 */
public class JSONPostRequest extends Request<JSONObject> {
    int mMethod;
    String mUrl;
    Map<String, String> mParams;
    Response.Listener<JSONObject> mListener;
    //ErrorListener mErrorListener;


    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        Log.d("@", "olalalaSaMarche");
        return mParams;
    };

    public JSONPostRequest(int method, String url, Map<String, String> params, Response.Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mMethod = method;
        this.mUrl = url;
        this.mParams = params;
        this.mListener = reponseListener;
        //this.mErrorListener = errorListener;
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        mListener.onResponse(response);
    }
}
