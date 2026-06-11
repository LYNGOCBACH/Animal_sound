package com.bach.animalsoundmvvm.view.act;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;


import com.bach.animalsoundmvvm.R;
import com.bach.animalsoundmvvm.view.OnMainCallBack;
import com.bach.animalsoundmvvm.view.fragment.BaseFragment;

import java.lang.reflect.Constructor;

public abstract class BaseAct<B extends ViewBinding, M extends ViewModel> extends AppCompatActivity implements View.OnClickListener, OnMainCallBack {
    protected B binding;
    protected M viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(initViewModel());
        initViews();
    }

    protected abstract void initViews();

    protected abstract Class<M> initViewModel();

    protected abstract B getViewBinding();

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in));
        clickView(v);
    }

    protected void clickView(View v) {

    }

    protected final void notify(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected final void notify(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFragment(String tag, Object data, Boolean isBack) {

        try {
            Class<?> clazz = Class.forName(tag);
            Constructor<?> cons = clazz.getConstructor();
            BaseFragment<?, ?> frg = (BaseFragment<?, ?>) cons.newInstance();
            frg.setData(data);
            frg.setCallBack(this);
            FragmentTransaction trans = getSupportFragmentManager()
                    .beginTransaction();
            if (isBack) {
                trans.addToBackStack(null);
            }
            trans.replace(R.id.main, frg, tag).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
