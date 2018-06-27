package com.jewel.mvvm.ui;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.jewel.adapter.CommonBindingAdapter;
import com.jewel.cooker.BR;
import com.jewel.cooker.GlideApp;
import com.jewel.cooker.R;
import com.jewel.model.cookery.RecipeDetail;
import com.jewel.model.cookery.RecipeInfo;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/20
 */
public class RecipeAdapter extends CommonBindingAdapter<RecipeInfo> {

    public RecipeAdapter() {
        super(R.layout.adapter_recipe, BR.RecipeInfo);
    }

    public static String getSubTitle(RecipeDetail recipeInfo) {
        if (recipeInfo == null) return null;
        return TextUtils.isEmpty(recipeInfo.getSumary()) ? recipeInfo.getIngredients() : recipeInfo.getSumary();
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, RecipeInfo recipeInfo) {
        GlideApp.with(view.getContext()).load(recipeInfo.getThumbnail()).placeholder(R.drawable.ic_collections_image_24dp)
                .error(R.drawable.ic_broken_image_24dp).fitCenter().into(view);
    }
}
