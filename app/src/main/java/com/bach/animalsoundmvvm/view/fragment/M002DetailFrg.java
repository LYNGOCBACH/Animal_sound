package com.bach.animalsoundmvvm.view.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bach.animalsoundmvvm.App;
import com.bach.animalsoundmvvm.R;

import com.bach.animalsoundmvvm.databinding.M002DetailFrgBinding;
import com.bach.animalsoundmvvm.model.Animal;
import com.bach.animalsoundmvvm.view.CommonVM;
import com.bach.animalsoundmvvm.view.dialog.DetailInfoDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class M002DetailFrg extends BaseFragment<M002DetailFrgBinding, CommonVM> {
    public static final String TAG = M002DetailFrg.class.getName();
    private int index;

    @Override
    protected void initViews() {
        Animal animal = (Animal) mData;
        updateUI(animal);
//        for (int i = 0; i < App.getInstance().getStorage().listAnimal.size(); i++) {
//            if (R.id.iv_animal_detail == App.getInstance().getStorage().listAnimal.get(i).getIvPhoto()) {
//                index = i;
//                break;
//            }
//        }
        index = App.getInstance().getStorage().listAnimal.indexOf(animal);
        binding.ivBack.setOnClickListener(this);
        binding.ivNext.setOnClickListener(this);
        binding.ivStart.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);
    }

    private void updateUI(Animal animal) {
        if (animal != null) {
            binding.ivAnimalDetail.setImageResource(animal.getIdPhoto());
            binding.tvTitle.setText(animal.getName());
        }
    }

    @Override
    protected void clickView(View v) {
        if (v.getId() == R.id.iv_back) {
            doBack();
        } else if (v.getId() == R.id.iv_next) {
            doNext();
        } else if (v.getId() == R.id.iv_start) {
            doStart();
        } else if (v.getId() == R.id.iv_search) {
//            doSearch(App.getInstance().getStorage().listAnimal.get(index).getName());
            showInfoDialog(App.getInstance().getStorage().listAnimal.get(index));
        }
    }

    private void showInfoDialog(Animal animal) {
        DetailInfoDialog dialog = new DetailInfoDialog(context, animal);
        dialog.show();
    }

    private void doSearch(String name) {
        try {
            String word = URLDecoder.decode(name, "UTF-8");
            Uri uri = Uri.parse("https://www.google.com/search?q=" + word);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void doStart() {
        Animal animal = App.getInstance().getStorage().listAnimal.get(index);
        MediaPlayer.create(context, animal.getIdSound()).start();
    }

    private void doNext() {
        if (index < App.getInstance().getStorage().listAnimal.size() - 1) {
            index++;
        } else {
            index = 0;
        }
//        binding.ivAnimalDetail.setImageResource(App.getInstance().getStorage().listAnimal.get(index).getIvPhoto());
//        binding.tvTitle.setText(App.getInstance().getStorage().listAnimal.get(index).getName());
        updateUI(App.getInstance().getStorage().listAnimal.get(index));
    }

    private void doBack() {
        if (index > 0) {
            index--;
        } else {
            index = App.getInstance().getStorage().listAnimal.size() - 1;
        }
        updateUI(App.getInstance().getStorage().listAnimal.get(index));
//        binding.ivAnimalDetail.setImageResource(App.getInstance().getStorage().listAnimal.get(index).getIvPhoto());
//        binding.tvTitle.setText(App.getInstance().getStorage().listAnimal.get(index).getName());
    }

    @Override
    protected Class<CommonVM> initViewModel() {
        return CommonVM.class;
    }

    @Override
    protected M002DetailFrgBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return M002DetailFrgBinding.inflate(inflater, container, false);
    }

}
