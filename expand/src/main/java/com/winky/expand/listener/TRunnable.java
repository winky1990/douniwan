package com.winky.expand.listener;

/**
 * @author winky
 * @date 2018/5/6
 */
public abstract class TRunnable<T> implements Runnable {

    private T t;

    public TRunnable(T t) {
        this.t = t;
    }

    @Override
    public void run() {
        this.run(t);
    }

    public abstract void run(T t);
}
