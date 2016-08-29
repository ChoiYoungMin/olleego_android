package com.edn.olleego.activity.mycenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mycenter.MyCenterPagerAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.fragment.mycenter.ConsultDetailsFragment;
import com.edn.olleego.fragment.mycenter.ReservationDetailsFragment;
import com.edn.olleego.model.mycenter.ReservationDetailsModel;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016. 8. 7..
 * <p/>
 * 마이센터 메인 페이지
 */
public class ReservationDetailsActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Resources res;
    private Context mContext;
    private SharedPreferences olleego_SP;
    private SharedPreferences.Editor editor;
    private MyCenterPagerAdapter pagerAdapter;
    private boolean isOngoingPt;
    private String title;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.layout_pt_status)
    RelativeLayout statusLayout;
    @BindView(R.id.pt_status)
    TextView status;
    @BindView(R.id.detail_view)
    TextView detailView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.d_day)
    TextView dDay;
    @BindView(R.id.expire)
    TextView expire;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.remainder_unit)
    TextView remainderUnit;
    @BindView(R.id.remainder)
    TextView remainder;
    @BindView(R.id.trainer_image)
    ImageView image;
    @BindView(R.id.trainer_name)
    TextView trainerName;

    @BindView(R.id.call_image)
    ImageView call_image;
    @BindView(R.id.call_trainer)
    TextView call_trainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        ButterKnife.bind(this);
        mContext = this;
        res = getResources();
        Intent intent = getIntent();
        final int reserveId = intent.getIntExtra("reserveId", 0);
        final int pageIndex = intent.getIntExtra("pageIndex", 0);

        olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);
        editor = olleego_SP.edit();
        String token = "Olleego " + olleego_SP.getString("login_token", "");


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

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        RestfulAdapter.setOlleegoInterface(null);
        Call<ReservationDetailsModel> call = RestfulAdapter.getInstance().getReservationDetails(token, reserveId, "reserve");
        call.enqueue(new Callback<ReservationDetailsModel>() {
            @Override
            public void onResponse(Call<ReservationDetailsModel> call, final retrofit2.Response<ReservationDetailsModel> response) {
                boolean isSuccessful = response.isSuccessful();
                Log.d(TAG, "isSuccessful : " + isSuccessful);
                if (isSuccessful) {
                    List<ReservationDetailsModel.Result.PtReserve> list = response.body().getResult().getPt_reserve();
                    ReservationDetailsModel.Result result = response.body().getResult();

                    if (!result.getPt_status().equals("PR")) { //완료된 pt권
                        statusLayout.setBackgroundColor(Color.parseColor("#606060"));
                        remainderUnit.setTextColor(Color.parseColor("#878885"));
                        status.setText(res.getString(R.string.completed_pt));
                    } else
                        isOngoingPt = true;

                    title = result.getCenter().getCenter_binfo().getName();
                    name.setText(title);
                    dDay.setText(Util.getDday(result.getPt_expire()) + res.getString(R.string.d_day_text));
                    expire.setText("(" + Util.getExpire(result.getPt_expire(), 0) + res.getString(R.string.expire_day_text) + ")");
                    total.setText(String.valueOf(result.getPt_total()));
                    remainder.setText(String.valueOf(result.getPt_remain()));
                    trainerName.setText(result.getPt_trainer().getName());

                    call_trainer.setTag(result.getPt_trainer().getBasic_info().getTel());
                    call_image.setTag(result.getPt_trainer().getBasic_info().getTel());

                    //TabLayout
                    tabLayout.addTab(tabLayout.newTab().setText(res.getString(R.string.reservation_details_tab_title1)).setIcon(R.drawable.mycenter_reservation_details_tab_icon_selector));
                    tabLayout.addTab(tabLayout.newTab().setText(res.getString(R.string.reservation_details_tab_title2)).setIcon(R.drawable.mycenter_reservation_details_tab_icon_selector2));

                    pagerAdapter = new MyCenterPagerAdapter(getSupportFragmentManager());
                    pagerAdapter.addFragment(new ReservationDetailsFragment().newInstance(reserveId, list, isOngoingPt, result.getPt_trainer().getBasic_info().getTel()));
                    pagerAdapter.addFragment(new ConsultDetailsFragment().newInstance(reserveId));

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
                    viewPager.setCurrentItem(pageIndex, true);
                }
            }

            @Override
            public void onFailure(Call<ReservationDetailsModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
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

    @OnClick({R.id.call_image, R.id.call_trainer})
    public void onClickItem(final View view) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.review_custom_dialog, null);
        RelativeLayout cancel = (RelativeLayout) dialogView.findViewById(R.id.layout_cancel);
        RelativeLayout confirm = (RelativeLayout) dialogView.findViewById(R.id.layout_ok);
        TextView message = (TextView) dialogView.findViewById(R.id.text_message);
        TextView call = (TextView) dialogView.findViewById(R.id.text_ok);
        message.setText(res.getString(R.string.alert_text_call_trainer));
        call.setText(res.getString(R.string.dialog_confirm));
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + view.getTag().toString()));
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
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
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
