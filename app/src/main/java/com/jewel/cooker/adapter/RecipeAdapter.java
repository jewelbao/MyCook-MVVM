package com.jewel.cooker.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jewel.cooker.GlideApp;
import com.jewel.cooker.R;
import com.jewel.model.cookery.RecipeInfo;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/03
 */
public class RecipeAdapter extends BaseQuickAdapter<RecipeInfo, BaseViewHolder> {

    public RecipeAdapter() {
        super(R.layout.adapter_item_recipe);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecipeInfo item) {
        helper.setText(R.id.tv_recipe_title, item.getName())
                .setText(R.id.tv_recipe_summary, item.getRecipe() == null ? item.getName() :
                        TextUtils.isEmpty(item.getRecipe().getSumary()) ? item.getRecipe().getIngredients() : item.getRecipe().getSumary());
        GlideApp.with(mContext).load(item.getThumbnail()).placeholder(R.drawable.ic_collections_image_24dp)
                .error(R.drawable.ic_broken_image_24dp).fitCenter().into((ImageView) helper.getView(R.id.iv_recipe_thumbnail));
    }
}
