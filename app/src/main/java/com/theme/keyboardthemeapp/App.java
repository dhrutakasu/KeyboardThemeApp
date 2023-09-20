package com.theme.keyboardthemeapp;

import android.app.Application;

public class App extends Application {
    public static App App = null;

    public static App getInstance() {
        return App;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        App = this;
    }
}
