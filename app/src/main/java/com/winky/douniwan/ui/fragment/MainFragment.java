package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.winky.douniwan.R;
import com.winky.douniwan.databinding.FragmentMainBinding;
import com.winky.expand.basics.BaseSkinFragment;
import com.winky.expand.utils.NavigationUtils;

public class MainFragment extends BaseSkinFragment {
    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        FragmentMainBinding binding = setDatabinding(view);
        binding.tvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.getInstance().navigate(getView(), R.id.fragment_test1);
            }
        });
    }

}
