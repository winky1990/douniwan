package com.winky.expand.db;

import android.content.Context;

import com.winky.expand.db.realm.ClassTask;
import com.winky.expand.db.realm.ListTask;
import com.winky.expand.db.realm.Task;
import com.winky.expand.utils.Singleton;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.Sort;


public class RealmUtils {

    private static final Singleton<RealmUtils> SINGLETON = new Singleton<RealmUtils>() {
        @Override
        protected RealmUtils create() {
            return new RealmUtils();
        }
    };

    public static RealmUtils getInstance() {
        return SINGLETON.get();
    }

    private Realm realm;
    private static final Object object = new Object();

    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        Realm.init(context.getApplicationContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(RealmConfiguration.DEFAULT_REALM_NAME)//数据库名
                .schemaVersion(1)//数据库版本
                .deleteRealmIfMigrationNeeded()//如果要迁移，删除库
                .encryptionKey(new byte[64])//数据加密
                .build();
        Realm.setDefaultConfiguration(configuration);
        this.realm = Realm.getDefaultInstance();
    }

    /**
     * 释放数据库资源
     */
    public void release() {
        realm.removeAllChangeListeners();
        realm.close();
        realm = null;
    }

    public void add(RealmModel data) {
        this.realm.executeTransaction(new Task(data, object) {
            @Override
            public void execute(Realm realm, RealmModel data) {
                realm.insert(data);
            }
        });
    }

    public void add(List<? extends RealmModel> datas) {
        this.realm.executeTransaction(new ListTask(datas, object) {
            @Override
            public void execute(Realm realm, List<? extends RealmModel> data) {
                realm.insert(data);
            }
        });
    }

    public void asyncAdd(RealmModel data) {
        this.realm.executeTransactionAsync(new Task(data, object) {
            @Override
            public void execute(Realm realm, RealmModel data) {
                realm.insert(data);
            }
        });

    }

    public void asyncAdd(List<? extends RealmModel> datas) {
        this.realm.executeTransactionAsync(new ListTask(datas, object) {
            @Override
            public void execute(Realm realm, List<? extends RealmModel> data) {
                realm.insert(data);
            }
        });
    }

    public void update(RealmModel data) {
        this.realm.executeTransaction(new Task(data, object) {
            @Override
            public void execute(Realm realm, RealmModel data) {
                realm.insertOrUpdate(data);
            }
        });
    }

    public void asyncUpdate(RealmModel data) {
        this.realm.executeTransactionAsync(new Task(data, object) {
            @Override
            public void execute(Realm realm, RealmModel data) {
                realm.insertOrUpdate(data);
            }
        });
    }

    public void update(List<? extends RealmModel> datas) {
        this.realm.executeTransaction(new ListTask(datas, object) {
            @Override
            public void execute(Realm realm, List<? extends RealmModel> data) {
                realm.insertOrUpdate(data);
            }
        });
    }

    public void asyncUpdate(List<? extends RealmModel> datas) {
        this.realm.executeTransactionAsync(new ListTask(datas, object) {
            @Override
            public void execute(Realm realm, List<? extends RealmModel> data) {
                realm.insertOrUpdate(data);
            }
        });
    }

    public void del(Class<? extends RealmModel> clazz) {
        this.realm.executeTransaction(new ClassTask(clazz, object) {
            @Override
            public void execute(Realm realm, Class<? extends RealmModel> clazz) {
                realm.delete(clazz);
            }
        });
    }

    public void asyncDel(Class<? extends RealmModel> clazz) {
        this.realm.executeTransactionAsync(new ClassTask(clazz, object) {
            @Override
            public void execute(Realm realm, Class<? extends RealmModel> clazz) {
                realm.delete(clazz);
            }
        });

    }

    public <T extends RealmModel> T queryFirst(Class<T> clazz) {
        synchronized (object) {
            return this.realm.where(clazz).findFirst();
        }
    }

    public <T extends RealmModel> T queryFirst(Class<T> clazz, String fieldName, String value) {
        synchronized (object) {
            return this.realm.where(clazz).equalTo(fieldName, value).findFirst();
        }
    }

    public <T extends RealmModel> T asyncQueryFirst(Class<T> clazz, String fieldName, String value) {
        synchronized (object) {
            return this.realm.where(clazz).equalTo(fieldName, value).findFirstAsync();
        }
    }

    /**
     * 返回数据倒序
     *
     * @param clazz
     * @param fieldName
     * @param <T>
     * @return
     */
    public <T extends RealmModel> List<T> queryAllByDesc(Class<T> clazz, String fieldName) {
        synchronized (object) {
            return this.realm.copyFromRealm(this.realm.where(clazz).findAll().sort(fieldName, Sort.DESCENDING));
        }
    }

    public <T extends RealmModel> List<T> asyncQueryAll(Class<T> clazz) {
        synchronized (object) {
            return this.realm.copyFromRealm(this.realm.where(clazz).findAllAsync());
        }
    }

    public <T extends RealmModel> List<T> asyncQueryAll(Class<T> clazz, String fieldName, String value) {
        synchronized (object) {
            return this.realm.copyFromRealm(this.realm.where(clazz).findAllAsync());
        }
    }


    //可跟查询条件
    //.or()                      或者
    //.beginsWith()              以xxx开头
    //.endsWith()                以xxx结尾
    //.greaterThan()             大于
    //.greaterThanOrEqualTo()    大于或等于
    //.lessThan()                小于
    //.lessThanOrEqualTo()       小于或等于
    //.equalTo()                 等于
    //.notEqualTo()              不等于
    //.findAll()                 查询所有
    //.average()                 平均值
    //.beginGroup()              开始分组
    //.endGroup()                结束分组
    //.between()                 在a和b之间
    //.contains()                包含xxx
    //.count()                   统计数量
    //.distinct()                去除重复
    //.findFirst()               返回结果集的第一行记录
    //.isNotEmpty()              非空串
    //.isEmpty()                 为空串
    //.isNotNull()               非空对象
    //.isNull()                  为空对象
    //.max()                     最大值
    //.maximumDate()             最大日期
    //.min()                     最小值
    //.minimumDate()             最小日期
    //.sum()                     求和
}
