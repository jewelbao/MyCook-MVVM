package com.jewel.http.business;

import com.jewel.http.core.CallServer;
import com.jewel.http.core.IHttpCallback;
import com.jewel.http.core.parse.FastJsonRequest;
import com.jewel.model.BaseData;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.model.cookery.RecipeInfoList;
import com.yanzhenjie.nohttp.rest.Request;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/02
 */

public class CookerRequest implements ICookerRequest {

    public CookerRequest() {

    }

    @Override
    public void getCategories(IHttpCallback<CategoryInfoList> callback) {
        Request<BaseData<CategoryInfoList>> request = new FastJsonRequest<>(CookHttp.Url.ALL_CATEGORIES, CategoryInfoList.class);
        CallServer.getInstance().request(WHAT_CATEGORIES, request, callback);
    }

    @Override
    public void getRecipes(String cid, int page, IHttpCallback<RecipeInfoList> callback) {
        Request<BaseData<RecipeInfoList>> request = new FastJsonRequest<>(CookHttp.Url.RECIPES_BY_CATEGORY, RecipeInfoList.class);
        request.add(CookHttp.Params.CID, cid);
        request.add(CookHttp.Params.PAGE, page);
        request.add(CookHttp.Params.SIZE, CookHttp.Params.SIZE_VALUE);
        CallServer.getInstance().request(WHAT_RECIPES, request, callback);
    }

}
