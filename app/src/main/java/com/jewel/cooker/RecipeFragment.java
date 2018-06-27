package com.jewel.cooker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jewel.core.BaseFragment;
import com.jewel.model.cookery.RecipeDetail;
import com.jewel.model.cookery.RecipeInfo;
import com.jewel.model.cookery.RecipeMethod;
import com.jewel.util.CompatUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.jewel.cooker.MainActivity.INTENT_KEY_RECIPE;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/04
 */
public class RecipeFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_recipe_title)
    TextView tvRecipeTitle;
    @BindView(R.id.iv_recipe_thumbnail)
    ImageView ivRecipeThumbnail;
    @BindView(R.id.iv_ingredients)
    ImageView ivIngredients;
    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    Unbinder unbinder;
    @BindView(R.id.ll_steps)
    LinearLayout llSteps;
    @BindView(R.id.tv_recipe_summary)
    TextView tvSummary;

    private boolean hadFavorite;

    public static RecipeFragment newInstance(RecipeInfo info) {

        Bundle args = new Bundle();
        args.putParcelable(INTENT_KEY_RECIPE, info);

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_recipe;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        setupToolbar();

        if (getArguments() != null) {
            Bundle args = getArguments();
            RecipeInfo recipeInfo = args.getParcelable(INTENT_KEY_RECIPE);
            if (recipeInfo != null && getContext() != null) {
                hadFavorite = FavoriteRecipeCache.getInstance().hadFavorite(recipeInfo);
                setupRecipeDetailView(getContext(), recipeInfo);
            }
        }
    }

    private void setupRecipeDetailView(@NonNull Context context, @NonNull RecipeInfo recipeInfo) {
        toolbar.setTitle(recipeInfo.getName());
        toolbar.inflateMenu(R.menu.activity_main_menu);
        if(hadFavorite) {
            toolbar.getMenu().getItem(0).setIcon(CompatUtil.getDrawable(context, R.drawable.ic_favorite_black_24dp));
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(hadFavorite) {
                    hadFavorite = false;
                    FavoriteRecipeCache.getInstance().removeFromFavorite(recipeInfo);
                    item.setIcon(CompatUtil.getDrawable(context, R.drawable.ic_favorite_border_24dp));
                } else {
                    hadFavorite = true;
                    FavoriteRecipeCache.getInstance().addToFavorite(recipeInfo);
                    item.setIcon(CompatUtil.getDrawable(context, R.drawable.ic_favorite_black_24dp));
                }
                return true;
            }
        });

        if (recipeInfo.getRecipe() != null) {
            RecipeDetail recipeDetail = recipeInfo.getRecipe();

            setupRecipeTitleViews(context, recipeDetail.getTitle(), recipeDetail.getIngredients(), recipeDetail.getImg());

            if (!TextUtils.isEmpty(recipeDetail.getMethod())) {
                setupRecipeMethodViews(context, recipeDetail.getMethod());
            }

            if (!TextUtils.isEmpty(recipeDetail.getSumary())) {
                tvSummary.setText(String.format(CompatUtil.getString(context, R.string.summary), recipeDetail.getSumary()));
            }
        } else {
            setupRecipeTitleViews(context, recipeInfo.getName(), "", recipeInfo.getThumbnail());
        }
    }

    private void setupRecipeTitleViews(@NonNull Context context, String title, String ingredients, String img) {
        tvRecipeTitle.setText(title);
        tvIngredients.setText(ingredients);

        GlideApp.with(context).load(img)
                .placeholder(R.drawable.ic_collections_image_24dp).error(R.drawable.ic_broken_image_24dp)
                .into(ivRecipeThumbnail);
    }

    private void setupRecipeMethodViews(@NonNull Context context, @NonNull String recipeMethod) {
        List<RecipeMethod> recipeMethods = JSON.parseArray(recipeMethod, RecipeMethod.class);
        if (recipeMethods != null && !recipeMethods.isEmpty()) {
            setupRecipeMethodView(context, recipeMethods);
        }
    }

    private void setupRecipeMethodView(@NonNull Context context, @NonNull List<RecipeMethod> recipeMethods) {
        for (RecipeMethod method : recipeMethods) {
            View methodView = View.inflate(getContext(), R.layout.layout_recipe_method, null);
            TextView tvStep = methodView.findViewById(R.id.tv_recipe_step);
            ImageView ivStep = methodView.findViewById(R.id.iv_recipe_step);
            tvStep.setText(method.getStep());
            if (!TextUtils.isEmpty(method.getImg())) {
                ivStep.setVisibility(View.VISIBLE);
                GlideApp.with(context).load(method.getImg()).fitCenter().into(ivStep);
            } else {
                ivStep.setVisibility(View.GONE);
            }

            llSteps.addView(methodView);
        }
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((MainActivity) _mActivity).pop());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
