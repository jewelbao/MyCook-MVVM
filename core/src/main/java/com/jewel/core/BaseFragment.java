package com.jewel.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/03
 */
public abstract class BaseFragment extends SupportFragment {

    protected abstract @LayoutRes int getContentLayout();
    protected abstract void initView(View view);
    protected void getData(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentLayout(), container, false);
        initView(view);
        getData();
        return view;
    }

    public void showLoading(BaseActivity activity) {
        if(activity != null) {
            activity.showLoading();
        }
    }

    public void dismissLoading(BaseActivity activity) {
        if(activity != null) {
            activity.dismissLoading();
        }
    }
}
