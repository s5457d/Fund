package com.lyq.fund.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lyq.fund.bean.FundLevelData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by sunzhongda
 * @date 4/22/21
 */
@Dao
public interface FundLevelDataDao {

    @Query("select * from FundLevel where code = :code and done =:done")
    List<FundLevelData> allFundLevelDatasByCode(String code, String done);

    @Query("select * from FundLevel where code = :code and done =:done order by exportDate")
    List<FundLevelData> allExportFundLevelDatasByCode(String code, String done);

    @Query("select * from FundLevel where id = :id")
    FundLevelData fundLevelDatasByID(long id);

    @Query("select * from FundLevel where code = :code and level = :level and done =:done")
    FundLevelData fundLevelDatasByCodeLevel(String code, String level, String done);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertFundLevelDatas(FundLevelData... fundDatas);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertFundLevelDatas(List<FundLevelData> fundDatas);

//    @Query("select * from `FundStalls` where id = :id")
//    FundStallData queryFundStallDataByID(long id);

    @Update
    int updateFundLevelDataByID(FundLevelData... fundDatas);

    @Delete
    int deleteFundLevelDatas(ArrayList<FundLevelData> fundDatas);

    @Delete
    int deleteFundLevelDatas(FundLevelData... fundDatas);
}
