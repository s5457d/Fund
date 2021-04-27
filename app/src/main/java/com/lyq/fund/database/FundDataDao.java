package com.lyq.fund.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lyq.fund.bean.FundData;

import java.util.List;

/**
 * @author by sunzhongda
 * @date 4/22/21
 */
@Dao
public interface FundDataDao {

    @Query("select * from `Fund`")
    List<FundData> allFundDatas();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertfundDatas(FundData... fundDatas);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertfundDatas(List<FundData> fundDatas);

    @Query("select * from `Fund` where id = :id")
    FundData queryFundDataByID(long id);

    @Update
    int updateFundDataByID(FundData... fundDatas);

    @Delete
    int deletefundDatas(FundData... fundDatas);
}
