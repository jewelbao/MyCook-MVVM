package com.jewel.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.jewel.mvvm.live.LoadingEvent;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/19
 */
public class BaseViewModel extends AndroidViewModel {

    private final LoadingEvent loadingEvent = new LoadingEvent();  // 是否显示正在加载中UI
    private final ObservableBoolean isEmptyDataSource = new ObservableBoolean(false); // 是否得到空数据
    private final ObservableBoolean isDataLoadingError = new ObservableBoolean(false); // 是否获取数据失败

    BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public LoadingEvent getLoadingEvent() {
        return loadingEvent;
    }

    public ObservableBoolean getIsDataLoadingError() {
        return isDataLoadingError;
    }

    public ObservableBoolean getIsEmptyDataSource() {
        return isEmptyDataSource;
    }
}
