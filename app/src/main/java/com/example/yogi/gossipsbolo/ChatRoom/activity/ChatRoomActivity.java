package com.example.yogi.gossipsbolo.ChatRoom.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogi.gossipsbolo.BaseActivity;
import com.example.yogi.gossipsbolo.ChatRoom.fragment.ChatRoomFragment;
import com.example.yogi.gossipsbolo.ChatRoom.manager.LoginManager;
import com.example.yogi.gossipsbolo.ChatRoom.model.LoginResponse;
import com.example.yogi.gossipsbolo.PhoneNumber.activity.PhoneNumberActivity;
import com.example.yogi.gossipsbolo.constant.IntentConstant;
import com.example.yogi.gossipsbolo.FireBase.utils.NotificationUtils;
import com.example.yogi.gossipsbolo.R;
import com.example.yogi.gossipsbolo.constant.FireBaseConstants;
import com.example.yogi.gossipsbolo.exception.GossipsBoloException;
import com.example.yogi.gossipsbolo.util.GBLoggerUtil;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

import static java.security.AccessController.getContext;

/**
 * Created by Yogi on 06-01-2017.
 */

public class ChatRoomActivity extends BaseActivity   {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        replace(R.id.layout_container, ChatRoomFragment.getInstance());


    }
}
    




