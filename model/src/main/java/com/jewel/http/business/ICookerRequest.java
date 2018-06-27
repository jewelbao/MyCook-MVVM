package com.jewel.http.business;

import com.jewel.http.core.IHttpCallback;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.model.cookery.RecipeInfoList;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/02
 */

public interface ICookerRequest {

    int WHAT_CATEGORIES = 1;
    int WHAT_RECIPES = 2;

    void getCategories(IHttpCallback<CategoryInfoList> callback);

    void getRecipes(String cid, int page, IHttpCallback<RecipeInfoList> callback);

}
