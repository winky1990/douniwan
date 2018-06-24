package com.winky.douniwan.tools.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NavigationRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.winky.douniwan.ui.activity.NavigationActivity;
import com.winky.expand.Config;
import com.winky.expand.R;
import com.winky.expand.utils.Singleton;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

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

    public void toNavigation(@NonNull Context context, @NavigationRes int navigationRes) {
        Intent intent = new Intent(context, NavigationActivity.class);
        intent.putExtra(Config.NAVIGATION_ID, navigationRes);
        context.startActivity(intent);
    }

    public void jump(@NonNull Fragment fragment, @IdRes int navigationId) {
        this.jump(fragment, navigationId, null);
    }

    public void jump(@NonNull Fragment fragment, @IdRes int navigationId, @Nullable Bundle bundle) {
        NavHostFragment.findNavController(fragment).navigate(navigationId, bundle, navOptions);
    }

    public boolean goBack(Fragment fragment) {
        return NavHostFragment.findNavController(fragment).navigateUp();
    }
}
