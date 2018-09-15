package com.jewel.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.jewel.mvvm.model.DataSource;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/15
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private Application application;

    public static ViewModelFactory getInstance(Application application) {
        if(INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if(INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(application);
        } else if(modelClass.isAssignableFrom(RecipeViewModel.class)) {
            return (T) new RecipeViewModel(application);
        }
        return super.create(modelClass);
    }
}
