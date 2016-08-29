package com.edn.olleego.fragment.alliance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.alliance.AllianceDetailsActivity;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.model.alliance.AllianceMapItemModel;
import com.edn.olleego.model.alliance.AllianceMapModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016. 8. 7..
 */
public class AllianceMapFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private String id;
    private String value;
    private double lon;
    private double lat;
    private GoogleMap map;
    private Resources res;
    private View markerView;
    private TextView markerText;

    @BindView(R.id.map)
    MapView mapView;

    @BindView(R.id.bottom_view)
    RelativeLayout bottomView;
    @BindView(R.id.category)
    TextView category;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.review_count)
    TextView reviewCount;
    @BindView(R.id.pt)
    TextView pt;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.sale_money)
    TextView saleMoney;
    @BindView(R.id.star)
    RatingBar ratingBar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.discount)
    ImageView discount;

    public AllianceMapFragment() {
    }

    public static AllianceMapFragment newInstance(String value, String id, double lon, double lat) {
        final AllianceMapFragment f = new AllianceMapFragment();
        final Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("value", value);
        args.putDouble("lon", lon);
        args.putDouble("lat", lat);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments() != null ? getArguments().getString("id") : "";
        value = getArguments() != null ? getArguments().getString("value") : "";
        lon = getArguments() != null ? getArguments().getDouble("lon") : 127.005315d;
        lat = getArguments() != null ? getArguments().getDouble("lat") : 37.61729d;
        Log.w(TAG, "id : " + id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        res = mContext.getResources();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alliance_map, container, false);
        ButterKnife.bind(this, v);
        mapView.onCreate(null);
        mapView.onResume();
        setCustomMarkerView();
        return v;
    }

    private void setCustomMarkerView() {
        markerView = LayoutInflater.from(mContext).inflate(R.layout.map_marker_layout, null);
        markerText = (TextView) markerView.findViewById(R.id.marker_text);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RestfulAdapter.setOlleegoInterface(null);
        Call<AllianceMapModel> call = null;
        //전체
        if (value.equals("306"))
            call = RestfulAdapter.getInstance().getAllianceMap(lon, lat);
        else
            call = RestfulAdapter.getInstance().getAllianceMap(lon, lat, id);

        call.enqueue(new Callback<AllianceMapModel>() {
            @Override
            public void onResponse(Call<AllianceMapModel> call, final retrofit2.Response<AllianceMapModel> response) {
                Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    setMap(response.body());
                }
            }

            @Override
            public void onFailure(Call<AllianceMapModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
    }

    @OnClick({R.id.bottom_view})
    public void onClickBottom(View view) {
        int id = (int) view.getTag();
        Intent intent = new Intent(mContext, AllianceDetailsActivity.class);
        intent.putExtra("id", id);
        mContext.startActivity(intent);
    }

    void setMap(final AllianceMapModel model) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                try {
                    MapsInitializer.initialize(mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (map != null) {
                    map.clear();
                    //map.setMyLocationEnabled(true);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 13));
                    map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

                    map.addCircle(new CircleOptions()
                            .center(new LatLng(lat, lon))
                            .radius(500)
                            .strokeColor(Color.parseColor("#884169e1"))
                            .fillColor(Color.parseColor("#5587cefa")));

                    //맵 클릭시
                    map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            bottomView.setVisibility(View.GONE);
                        }
                    });
                    //마커 클릭시
                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            LatLng latLng = marker.getPosition();
                            double lon = latLng.longitude;
                            double lat = latLng.latitude;
                            String id = marker.getSnippet();

                            Log.w(TAG, "marker.getSnippet : " + id);
                            RestfulAdapter.setOlleegoInterface(null);
                            Call<AllianceMapItemModel> call = RestfulAdapter.getInstance().getCenterInfo(id, lon, lat);

                            call.enqueue(new Callback<AllianceMapItemModel>() {
                                @Override
                                public void onResponse(Call<AllianceMapItemModel> call, final retrofit2.Response<AllianceMapItemModel> response) {
                                    Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                                    if (response.isSuccessful()) {
                                        bottomView.setVisibility(View.VISIBLE);
                                        AllianceMapItemModel.Result result = response.body().getResult();

                                        int price = 0;
                                        if (result.getPt() != null) {
                                            price = result.getPt().getMoney();
                                            pt.setText(result.getPt().getTitle());
                                        }

                                        boolean saleFlag = result.isSale_flag();
                                        if (saleFlag) {
                                            discount.setVisibility(View.VISIBLE);
                                            price = result.getSale_money();
                                            saleMoney.setText(String.valueOf(price));
                                            saleMoney.setPaintFlags(saleMoney.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                        } else {
                                            discount.setVisibility(View.GONE);
                                        }

                                        int colorResourceId = res.getColor(R.color.category_fitness);

                                        if (result.getCenter_binfo() != null) {
                                            if (result.getCenter_binfo().getType() != null) {
                                                int id = result.getCenter_binfo().getType().get_id();

                                                if (id == 301)
                                                    colorResourceId = res.getColor(R.color.category_fitness);
                                                else if (id == 303)
                                                    colorResourceId = res.getColor(R.color.category_ptshop);
                                                else if (id == 305)
                                                    colorResourceId = res.getColor(R.color.category_yoga);

                                                category.setTextColor(colorResourceId);
                                                category.setText(result.getCenter_binfo().getType().getValue());
                                            }

                                            location.setText(result.getCenter_binfo().getLocate());
                                            place.setText("(" + result.getCenter_binfo().getPlace() + ")");
                                            name.setText(result.getCenter_binfo().getName());

                                            Glide.with(mContext)
                                                    .load(result.getCenter_binfo().getImage())
                                                    .asBitmap()
                                                    .centerCrop()
                                                    .into(image);
                                        }

                                        ratingBar.setRating(result.getPoint_avg());
                                        reviewCount.setText("(" + result.getReview_count() + ")");
                                        money.setText(String.valueOf(price));

                                        bottomView.setTag(result.get_id());
                                    }
                                }

                                @Override
                                public void onFailure(Call<AllianceMapItemModel> call, Throwable t) {
                                    Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                                }
                            });

                            return true;
                        }
                    });

                    for (AllianceMapModel.Result info : model.getResult()) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.snippet(String.valueOf(info.get_id()));

                        if (info.getCenter_binfo() != null) {
                            LatLng latLng = new LatLng(info.getCenter_binfo().getLoc().getCoordinates().get(1),
                                    info.getCenter_binfo().getLoc().getCoordinates().get(0));

                            if (info.getCenter_binfo().getType() != null) {
                                int id = info.getCenter_binfo().getType().get_id();
                                int resourcesId = R.drawable.t_fitness;

                                if (id == 301)                              //피트니스
                                    resourcesId = R.drawable.t_fitness;
                                else if (id == 302 || id == 305)            //필라테스/요가
                                    resourcesId = R.drawable.t_yoga;
                                else if (id == 303)                         //PT샵
                                    resourcesId = R.drawable.t_pt;
                                else if (id == 304)                         //기타
                                    resourcesId = R.drawable.t_fitness;

                                markerText.setBackgroundResource(resourcesId);
                                markerText.setText(info.getCenter_binfo().getType().getValue());

                                if (id == 302 || id == 305)
                                    markerText.setText(res.getString(R.string.marker_pilates));
                            }

                            markerOptions.position(latLng);
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(mContext, markerView)));
                            map.addMarker(markerOptions);
                        }
                    }
                }
            }
        });
    }

    // View를 Bitmap으로 변환
    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
