package com.jewel.model.cookery;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 做法详情
 * @author Jewel
 * @version 1.0
 * @since 2018/04/05
 */
public class RecipeMethod implements Parcelable{

    private String img;
    private String step;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
        dest.writeString(this.step);
    }

    public RecipeMethod() {
    }

    protected RecipeMethod(Parcel in) {
        this.img = in.readString();
        this.step = in.readString();
    }

    public static final Creator<RecipeMethod> CREATOR = new Creator<RecipeMethod>() {
        @Override
        public RecipeMethod createFromParcel(Parcel source) {
            return new RecipeMethod(source);
        }

        @Override
        public RecipeMethod[] newArray(int size) {
            return new RecipeMethod[size];
        }
    };
}
