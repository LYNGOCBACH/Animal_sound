package com.bach.animalsoundmvvm.model;

import java.util.Objects;

public class Animal {
    private final int idPhoto;
    private final int idSound;
    private final String name;

    public Animal(int ivPhoto, int idSound,String name) {
        this.idPhoto = ivPhoto;
        this.idSound = idSound;
        this.name = name;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public int getIdSound() {
        return idSound;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return idPhoto == animal.idPhoto;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPhoto);
    }
}
