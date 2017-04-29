package com.ormlite.android.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangquan on 17/4/29.
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "user.db";
    private static final int VERSION = 1;

    private static DataBaseHelper instance = null;
    private Map<String, Dao> daos = new HashMap<>();

    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DataBaseHelper getInstance(Context context) {
        if (null == null) {
            synchronized (DataBaseHelper.class) {
                if (null == instance) {
                    instance = new DataBaseHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Artical.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            int drop = TableUtils.dropTable(connectionSource, User.class, true);
            System.out.println("onUpgrade,drop=" + drop);

            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dao<T,TD>
     * T: 数据表
     * TD: id类型
     */
    public Dao getDao(Class cls) throws SQLException {

        synchronized (this) {
            String name = cls.getName();
            if (daos.containsKey(name)) {
                return daos.get(name);
            } else {
                Dao dao = super.getDao(cls);
                daos.put(name, dao);
                return dao;
            }
        }
    }

    @Override
    public void close() {
        super.close();
        daos.clear();
        daos=null;
    }
}
