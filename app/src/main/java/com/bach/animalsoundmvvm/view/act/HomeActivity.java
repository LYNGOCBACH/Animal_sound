package com.bach.animalsoundmvvm.view.act;

import com.bach.animalsoundmvvm.databinding.ActivityHomeBinding;
import com.bach.animalsoundmvvm.view.CommonVM;
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
}