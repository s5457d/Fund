package com.lyq.fund.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author by sunzhongda
 * @date 4/22/21
 */

@Entity(tableName = "FundLevel")
public class FundLevelData {

    /**
     * 主键，自增长
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "code")
    public String code = "";

    @ColumnInfo(name = "level")
    public String level = "";

    @ColumnInfo(name = "type")
    public String type = "";

    @ColumnInfo(name = "trigger")
    public String trigger = "";

    @ColumnInfo(name = "importPrice")
    public String importPrice = "";

    @ColumnInfo(name = "importNum")
    public String importNum = "";

    @ColumnInfo(name = "point")
    public String point = "";

    @ColumnInfo(name = "importDate")
    public String importDate = "";

    @ColumnInfo(name = "exportDate")
    public String exportDate = "";

    @ColumnInfo(name = "exportPrice")
    public String exportPrice = "";

    @ColumnInfo(name = "exportNum")
    public String exportNum = "";
    /**
     * 0 只有买入
     * 1 卖出的
     * 2 只是一个档位而已
     */
    @ColumnInfo(name = "done")
    public String done = "";

    @Override
    public String toString() {
        return "FundLevelData{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", level='" + level + '\'' +
                ", type='" + type + '\'' +
                ", trigger='" + trigger + '\'' +
                ", importPrice='" + importPrice + '\'' +
                ", importNum='" + importNum + '\'' +
                ", point='" + point + '\'' +
                ", importDate='" + importDate + '\'' +
                ", exportDate='" + exportDate + '\'' +
                ", done='" + done + '\'' +
                '}';
    }
}
