package com.bach.animalsoundmvvm.view.viewmodel;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.bach.animalsoundmvvm.App;
import com.bach.animalsoundmvvm.MTask;
import com.bach.animalsoundmvvm.model.Animal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class M002DetailVM extends ViewModel {
    public static final String TAG = M002DetailVM.class.getName();

    public Object copyPhotoToStorage(Animal animal) {
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
}
