package com.simple.apt;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.simple.annotation.Route;
import com.simple.arouter.api.ARouter;

@Route(path = "main/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openOneActivity(View view) {
        ARouter.getInstance().jumpActivity("one/OneActivity");
    }

    public void openTwoActivity(View view) {
        ARouter.getInstance().jumpActivity("two/TwoActivity");
    }
}
