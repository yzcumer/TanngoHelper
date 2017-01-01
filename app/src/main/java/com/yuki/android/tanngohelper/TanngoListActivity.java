package com.yuki.android.tanngohelper;

import android.support.v4.app.Fragment;

/**
 * Created by fxf on 2016/10/25.
 */
public class TanngoListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TanngoListFragment();
    }
}
