package com.winky.expand.basics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;

import com.winky.expand.skin.SkinInflaterFactory;
import com.winky.expand.skin.SkinManager;
import com.winky.expand.skin.listener.ISkinUpdate;

/**
 * 动态即时换肤
 *
 * @author winky
 * @date 2018/5/27
 */
public class BaseSkinActivity extends BaseAppActivity implements ISkinUpdate {

    private SkinInflaterFactory inflaterFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inflaterFactory = new SkinInflaterFactory(this);
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), inflaterFactory);
        super.onCreate(savedInstanceState);
    }

    public SkinInflaterFactory getInflaterFactory() {
        return inflaterFactory;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        inflaterFactory.clean();
    }

    @Override
    public void onThemeUpdate() {
        inflaterFactory.applySkin();
    }
}
