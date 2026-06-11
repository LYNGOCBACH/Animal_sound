package com.bach.animalsoundmvvm.view.fragment;


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
import com.bach.animalsoundmvvm.view.CommonVM;

import java.util.Locale;


public class M001MainFrg extends BaseFragment<M001MainFrgBinding, CommonVM> {
    public static final String TAG = M001MainFrg.class.getName();

    private TextToSpeech tts;

    @Override
    protected void initViews() {
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

    private void initAnimalViews() {
        binding.lnAnimalList.removeAllViews();
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
            iv1.setImageResource(item1.getIdPhoto());
            iv2.setImageResource(item2.getIdPhoto());
            iv3.setImageResource(item3.getIdPhoto());

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
    }

    @Override
    protected void clickView(View view) {
        Animal tag = (Animal) view.getTag();
        gotoDetailScreen(tag);
    }

    private void gotoDetailScreen(Animal animal) {
        tts.speak(animal.getName(), TextToSpeech.QUEUE_ADD, null);
        callBack.showFragment(M002DetailFrg.TAG, animal, true);
    }

    private void initData() {
        App.getInstance().getStorage().listAnimal.clear();
        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_elephant, R.raw.elephant, "elephant"));
        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_zebra, R.raw.zebra, "zebra"));
        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_lion, R.raw.lion, "lion"));
        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_hippo, R.raw.hippo, "hippo"));
        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_camel, R.raw.camel, "camel"));
        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_rhino, R.raw.rhino, "rhino"));
        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_tiger, R.raw.tiger, "tiger"));
        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_crocodile, R.raw.crocodile, "crocodile"));
        App.getInstance().getStorage().listAnimal.add(new Animal(R.drawable.ic_dolphin, R.raw.dolphin, "dolphin"));

    }

    @Override
    protected Class<CommonVM> initViewModel() {
        return CommonVM.class;
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
