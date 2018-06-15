package com.winky.expand.utils;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;

public class VoiceUtils {

    private static final Singleton<VoiceUtils> SINGLETON = new Singleton<VoiceUtils>() {
        @Override
        protected VoiceUtils create() {
            return new VoiceUtils();
        }
    };

    public static VoiceUtils getInstance() {
        return SINGLETON.get();
    }

    // SoundPool对象
    private SoundPool soundPool;
    private Map<String, Integer> soundMap;

    private VoiceUtils() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_SYSTEM).build())
                    .build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        }
        soundMap = new HashMap<>();
    }

    public void play(String path) {
        int id;
        if (soundMap.containsKey(path)) {
            id = soundMap.get(path);
        } else {
            id = soundPool.load(path, 1);
            soundMap.put(path, id);
        }
//        float  leftVolume：左声道音量大小，这是一个相对值，大小在0.0f - 1.0f之间，具体计算方法为：你想要的声音大小 / 最大音量，比如取值0.8f表示最大音量的80%
//        float  rightVolume：右声道音量大小，具体如上
//        int  priority：优先级，值越大优先级越高，0的优先级最低。之前在第3步中也有一个优先级参数，但是那里无效，而现在这里的这个参数是正儿八经有效果的。
//        int loop：是否需要循环播放，取值不限。其中负数表示无穷循环（官方建议，如果无穷循环，用-1，当然-2、-3等也行），非负数表示循环次数，比如0表示循环0次，也就是播放一次就不再循环了，总共就只播放一次；1则表示循环1次（总共播放2次）。
//        float  rate：这个参数有点意思，可以理解为播放速率（就是快进、快退啥的），取值0.5f - 2.0f，其中0.5表示播放速度为正常的0.5倍。1表示正常速率播放。
        soundPool.play(id, 0.8f, 0.8f, 1, 0, 1.0f);
    }

    public void release() {
        soundMap.clear();
        soundMap = null;
        soundPool.release();
        soundPool = null;
    }


}
