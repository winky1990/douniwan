package com.winky.expand.listener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author winky
 * @date 2018/5/12
 */
public interface IBinding {

    int bindLayout();

    void init(@Nullable View view, @Nullable Bundle savedInstanceState);
}
