package com.simple.apt;

import android.app.Application;

import com.simple.arouter.api.ARouter;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
