package com.jewel.mvvm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/14
 */
public class FragmentUtils {

    public static void replaceFragmentInActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }
}
