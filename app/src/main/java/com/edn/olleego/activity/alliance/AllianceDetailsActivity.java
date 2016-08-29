package com.edn.olleego.activity.alliance;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.alliance.AllianceDetailsPagerAdapter;
import com.edn.olleego.adapter.alliance.AllianceImagePagerAdapter;
import com.edn.olleego.adapter.alliance.PurchasePTadapter;
import com.edn.olleego.common.OlleegoEvent;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.fragment.alliance.AllianceCenterInfoFragment;
import com.edn.olleego.fragment.alliance.AllianceDetailsInfoFragment;
import com.edn.olleego.fragment.alliance.AllianceNoticeFragment;
import com.edn.olleego.fragment.alliance.AllianceReviewFragment;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AlliancePTListModel;
import com.edn.olleego.model.alliance.AlliancePTticketModel;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016. 8. 8..
 * <p/>
 * 제휴센터 상세 페이지 최상위 뷰
 */
public class AllianceDetailsActivity extends AppCompatActivity implements PurchasePTadapter.PurchasePTadapterListener {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private BottomSheetBehavior behavior;
    private String title;
    private int id;
    private EventBus bus = EventBus.getDefault();
    private AllianceDetailsPagerAdapter pagerAdapter;
    private AlliancePTticketModel ticketInfo;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager1)
    ViewPager viewPager1;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.visited)
    TextView visited;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.review_count)
    TextView review_count;
    @BindView(R.id.locate)
    TextView locate;
    @BindView(R.id.star)
    RatingBar star;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager2)
    ViewPager viewPager2;

    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alliance_details);
        ButterKnife.bind(this);
        mContext = this;
        bus.register(this);
        res = getResources();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        Log.w(TAG, "id : " + id);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final PurchasePTadapter purchasePTadapter = new PurchasePTadapter(this);
        recyclerView.setAdapter(purchasePTadapter);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) { //appbar layout이 완전히 접혔을 때 타이틀 보이기
                    collapsingToolbar.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_phone) {

                    if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (tabId == R.id.tab_purchase) {
                    if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        if (ticketInfo != null) {
                            purchasePTadapter.clearSelection(ticketInfo.getPosition());
                            ticketInfo = null;
                        }
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_phone) {

                    if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (tabId == R.id.tab_purchase) {
                    if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        if (ticketInfo != null) {
                            purchasePTadapter.clearSelection(ticketInfo.getPosition());
                            ticketInfo = null;
                        }
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                        if (ticketInfo != null) {
                            Intent intent = new Intent(mContext, AlliancePurchasePTActivity.class);
                            intent.putExtra("centerId", id);
                            intent.putExtra("centerName", title);
                            intent.putExtra("ptId", ticketInfo.getId());
                            intent.putExtra("ptName", ticketInfo.getTitle());
                            intent.putExtra("expiry", ticketInfo.getExpiry());
                            intent.putExtra("money", ticketInfo.getMoney());
                            intent.putExtra("saleMoney", ticketInfo.getSale_money());
                            startActivity(intent);
                        }
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }
        });

        //하단 구매하기 PT 상품 목록
        RestfulAdapter.setOlleegoInterface(null);
        Call<AlliancePTListModel> ptCall = RestfulAdapter.getInstance().getPTList(id);
        ptCall.enqueue(new Callback<AlliancePTListModel>() {
                           @Override
                           public void onResponse(Call<AlliancePTListModel> call, retrofit2.Response<AlliancePTListModel> response) {
                               Log.d(TAG, "isSuccessful : " + response.isSuccessful());

                               AlliancePTListModel model = new AlliancePTListModel();
                               if (response.body() != null) model = response.body();
                               List<AlliancePTListModel> ptList = new ArrayList<>();
                               ptList.add(model);
                               if (purchasePTadapter != null)
                                   purchasePTadapter.setData(ptList);
                           }

                           @Override
                           public void onFailure(Call<AlliancePTListModel> call, Throwable t) {
                               Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                           }
                       }
        );

        RestfulAdapter.setOlleegoInterface(null);
        Call<AllianceDetailsModel> call = RestfulAdapter.getInstance().getAllianceDetails(id);
        call.enqueue(new Callback<AllianceDetailsModel>() {
                         @Override
                         public void onResponse
                                 (Call<AllianceDetailsModel> call, retrofit2.Response<AllianceDetailsModel> response) {
                             Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                             List<AllianceDetailsModel> list = new ArrayList<>();
                             if (response.isSuccessful()) {
                                 list.add(response.body());

                                 List<String> imageList = response.body().getResult().getCenter_dinfo().getImage();
                                 final AllianceImagePagerAdapter adapter = new AllianceImagePagerAdapter(getSupportFragmentManager(), imageList);
                                 viewPager1.setAdapter(adapter);

                                 if (response.body().getResult().getCenter_binfo() != null) {
                                     title = response.body().getResult().getCenter_binfo().getName() + " (" + response.body().getResult().getCenter_binfo().getPlace() + ")";
                                     name.setText(response.body().getResult().getCenter_binfo().getName());
                                     place.setText("(" + response.body().getResult().getCenter_binfo().getPlace() + ")");
                                     locate.setText(response.body().getResult().getCenter_binfo().getLocate());

                                     if (response.body().getResult().getCenter_binfo().getType() != null) {
                                         category.setText(response.body().getResult().getCenter_binfo().getType().getValue());
                                     }
                                 }

                                 star.setRating(response.body().getResult().getPoint_avg());
                                 review_count.setText("(" + response.body().getResult().getReview_count() + ")");

                                 tabLayout.addTab(tabLayout.newTab().setText(res.getString(R.string.alliance_tab1)));
                                 tabLayout.addTab(tabLayout.newTab().setText(res.getString(R.string.alliance_tab2)));
                                 tabLayout.addTab(tabLayout.newTab().setText(res.getString(R.string.alliance_tab3)));
                                 tabLayout.addTab(tabLayout.newTab().setText(res.getString(R.string.alliance_tab4)));

                                 pagerAdapter = new AllianceDetailsPagerAdapter(getSupportFragmentManager());
                                 pagerAdapter.addFragment(new AllianceCenterInfoFragment().newInstance(list));
                                 pagerAdapter.addFragment(new AllianceDetailsInfoFragment().newInstance(list));
                                 pagerAdapter.addFragment(new AllianceNoticeFragment().newInstance(""));
                                 pagerAdapter.addFragment(new AllianceReviewFragment().newInstance(id));

                                 viewPager2.setAdapter(pagerAdapter);
                                 viewPager2.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                                 tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager2) {
                                     @Override
                                     public void onTabSelected(TabLayout.Tab tab) {
                                         viewPager2.setCurrentItem(tab.getPosition());
                                     }

                                     @Override
                                     public void onTabUnselected(TabLayout.Tab tab) {
                                     }

                                     @Override
                                     public void onTabReselected(TabLayout.Tab tab) {
                                     }
                                 });
                             }
                         }

                         @Override
                         public void onFailure(Call<AllianceDetailsModel> call, Throwable t) {
                             Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                         }
                     }
        );
    }

    @Override
    public void setSelectedItem(AlliancePTticketModel info) {
        this.ticketInfo = info;
    }

    @Subscribe
    public void onEvent(OlleegoEvent.WriteReviewEvent event) {
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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


}
