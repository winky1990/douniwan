package com.winky.expand.basics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

import androidx.navigation.fragment.NavHostFragment;

/**
 * 上下文管理
 */
public class BaseAppActivity extends AppCompatActivity {

    private WeakReference<FragmentActivity> weakReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weakReference = new WeakReference<FragmentActivity>(this);
    }

    public FragmentActivity getActivity() {
        return weakReference.get();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weakReference.clear();
        weakReference = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
