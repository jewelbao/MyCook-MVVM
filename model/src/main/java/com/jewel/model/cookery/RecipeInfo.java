package com.jewel.model.cookery;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.JSON;
import com.jewel.ListStringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * 菜谱信息
 * @author Jewel
 * @version 1.0
 * @since 2018/04/03
 */
@Entity
public class RecipeInfo implements Parcelable{


    /**
     * ctgIds : 菜谱标签ID数组
     * ctgTitles : 菜谱标签数组
     * menuId : 菜谱ID
     * name : 菜谱名
     * recipe : 做法
     * thumbnail : 菜谱缩略图
     */

    private String ctgTitles;
    @Id
    private String menuId;
    private String name;
    @Convert(columnType = String.class, converter = ObjToStringConverter.class)
    private RecipeDetail recipe;
    private String thumbnail;
    @Convert(columnType = String.class, converter = ListStringConverter.class)
    private List<String> ctgIds;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ctgTitles);
        dest.writeString(this.menuId);
        dest.writeString(this.name);
        dest.writeParcelable(this.recipe, flags);
        dest.writeString(this.thumbnail);
        dest.writeStringList(this.ctgIds);
    }

    public String getCtgTitles() {
        return this.ctgTitles;
    }

    public void setCtgTitles(String ctgTitles) {
        this.ctgTitles = ctgTitles;
    }

    public String getMenuId() {
        return this.menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getCtgIds() {
        return this.ctgIds;
    }

    public void setCtgIds(List<String> ctgIds) {
        this.ctgIds = ctgIds;
    }

    public RecipeDetail getRecipe() {
        return this.recipe;
    }

    public void setRecipe(RecipeDetail recipe) {
        this.recipe = recipe;
    }


    public RecipeInfo() {
    }

    protected RecipeInfo(Parcel in) {
        this.ctgTitles = in.readString();
        this.menuId = in.readString();
        this.name = in.readString();
        this.recipe = in.readParcelable(RecipeDetail.class.getClassLoader());
        this.thumbnail = in.readString();
        this.ctgIds = in.createStringArrayList();
    }

    @Generated(hash = 605436352)
    public RecipeInfo(String ctgTitles, String menuId, String name,
            RecipeDetail recipe, String thumbnail, List<String> ctgIds) {
        this.ctgTitles = ctgTitles;
        this.menuId = menuId;
        this.name = name;
        this.recipe = recipe;
        this.thumbnail = thumbnail;
        this.ctgIds = ctgIds;
    }

    public static final Creator<RecipeInfo> CREATOR = new Creator<RecipeInfo>() {
        @Override
        public RecipeInfo createFromParcel(Parcel source) {
            return new RecipeInfo(source);
        }

        @Override
        public RecipeInfo[] newArray(int size) {
            return new RecipeInfo[size];
        }
    };

    public static class ObjToStringConverter implements PropertyConverter<RecipeDetail, String> {

        @Override
        public RecipeDetail convertToEntityProperty(String databaseValue) {
            if(databaseValue == null) {
                return null;
            } else {
                return JSON.parseObject(databaseValue, RecipeDetail.class);
            }
        }

        @Override
        public String convertToDatabaseValue(RecipeDetail entityProperty) {
            if(entityProperty == null) {
                return null;
            } else {
                return JSON.toJSONString(entityProperty);
            }
        }
    }
}
