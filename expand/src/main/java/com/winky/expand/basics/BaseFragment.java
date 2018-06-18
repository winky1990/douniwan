package com.winky.expand.basics;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winky.expand.listener.IBinding;

import java.lang.ref.WeakReference;

public abstract class BaseFragment extends Fragment implements IBinding {

    private WeakReference<Fragment> weakReference = null;
    private boolean VIEW_CREATED_PREVENT_REPEAT = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        weakReference = new WeakReference<Fragment>(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(bindLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (VIEW_CREATED_PREVENT_REPEAT) {
            VIEW_CREATED_PREVENT_REPEAT = false;
            init(view, savedInstanceState);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public <T extends ViewDataBinding> T setDatabinding(View view) {

        return DataBindingUtil.bind(view);
    }

    public Fragment getFragment() {
        return weakReference.get();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        weakReference.clear();
        weakReference = null;
    }
}
