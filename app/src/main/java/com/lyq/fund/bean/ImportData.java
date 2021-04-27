package com.lyq.fund.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author by sunzhongda
 * @date 4/25/21
 */
public class ImportData implements Parcelable {

    String level;
    String type;
    String price;
    String number;
    String date;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ImportData{" +
                "level='" + level + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.level);
        dest.writeString(this.type);
        dest.writeString(this.price);
        dest.writeString(this.number);
        dest.writeString(this.date);
    }

    public void readFromParcel(Parcel source) {
        this.level = source.readString();
        this.type = source.readString();
        this.price = source.readString();
        this.number = source.readString();
        this.date = source.readString();
    }

    public ImportData() {
    }

    protected ImportData(Parcel in) {
        this.level = in.readString();
        this.type = in.readString();
        this.price = in.readString();
        this.number = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<ImportData> CREATOR = new Parcelable.Creator<ImportData>() {
        @Override
        public ImportData createFromParcel(Parcel source) {
            return new ImportData(source);
        }

        @Override
        public ImportData[] newArray(int size) {
            return new ImportData[size];
        }
    };
}
