package com.bach.animalsoundmvvm.view.viewmodel;

import androidx.lifecycle.ViewModel;

import com.bach.animalsoundmvvm.App;
import com.bach.animalsoundmvvm.model.Animal;

public class M001MainVM extends ViewModel {
    public void initData() {
        App.getInstance().getStorage().listAnimal.clear();

        try {
            String[] path = App.getInstance().getAssets().list("animal");
            for (String item : path) {
                String name = item.replace(".png", "");
                Animal animal = new Animal("animal/" + item,
                        "sound/" + name + ".mp3", name);
                App.getInstance().getStorage().listAnimal.add(animal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
