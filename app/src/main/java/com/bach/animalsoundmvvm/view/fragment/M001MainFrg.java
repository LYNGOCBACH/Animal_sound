package com.bach.animalsoundmvvm.view.fragment;


import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.bach.animalsoundmvvm.App;
import com.bach.animalsoundmvvm.R;
import com.bach.animalsoundmvvm.databinding.M001MainFrgBinding;
import com.bach.animalsoundmvvm.model.Animal;
import com.bach.animalsoundmvvm.view.viewmodel.CommonVM;
import com.bach.animalsoundmvvm.view.dialog.MiniGameDialog;
import com.bach.animalsoundmvvm.view.viewmodel.M001MainVM;

import java.io.InputStream;
import java.util.Locale;


public class M001MainFrg extends BaseFragment<M001MainFrgBinding, M001MainVM> {
    public static final String TAG = M001MainFrg.class.getName();

    private TextToSpeech tts;

    @Override
    protected void initViews() {
        binding.tvGame.setOnClickListener(this);
        initData();
        initAnimalViews();

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        tts.speak("Welcome to Animal Sound", TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    private void initData() {
        viewModel.initData();
    }

    private void initAnimalViews() {
        binding.lnAnimalList.removeAllViews();
        try {
            for (int i = 0; i <= App.getInstance().getStorage().listAnimal.size() - 3; i += 3) {
                Animal item1 = App.getInstance().getStorage().listAnimal.get(i);
                Animal item2 = App.getInstance().getStorage().listAnimal.get(i + 1);
                Animal item3 = App.getInstance().getStorage().listAnimal.get(i + 2);

                View v1 = LayoutInflater.from(context).inflate(R.layout.item_animal, null);
                View v2 = LayoutInflater.from(context).inflate(R.layout.item_animal, null);
                View v3 = LayoutInflater.from(context).inflate(R.layout.item_animal, null);

                ImageView iv1 = v1.findViewById(R.id.iv_animal);
                ImageView iv2 = v2.findViewById(R.id.iv_animal);
                ImageView iv3 = v3.findViewById(R.id.iv_animal);

                InputStream in1 = App.getInstance().getAssets().open(item1.getIdPhoto());
                InputStream in2 = App.getInstance().getAssets().open(item2.getIdPhoto());
                InputStream in3 = App.getInstance().getAssets().open(item3.getIdPhoto());

                iv1.setImageBitmap(BitmapFactory.decodeStream(in1));
                iv2.setImageBitmap(BitmapFactory.decodeStream(in2));
                iv3.setImageBitmap(BitmapFactory.decodeStream(in3));

                iv1.setTag(item1);
                iv2.setTag(item2);
                iv3.setTag(item3);

                iv1.setOnClickListener(this);
                iv2.setOnClickListener(this);
                iv3.setOnClickListener(this);

                TableRow tr = new TableRow(context);
                tr.setGravity(Gravity.CENTER);
                tr.addView(v1, new TableRow.LayoutParams(300, 560));
                tr.addView(v2, new TableRow.LayoutParams(300, 560));
                tr.addView(v3, new TableRow.LayoutParams(300, 560));

                binding.lnAnimalList.addView(tr, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void clickView(View view) {
        if (view.getId() == R.id.tv_game) {
            showMiniGame();
            return;
        }
        Animal tag = (Animal) view.getTag();
        gotoDetailScreen(tag);
    }

    private void showMiniGame() {
        MiniGameDialog dialog = new MiniGameDialog(context,
                this,App.getInstance().getStorage().listAnimal);
        dialog.show();
    }

    private void gotoDetailScreen(Animal animal) {
        tts.speak(animal.getName(), TextToSpeech.QUEUE_ADD, null);
        callBack.showFragment(M002DetailFrg.TAG, animal, true);
    }


//        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_elephant, R.raw.elephant, "Elephant"));
//        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_zebra, R.raw.zebra, "Zebra"));
//        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_lion, R.raw.lion, "Lion"));
//        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_hippo, R.raw.hippo, "Hippo"));
//        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_camel, R.raw.camel, "Camel"));
//        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_rhino, R.raw.rhino, "Rhino"));
//        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_tiger, R.raw.tiger, "Tiger"));
//        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_crocodile, R.raw.crocodile, "Crocodile"));
//        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_dolphin, R.raw.dolphin, "Dolphin"));


    @Override
    protected Class<M001MainVM> initViewModel() {
        return M001MainVM.class;
    }

    @Override
    protected M001MainFrgBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return M001MainFrgBinding.inflate(inflater, container, false);
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
