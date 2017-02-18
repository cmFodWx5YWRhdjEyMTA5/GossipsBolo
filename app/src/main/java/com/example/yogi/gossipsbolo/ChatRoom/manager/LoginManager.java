package com.example.yogi.gossipsbolo.ChatRoom.manager;

import android.util.Log;

import com.example.yogi.gossipsbolo.ChatRoom.activity.ChatRoomActivity;
import com.example.yogi.gossipsbolo.ChatRoom.fragment.ChatRoomFragment;
import com.example.yogi.gossipsbolo.ChatRoom.manager.operation.UserLoginOperation;
import com.example.yogi.gossipsbolo.ChatRoom.model.LoginRequest;
import com.example.yogi.gossipsbolo.ChatRoom.model.LoginResponse;
import com.example.yogi.gossipsbolo.exception.GossipsBoloException;
import com.example.yogi.gossipsbolo.manager.WebServiceManager;
import com.example.yogi.gossipsbolo.util.GBLoggerUtil;
import com.google.gson.Gson;

public class LoginManager extends WebServiceManager implements  UserLoginOperation.IUserLoginOperation
{
    private static final String TAG=LoginManager.class.getSimpleName();

    private IUserLoginManager iUserLoginManager;

    public void doAuthenticate(String mProfileName, String mMobileNumber,String mFirebaseInstanceId,IUserLoginManager listener) {
        Log.d("check_gblogin","2.doAuthenticate called");
        iUserLoginManager=listener;

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.mobileNumber = mMobileNumber;
        loginRequest.emailId = mProfileName;
        loginRequest.firebaseInstanceId = mFirebaseInstanceId;

        String json = new Gson().toJson(loginRequest);  //.toString() not needed
       Log.e(TAG, json + "\n" + getHeaders());
        GBLoggerUtil.debug(LoginManager.class.getSimpleName(), json + "\n" + getHeaders());

        UserLoginOperation userLoginOperation = new UserLoginOperation(getHeaders(),json ,this);
          userLoginOperation.addToRequestQueue();

    }

    @Override
    public void onUserLoginSuccess(LoginResponse loginResponse) {
    iUserLoginManager.OnUserLoginSuccess(loginResponse);
        Log.d("check_gblogin","6.onUserLoginSuccess_loginmanager");
    }

    @Override
    public void onUserLoginError(GossipsBoloException exception) {
        Log.d("check_gblogin","ii.LoginManger.onUserLoginError Entered");
        iUserLoginManager.OnUserLoginError(exception);


    }


    public interface IUserLoginManager
    {
        void OnUserLoginSuccess(LoginResponse response);

        void OnUserLoginError(GossipsBoloException exception);
    }
}

