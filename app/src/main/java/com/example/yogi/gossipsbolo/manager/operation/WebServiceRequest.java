package com.example.yogi.gossipsbolo.manager.operation;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.yogi.gossipsbolo.util.GBLoggerUtil;
import com.google.gson.Gson;
//import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class WebServiceRequest extends Request {

    private final Gson gson = new Gson();
    private Map<String, String> mHeaders;
    private String mBody;
    private Response.Listener mListener;
    private Class mClazz;

    /**
     * Make a GET, POST, PUT request
     *
     * @param method           Type of request GET/POST/PUT
     * @param url              URL of the request to make
     * @param headers          headers adviseInfo for request headers
     * @param body             body of the request if method is POST/PUT
     * @param responseListener
     * @param errorListener
     */
    public WebServiceRequest(String url, int method, Map<String, String> headers,
                             String body, Response.Listener responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        GBLoggerUtil.debug(WebServiceOperation.class.getSimpleName(),"url:=" + url);//getInstanceOf()
        System.out.println("URL:=" + url);
        this.mHeaders = headers;
        this.mBody = body;
        this.mListener = responseListener;

    }

    /**
     * Return headers adviseInfo for request headers
     *
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        mHeaders = mHeaders != null ? mHeaders : super.getHeaders();
        //mHeaders.put("Accept", "application/json");
        mHeaders.put("Content-Type", "application/json");
        return mHeaders;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }


    /**
     * Returns the raw POST or PUT body to be sent.
     *
     * @throws AuthFailureError in the event of auth failure
     */
    public byte[] getBody() throws AuthFailureError {
        GBLoggerUtil.debug(this, "body:=" + mBody);
        System.out.println("body:=" + mBody);

        if (!TextUtils.isEmpty(mBody)) {
            return mBody.getBytes();
        }
        return null;

    }

    @Override
    protected void deliverResponse(Object response) {
        if (mListener != null)
            Log.d("check_gblogin","iv.WebServiceRequest.deliveResponse() called with object response");
            mListener.onResponse(response);
    }

    /**
     * Return the response data as json string on main thread, if response code is ok i.e. 200/201.
     *
     * @param response
     * @return
     */
    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        GBLoggerUtil.debug(this, "Network Response:=" + response.data.length);
        System.out.println("Network Response:=" + response.data);

        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
           GBLoggerUtil.debug(this, "Network Response:=" + json);
            System.out.println("Network Response:=" + json);
            return Response.success(json,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}



