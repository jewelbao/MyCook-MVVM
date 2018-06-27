package com.jewel.model.cookery;

import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/03
 */
public class RecipeInfoList {

    private int curPage;
    private List<RecipeInfo> list;
    private int total;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<RecipeInfo> getList() {
        return list;
    }

    public void setList(List<RecipeInfo> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
