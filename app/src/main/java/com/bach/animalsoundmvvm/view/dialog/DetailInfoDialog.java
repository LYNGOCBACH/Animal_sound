package com.bach.animalsoundmvvm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bach.animalsoundmvvm.R;
import com.bach.animalsoundmvvm.databinding.ViewDetailInfoBinding;
import com.bach.animalsoundmvvm.model.Animal;

public class DetailInfoDialog extends Dialog implements View.OnClickListener {
    private final ViewDetailInfoBinding binding;
    private final Animal animal;
    private Context context;

    public DetailInfoDialog(@NonNull Context context, Animal animal) {
        super(context, R.style.Theme_Dialog);
        this.animal = animal;
        this.context = context;
        binding = ViewDetailInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews() {
        binding.ivBack.setOnClickListener(this);
        binding.tvTitle.setText(animal.getName());
        binding.webInfo.getSettings().setJavaScriptEnabled(true);
        binding.webInfo.getSettings().setAllowContentAccess(true);
        binding.webInfo.getSettings().setBuiltInZoomControls(true);
        binding.webInfo.getSettings().setAllowFileAccess(true);
        binding.webInfo.getSettings().setDomStorageEnabled(true);
        binding.webInfo.getSettings().setSupportZoom(true);
        binding.webInfo.setWebChromeClient(new WebChromeClient());
        binding.webInfo.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        binding.webInfo.loadUrl("https://en.wikipedia.org/wiki/" + animal.getName());
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        if (v.getId() == R.id.iv_back) {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        if (!binding.webInfo.canGoBack()) {
            super.dismiss();
            return;
        }
        binding.webInfo.goBack();
//        super.dismiss();
    }
}


