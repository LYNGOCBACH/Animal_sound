package com.bach.animalsoundmvvm.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bach.animalsoundmvvm.App;
import com.bach.animalsoundmvvm.MTask;
import com.bach.animalsoundmvvm.R;

import com.bach.animalsoundmvvm.databinding.M002DetailFrgBinding;
import com.bach.animalsoundmvvm.model.Animal;
import com.bach.animalsoundmvvm.view.CommonVM;
import com.bach.animalsoundmvvm.view.dialog.DetailInfoDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        binding.ivSave.setOnClickListener(this);
    }

    private void updateUI(Animal animal) {
        if (animal != null) {
            try {
                InputStream in1 = App.getInstance().getAssets().open(animal.getIdPhoto());
                binding.ivAnimalDetail.setImageBitmap(BitmapFactory.decodeStream(in1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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
            Animal animal = App.getInstance().getStorage().listAnimal.get(index);
            playSound(animal.getIdSound());
//            doStart();
        } else if (v.getId() == R.id.iv_search) {
//            doSearch(App.getInstance().getStorage().listAnimal.get(index).getName());
            showInfoDialog(App.getInstance().getStorage().listAnimal.get(index));
        } else if (v.getId() == R.id.iv_save) {
            saveToStorage();
        }
    }

    private void saveToStorage() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                return;
            }
        }
        new MTask("KEY_SAVE_PHOTO", new MTask.OnCallBack() {
            @Override
            public Object execTask(String key, Object params, MTask task) {
                Animal animal = App.getInstance().getStorage().listAnimal.get(index);
                try {
                    InputStream in = App.getInstance().getAssets().open(animal.getIdPhoto());
                    byte[] buff = new byte[1024];
//                    String outPath = App.getInstance().getExternalFilesDir(null).getPath();
                    String outPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

                    FileOutputStream out = new FileOutputStream(new File(outPath + "/" + animal.getName() + ".png"));
                    int len = in.read(buff);
                    while (len > 0) {
                        out.write(buff, 0, len);
                        len = in.read(buff);
                    }
                    in.close();
                    out.close();
//                    Toast.makeText(context, "Save to storage", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "run: save to storage");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return true;
            }

            @Override
            public void completeTask(String key, Object value) {
                if (value == null) {
                    Toast.makeText(context, "Save failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Save to storage", Toast.LENGTH_SHORT).show();
                }
            }
        }).startAsync(null);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Animal animal = App.getInstance().getStorage().listAnimal.get(index);
//                try {
//                    InputStream in = App.getInstance().getAssets().open(animal.getIdPhoto());
//                    byte[] buff = new byte[1024];
//                    String outPath = App.getInstance().getExternalFilesDir(null).getPath();
////                    String outPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
//
//                    FileOutputStream out = new FileOutputStream(new File(outPath + "/" + animal.getName() + ".png"));
//                    int len = in.read(buff);
//                    while (len > 0) {
//                        out.write(buff, 0, len);
//                        len = in.read(buff);
//                    }
//                    in.close();
//                    out.close();
////                    Toast.makeText(context, "Save to storage", Toast.LENGTH_SHORT).show();
//                    Log.i(TAG, "run: save to storage");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    private void playSound(String idSound) {
        try {
            AssetFileDescriptor afd = App.getInstance().getAssets().openFd(idSound);
            MediaPlayer player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
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

//    private void doStart() {
//        Animal animal = App.getInstance().getStorage().listAnimal.get(index);
//        MediaPlayer.create(context, animal.getIdSound()).start();
//    }

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
