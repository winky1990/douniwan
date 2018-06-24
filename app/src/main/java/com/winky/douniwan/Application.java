package com.winky.douniwan;

import android.os.Build;
import android.os.Looper;

import com.mob.MobSDK;
import com.mob.MobUser;
import com.winky.douniwan.tools.LibControl;
import com.winky.expand.utils.SysUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Looper.getMainLooper().isCurrentThread())
                LibControl.getInstance().init(this);
        } else {
            if (Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId()) {
                LibControl.getInstance().init(this);
            }
        }

        // TODO: 2018/6/24 放在welcome页面
        MobSDK.getUser(new MobUser.OnUserGotListener() {
            @Override
            public void onUserGot(MobUser mobUser) {
                if (mobUser == null) {
                    MobSDK.setUser(SysUtils.getInstance().getIMEI(), randomNickname(), randomPhoto(), null);
                }
            }
        });
    }

    private String randomNickname() {
        StringBuffer buffer = new StringBuffer(14);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm_");
        buffer.append(dateFormat.format(new Date()));
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            buffer.append(str[random.nextInt(26)]);
        }
        return buffer.toString();
    }

    private String randomPhoto() {
        List<String> list = new ArrayList<>(15);
        list.add("http://download.sdk.mob.com/e72/83d/e247e8b45bd557f70ac6dcc0cb.png");
        list.add("http://download.sdk.mob.com/7b6/264/2c4a9fef9ffa03e5deb5973ab9.png");
        list.add("http://download.sdk.mob.com/bbd/480/d993f23339944e4de27e4b0a12.png");
        list.add("http://download.sdk.mob.com/3a6/b11/ba6a81f2c13fb0ba3b96d99619.png");
        list.add("http://download.sdk.mob.com/a0b/7d0/0520d3554a69ad50a3b87d1760.png");
        list.add("http://download.sdk.mob.com/510/deb/0c0731ac543eb71311c482a2e2.png");
        list.add("http://download.sdk.mob.com/7d7/e2b/91d898dfde6fb787ab3d926f9d.png");
        list.add("http://download.sdk.mob.com/29f/06f/e6a941cd02e3f29465cd438d16.png");
        list.add("http://download.sdk.mob.com/167/bc4/38197ca7950aec7020d516fbb2.png");
        list.add("http://download.sdk.mob.com/f57/a5e/72ecd0c6ca96361c7f3bcd7144.png");
        list.add("http://download.sdk.mob.com/e31/c6e/315fdfa6abc4b17d8c139605de.png");
        list.add("http://download.sdk.mob.com/cc3/00e/dedc8bf1514d6c6a5e456fba74.png");
        list.add("http://download.sdk.mob.com/f22/154/e27eaf3fc3e24047bd5d4ec3a8.png");
        list.add("http://download.sdk.mob.com/d33/6f9/c15ee2d2f01aba51d33985e6c5.png");
        list.add("http://download.sdk.mob.com/cc6/115/2628761069dd35867eda68fe2a.png");
        list.add("http://download.sdk.mob.com/047/a51/38cfad789e9808443d11f2f9be.png");

        return list.get(new Random().nextInt(15));
    }

    /**
     * 内存不足时
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }
}
