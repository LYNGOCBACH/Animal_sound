package com.bach.animalsoundmvvm.view.fragment;


import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bach.animalsoundmvvm.databinding.M000SplashFrgBinding;
import com.bach.animalsoundmvvm.view.CommonVM;
import com.bach.animalsoundmvvm.view.act.HomeActivity;


public class M000SplashFrg extends BaseFragment<M000SplashFrgBinding, CommonVM> {
    public static final String TAG = M000SplashFrg.class.getName();

    @Override
    protected void initViews() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMainScreen();
            }
        }, 2000);
    }


    private void gotoMainScreen() {
//        Intent intent = new Intent(context, HomeActivity.class);
//        startActivity(intent);
//        getActivity().finish();
        callBack.showFragment(M001MainFrg.TAG, null, false);
    }

    @Override
    protected Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    protected M000SplashFrgBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return M000SplashFrgBinding.inflate(inflater, container, false);
    }
}
