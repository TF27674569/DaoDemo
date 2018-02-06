package com.daodemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.daodemo.data.Person;
import org.dao.DaoFactory;
import org.dao.support.IUpgradeSupport;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2018/2/5.
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */

public class App extends Application {
    private static final int DB_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoFactory.Builder
                .app(this)
                .dbName("test.db")
                .dbVersion(DB_VERSION)
                .dbDaoSupport(new IUpgradeSupport() {

                    @Override
                    public <T> void onUpgradeSql(SQLiteDatabase db, int oldVersion, int newVersion, Class<T> clazz, String tableName) {
                        if ((clazz == Person.class)) {
                            String upgrade = "alter table " + tableName + " add sex text";//增加一个列sex
                            db.execSQL(upgrade);
                        }
                        Log.e("TAG", "onUpgradeSql: " + tableName);
                    }
                })
                .build();
    }
}
