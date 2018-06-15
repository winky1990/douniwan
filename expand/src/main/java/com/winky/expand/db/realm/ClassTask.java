package com.winky.expand.db.realm;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * @author winky
 * @date 2018/5/10
 */
public abstract class ClassTask implements Realm.Transaction {

    private Class<? extends RealmModel> clazz;
    private Object object;

    public ClassTask(Class<? extends RealmModel> clazz, Object object) {
        this.clazz = clazz;
        this.object = object;
    }

    @Override
    public void execute(Realm realm) {
        synchronized (object) {
            execute(realm, clazz);
        }
    }

    public abstract void execute(Realm realm, Class<? extends RealmModel> clazz);
}
