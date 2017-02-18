package com.example.yogi.gossipsbolo.ChatRoom.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogi.gossipsbolo.BaseFragment;

import com.example.yogi.gossipsbolo.ChatRoom.manager.LoginManager;

import com.example.yogi.gossipsbolo.ChatRoom.model.LoginResponse;
import com.example.yogi.gossipsbolo.FireBase.utils.NotificationUtils;
import com.example.yogi.gossipsbolo.PhoneNumber.activity.PhoneNumberActivity;
import com.example.yogi.gossipsbolo.R;
import com.example.yogi.gossipsbolo.constant.FireBaseConstants;
import com.example.yogi.gossipsbolo.constant.IntentConstant;
import com.example.yogi.gossipsbolo.exception.GossipsBoloException;
import com.example.yogi.gossipsbolo.util.GBLoggerUtil;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Yogi on 04-02-2017.
 */

public class ChatRoomFragment extends BaseFragment implements LoginManager.IUserLoginManager{
    private static final String TAG = ChatRoomFragment.class.getSimpleName();
    private TextView txtRegId, txtMsg;
    private EditText profileNameEditText, mobileNumberEditText;
    private TextInputLayout TILprofileName, TILmobileNumber;
    private Button loginButton;
    private String mProfileName, mMobileNumber, mFirebaseInstanceId;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public static ChatRoomFragment getInstance()
    {
        ChatRoomFragment fragment = new ChatRoomFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Check_gblogin","onCreateView working....");
        return inflater.inflate(R.layout.fragment_chatroom,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtRegId = (TextView) view.findViewById(R.id.FireBaseRegId);
        txtMsg = (TextView) view.findViewById(R.id.FireBaseMsg);

        TILprofileName = (TextInputLayout) view.findViewById(R.id.TILprofileName);
        TILmobileNumber = (TextInputLayout) view.findViewById(R.id.TILmobileNumber);

        profileNameEditText = (EditText) view.findViewById(R.id.profileName);
        mobileNumberEditText = (EditText) view.findViewById(R.id.phoneNumber);
        loginButton = (Button) view.findViewById(R.id.login);
        profileNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                TILprofileName.setErrorEnabled(false);
            }
        });

        mobileNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                TILmobileNumber.setErrorEnabled(false);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if (isValid()) {
                                                   showProgressDialog(R.string.loading);
                                                   LoginManager loginManager = new LoginManager();
                                                   Log.d("check_gblogin", "1.loginManager.doAuthenticate calling...");
                                                   loginManager.doAuthenticate(mProfileName, mMobileNumber, mFirebaseInstanceId,ChatRoomFragment.this);
                                               }
                                           }

                                           private boolean isValid() {
                                               mMobileNumber = mobileNumberEditText.getText().toString();
                                               mProfileName = profileNameEditText.getText().toString();
                                               mFirebaseInstanceId = txtRegId.getText().toString();
                                               if (TextUtils.isEmpty(mMobileNumber)) {
                                                   TILmobileNumber.setErrorEnabled(true);
                                                   TILmobileNumber.setError(getResources().getString(R.string.lbl_error_mobileNumber));
                                                   return false;
                                               } else if (TextUtils.isEmpty(mProfileName)) {
                                                   TILprofileName.setErrorEnabled(true);
                                                   TILprofileName.setError(getResources().getString(R.string.lbl_error_profileName));
                                                   return false;
                                               }
                                               return true;

                                           }
                                       }


        );
        GBLoggerUtil.debug("check_gblogin","onView Created working successfully");



        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //checking for type intent filter
                if (intent.getAction().equals(FireBaseConstants.REGISTRATION_COMPLETE)) {
                    //FCM successfully Registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(FireBaseConstants.TOPIC_GLOBAL);
                    displayFireBaseRegID();

                } else if (intent.getAction().equals(FireBaseConstants.PUSH_NOTIFIACTION)) {
                    //new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getActivity().getApplicationContext(), "Push Notification:", Toast.LENGTH_SHORT).show();
                    txtMsg.setText(message);
                }
            }


        };
        GBLoggerUtil.debug("check_gblogin","calling displayFIreBaseRegId");
        displayFireBaseRegID();
    }


    //Fetches Registration Id from sharedPreferences and displays on the screen
    private void displayFireBaseRegID() {
        SharedPreferences mSharedPreferences = getActivity().getApplicationContext().getSharedPreferences(FireBaseConstants.SHARED_PREF, 0);
        String regId = mSharedPreferences.getString("regId", null);

        Log.e(TAG, "FireBase Reistration ID:" + regId);
        if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    public void onResume() {
        super.onResume();
        GBLoggerUtil.debug("check_gblogin","onResume() working.....");
        // register FCM registration complete receiver
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FireBaseConstants.REGISTRATION_COMPLETE));
// register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FireBaseConstants.PUSH_NOTIFIACTION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getActivity().getApplicationContext());

    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    @Override
    public void OnUserLoginSuccess(LoginResponse response) {
        if (isAdded()) {
            dismissProgressDialog();
            GBLoggerUtil.debug("resp",response.roles);
            getActivity().finish();
            Intent intent=new Intent(getActivity(), PhoneNumberActivity.class);
            if(null!=response)
                intent.putExtra(IntentConstant.USERNAME,response.roles);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void OnUserLoginError(GossipsBoloException exception) {
        if (isAdded()) {
            dismissProgressDialog();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}



