package com.winky.douniwan.tools.navigation;

import android.support.annotation.NonNull;

import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

/**
 * 替换 navigate 方法 实现fragment add 方式入栈，现在都是替换
 */
public class NavHostAddFragment extends NavHostFragment {

    @NonNull
    @Override
    protected Navigator<? extends FragmentNavigator.Destination> createFragmentNavigator() {
        return new FragmentAddNavigator(requireContext(), getChildFragmentManager(), getId());
    }
}
