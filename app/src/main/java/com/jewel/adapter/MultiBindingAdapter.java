//package com.jewel.adapter;
//
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import java.util.List;
//
///**
// * @author Jewel
// * @version 1.0
// * @since 2018/06/13
// */
//public abstract class MultiBindingAdapter extends RecyclerView.Adapter<CommonViewHolder> {
//
//    protected List<Object> data;
//
//    public MultiBindingAdapter(List<Object> data) {
//        this.data = data;
//    }
//
//    @NonNull
//    @Override
//    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
//        return new CommonViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
//        Object obj = getObjForPosition(position);
//        holder.bind(obj);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return getLayoutIdForPosition(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        if(data == null) return 0;
//        return data.size();
//    }
//
//    protected abstract Object getObjForPosition(int position);
//
//    protected abstract int getLayoutIdForPosition(int position);
//}
