package com.jewel.mvvm.model.local;

import com.jewel.http.core.IHttpCallback;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.mvvm.model.CategoryDataSource;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/14
 */
public class CategoryLocalDataSource implements CategoryDataSource {

    private static volatile CategoryLocalDataSource INSTANCE;

    public static CategoryLocalDataSource getInstance() {
        if(INSTANCE == null) {
            synchronized (CategoryLocalDataSource.class) {
                if(INSTANCE == null) {
                    INSTANCE = new CategoryLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private CategoryLocalDataSource() {

    }

    @Override
    public void getCategory(IHttpCallback<CategoryInfoList> callback) {

    }
}
