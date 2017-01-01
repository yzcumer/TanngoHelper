package com.yuki.android.tanngohelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by fxf on 2016/10/25.
 */
public class TanngoPagerActivity extends AppCompatActivity {

    private static final String EXTRA_TANNGO_ID = "com.yuki.android.tanngohelper.tanngo_id";

    private ViewPager mViewPager;
    private List<Tanngo> mTanngos;

    public static Intent newIntent(Context packageContext, UUID tanngoId) {
        Intent intent = new Intent(packageContext, TanngoPagerActivity.class);
        intent.putExtra(EXTRA_TANNGO_ID, tanngoId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanngo_pager);

        UUID tanngoId = (UUID) getIntent().getSerializableExtra(EXTRA_TANNGO_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_tanngo_pager_view_pager);
        mTanngos = TanngoLab.get(this).getTanngos();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Tanngo tanngo = mTanngos.get(position);
                return TanngoFragment.newInstance(tanngo.getId());
            }

            @Override
            public int getCount() {
                return mTanngos.size();
            }
        });

        for (int i = 0; i < mTanngos.size(); i++) {
            if (mTanngos.get(i).getId().equals(tanngoId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
