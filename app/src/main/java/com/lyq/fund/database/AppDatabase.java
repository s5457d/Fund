package com.lyq.fund.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lyq.fund.bean.FundData;
import com.lyq.fund.bean.FundLevelData;

/**
 * @author by sunzhongda
 * @date 4/22/21
 */
@Database(entities = {FundData.class, FundLevelData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "fund-db";
    private static AppDatabase appDatabase;

    public abstract FundDataDao FundDataDao();

    public abstract FundLevelDataDao FundLevelDataDao();

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            synchronized (AppDatabase.class) {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        return appDatabase;
    }
}
