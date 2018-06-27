package com.jewel.mvvm.live;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/15
 */
public class LoadingEvent extends SingleLiveEvent<Boolean> {

    public void observe(LifecycleOwner owner, LoadingObserver observer) {
        super.observe(owner, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean show) {
                observer.onLoadingShowHide(show == null ? false : show);
            }
        });
    }

    public interface LoadingObserver {
        void onLoadingShowHide(boolean show);
    }
}
