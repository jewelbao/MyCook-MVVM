package com.jewel.model.category;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/01
 */

public class CategoryInfoList implements Parcelable{

    private CategoryInfo categoryInfo;
    private List<CategoryInfoList> childs;
    private boolean isChecked;

    public CategoryInfoList(CategoryInfo categoryInfo, List<CategoryInfoList> childs, boolean isChecked) {
        this.categoryInfo = categoryInfo;
        this.childs = childs;
        this.isChecked = isChecked;
    }

    public CategoryInfo getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public List<CategoryInfoList> getChilds() {
        return childs;
    }

    public void setChilds(List<CategoryInfoList> childs) {
        this.childs = childs;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.categoryInfo, flags);
        dest.writeTypedList(this.childs);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    public CategoryInfoList() {
    }

    protected CategoryInfoList(Parcel in) {
        this.categoryInfo = in.readParcelable(CategoryInfo.class.getClassLoader());
        this.childs = in.createTypedArrayList(CategoryInfoList.CREATOR);
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<CategoryInfoList> CREATOR = new Creator<CategoryInfoList>() {
        @Override
        public CategoryInfoList createFromParcel(Parcel source) {
            return new CategoryInfoList(source);
        }

        @Override
        public CategoryInfoList[] newArray(int size) {
            return new CategoryInfoList[size];
        }
    };
}
