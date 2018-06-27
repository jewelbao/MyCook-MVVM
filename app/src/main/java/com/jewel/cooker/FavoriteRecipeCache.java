package com.jewel.cooker;

import android.text.TextUtils;

import com.jewel.greendao.auto.RecipeDetailDao;
import com.jewel.greendao.auto.RecipeInfoDao;
import com.jewel.model.cookery.RecipeInfo;

import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/05/13
 */
public class FavoriteRecipeCache {

    private static final FavoriteRecipeCache ourInstance = new FavoriteRecipeCache();

    static FavoriteRecipeCache getInstance() {
        return ourInstance;
    }

    private RecipeInfoDao recipeInfoDao;
    private RecipeDetailDao recipeDetailDao;

    private FavoriteRecipeCache() {
        if (recipeInfoDao == null)
            recipeInfoDao = BaseApp.getInstance().getDaoSession().getRecipeInfoDao();
        if (recipeDetailDao == null)
            recipeDetailDao = BaseApp.getInstance().getDaoSession().getRecipeDetailDao();
    }

    public RecipeDetailDao getRecipeDetailDao() {
        return recipeDetailDao;
    }

    public RecipeInfoDao getRecipeInfoDao() {
        return recipeInfoDao;
    }

    public List<RecipeInfo> getFavorites() {
        return getRecipeInfoDao().loadAll();
    }

    public boolean hadFavorite(RecipeInfo info) {
        RecipeInfo temp = getRecipeInfoDao().load(info.getMenuId());
        if(temp == null) {
            return false;
        }
        if(!TextUtils.equals(info.getMenuId(),temp.getMenuId())) {
            return false;
        }
        return true;
    }

    public void addToFavorite(RecipeInfo info) {
        if(hadFavorite(info)) {
            return;
        }
        getRecipeInfoDao().insertOrReplace(info);
    }

    public void removeFromFavorite(RecipeInfo info) {
        getRecipeInfoDao().deleteByKey(info.getMenuId());
    }
}
