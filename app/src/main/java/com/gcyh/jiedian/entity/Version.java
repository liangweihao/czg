package com.gcyh.jiedian.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class Version implements Parcelable {
    private int id;
    private String version_info;//版本描述信息
    private String address;//下载地址
    private String create_date;
    private String title;//标识，前端不展示
    private String must;//1为强制更新，2否

    private String theTitle;//版本号

    public String getTheTitle() {
        return theTitle;
    }

    public void setTheTitle(String theTitle) {
        this.theTitle = theTitle;
    }

    public String getMust() {
        return must;
    }

    public void setMust(String must) {
        this.must = must;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion_info() {
        return version_info;
    }

    public void setVersion_info(String versionInfo) {
        version_info = versionInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String createDate) {
        create_date = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Version [address=" + address + ", create_date=" + create_date
                + ", id=" + id + ", title=" + title + ", version_info="
                + version_info + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.version_info);
        dest.writeString(this.address);
        dest.writeString(this.create_date);
        dest.writeString(this.title);
    }

    public Version() {
    }

    protected Version(Parcel in) {
        this.id = in.readInt();
        this.version_info = in.readString();
        this.address = in.readString();
        this.create_date = in.readString();
        this.title = in.readString();
    }

    public static final Creator<Version> CREATOR = new Creator<Version>() {
        @Override
        public Version createFromParcel(Parcel source) {
            return new Version(source);
        }

        @Override
        public Version[] newArray(int size) {
            return new Version[size];
        }
    };
}
