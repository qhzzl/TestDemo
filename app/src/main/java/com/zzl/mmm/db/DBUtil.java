package com.zzl.mmm.db;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by Administrator on 2016/8/15.
 */
public class DBUtil {
    private DbManager.DaoConfig daoConfig;
    private static DbManager db;
    private final String DB_NAME = "mydb";
    private final int VERSION = 1;//1
    private DBUtil() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName(DB_NAME)
                .setDbVersion(VERSION)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启WAL, 对写入加速提升巨大(作者原话)
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        //数据库升级操作

                    }
                });
        db = x.getDb(daoConfig);
    }

    public synchronized static DbManager getDbManager(){
        if (db == null){
            DBUtil databaseOpenHelper = new DBUtil();
        }
        return db;
    }
}
