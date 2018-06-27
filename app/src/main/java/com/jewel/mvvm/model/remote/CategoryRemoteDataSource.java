package com.jewel.mvvm.model.remote;

import com.jewel.http.business.CookerRequest;
import com.jewel.http.business.ICookerRequest;
import com.jewel.http.core.IHttpCallback;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.mvvm.model.CategoryDataSource;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/14
 */
public class CategoryRemoteDataSource implements CategoryDataSource {

    private final ICookerRequest cookerRequest;
    private static CategoryRemoteDataSource INSTANCE;

    public static CategoryRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryRemoteDataSource();
        }
        return INSTANCE;
    }

    private CategoryRemoteDataSource() {
        cookerRequest = new CookerRequest();
    }

    @Override
    public void getCategory(IHttpCallback<CategoryInfoList> callback) {
        cookerRequest.getCategories(callback);
    }
}
