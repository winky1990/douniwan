package com.winky.expand.utils;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 抽象单例
 *
 * @param <T>
 */
public abstract class Singleton<T> {

    private final AtomicReference<T> atomicReference = new AtomicReference<>();
    private static final Object object = new Object();

    public T get() {
        T model = atomicReference.get();
        if (model == null) {
            synchronized (object) {
                if (atomicReference.get() == null) {
                    model = create();
                    atomicReference.set(model);
                } else {
                    model = atomicReference.get();
                }
            }
        }
        return model;
    }

    protected abstract T create();
}
