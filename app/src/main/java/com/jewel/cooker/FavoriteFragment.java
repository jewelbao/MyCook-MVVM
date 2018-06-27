package com.jewel.cooker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jewel.cooker.adapter.RecipeAdapter;
import com.jewel.core.BaseFragment;
import com.jewel.http.business.CookHttp;
import com.jewel.util.RecyclerViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/05/13
 */
public class FavoriteFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;
    @BindView(R.id.fab_top)
    FloatingActionButton fabTop;

    private Unbinder unbinder;
    private RecipeAdapter recipeAdapter;

    public static FavoriteFragment newInstance() {

        Bundle args = new Bundle();

        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        setupToolbar();

        fabTop.setVisibility(View.GONE);

        recipeAdapter = new RecipeAdapter();
        recipeAdapter.setOnItemClickListener(this);
//        recipeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
//        recipeAdapter.isFirstOnly(false);
        rvRecipe.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findFirstVisibleItemPosition() > CookHttp.Params.SIZE_VALUE) {
                    fabTop.setVisibility(View.VISIBLE);
                } else {
                    fabTop.setVisibility(View.GONE);
                }
            }
        });
        recipeAdapter.setOnLoadMoreListener(this::getData, rvRecipe);
        RecyclerViewUtil.setupListView(rvRecipe, recipeAdapter, false, android.R.color.transparent, BaseApp.getInstance().getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin));
    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.favorite);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((MainActivity) _mActivity).pop());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        start(RecipeFragment.newInstance(recipeAdapter.getItem(position)));
    }

    @Override
    protected void getData() {
        super.getData();
        recipeAdapter.setNewData(FavoriteRecipeCache.getInstance().getFavorites());
        recipeAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
