package com.jewel.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.jewel.http.business.CookCallback;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.mvvm.live.SingleLiveEvent;
import com.jewel.mvvm.model.CategoryDataSource;
import com.jewel.mvvm.model.CategoryRepository;
import com.jewel.mvvm.model.remote.CategoryRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/13
 */
public class CategoryViewModel extends BaseViewModel {

    private final SingleLiveEvent<List<CategoryInfoList>> categoryList = new SingleLiveEvent<>(); // 导航栏菜单观察数据
    private final SingleLiveEvent<Integer> currentCategoryIndex = new SingleLiveEvent<>(); // 导航栏菜单当前选择位置

    private final LiveData<List<CategoryInfoList>> subCategoryList = Transformations.map(currentCategoryIndex, this::getSubCategory);
    private final SingleLiveEvent<Integer> currentSubCategoryIndex = new SingleLiveEvent<>(); // 二级菜单当前选择位置

    private final CategoryDataSource categoryDataSource; // 数据加载源

    CategoryViewModel(@NonNull Application application) {
        super(application);
        this.categoryDataSource = CategoryRepository.getInstance(CategoryRemoteDataSource.getInstance());
        categoryList.setValue(null);
        getCategory();
    }

    public SingleLiveEvent<List<CategoryInfoList>> getCategoryList() {
        return categoryList;
    }

    public LiveData<List<CategoryInfoList>> getSubCategoryList() {
        return subCategoryList;
    }

    public SingleLiveEvent<Integer> getCurrentCategoryIndex() {
        return currentCategoryIndex;
    }

    public SingleLiveEvent<Integer> getCurrentSubCategoryIndex() {
        return currentSubCategoryIndex;
    }

    public void setCurrentCategoryIndex(int index) {
        currentCategoryIndex.setValue(index);
        setCurrentSubCategoryIndex(0);
    }

    public void setCurrentSubCategoryIndex(int index) {
        currentSubCategoryIndex.setValue(index);
    }

    /**
     * 获取菜单数据
     */
    private void getCategory() {
        if(categoryList.getValue() != null) return;
        categoryDataSource.getCategory(new CookCallback<CategoryInfoList>() {

            @Override
            public void onSuccess(int what, CategoryInfoList data) {
                super.onSuccess(what, data);
                getIsDataLoadingError().set(false);
                categoryList.setValue(data == null ? new ArrayList<>() : data.getChilds());
                setCurrentCategoryIndex(0);
                getIsEmptyDataSource().set(data == null || data.getChilds() == null || data.getChilds().isEmpty());
            }

            @Override
            public void onFail(int what, String msg) {
                super.onFail(what, msg);
                getIsDataLoadingError().set(true);
            }

            @Override
            public void onError(int what, Throwable e) {
                super.onError(what, e);
                getIsDataLoadingError().set(true);
            }
        });
    }

    /**
     * 获取二级菜单数据
     *
     * @param currentCategoryIndex 父菜单数据当前选择位置
     * @return 二级菜单
     */
    private List<CategoryInfoList> getSubCategory(Integer currentCategoryIndex) {
        List<CategoryInfoList> parentCategoryList = categoryList.getValue();
        if (parentCategoryList == null || parentCategoryList.isEmpty() || currentCategoryIndex == null || currentCategoryIndex >= parentCategoryList.size() || currentCategoryIndex < 0) {
            return new ArrayList<>();
        }
        int lastSubCategoryIndex = currentSubCategoryIndex.getValue() == null ? 0 : currentSubCategoryIndex.getValue();
        List<CategoryInfoList> childCategoryList = subCategoryList.getValue();
        if(lastSubCategoryIndex != 0 && childCategoryList != null && childCategoryList.size() > lastSubCategoryIndex) {
            childCategoryList.get(lastSubCategoryIndex).setChecked(false);
        }
        currentSubCategoryIndex.setValue(0);
        return categoryList.getValue().get(currentCategoryIndex).getChilds();
    }
}
