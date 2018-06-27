package com.jewel.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jewel.cooker.BR;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/13
 */
public class CommonViewHolder<T> extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    public CommonViewHolder(ViewDataBinding itemViewBinding) {
        super(itemViewBinding.getRoot());
        this.binding = itemViewBinding;
    }
    public void bind(int variableId, T obj, int position) {
        binding.setVariable(variableId, obj);
        binding.executePendingBindings();
    }
}
