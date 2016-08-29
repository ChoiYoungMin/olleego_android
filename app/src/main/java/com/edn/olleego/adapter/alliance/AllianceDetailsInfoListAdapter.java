package com.edn.olleego.adapter.alliance;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AllianceTrainersModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kym on 2016. 8. 1..
 *
 * 제휴센터 상세 페이지 > 세부정보 탭 ListAdapter
 */
public class AllianceDetailsInfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private final static int TRAINER_LIST_HEADER_VIEW = 100;
    private final static int TRAINER_LIST_VIEW = 200;
    private final static int INFO_VIEW = 300;

    private Context mContext;
    private Resources res;
    private ProfessionalListAdapter professionalListAdapter;
    private FacilitiesListAdapter facilitiesListAdapter;
    private List<AllianceTrainersModel> list = new ArrayList<>();
    private List<AllianceDetailsModel> detalisList = new ArrayList<>();
    private AllianceDetailsModel.Result detailsResult;
    private AllianceTrainersModel.Result result;

    public AllianceDetailsInfoListAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
    }

    public void setData(List<AllianceTrainersModel> data, List<AllianceDetailsModel> detalisList) {
        if (data != null) list = data;
        this.detalisList = detalisList;

        notifyDataSetChanged();
    }

    public class TrainerListHeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.trainer_count)
        TextView count;

        public TrainerListHeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            count.setText(String.valueOf(getItemCount() - 2) + "명");
        }
    }

    public class AllianceTrainersListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardview)
        CardView cardView;
        @BindView(R.id.professional_list)
        RecyclerView professionalList;
        @BindView(R.id.career_list)
        TextView careerList;
        @BindView(R.id.trainer_image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;

        public AllianceTrainersListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(0).getResult().get(position);

            if (result.getBasic_info().getCareer().getActivity() != null) {
                int cnt = 0;
                StringBuilder sb = new StringBuilder();
                for (String str : result.getBasic_info().getCareer().getActivity()) {
                    if (cnt == 0) sb.append("•  " + str);
                    else sb.append("\n•  " + str);
                    cnt++;
                }
                careerList.setText(sb.toString());
            }

            if (result.getBasic_info().getCareer().getProfessional() != null
                    && result.getBasic_info().getCareer().getProfessional().size() > 0) {

                professionalListAdapter.setData(result.getBasic_info().getCareer().getProfessional());
            }

            name.setText(result.getName());

            Glide.with(mContext)
                    .load(list.get(0).getResult().get(position).getAvatar())
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .centerCrop()
                    .into(image);
        }
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {
        private GoogleMap map;

        @BindView(R.id.recyclerview)
        RecyclerView facilityList;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.holiday)
        TextView holiday;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.tel)
        TextView tel;
        @BindView(R.id.map)
        MapView mapView;

        public InfoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            detailsResult = detalisList.get(0).getResult();
            //시설정보
            facilitiesListAdapter.getItemsList(detailsResult.getCenter_dinfo().getFacilities_info());
            //이용시간
            time.setText(detailsResult.getCenter_dinfo().getOptime().getStart() + " ~ " + detailsResult.getCenter_dinfo().getOptime().getEnd());
            //위치정보
            address.setText(detailsResult.getCenter_binfo().getAddress().getSub1());
            //전화번호
            tel.setText("Tel. " + detailsResult.getCenter_binfo().getTel().getSub1()
                    + "-" + detailsResult.getCenter_binfo().getTel().getSub2()
                    + "-" + detailsResult.getCenter_binfo().getTel().getSub3());

            mapView.onCreate(null);
            mapView.onResume();
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

                        Double dLat = detailsResult.getCenter_binfo().getLoc().getCoordinates().get(1);
                        Double dLng = detailsResult.getCenter_binfo().getLoc().getCoordinates().get(0);

                        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.olleego_location);

                        LatLng latLng = new LatLng(dLat, dLng);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.title(detailsResult.getCenter_binfo().getName());
                        markerOptions.position(latLng);
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));

                        map.addMarker(markerOptions);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == TRAINER_LIST_HEADER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_trainers_list_header, null);
            TrainerListHeaderViewHolder headerViewHolder = new TrainerListHeaderViewHolder(v);
            return headerViewHolder;
        } else if (viewType == TRAINER_LIST_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_trainers_list_item, null);
            AllianceTrainersListHolder trainersListHolder = new AllianceTrainersListHolder(v);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            trainersListHolder.professionalList.setLayoutManager(layoutManager);
            professionalListAdapter = new ProfessionalListAdapter(mContext);
            trainersListHolder.professionalList.setAdapter(professionalListAdapter);
            trainersListHolder.professionalList.setNestedScrollingEnabled(false);
            return trainersListHolder;
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_details_info_view_item, null);
            InfoViewHolder infoViewHolder = new InfoViewHolder(v);
            infoViewHolder.facilityList.setLayoutManager(new GridLayoutManager(mContext, 4));
            facilitiesListAdapter = new FacilitiesListAdapter(mContext);
            infoViewHolder.facilityList.setAdapter(facilitiesListAdapter);
            infoViewHolder.facilityList.setNestedScrollingEnabled(false);
            return infoViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof TrainerListHeaderViewHolder) {
                TrainerListHeaderViewHolder vh = (TrainerListHeaderViewHolder) holder;
                vh.bindView(position);
            } else if (holder instanceof AllianceTrainersListHolder) {
                AllianceTrainersListHolder vh = (AllianceTrainersListHolder) holder;
                vh.bindView(position);
            } else {
                InfoViewHolder vh = (InfoViewHolder) holder;
                vh.bindView(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TRAINER_LIST_HEADER_VIEW;
        else if (position == getItemCount() - 1)
            return INFO_VIEW;

        return TRAINER_LIST_VIEW;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null && list.size() > 0)
            count = list.get(0).getResult().size();
        return count;
    }
}