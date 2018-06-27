package com.jewel.mvvm.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.jewel.adapter.CommonBindingAdapter;
import com.jewel.cooker.R;
import com.jewel.cooker.databinding.FragmentHomeNewBinding;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.model.cookery.RecipeInfo;
import com.jewel.mvvm.live.LoadingEvent;
import com.jewel.mvvm.viewmodel.CategoryViewModel;
import com.jewel.mvvm.viewmodel.RecipeViewModel;
import com.jewel.mvvm.viewmodel.ViewModelFactory;
import com.jewel.util.RecyclerViewUtil;

import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/15
 */
public class MainFragment extends Fragment {
    private CategoryViewModel categoryViewModel;
    private RecipeViewModel recipeViewModel;

    private CategoryAdapter categoryAdapter;
    private RecipeAdapter recipeAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeNewBinding viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_new, container, false);
        if (getActivity() == null)return super.onCreateView(inflater, container, savedInstanceState);
        
        categoryViewModel = MainActivity.obtainViewModel(getActivity());
        ViewModelFactory factory = ViewModelFactory.getInstance(this.getActivity().getApplication());
        recipeViewModel = ViewModelProviders.of(this, factory).get(RecipeViewModel.class);

        initCategoryAdapter(viewBinding.rvCategoryChild);
        initRecipeAdapter(viewBinding.rvRecipe);

        subscribeRecipeDataChanged();
        subscribeCategoryDataChanged();

        return viewBinding.getRoot();
    }

    private void initCategoryAdapter(RecyclerView recyclerView) {
        categoryAdapter = new CategoryAdapter(R.layout.adapter_category_child, BR.category);
        categoryAdapter.setOnItemClickListener(new CommonBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonBindingAdapter adapter, View view, int position) {
                int lastIndex = categoryViewModel.getCurrentSubCategoryIndex().getValue() == null ? 0 : categoryViewModel.getCurrentSubCategoryIndex().getValue();
                if(lastIndex == position) return;
                if (lastIndex >= 0 && categoryAdapter.getData() != null && categoryAdapter.getData().size() > lastIndex) {
                    categoryAdapter.getItem(lastIndex).setChecked(false);
                    categoryAdapter.notifyItemChanged(lastIndex);
                }
                categoryViewModel.setCurrentSubCategoryIndex(position);
                recipeViewModel.setCurrentCategoryID(categoryAdapter.getItem(position).getCategoryInfo().getCtgId());
            }
        });
        RecyclerViewUtil.setupHorizontalListView(recyclerView, categoryAdapter, R.color.divider);
    }
    
    private void initRecipeAdapter(RecyclerView recyclerView) {
        recipeAdapter = new RecipeAdapter();
        recipeAdapter.setOnItemClickListener(new CommonBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonBindingAdapter adapter, View view, int position) {
                
            }
        });
        RecyclerViewUtil.setupListView(recyclerView, recipeAdapter, R.color.divider);
    }

    private void subscribeRecipeDataChanged() {
        if (getActivity() == null) return;
        recipeViewModel.getRecipeList().observe(this, new Observer<List<RecipeInfo>>() {
            @Override
            public void onChanged(@Nullable List<RecipeInfo> recipeInfos) {
                recipeAdapter.setData(recipeInfos);
            }
        });
        recipeViewModel.getLoadingEvent().observe(this, new LoadingEvent.LoadingObserver() {
            @Override
            public void onLoadingShowHide(boolean show) {
                Toast.makeText(getActivity(), "加载中", Toast.LENGTH_SHORT).show();
            }
        });
        recipeViewModel.getCurrentCategoryID().observe(this, categoryId -> recipeViewModel.getRecipeList(categoryId));
    }

    private void subscribeCategoryDataChanged() {
        if (getActivity() == null) return;
        categoryViewModel.getSubCategoryList().observe(this, this::updateCategoryList);
        categoryViewModel.getCurrentSubCategoryIndex().observe(this, this::setCategoryChecked);
    }

    private void updateCategoryList(List<CategoryInfoList> categoryInfoLists) {
        com.orhanobut.logger.Logger.d("当前二级菜单数据变更");
//        com.orhanobut.logger.Logger.json(JSON.toJSONString(categoryInfoLists));
        categoryAdapter.setData(categoryInfoLists);
        recipeViewModel.getRecipeList(categoryInfoLists.get(0).getCategoryInfo().getCtgId());
    }

    private void setCategoryChecked(Integer position) {
        com.orhanobut.logger.Logger.d("当前二级菜单选择变更 %s", position);
        if (position != null && position >= 0 && categoryAdapter.getData() != null && categoryAdapter.getData().size() > position) {
            categoryAdapter.getItem(position).setChecked(true);
            categoryAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
