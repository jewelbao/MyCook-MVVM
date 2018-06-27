package com.jewel.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/13
 */
public abstract class CommonBindingAdapter<T> extends RecyclerView.Adapter<CommonViewHolder<T>> {

    private int variableId;
    private int layoutResId;
    protected List<T> data;

    private OnItemClickListener onItemClickListener;

    public CommonBindingAdapter(@LayoutRes int layoutResId, List<T> data, int variableId) {
        this.data = data;
        this.variableId = variableId;
        if (layoutResId != 0) {
            this.layoutResId = layoutResId;
        }
    }

    public CommonBindingAdapter(List<T> data, int variableId) {
        this(0, data, variableId);
    }

    public CommonBindingAdapter(@LayoutRes int layoutResId, int variableId) {
        this(layoutResId, null, variableId);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data == null ? new ArrayList<>() : data;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (position < data.size()) return data.get(position);
        return null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CommonViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        CommonViewHolder<T> holder = new CommonViewHolder<>(binding);
        if(onItemClickListener != null) {
            binding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(CommonBindingAdapter.this, holder.itemView, holder.getLayoutPosition()));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder<T> holder, int position) {
        T obj = getItem(position);
        holder.bind(variableId, obj, position);
    }

    @Override
    public int getItemViewType(int position) {
        return layoutResId;
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }



    public interface OnItemClickListener {
        void onItemClick(CommonBindingAdapter adapter, View view, int position);
    }
}
