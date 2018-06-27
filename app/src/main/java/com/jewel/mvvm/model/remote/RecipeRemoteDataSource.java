package com.jewel.mvvm.model.remote;

import com.jewel.http.business.CookerRequest;
import com.jewel.http.business.ICookerRequest;
import com.jewel.http.core.IHttpCallback;
import com.jewel.model.cookery.RecipeInfoList;
import com.jewel.mvvm.model.RecipeDataSource;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/20
 */
public class RecipeRemoteDataSource implements RecipeDataSource {

    private final ICookerRequest cookerRequest;
    private static RecipeRemoteDataSource INSTANCE;

    public static RecipeRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RecipeRemoteDataSource();
        }
        return INSTANCE;
    }

    private RecipeRemoteDataSource() {
        cookerRequest = new CookerRequest();
    }

    @Override
    public void getRecipes(String categoryId, int page, IHttpCallback<RecipeInfoList> recipeInfoListCallback) {
        cookerRequest.getRecipes(categoryId, page, recipeInfoListCallback);
    }
}
