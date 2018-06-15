package com.winky.douniwan.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.winky.expand.R;
import com.winky.expand.utils.Singleton;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

public class NavigationUtils {

    private static final Singleton<NavigationUtils> SINGLETON = new Singleton<NavigationUtils>() {
        @Override
        protected NavigationUtils create() {
            return new NavigationUtils();
        }
    };

    public static NavigationUtils getInstance() {
        return SINGLETON.get();
    }

    private NavOptions navOptions;
    private NavController controller;

    private NavigationUtils() {
        navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_right_in)
                .setExitAnim(R.anim.slide_left_out)
                .setPopEnterAnim(R.anim.slide_left_in)
                .setPopExitAnim(R.anim.slide_right_out)
                .build();
    }

    public void setNavOptions(@AnimRes @AnimatorRes int enterAnim,
                              @AnimRes @AnimatorRes int exitAnim,
                              @AnimRes @AnimatorRes int popEnterAnim,
                              @AnimRes @AnimatorRes int popExitAnim) {
        this.navOptions = new NavOptions.Builder()
                .setEnterAnim(enterAnim)
                .setExitAnim(exitAnim)
                .setPopEnterAnim(popEnterAnim)
                .setPopExitAnim(popExitAnim)
                .build();
    }

    public void init(Context context) {
        controller = new NavController(context);
    }

    public void navigate(Fragment fragment, @IdRes int fragmentId) {
        navigate(fragment, fragmentId, null);
    }

    public void navigate(@NonNull Fragment fragment, @IdRes int fragmentId, @Nullable Bundle bundle) {
        View view = (View) fragment.getView().getParent();
        if (view != null)
            Navigation.findNavController(view).navigate(fragmentId, bundle, navOptions);
    }

    public void navigate(@NonNull Fragment fragment, @IdRes int fragmentId, @Nullable Bundle bundle,NavOptions navOptions) {
        View view = (View) fragment.getView().getParent();
        if (view != null)
            Navigation.findNavController(view).navigate(fragmentId, bundle, navOptions);
    }

    public void navigateUp(@NonNull Fragment fragment) {
        View view = (View) fragment.getView().getParent();
        if (view != null)
            Navigation.findNavController(view).navigateUp();
    }
}
