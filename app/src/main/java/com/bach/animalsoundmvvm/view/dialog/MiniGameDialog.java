package com.bach.animalsoundmvvm.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bach.animalsoundmvvm.App;
import com.bach.animalsoundmvvm.CommonUtils;
import com.bach.animalsoundmvvm.R;
import com.bach.animalsoundmvvm.databinding.ViewDetailInfoBinding;
import com.bach.animalsoundmvvm.databinding.ViewMiniGameBinding;
import com.bach.animalsoundmvvm.model.Animal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MiniGameDialog extends Dialog implements View.OnClickListener {
    private static final String KEY_SCORE = "KEY_SCORE";
    private final ViewMiniGameBinding binding;
    private Animal animal;
    private Context context;
    private final List<Animal> animalList;
    private int index = 0;
    private int score = 0;

    public MiniGameDialog(@NonNull Context context, List<Animal> animalList) {
        super(context, R.style.Theme_Dialog);
        this.context = context;
        this.animalList = new ArrayList<>(animalList);
        Collections.shuffle(this.animalList);

        binding = ViewMiniGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews() {
        binding.ivCard.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.tvCard.setOnClickListener(this);
        binding.tvA.setOnClickListener(this);
        binding.tvB.setOnClickListener(this);
        initCard();
        String txtScore = CommonUtils.getINSTANCE().getPref(KEY_SCORE);
        if (txtScore != null) {
            score = Integer.parseInt(txtScore);
            binding.tvScore.setText("Score: " + score);
        }
    }

    private void initCard() {
        animal = animalList.get(index);
        List<Animal> tmpList = new ArrayList<>(animalList);
        tmpList.remove(animal);
        Collections.shuffle(tmpList);
        if (new Random().nextBoolean()) {
            binding.tvA.setText("A: " + animal.getName());
            binding.tvB.setText("B: " + tmpList.get(0).getName());
        } else {
            binding.tvA.setText("A: " + tmpList.get(0).getName());
            binding.tvB.setText("B: " + animal.getName());
        }
//
//        String textA = binding.tvA.getText().toString();
//        String textB = binding.tvB.getText().toString();
//        int lenA = textA.length();
//        int lenB = textB.length();
//        String max = lenA > lenB ? textA : textB;
//
//
//        Rect bounds = new Rect();
//        Paint textPaint = binding.tvA.getPaint();
//        textPaint.getTextBounds(max, 0, max.length(), bounds);
//        int width = bounds.width();
//
//        binding.tvA.setWidth(width + 400);
//        binding.tvB.setWidth(width + 400);


//        ViewTreeObserver viewTreeObserver = binding.tvB.getViewTreeObserver();
//        if (viewTreeObserver.isAlive()) {
//            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    binding.tvB.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    int wA = binding.tvA.getWidth();
//                    int wB = binding.tvB.getWidth();
//                    int max = Math.max(wA, wB);
//                    binding.tvA.setWidth(max);
//                    binding.tvB.setWidth(max);
//                    int viewWidth = 0;
//                    int viewHeight = 0;
//                    viewWidth = view.getWidth();
//                    viewHeight = view.getHeight();
//                    int max = binding.tvB.getWidth() > binding.tvB.getHeight() ? binding.tvB.getWidth() : binding.tvA.getWidth();
//                }
//            });
    }


    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        if (v.getId() == R.id.iv_back) {
            dismiss();
        } else if (v.getId() == R.id.iv_card || v.getId() == R.id.tv_card) {
            binding.frCard.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
            showCardAnimal();
        } else if (v.getId() == R.id.tv_a || v.getId() == R.id.tv_b) {
            checkAnswer(((TextView) v).getText().toString());
        }
    }

    private void checkAnswer(String ans) {
        if (ans.equals("A: " + animal.getName())
                || ans.equals("B: " + animal.getName())) {
            score++;
            binding.tvScore.setText("Score: " + score);
            index++;
            if (index >= animalList.size()) {
                index = 0;
            }
            initCard();
            savePoint();
        } else {
            Toast.makeText(context, "Wrong Answer", Toast.LENGTH_SHORT).show();
        }

    }

    private void savePoint() {
        CommonUtils.getINSTANCE().savePref(KEY_SCORE, score + "");
    }

    private void showCardAnimal() {
//        int index = (int) (Math.random() * animalList.size());
//        animal = animalList.get(index);
//        binding.ivCard.setImageResource(animal.getIdPhoto());
//        binding.tvCard.setText(animal.getName());

        Toast toast = new Toast(context);
        ImageView ivAnimal = new ImageView(context);
        try {
            InputStream in1 = App.getInstance().getAssets().open(animal.getIdPhoto());
            ivAnimal.setImageBitmap(BitmapFactory.decodeStream(in1));
        } catch (Exception e) {
            e.printStackTrace();
        }


        toast.setView(ivAnimal);

        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}


