package com.winky.douniwan.tools.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayDeque;
import java.util.List;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;

public class FragmentAddNavigator extends Navigator<FragmentNavigator.Destination> {

    private final Context mContext;
    private final FragmentManager mFragmentManager;
    private final int mContainerId;
    private ArrayDeque<Integer> mBackStack = new ArrayDeque<>();

    public FragmentAddNavigator(@NonNull Context context, @NonNull FragmentManager manager,
                                int containerId) {
        mContext = context;
        mFragmentManager = manager;
        mContainerId = containerId;

        mFragmentManager.addOnBackStackChangedListener(mOnBackStackChangedListener);
    }

    private final FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener =
            new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    // The initial Fragment won't be on the back stack, so the
                    // real count of destinations is the back stack entry count + 1
                    int newCount = mFragmentManager.getBackStackEntryCount() + 1;
                    if (newCount < mBackStack.size()) {
                        // Handle cases where the user hit the system back button
                        while (mBackStack.size() > newCount) {
                            mBackStack.removeLast();
                        }
                        int destId = mBackStack.isEmpty() ? 0 : mBackStack.peekLast();
                        dispatchOnNavigatorNavigated(destId, BACK_STACK_DESTINATION_POPPED);
                    }
                }
            };

    @NonNull
    @Override
    public FragmentNavigator.Destination createDestination() {
        return new FragmentNavigator.Destination(this);
    }

    @Override
    public void navigate(@NonNull FragmentNavigator.Destination destination, @Nullable Bundle args, @Nullable NavOptions navOptions) {
        final Fragment frag = destination.createFragment(args);
        final FragmentTransaction ft = mFragmentManager.beginTransaction();

        int enterAnim = navOptions != null ? navOptions.getEnterAnim() : -1;
        int exitAnim = navOptions != null ? navOptions.getExitAnim() : -1;
        int popEnterAnim = navOptions != null ? navOptions.getPopEnterAnim() : -1;
        int popExitAnim = navOptions != null ? navOptions.getPopExitAnim() : -1;
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = enterAnim != -1 ? enterAnim : 0;
            exitAnim = exitAnim != -1 ? exitAnim : 0;
            popEnterAnim = popEnterAnim != -1 ? popEnterAnim : 0;
            popExitAnim = popExitAnim != -1 ? popExitAnim : 0;
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
        }
        List<Fragment> fragmentList = mFragmentManager.getFragments();

        ft.replace(mContainerId, frag);

        final @IdRes int destId = destination.getId();
        final boolean initialNavigation = mBackStack.isEmpty();
        final boolean isClearTask = navOptions != null && navOptions.shouldClearTask();
        final boolean isSingleTopReplacement = navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId;
        int backStackEffect;
        if (!initialNavigation && !isClearTask && !isSingleTopReplacement) {
            ft.addToBackStack(getBackStackName(destId));
            backStackEffect = BACK_STACK_DESTINATION_ADDED;
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size() > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                mFragmentManager.popBackStack();
                ft.addToBackStack(getBackStackName(destId));
            }
            backStackEffect = BACK_STACK_UNCHANGED;
        } else {
            backStackEffect = BACK_STACK_DESTINATION_ADDED;
        }
        ft.setReorderingAllowed(true);
        ft.commit();
        // The commit succeeded, update our view of the world
        if (initialNavigation || !isSingleTopReplacement) {
            mBackStack.add(destId);
        }
        dispatchOnNavigatorNavigated(destId, backStackEffect);
    }

    @NonNull
    private String getBackStackName(@IdRes int destinationId) {
        try {
            return mContext.getResources().getResourceName(destinationId);
        } catch (Resources.NotFoundException e) {
            return Integer.toString(destinationId);
        }
    }

    @Override
    public boolean popBackStack() {
        if (mFragmentManager.getBackStackEntryCount() == 0) {
            return false;
        }
        mFragmentManager.popBackStack();
        mBackStack.removeLast();
        int destId = mBackStack.isEmpty() ? 0 : mBackStack.peekLast();
        dispatchOnNavigatorNavigated(destId, BACK_STACK_DESTINATION_POPPED);
        return true;
    }
}
