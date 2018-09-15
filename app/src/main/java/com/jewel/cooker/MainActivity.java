package com.jewel.cooker;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jewel.components.DotTextView;
import com.jewel.cooker.adapter.CategoryAdapter;
import com.jewel.core.BaseActivity;
import com.jewel.core.BaseFragment;
import com.jewel.http.business.CookCallback;
import com.jewel.http.business.CookerRequest;
import com.jewel.http.business.ICookerRequest;
import com.jewel.http.core.IHttpCallback;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.util.RecyclerViewUtil;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    public static final String INTENT_KEY_CATEGORY = "key_category";
    public static final String INTENT_KEY_RECIPE = "key_recipe";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rv_nav_category)
    RecyclerView rvNavCategory;
    @BindView(R.id.tv_update)
    DotTextView tvUpdate;

    private CategoryAdapter categoryAdapter;

    private final IHttpCallback<CategoryInfoList> categoryListCallback = new CookCallback<CategoryInfoList>() {
        @Override
        public void onSuccess(int what, CategoryInfoList data) {
            super.onSuccess(what, data);
            if (data != null) {
                if (data.getChilds() != null && !data.getChilds().isEmpty()) {
                    data.getChilds().get(0).setChecked(true);

                    startHomeFragment(data.getChilds().get(0));
                }
                categoryAdapter.setNewData(data.getChilds());
            } else {
                categoryAdapter.setNewData(null);
            }
            dismissLoading();
        }
    };

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        if (findFragment(HomeFragment.class) == null) {
            loadRootFragment(R.id.fl_container, HomeFragment.newInstance());
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        categoryAdapter = new CategoryAdapter(false);
        categoryAdapter.setOnItemClickListener(this);
        RecyclerViewUtil.setupListView(rvNavCategory, categoryAdapter, R.color.divider);

        Beta.checkUpgrade(false, true);
    }

    @Override
    protected void getData() {
        showLoading();
        ICookerRequest cookerRequest = new CookerRequest();
        cookerRequest.getCategories(categoryListCallback);

        tvUpdate.postDelayed(() -> {
            UpgradeInfo updateInfo = Beta.getUpgradeInfo();
            if (updateInfo != null) {
                tvUpdate.setShowDot(true);
            }
        }, 10000);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
        categoryAdapter.setChecked(position);

        closeMenu(() -> startHomeFragment(categoryAdapter.getData().get(position)));
    }

    private void startHomeFragment(CategoryInfoList child) {
        BaseFragment topFragment = (BaseFragment) getTopFragment();
        HomeFragment homeFragment = findFragment(HomeFragment.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_KEY_CATEGORY, child);
        homeFragment.putNewBundle(bundle);
        topFragment.start(homeFragment, SupportFragment.SINGLETASK);
    }

    public void openMenu() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeMenu(Runnable runnable) {
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.postDelayed(runnable, 300);
    }

    @Override
    public void onBackPressedSupport() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressedSupport();
        }
    }

    @OnClick({R.id.tv_update, R.id.tv_share, R.id.tv_favorite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update:
                Beta.checkUpgrade(true, false);
                break;
            case R.id.tv_share:
                break;
            case R.id.tv_favorite:
                closeMenu(() -> start(FavoriteFragment.newInstance()));
                break;
        }
    }
}
