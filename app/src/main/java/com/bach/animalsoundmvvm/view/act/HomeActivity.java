package com.bach.animalsoundmvvm.view.act;

import androidx.appcompat.app.AlertDialog;

import android.util.Log;

import com.bach.animalsoundmvvm.databinding.ActivityHomeBinding;
import com.bach.animalsoundmvvm.view.viewmodel.CommonVM;
import com.bach.animalsoundmvvm.view.OnMainCallBack;
import com.bach.animalsoundmvvm.view.fragment.M000SplashFrg;

public class HomeActivity extends BaseAct<ActivityHomeBinding, CommonVM> implements OnMainCallBack {
    public static final String TAG = HomeActivity.class.getName();

    @Override
    protected void initViews() {
        showFragment(M000SplashFrg.TAG, null, false);
    }

    @Override
    protected Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    protected ActivityHomeBinding getViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void backToPrevious() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.i(TAG, "onBackPressed: " + count);
        if (count == 0) {
            askForExitApp();
            return;
        }
        super.onBackPressed();
    }

    private void askForExitApp() {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Alert");
        alert.setMessage("Do you want to exit?");
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialog, which) -> super.onBackPressed());
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog, which) -> {
        });
        alert.show();
    }
}