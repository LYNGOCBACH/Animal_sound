package com.bach.animalsoundmvvm.model;

import java.util.Objects;

public class Animal {
    private final String idPhoto;
    private final String idSound;
    private final String name;

    public Animal(String ivPhoto, String idSound, String name) {
        this.idPhoto = ivPhoto;
        this.idSound = idSound;
        this.name = name;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public String getIdSound() {
        return idSound;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return idPhoto.equals(animal.idPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPhoto);
    }
}
