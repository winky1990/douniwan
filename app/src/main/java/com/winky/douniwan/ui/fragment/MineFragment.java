package com.winky.douniwan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mob.MobSDK;
import com.mob.MobUser;
import com.winky.douniwan.R;
import com.winky.expand.basics.SkinFragment;
import com.winky.expand.utils.GlideUtils;
import com.winky.expand.utils.SysUtils;

public class MineFragment extends SkinFragment {

    @Override
    public int bindLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        MobSDK.getUser(new MobUser.OnUserGotListener() {
            @Override
            public void onUserGot(MobUser mobUser) {
                GlideUtils.getInstance().LoadContextCircleBitmap(getContext(), mobUser.getAvatar(), (ImageView) findViewById(R.id.image1));
                ((TextView) findViewById(R.id.text1)).setText(mobUser.getId());
                ((TextView) findViewById(R.id.text2)).setText(mobUser.getNickName());

            }
        });
    }
}
