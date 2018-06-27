package com.jewel.model.cookery;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 制作步骤
 *
 * @author Jewel
 * @version 1.0
 * @since 2018/04/03
 */
@Entity
public class RecipeDetail implements Parcelable{


    /**
     * img : 菜谱详情图
     * ingredients : 配料
     * method : 做法 可解析为{@link java.util.List<RecipeMethod>}
     * sumary : 总结
     * title : 标题
     */

    @Id(autoincrement = true)
    private long id;

    private String img;
    private String ingredients;
    private String method;
    private String sumary;
    private String title;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSumary() {
        return this.sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Generated(hash = 1201224872)
    public RecipeDetail(long id, String img, String ingredients, String method, String sumary,
            String title) {
        this.id = id;
        this.img = img;
        this.ingredients = ingredients;
        this.method = method;
        this.sumary = sumary;
        this.title = title;
    }

    @Generated(hash = 350401858)
    public RecipeDetail() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.img);
        dest.writeString(this.ingredients);
        dest.writeString(this.method);
        dest.writeString(this.sumary);
        dest.writeString(this.title);
    }

    protected RecipeDetail(Parcel in) {
        this.id = in.readLong();
        this.img = in.readString();
        this.ingredients = in.readString();
        this.method = in.readString();
        this.sumary = in.readString();
        this.title = in.readString();
    }

    public static final Creator<RecipeDetail> CREATOR = new Creator<RecipeDetail>() {
        @Override
        public RecipeDetail createFromParcel(Parcel source) {
            return new RecipeDetail(source);
        }

        @Override
        public RecipeDetail[] newArray(int size) {
            return new RecipeDetail[size];
        }
    };
}
