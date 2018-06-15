package com.winky.expand.db.realm;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * @author winky
 * @date 2018/5/10
 */
public abstract class Task<T extends RealmModel> implements Realm.Transaction {
    private T data;
    private Object object;

    public Task(T data, Object object) {
        this.data = data;
        this.object = object;
    }

    @Override
    public void execute(Realm realm) {
        synchronized (object) {
            execute(realm, data);
        }
    }

    public abstract void execute(Realm realm, T data);
}
