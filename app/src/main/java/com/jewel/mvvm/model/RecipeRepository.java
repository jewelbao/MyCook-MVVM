package com.jewel.mvvm.model;

import com.jewel.http.business.CookCallback;
import com.jewel.http.core.IHttpCallback;
import com.jewel.model.cookery.RecipeInfoList;
import com.orhanobut.logger.Logger;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/20
 */
public class RecipeRepository implements RecipeDataSource {

    private volatile static RecipeRepository INSTANCE;

    private final RecipeDataSource remoteDataSource;
    private final RecipeDataSource localDataSource;

    public static RecipeRepository getInstance(RecipeDataSource remoteDataSource, RecipeDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (CategoryRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RecipeRepository(remoteDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    private RecipeRepository(RecipeDataSource remoteDataSource, RecipeDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public void getRecipes(String categoryId, int page, IHttpCallback<RecipeInfoList> recipeInfoListCallback) {
        if(localDataSource == null) {
            getRecipesFromRemote(categoryId, page, recipeInfoListCallback);
            return;
        }
        Logger.d("从本地获取数据");
        localDataSource.getRecipes(categoryId, page, new CookCallback<RecipeInfoList>() {

            @Override
            public void onSuccess(int what, RecipeInfoList data) {
                if(data == null || data.getList() == null) {
                    getRecipesFromRemote(categoryId, page, recipeInfoListCallback);
                    return;
                }
                if(recipeInfoListCallback != null) {
                    recipeInfoListCallback.onSuccess(what, data);
                }
            }

            @Override
            public void onFail(int what, String msg) {
                getRecipesFromRemote(categoryId, page, recipeInfoListCallback);
            }

            @Override
            public void onError(int what, Throwable e) {
                getRecipesFromRemote(categoryId, page, recipeInfoListCallback);
            }
        });
    }

    private void getRecipesFromRemote(String categoryId, int page, IHttpCallback<RecipeInfoList> recipeInfoListCallback) {
        if(remoteDataSource == null) {
            throw new NullPointerException("data source is null.Please init at least one of DataSource");
        }
        Logger.d("从服务器获取数据");
        remoteDataSource.getRecipes(categoryId, page, new IHttpCallback<RecipeInfoList>() {
            @Override
            public void onStart(int what) {
                if(recipeInfoListCallback != null) {
                    recipeInfoListCallback.onStart(what);
                }
            }

            @Override
            public void onSuccess(int what, RecipeInfoList data) {
                if(recipeInfoListCallback != null) {
                    recipeInfoListCallback.onSuccess(what, data);
                }
            }

            @Override
            public void onFail(int what, String msg) {
                if(recipeInfoListCallback != null) {
                    recipeInfoListCallback.onFail(what, msg);
                }
            }

            @Override
            public void onError(int what, Throwable e) {
                if(recipeInfoListCallback != null) {
                    recipeInfoListCallback.onError(what, e);
                }
            }

            @Override
            public void onFinish(int what) {
                if(recipeInfoListCallback != null) {
                    recipeInfoListCallback.onFinish(what);
                }
            }
        });
    }
}
