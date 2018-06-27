package com.jewel.cooker.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jewel.cooker.R;
import com.jewel.model.category.CategoryInfo;
import com.jewel.model.category.CategoryInfoList;
import com.jewel.util.CompatUtil;

import java.util.List;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/02
 */

public class CategoryAdapter extends BaseQuickAdapter<CategoryInfoList, BaseViewHolder> {

    private int lastCheckedPosition = -1;

    public CategoryAdapter(boolean isChild) {
        super(isChild ? R.layout.adapter_item_category_child : R.layout.adapter_item_category);
    }

    public void setChecked(int position) {
        if (lastCheckedPosition == position) return;
        if (lastCheckedPosition != -1) {
            getData().get(lastCheckedPosition).setChecked(false);
            notifyItemChanged(lastCheckedPosition);
        }
        if(position == -1) {
            lastCheckedPosition = position;
            return;
        }
        getData().get(position).setChecked(true);
        notifyItemChanged(position);
        lastCheckedPosition = position;
    }

    @Override
    public void setNewData(@Nullable List<CategoryInfoList> data) {
        super.setNewData(data);
        if(data != null && !data.isEmpty()) {
            lastCheckedPosition = 0;
        }
    }

    public @Nullable CategoryInfo getCurrentCategory() {
        if(lastCheckedPosition < 0) {
            return null;
        }
        if(lastCheckedPosition >= getData().size()) return null;
        return getData().get(lastCheckedPosition).getCategoryInfo();
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryInfoList item) {
        if (-1 == lastCheckedPosition && item.isChecked()) {
            lastCheckedPosition = helper.getLayoutPosition();
        }
        helper.setText(R.id.tv_item_category, item.getCategoryInfo().getName());

        helper.setTextColor(R.id.tv_item_category, item.isChecked() ? CompatUtil.getColor(mContext, R.color.brown_50) : CompatUtil.getColor(mContext, R.color.brown_900))
                .setBackgroundRes(R.id.fl_category, item.isChecked() ? R.color.colorAccent : R.drawable.selectable_item_background);

    }

}
