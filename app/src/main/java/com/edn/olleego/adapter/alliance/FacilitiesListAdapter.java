package com.edn.olleego.adapter.alliance;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.common.GlobalFont;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AllianceFacilitiesModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kym on 2016. 8. 1..
 *
 * 제휴센터 상세 페이지 > 세부정보 탭 > 시설정보
 */
public class FacilitiesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private List<AllianceFacilitiesModel> list = new ArrayList<>();
    private List<AllianceDetailsModel.Result.CenterDinfo.FacilitiesInfo> availableList = new ArrayList<>();
    private AllianceFacilitiesModel.Result result;

    public FacilitiesListAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
    }

    public void setData(List<AllianceFacilitiesModel> data) {
        if (data != null) list = data;

        notifyDataSetChanged();
    }

    public class FacilitiesListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;

        public FacilitiesListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(0).getResult().get(position);
            name.setText(result.getValue());

            TypedArray imgArrayLight = res.obtainTypedArray(R.array.facilities_image_light_array);
            TypedArray imgArray = res.obtainTypedArray(R.array.facilities_image_array);

            boolean flag = false;

            if (availableList != null && availableList.size() > 0) {
                for (AllianceDetailsModel.Result.CenterDinfo.FacilitiesInfo info : availableList) {
                    if (info.get_id() == result.get_id()) {
                        flag = true;
                    }
                }
            }

            int imgResourceId = imgArray.getResourceId(position, -1);
            if (flag) {
                imgResourceId = imgArrayLight.getResourceId(position, -1);
                name.setTextColor(Color.parseColor("#5e7efc"));
            } else
                name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Glide.with(mContext)
                    .load(imgResourceId)
                    .asBitmap()
                    .centerCrop()
                    .into(image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_facilities_list_item, null);
        FacilitiesListHolder facilitiesListHolder = new FacilitiesListHolder(v);
        return facilitiesListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            FacilitiesListHolder vh = (FacilitiesListHolder) holder;
            vh.bindView(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null && list.size() > 0)
            count = list.get(0).getResult().size();
        return count;
    }

    public interface AllianceService {
        @GET("/api/v2/codes")
        Call<AllianceFacilitiesModel> getFacilitiesList(@Query("filter") String center);
    }

    void getItemsList(List<AllianceDetailsModel.Result.CenterDinfo.FacilitiesInfo> availableList) {
        this.availableList = availableList;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalFont.getInstance().getWebHostUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllianceService service = retrofit.create(AllianceService.class);
        Call<AllianceFacilitiesModel> call = service.getFacilitiesList("{\"category\":\"CENTER\",\"type\":\"facilities_info\"}");
        call.enqueue(new Callback<AllianceFacilitiesModel>() {
            @Override
            public void onResponse(Call<AllianceFacilitiesModel> call, retrofit2.Response<AllianceFacilitiesModel> response) {
                Log.d(TAG + "_onResponse", "isSuccessful : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    list.add(response.body());
                    setData(list);
                }
            }

            @Override
            public void onFailure(Call<AllianceFacilitiesModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
    }
}