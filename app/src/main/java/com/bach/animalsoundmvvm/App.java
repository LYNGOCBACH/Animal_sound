package com.bach.animalsoundmvvm;

import android.app.Application;

public class App extends Application {
    private static App INSTANCE;
    private Storage storage;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        storage = new Storage();
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public Storage getStorage() {
        return storage;
    }
}
