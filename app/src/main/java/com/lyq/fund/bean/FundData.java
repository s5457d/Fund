package com.lyq.fund.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author by sunzhongda
 * @date 4/22/21
 */

@Entity(tableName = "Fund")
public class FundData {

    /**
     * 主键，自增长
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name")
    public String name = "";

    @ColumnInfo(name = "code")
    public String code = "";

    @ColumnInfo(name = "lastPrice")
    public String lastPrice = "";

    @Override
    public String toString() {
        return "FundData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                '}';
    }
}
