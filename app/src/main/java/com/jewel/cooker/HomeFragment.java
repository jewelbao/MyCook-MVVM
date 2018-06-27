package com.jewel.cooker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jewel.cooker.adapter.CategoryAdapter;
import com.jewel.cooker.adapter.RecipeAdapter;
import com.jewel.core.BaseActivity;
import com.jewel.core.BaseFragment;
import com.jewel.http.business.CookCallback;
import com.jewel.http.business.CookHttp;
import com.jewel.http.business.CookerRequest;
import com.jewel.http.business.ICookerRequest;
import com.jewel.http.core.IHttpCallback;
import com.jewel.model.category.CategoryInfo;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.model.cookery.RecipeInfoList;
import com.jewel.util.RecyclerViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/01
 */

public class HomeFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_category_child)
    RecyclerView rvCategoryChild;
    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;
    @BindView(R.id.fab_top)
    FloatingActionButton fabTop;

    private CategoryAdapter categoryAdapter;
    private RecipeAdapter recipeAdapter;


    private Unbinder unbinder;

    private int page = 1;

    private final ICookerRequest cookerRequest = new CookerRequest();
    private final IHttpCallback<RecipeInfoList> recipeInfoListCallback = new CookCallback<RecipeInfoList>() {
        @Override
        public void onSuccess(int what, RecipeInfoList data) {
            super.onSuccess(what, data);
            refreshRecipe(data);
        }

        @Override
        public void onFinish(int what) {
            super.onFinish(what);
            dismissLoading((BaseActivity) getActivity());
        }

        @Override
        public void onError(int what, Throwable e) {
            super.onError(what, e);
            recipeAdapter.loadMoreFail();
        }

        @Override
        public void onFail(int what, String msg) {
            super.onFail(what, msg);
            recipeAdapter.loadMoreFail();
        }
    };

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        setupToolbar();

        fabTop.setVisibility(View.GONE);

        categoryAdapter = new CategoryAdapter(true);
        categoryAdapter.setOnItemClickListener(this);
        RecyclerViewUtil.setupHorizontalListView(rvCategoryChild, categoryAdapter, R.color.divider);

        recipeAdapter = new RecipeAdapter();
        recipeAdapter.setOnItemClickListener(this);
        recipeAdapter.setEnableLoadMore(true);
        recipeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recipeAdapter.isFirstOnly(false);
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
        recipeAdapter.setOnLoadMoreListener(() -> getData(false), rvRecipe);
        RecyclerViewUtil.setupListView(rvRecipe, recipeAdapter, false, android.R.color.transparent, BaseApp.getInstance().getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin));

    }

    @Override
    protected void getData() {
        CategoryInfo categoryInfo = categoryAdapter.getCurrentCategory();
        if (categoryInfo == null) return;
        if (page == 1) {
            showLoading((BaseActivity) getActivity());
        }
        cookerRequest.getRecipes(categoryInfo.getCtgId(), page, recipeInfoListCallback);
    }

    private void getData(boolean refresh) {
        if (refresh) {
            page = 1;
            recipeAdapter.setEnableLoadMore(true);
        } else {
            page++;
        }
        getData();
    }

    private void refreshRecipe(RecipeInfoList data) {
        if (data != null) {
            if (data.getList() != null && !data.getList().isEmpty()) {

                if (data.getTotal() <= CookHttp.Params.SIZE_VALUE * page) {
                    recipeAdapter.setEnableLoadMore(false);
                }

                if (page == 1) {
                    recipeAdapter.setNewData(data.getList());
                    rvRecipe.scrollToPosition(0);
                } else {
                    recipeAdapter.loadMoreComplete();
                    recipeAdapter.addData(data.getList());
                }
            }
        }
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_view_list_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((MainActivity) _mActivity).openMenu());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof CategoryAdapter) {
            categoryAdapter.setChecked(position);
            getData(true);
        } else if (adapter instanceof RecipeAdapter) {
            com.orhanobut.logger.Logger.d(recipeAdapter.getItem(position));
            start(RecipeFragment.newInstance(recipeAdapter.getItem(position)));
        }
    }


    @OnClick(R.id.fab_top)
    public void toTopList() {
        rvRecipe.scrollToPosition(0);
    }

    @Override
    public void onNewBundle(Bundle args) {
        super.onNewBundle(args);
        if (args != null) {
            CategoryInfoList categoryInfoList = args.getParcelable(MainActivity.INTENT_KEY_CATEGORY);
            if (categoryInfoList != null) {
                if (categoryInfoList.getCategoryInfo() != null) {
                    toolbar.setTitle(categoryInfoList.getCategoryInfo().getName());
                }

                categoryAdapter.setChecked(-1);
                if (categoryInfoList.getChilds() != null && categoryInfoList.getChilds().size() > 0) {
                    categoryInfoList.getChilds().get(0).setChecked(true);
                }
                categoryAdapter.setNewData(categoryInfoList.getChilds());
                rvCategoryChild.scrollToPosition(0);
                getData(true);
            } else {
                categoryAdapter.setNewData(null);
            }
        }
    }
}
