package org.dao;

import android.app.Application;

import org.dao.support.DaoSQLiteOpenHelperSupport;
import org.dao.support.IUpgradeSupport;


/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2018/2/5.
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */

public class DaoFactory<T> {
    private static DaoFactory sDaoFactory;

    private Builder.Params<T> params;

    private DaoFactory(Builder.Params<T> params) {
        this.params = params;
    }

    // 获取单例
    public static DaoFactory get() {
        if (sDaoFactory == null) {
            throw new IllegalStateException("请Application中初始化" + DaoFactory.class.getSimpleName());
        }
        return sDaoFactory;
    }

    // 获取DaoSupport并且创建一个表
    public IDaoSupport<T> getDaoSupportAndCreateTable(Class<T> clazz) {
        params.mDaoSoupport.init(clazz);
        return params.mDaoSoupport;
    }

    /************************ 用来构建DaoFactory *******************************************************************************************/
    public static class Builder {

        private Params P;

        private Builder(Application app) {
            P = new Params(app);
        }

        /**
         * application
         */
        public static Builder app(Application app) {
            return new Builder(app);
        }

        // 数据库名
        public Builder dbName(String dbName) {
            if (dbName.endsWith(".db")) {
                P.mDbName = dbName;
            } else {
                P.mDbName = dbName + ".db";
            }
            return this;
        }

        // 数据库版本
        public Builder dbVersion(int dbVersion) {
            P.mDbVersion = dbVersion;
            return this;
        }

        // 自定义的daoSoupport
        public Builder dbDaoSupport(IUpgradeSupport upgradeSupport) {
            P.IUpgradeSupport = upgradeSupport;
            return this;
        }

        // 构建 也就是 init
        public <T> void build() {
            P.mDaoSoupport = new DaoSQLiteOpenHelperSupport(P.IUpgradeSupport, P.mApp, P.mDbName, P.mDbVersion);
            sDaoFactory = new DaoFactory(P);
        }

        private static class Params<T> {
            private Application mApp;
            private String mDbName = "simple.db";
            public int mDbVersion = 1;
            private IUpgradeSupport IUpgradeSupport;
            public IDaoSupport<T> mDaoSoupport;
            public Params(Application app) {
                mApp = app;
            }
        }
    }
}
