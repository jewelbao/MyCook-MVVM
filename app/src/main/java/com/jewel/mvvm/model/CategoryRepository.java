package com.jewel.mvvm.model;

import com.jewel.http.core.IHttpCallback;
import com.jewel.model.category.CategoryInfoList;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/14
 */
public class CategoryRepository implements CategoryDataSource {

    private volatile static CategoryRepository INSTANCE;

    private final CategoryDataSource remoteDataSource;

    public static CategoryRepository getInstance(CategoryDataSource remoteDataSource) {
        if (INSTANCE == null) {
            synchronized (CategoryRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CategoryRepository(remoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    private CategoryRepository(CategoryDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void getCategory(IHttpCallback<CategoryInfoList> callback) {
        remoteDataSource.getCategory(callback);
    }

}
