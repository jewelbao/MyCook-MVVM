package com.jewel.mvvm.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import com.android.databinding.library.baseAdapters.BR;
import com.blankj.utilcode.util.ToastUtils;
import com.jewel.cooker.R;
import com.jewel.cooker.databinding.ActivityMainNewBinding;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.mvvm.FragmentUtils;
import com.jewel.mvvm.live.LoadingEvent;
import com.jewel.mvvm.viewmodel.CategoryViewModel;
import com.jewel.mvvm.viewmodel.ViewModelFactory;
import com.jewel.util.RecyclerViewUtil;

import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/13
 */
public class MainActivity extends AppCompatActivity {

    private CategoryAdapter categoryAdapter;
    private ActivityMainNewBinding viewBinding;
    private CategoryViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_new);
        setupHomeFragment();
        setupNavigation();

        subscribeNavigationCategory();
    }

    private void setupNavigation() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, viewBinding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        setupCategory();
    }

    private void setupCategory() {
        categoryAdapter = new CategoryAdapter(R.layout.adapter_category, BR.category);
        categoryAdapter.setOnItemClickListener((adapter, view, position) -> onCategoryItemClicked(position));
        RecyclerViewUtil.setupListView(viewBinding.nav.rvNavCategory , categoryAdapter, R.color.divider);
    }

    private void setupHomeFragment() {
        MainFragment homeFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if(homeFragment == null) {
            homeFragment = MainFragment.newInstance();
            FragmentUtils.replaceFragmentInActivity(getSupportFragmentManager(), homeFragment, R.id.fl_container);
        }
    }

    private void subscribeNavigationCategory() {
        viewModel = obtainViewModel(MainActivity.this);
        // 订阅菜单数据加载状态事件，更新加载中UI
        viewModel.getLoadingEvent().observe(this, (LoadingEvent.LoadingObserver) show -> ToastUtils.showLong(show ? "Activity加载中" : "Activity加载完"));
        // 订阅菜单数据变更事件，更新Adapter数据源
        viewModel.getCategoryList().observe(this, this::updateCategoryList);
        // 订阅菜单点击位置事件，更新点击位置的Adapter UI。
        viewModel.getCurrentCategoryIndex().observe(this, this::setCategoryChecked);
    }

    public static CategoryViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(CategoryViewModel.class);
    }

    public void openMenu() {
        viewBinding.drawerLayout.openDrawer(GravityCompat.START);
    }

    private void onCategoryItemClicked(int position) {
        int lastCheckedPosition = viewModel.getCurrentCategoryIndex().getValue() != null ? viewModel.getCurrentCategoryIndex().getValue() : 0;
        if(lastCheckedPosition != position) {
            if (lastCheckedPosition >= 0 && categoryAdapter.getData() != null && categoryAdapter.getData().size() > lastCheckedPosition) {
                categoryAdapter.getItem(lastCheckedPosition).setChecked(false);
                categoryAdapter.notifyItemChanged(lastCheckedPosition);
            }
            viewModel.setCurrentCategoryIndex(position);
        }
        closeMenu(null);
    }

    private void updateCategoryList(List<CategoryInfoList> categoryInfoLists) {
        com.orhanobut.logger.Logger.d("当前导航菜单数据变更");
//        com.orhanobut.logger.Logger.json(JSON.toJSONString(categoryInfoLists));
        categoryAdapter.setData(categoryInfoLists);
    }

    private void setCategoryChecked(Integer position) {
        com.orhanobut.logger.Logger.d("当前导航菜单选择变更 %s", position);
        if(position != null && position >=0 && categoryAdapter.getData() != null && categoryAdapter.getData().size() > position) {
            categoryAdapter.getItem(position).setChecked(true);
            categoryAdapter.notifyItemChanged(position);
        }
    }

    public void closeMenu(Runnable runnable) {
        viewBinding.drawerLayout.closeDrawer(GravityCompat.START);
        viewBinding.drawerLayout.postDelayed(runnable, 300);
    }

    @Override
    public void onBackPressed() {
        if (viewBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            viewBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
