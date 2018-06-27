package com.jewel.mvvm.model;

import com.jewel.http.core.IHttpCallback;
import com.jewel.model.cookery.RecipeInfoList;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/20
 */
public interface RecipeDataSource extends DataSource {

    void getRecipes(String categoryId, int page, IHttpCallback<RecipeInfoList> recipeInfoListCallback);
}
