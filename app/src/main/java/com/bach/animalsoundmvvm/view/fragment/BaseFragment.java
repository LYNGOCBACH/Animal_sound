package com.bach.animalsoundmvvm.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.bach.animalsoundmvvm.view.OnMainCallBack;


public abstract class BaseFragment<B extends ViewBinding, M extends ViewModel> extends Fragment implements OnClickListener {
    protected Context context;
    protected B binding;
    protected M viewModel;
    protected OnMainCallBack callBack;
    protected Object mData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding(inflater, container);
        viewModel = new ViewModelProvider(this).get(initViewModel());
        initViews();
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        clickView(v);
    }

    protected void clickView(View v) {
    }

    protected abstract void initViews();


    protected abstract Class<M> initViewModel();

    protected abstract B initViewBinding(LayoutInflater inflater, ViewGroup container);

    public void setData(Object data) {
        mData = data;
    }

    public void setCallBack(OnMainCallBack callBack) {
        this.callBack = callBack;
    }
}

