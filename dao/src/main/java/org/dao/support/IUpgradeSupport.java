package org.dao.support;

import android.database.sqlite.SQLiteDatabase;

/**
 * description：数据库升级回调接口
 * <p/>
 * Created by TIAN FENG on 2018/2/5.
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */

public interface IUpgradeSupport {

   <T> void onUpgradeSql(SQLiteDatabase db, int oldVersion, int newVersion, Class<T> clazz, String tableName);
}
