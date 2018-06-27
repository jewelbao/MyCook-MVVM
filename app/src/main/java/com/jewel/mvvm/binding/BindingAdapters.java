package com.jewel.mvvm.binding;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/15
 */
public class BindingAdapters {

    private static final String VISIBLE_GONE = "visibleOrGone";

    @BindingAdapter(VISIBLE_GONE)
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
