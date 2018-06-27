package com.jewel.http.business;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/01
 */

public class CookHttp {

    public static class Params {
        public static final String KEY = "key";
        public static final String KEY_VALUE = "1bb017a697230";

        public static final String CID = "cid";

        public static final String PAGE = "page";
        public static final String SIZE = "size";
        public static final int SIZE_VALUE = 20;
    }

    public static class Url{
        /**
         * 所有分类
         */
        public static final String ALL_CATEGORIES = "http://apicloud.mob.com/v1/cook/category/query";
        /**
         * 根据标签{@link CookHttp.Params#CID}获取菜单列表
         */
        public static final String RECIPES_BY_CATEGORY = "http://apicloud.mob.com/v1/cook/menu/search";
        /**
         * 根据ID获取详情
         */
        public static final String DETAIL_BY_ID = "http://apicloud.mob.com/v1/cook/menu/query";
    }

}
