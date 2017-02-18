package com.example.yogi.gossipsbolo.ChatRoom.manager.operation;


import android.util.Log;

import com.android.volley.Request;

import com.example.yogi.gossipsbolo.ChatRoom.model.LoginResponse;
import com.example.yogi.gossipsbolo.Config;
import com.example.yogi.gossipsbolo.constant.URLConstant;
import com.example.yogi.gossipsbolo.exception.GossipsBoloException;
import com.example.yogi.gossipsbolo.manager.operation.WebServiceOperation;



import java.util.Map;

public  class UserLoginOperation extends WebServiceOperation
{

private IUserLoginOperation iUserLoginOperation;

    public UserLoginOperation(Map<String, String> headers, String body, IUserLoginOperation listener) {

        super(URLConstant.USER_LOGIN_URI, Request.Method.POST,headers,body,LoginResponse.class,UserLoginOperation.class.getSimpleName());
         this.iUserLoginOperation = listener;
        Log.d("check_gblogin","3.UserLoginOperation Constructor called");
    }

    @Override
    public void addToRequestQueue() {
        Log.d("check_gblogin","4.addToRequestQueue called");
        if(Config.HARDCODED_ENABLE)
        {
            Log.d("check_gblogin","4.1.Configuration.hardcore enabled=true");
            onSuccess(getFromAssetsFolder("loginresponse.json",LoginResponse.class));


        }
        else {
            Log.d("check_gblogin","hardcorenot enabled!!! addTorequestqueue called");
            super.addToRequestQueue();
            Log.d("check_gblogin","request successfully added");
        }
    }

    @Override
    public void onError(GossipsBoloException exception) {
        Log.d("check_gblogin","i.userloginoperation.onError_entered");
        iUserLoginOperation.onUserLoginError(exception);

    }

    @Override
    public void onSuccess(Object response) {
        Log.d("check_gblogin","5.(login.json,loginresponse.class)");
        LoginResponse loginResponse = (LoginResponse) response;
        iUserLoginOperation.onUserLoginSuccess(loginResponse);
        Log.d("check_gblogin","onUserLoginsucces_userloginoperation");
    }
    public interface IUserLoginOperation
    {
        void onUserLoginSuccess(LoginResponse loginResponse);
        void onUserLoginError(GossipsBoloException exception);
    }
}