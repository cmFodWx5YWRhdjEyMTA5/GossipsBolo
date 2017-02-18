package com.example.yogi.gossipsbolo;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.example.yogi.gossipsbolo.util.GBLoggerUtil;

import java.util.Arrays;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setActionBar(int resId) {
        mToolbar = (Toolbar) findViewById(resId);
        setSupportActionBar(mToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    public void setTitle(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(resId);
        }
    }
    public void add(int containerId, BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(containerId, fragment, Integer.toString(getSupportFragmentManager().getBackStackEntryCount()));
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Method for adding fragment
     *  @param containerId Container of the fragment
     * @param fragment    The fragment to be added
     */

    public void replace(int containerId, BaseFragment fragment) {
        replace(containerId, fragment, true);
    }

    public void replace(int containerId, DialogFragment fragment) {
        replace(containerId, fragment, true);
    }

    /**
     * Method for replacing fragment
     *
     * @param containerId Container of the fragment
     * @param fragment    The fragment to place in the container
     */
    public void replace(int containerId, BaseFragment fragment, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment, Integer.toString(getSupportFragmentManager().getBackStackEntryCount()));
        if (addToBackStack) ft.addToBackStack(null);
        ft.commit();
        fm.executePendingTransactions();
        new GBLoggerUtil().debug(BaseActivity.class.getSimpleName(), "BackStackEntryCount: " + fm.getBackStackEntryCount());
    }

    public void replace(int containerId, DialogFragment fragment, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment, Integer.toString(getSupportFragmentManager().getBackStackEntryCount()));
        if (addToBackStack) ft.addToBackStack(null);
        ft.commit();
        fm.executePendingTransactions();
        new GBLoggerUtil().debug(BaseActivity.class.getSimpleName(), "BackStackEntryCount: " + fm.getBackStackEntryCount());
    }

    public void pop() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate();
    }



    public void popAllFragmentsUpto(int index) {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count - index; ++i) {
            fm.popBackStack();
        }
    }

    public void showProgressDialog(int resMessage) {
        showProgressDialog("", this.getResources().getString(resMessage));
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            GBLoggerUtil.debug("ee","ee");
            finish();
        }else if(count==2){
            Fragment fragment = getSupportFragmentManager().findFragmentByTag((count - 1) + "");
            ((BaseFragment)fragment).onBackPress();
        }
        else {

            Fragment fragment = getSupportFragmentManager().findFragmentByTag((count - 1) + "");
            if (fragment != null) {
                ((BaseFragment)fragment).onBackPress();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GBLoggerUtil.debug("LeaveLetter", "Destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
         GBLoggerUtil.debug("LeaveLetter", "Pause");
    }

    public List<String> getFromStringArray(int resId) {
        return Arrays.asList(getResources().getStringArray(resId));
    }

    protected void setBackEnabled() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void showProgressDialog(String message) {
        showProgressDialog("", message);
    }

    public void showProgressDialog(String title, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, title, message);
        }
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void showAlertDialog(String title, String message, String positiveButton, String negativeButton, final OnHSAlertDialog listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);

        if (!TextUtils.isEmpty(positiveButton)) {
            builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (listener != null) {
                        listener.onPositiveButtonClick();
                    }
                }
            });
        }

        if (!TextUtils.isEmpty(negativeButton)) {
            builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listener != null) {
                        listener.onNegativeButtonClick();
                    }
                }
            });
        }
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }

    public void showAlertDialog(String message) {
        showAlertDialog("", message, "", "", null);
    }

    public void showAlertDialog(String title, String message) {
        showAlertDialog(title, message, getString(R.string.lbl_alert_ok), "", null);
    }

    public void showAlertDialog(String message, OnHSAlertDialog listener) {
        showAlertDialog("", message, getString(R.string.lbl_alert_ok), "", listener);
    }


    public void showAlertDialog(String message, String positiveButton, OnHSAlertDialog listener) {
        showAlertDialog("", message, positiveButton, "", listener);
    }

    public void showAlertDialog(String title, String message, String positiveButton, String negativeButton) {
        showAlertDialog(title, message, positiveButton, negativeButton, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag((count - 1) + "");
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public interface OnHSAlertDialog {
        void onPositiveButtonClick();

        void onNegativeButtonClick();
    }


}
