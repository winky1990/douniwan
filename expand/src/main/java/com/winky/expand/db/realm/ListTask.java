package com.winky.expand.db.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * @author winky
 * @date 2018/5/10
 */
public abstract class ListTask implements Realm.Transaction {

    private List<? extends RealmModel> data;
    private Object object;

    public ListTask(List<? extends RealmModel> data, Object object) {
        this.data = data;
        this.object = object;
    }

    @Override
    public void execute(Realm realm) {
        synchronized (object) {
            execute(realm, data);
        }
    }

    public abstract void execute(Realm realm, List<? extends RealmModel> data);
}
