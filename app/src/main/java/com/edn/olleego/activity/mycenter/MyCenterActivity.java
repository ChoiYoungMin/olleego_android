package com.edn.olleego.activity.mycenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mycenter.MyCenterPagerAdapter;
import com.edn.olleego.common.OlleegoEvent;
import com.edn.olleego.fragment.mycenter.MyReviewFragment;
import com.edn.olleego.fragment.mycenter.PurchaseListFragment;
import com.edn.olleego.fragment.mycenter.ReservationFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kym on 2016. 8. 7..
 * <p/>
 * 마이센터 메인 페이지
 */
public class MyCenterActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Resources res;
    private Context mContext;
    private SharedPreferences olleego_SP;
    private SharedPreferences.Editor editor;
    private MyCenterPagerAdapter pagerAdapter;
    private EventBus bus = EventBus.getDefault();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.home)
    ImageView home;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycenter);
        ButterKnife.bind(this);
        mContext = this;
        bus.register(this);
        res = getResources();
        olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);
        editor = olleego_SP.edit();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(res.getColor(R.color.fitness_blue));

        title.setText(res.getString(R.string.my_center_title));

        tabLayout.addTab(tabLayout.newTab().setText(res.getString(R.string.my_center_tap_title1)));
        tabLayout.addTab(tabLayout.newTab().setText(res.getString(R.string.my_center_tap_title2)));
        tabLayout.addTab(tabLayout.newTab().setText(res.getString(R.string.my_center_tap_title3)));

        pagerAdapter = new MyCenterPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new ReservationFragment().newInstance());
        pagerAdapter.addFragment(new PurchaseListFragment().newInstance());
        pagerAdapter.addFragment(new MyReviewFragment().newInstance());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @OnClick({R.id.home})
    public void onClickHome(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mycenter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(OlleegoEvent.WriteReviewEvent event) {
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
