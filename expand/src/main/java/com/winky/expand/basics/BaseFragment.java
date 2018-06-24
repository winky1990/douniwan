package com.winky.expand.basics;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.winky.expand.listener.IBinding;

import java.lang.ref.WeakReference;

import androidx.navigation.fragment.NavHostFragment;

public abstract class BaseFragment extends Fragment implements IBinding {

    private WeakReference<Fragment> weakReference = null;
    private View contentView;
    private String title;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        weakReference = new WeakReference<Fragment>(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(bindLayout(), container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(contentView, savedInstanceState);
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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

    public <T extends View> T findViewById(@IdRes int id) {
        return contentView.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
    }

    public String getTitle() {
        return title;
    }

    public BaseFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (!NavHostFragment.findNavController(getFragment()).navigateUp()) {
                getActivity().finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
