package com.jewel.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.jewel.http.core.IHttpCallback;
import com.jewel.model.cookery.RecipeInfo;
import com.jewel.model.cookery.RecipeInfoList;
import com.jewel.mvvm.live.SingleLiveEvent;
import com.jewel.mvvm.model.RecipeDataSource;
import com.jewel.mvvm.model.RecipeRepository;
import com.jewel.mvvm.model.remote.RecipeRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/19
 */
public class RecipeViewModel extends BaseViewModel {

    private final MediatorLiveData<List<RecipeInfo>> recipeList = new MediatorLiveData<>(); // 二级菜单当前选择位置对应的美食列表
    private final SingleLiveEvent<Integer> currentPageEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> currentCategoryID = new SingleLiveEvent<>();
    private final RecipeDataSource recipeDataSource; // 数据加载源


    public RecipeViewModel(@NonNull Application application) {
        super(application);
        this.recipeDataSource = RecipeRepository.getInstance(RecipeRemoteDataSource.getInstance(), null);
        currentPageEvent.setValue(1);
    }

    public MediatorLiveData<List<RecipeInfo>> getRecipeList() {
        return recipeList;
    }

    public SingleLiveEvent<Integer> getCurrentPageEvent() {
        return currentPageEvent;
    }

    public SingleLiveEvent<String> getCurrentCategoryID() {
        return currentCategoryID;
    }

    public void setCurrentCategoryID(String categoryID) {
        currentCategoryID.setValue(categoryID);
    }

    public void getRecipeList(String categoryId) {
        int page = 1;
        getRecipeList(categoryId, page);
    }

    public void getNextRecipeList() {
        int page = currentPageEvent.getValue() == null ? 1 : currentPageEvent.getValue();
        String categoryId = currentCategoryID.getValue();
        getRecipeList(categoryId, page);
    }

    private void getRecipeList(String categoryId, int page) {
        recipeDataSource.getRecipes(categoryId, page, new IHttpCallback<RecipeInfoList>() {
            @Override
            public void onStart(int what) {
                getLoadingEvent().setValue(true);
            }

            @Override
            public void onSuccess(int what, RecipeInfoList data) {
                getIsDataLoadingError().set(false);
                recipeList.setValue(data == null ? new ArrayList<>() : data.getList());
                getIsEmptyDataSource().set(data == null || data.getList() == null || data.getList().isEmpty());
                currentPageEvent.setValue(page);
            }

            @Override
            public void onFail(int what, String msg) {
                getIsDataLoadingError().set(true);
            }

            @Override
            public void onError(int what, Throwable e) {
                getIsDataLoadingError().set(true);
            }

            @Override
            public void onFinish(int what) {
                getLoadingEvent().setValue(false);
            }
        });
    }
}
