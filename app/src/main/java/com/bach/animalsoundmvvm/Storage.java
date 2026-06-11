package com.bach.animalsoundmvvm;

import com.bach.animalsoundmvvm.model.Animal;

import java.util.ArrayList;

public final class Storage {
    public int m002DrawableId;
    public int m002ContentId;
    public ArrayList<Animal> listAnimal = new ArrayList<>();

    public void clearDataM002() {
        m002DrawableId = 0;
        m002ContentId = 0;
    }
}
