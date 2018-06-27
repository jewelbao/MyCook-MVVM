package com.jewel.mvvm.model;

import com.jewel.http.core.IHttpCallback;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.mvvm.model.DataSource;

import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/13
 */
public interface CategoryDataSource extends DataSource {

    void getCategory(IHttpCallback<CategoryInfoList> callback);
}
