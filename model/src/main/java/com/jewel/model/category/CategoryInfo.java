package com.jewel.model.category;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/01
 */

public class CategoryInfo implements Parcelable{


    /**
     * ctgId : 标签ID
     * name : 标签名
     * parentId : 父标签ID
     */
    private String ctgId;
    private String name;
    private String parentId;

    public String getCtgId() {
        return ctgId;
    }

    public void setCtgId(String ctgId) {
        this.ctgId = ctgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ctgId);
        dest.writeString(this.name);
        dest.writeString(this.parentId);
    }

    public CategoryInfo() {
    }

    protected CategoryInfo(Parcel in) {
        this.ctgId = in.readString();
        this.name = in.readString();
        this.parentId = in.readString();
    }

    public static final Creator<CategoryInfo> CREATOR = new Creator<CategoryInfo>() {
        @Override
        public CategoryInfo createFromParcel(Parcel source) {
            return new CategoryInfo(source);
        }

        @Override
        public CategoryInfo[] newArray(int size) {
            return new CategoryInfo[size];
        }
    };
}
