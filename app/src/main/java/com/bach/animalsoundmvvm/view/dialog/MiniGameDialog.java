package com.bach.animalsoundmvvm.view.dialog;

import android.app.Dialog;
import android.content.Context;

import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bach.animalsoundmvvm.CommonUtils;
import com.bach.animalsoundmvvm.R;
import com.bach.animalsoundmvvm.databinding.ViewMiniGameBinding;
import com.bach.animalsoundmvvm.model.Animal;
import com.bach.animalsoundmvvm.view.viewmodel.MiniGameVM;
import com.bumptech.glide.Glide;


import java.util.List;


public class MiniGameDialog extends Dialog implements View.OnClickListener {
    private static final String KEY_SCORE = "KEY_SCORE";
    private final ViewMiniGameBinding binding;

    private Context context;

    private MiniGameVM viewModel;

    public MiniGameDialog(@NonNull Context context, ViewModelStoreOwner owner, List<Animal> animalList) {
        super(context, R.style.Theme_Dialog);
        this.context = context;
        viewModel = new ViewModelProvider(owner).get(MiniGameVM.class);
//        this.animalList = new ArrayList<>(animalList);
        viewModel.initAnimalList(animalList);

//        Collections.shuffle(this.animalList);

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
            viewModel.initScore(Integer.parseInt(txtScore));
            binding.tvScore.setText("Score: " + viewModel.getScore());
        }
    }

    private void initCard() {
        String[] txtArr = viewModel.initCard();
        binding.tvA.setText(txtArr[0]);
        binding.tvB.setText(txtArr[1]);
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
        boolean rs = viewModel.checkAnswer(ans);
        if (rs) {
            binding.tvScore.setText("Score: " + viewModel.getScore());
            initCard();
            savePoint();
        } else {
            Toast.makeText(context, "Wrong Answer", Toast.LENGTH_SHORT).show();
        }

    }

    private void savePoint() {
        CommonUtils.getINSTANCE().savePref(KEY_SCORE, viewModel.getScore() + "");
    }

    private void showCardAnimal() {
//        int index = (int) (Math.random() * animalList.size());
//        animal = animalList.get(index);
//        binding.ivCard.setImageResource(animal.getIdPhoto());
//        binding.tvCard.setText(animal.getName());

//        Toast toast = new Toast(context);
//        ImageView ivAnimal = new ImageView(context);
//
//        int size = 600;
//        ivAnimal.setLayoutParams(new android.view.ViewGroup.LayoutParams(size, size));
//        ivAnimal.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        try {
//            InputStream in1 = App.getInstance().getAssets().open(viewModel.getAnimal().getIdPhoto());
//            ivAnimal.setImageBitmap(BitmapFactory.decodeStream(in1));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        toast.setView(ivAnimal);
//
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();

        Glide.with(context)
                .load("file:///android_asset/" + viewModel.getAnimal().getIdPhoto())
                .into(binding.ivAnimal);
        binding.ivAnimal.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.ivAnimal.setVisibility(View.GONE);
            }
        }, 2000);

    }

}


