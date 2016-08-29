package com.edn.olleego.activity.alliance;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.alliance.AllianceMapPagerAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.model.alliance.AllianceCategoryModel;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016. 8. 7..
 * <p/>
 * 제휴센터 Map
 */
public class AllianceMapActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Resources res;
    private Context mContext;
    private double lon;
    private double lat;
    private LocationManager locationManager; // 위치 정보 프로바이더
    private MyLocationListener locationListener;
    private boolean isOnGps;
    private boolean isMyLocation;
    private boolean isAgree;
    private SharedPreferences olleego_SP;
    private SharedPreferences.Editor editor;
    private AllianceMapPagerAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.layout_my_location)
    LinearLayout mLocation;
    @BindView(R.id.my_location)
    TextView mLocText;
    @BindView(R.id.image_gps)
    ImageView gpsImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alliance_map);
        ButterKnife.bind(this);
        mContext = this;
        res = getResources();

        olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);
        editor = olleego_SP.edit();
        isAgree = olleego_SP.getBoolean("agreeLocation", false);

        Intent intent = getIntent();
        lon = intent.getDoubleExtra("lon", 127.005315d);
        lat = intent.getDoubleExtra("lat", 37.61729d);
        isMyLocation = intent.getBooleanExtra("isMyLocation", false);

        //내 위치 정보
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isOnGps = true;
        } else isOnGps = false;

        if (isAgree) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.addGpsStatusListener(gpsStatusListener);
            setMyLocationStatus();
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(res.getColor(R.color.fitness_blue));

        title.setText(res.getString(R.string.alliance_center_title));

        RestfulAdapter.setOlleegoInterface(null);
        Call<AllianceCategoryModel> call = RestfulAdapter.getInstance().getAllianceCategoryList("{\"key\":1}");
        call.enqueue(new Callback<AllianceCategoryModel>() {
            @Override
            public void onResponse(Call<AllianceCategoryModel> call, retrofit2.Response<AllianceCategoryModel> response) {
                Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                List<AllianceCategoryModel> list = new ArrayList<>();
                if (response.isSuccessful()) {
                    list.add(response.body());
                    for (AllianceCategoryModel.Result model : response.body().getResult()) {
                        tabLayout.addTab(tabLayout.newTab().setText(model.getValue())); // tab 카테고리 리스트
                    }
                    adapter = new AllianceMapPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), list, lon, lat);
                    viewPager.setAdapter(adapter);
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
            }

            @Override
            public void onFailure(Call<AllianceCategoryModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alliance_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_list:
                finish();
                break;
            case R.id.menu_main:
                setResult(RESULT_OK);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            switch (event) {
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.w("onGpsStatusChanged", "GPS_EVENT_STARTED");
                    isOnGps = true;
                    setMyLocationStatus();
                    break;
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.w("onGpsStatusChanged", "GPS_EVENT_STOPPED");
                    isOnGps = false;
                    setMyLocationStatus();
                    break;
            }
        }
    };

    @OnClick({R.id.layout_my_location})
    public void onClickMyLocation(View v) {
        isAgree = olleego_SP.getBoolean("agreeLocation", false);
        if (!isAgree) {
            showAlertDialog();
        } else {
            if (isOnGps) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
            } else {
                requestMyLocation();
            }
        }
    }

    private void showAlertDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.location_service_custom_dialog, null);
        RelativeLayout cancel = (RelativeLayout) dialogView.findViewById(R.id.layout_cancel);
        RelativeLayout agree = (RelativeLayout) dialogView.findViewById(R.id.layout_ok);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("agreeLocation", true);
                editor.commit();
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.addGpsStatusListener(gpsStatusListener);

                alertDialog.dismiss();
                requestMyLocation();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    void requestMyLocation() {
        if ((locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
            locationListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);

        } else if ((locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
            locationListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
        }

        if (!isOnGps) {
            // GPS 꺼져있을 때
            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        }
    }

    void setMyLocationStatus() {
        if (isOnGps) {
            mLocation.setBackgroundResource(R.drawable.alliance_center_list_my_location_bg_ov);
            mLocText.setTextColor(Color.parseColor("#5e7efc"));
            gpsImage.setImageResource(R.drawable.ic_gps_fixed_p);

            if ((locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
                locationListener = new MyLocationListener();
                if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);

            } else if ((locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
                locationListener = new MyLocationListener();
                if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
            }
        } else {
            mLocation.setBackgroundResource(R.drawable.alliance_center_list_my_location_bg);
            mLocText.setTextColor(Color.parseColor("#878885"));
            gpsImage.setImageResource(R.drawable.ic_gps_fixed_x);
        }
    }

    class MyLocationListener implements LocationListener {
        public MyLocationListener() {
        }

        // 위치정보는 아래 메서드를 통해서 전달된다.
        @Override
        public void onLocationChanged(Location location) {
            Log.d("MyLocationListener", "onLocationChanged()가 호출되었습니다");

            lat = location.getLatitude();
            lon = location.getLongitude();

            //현재 내 위치로 조회
            if (adapter != null)
                adapter.setData(lon, lat);

            if (locationListener != null) {
                if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.removeUpdates(locationListener);
                locationListener = null;
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.w(TAG, "onProviderDisabled");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.w(TAG, "onProviderEnabled");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
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
        if (gpsStatusListener != null)
            locationManager.removeGpsStatusListener(gpsStatusListener);

        if (locationListener != null){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(locationListener);
        }
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
