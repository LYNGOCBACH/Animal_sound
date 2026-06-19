package com.bach.animalsoundmvvm.view.viewmodel;

import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.bach.animalsoundmvvm.model.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MiniGameVM extends ViewModel {
    private final List<Animal> animalList = new ArrayList<>();
    private int index = 0;
    private int score = 0;
    private Animal animal;

    public void initAnimalList(List<Animal> data) {
        animalList.clear();
        animalList.addAll(data);
        Collections.shuffle(this.animalList);
    }

    public void initScore(int data) {
        score = data;
    }

    public int getScore() {
        return score;
    }

    public String[] initCard() {
        animal = animalList.get(index);
        List<Animal> tmpList = new ArrayList<>(animalList);
        tmpList.remove(animal);
        Collections.shuffle(tmpList);
        String textA;
        String textB;
        if (new Random().nextBoolean()) {
            textA = "A: " + animal.getName();
            textB = "B: " + tmpList.get(0).getName();
        } else {
            textA = "A: " + tmpList.get(0).getName();
            textB = "B: " + animal.getName();
        }
        return new String[]{textA, textB};
    }

    public boolean checkAnswer(String ans) {
        if (ans.equals("A: " + animal.getName())
                || ans.equals("B: " + animal.getName())) {
            score++;
            index++;
            if (index >= animalList.size()) {
                index = 0;
            }
            return true;
        } else {
            return false;
        }
    }

    public Animal getAnimal() {
        return animal;
    }
}
