package com.edn.olleego.fragment.alliance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.alliance.AllianceCenterListAdapter;
import com.edn.olleego.common.GlobalFont;
import com.edn.olleego.model.alliance.AllianceListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kym on 2016. 8. 7..
 * <p/>
 * 제휴센터 리스트
 */
public class AllianceCenterListFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private String id;
    private String value;
    private double lon;
    private double lat;

    private AllianceCenterListAdapter allianceCenterListAdapter;
    private int index;
    private int totalItemCount;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    TextView emptyView;

    public AllianceCenterListFragment() {
    }

    public static AllianceCenterListFragment newInstance(String value, String id, double lon, double lat) {
        final AllianceCenterListFragment f = new AllianceCenterListFragment();
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
        index = 0;
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alliance_center_list, container, false);
        ButterKnife.bind(this, v);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        allianceCenterListAdapter = new AllianceCenterListAdapter(mContext, recyclerView);
        recyclerView.setAdapter(allianceCenterListAdapter);

        allianceCenterListAdapter.setOnLoadMoreListener(new AllianceCenterListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                index += 5;
                getAllianceList(true);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public interface AllianceService {
        @GET("/api/v2/centers/list/{lon}/{lat}/{index}")
        Call<AllianceListModel> getAllianceList(@Path("lon") double lon,
                                                @Path("lat") double lat,
                                                @Path("index") int index,
                                                @Query("filter") String id);

        @GET("/api/v2/centers/list/{lon}/{lat}/{index}")
        Call<AllianceListModel> getAllianceList(@Path("lon") double lon,
                                                @Path("lat") double lat,
                                                @Path("index") int index);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAllianceList(false);
    }

    void getAllianceList(final boolean isLoadMore) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalFont.getInstance().getWebHostUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllianceService service = retrofit.create(AllianceService.class);
        Call<AllianceListModel> call = null;

        //전체
        if (value.equals("306"))
            call = service.getAllianceList(lon, lat, index);
        else
            call = service.getAllianceList(lon, lat, index, id);

        call.enqueue(new Callback<AllianceListModel>() {
            @Override
            public void onResponse(Call<AllianceListModel> call, retrofit2.Response<AllianceListModel> response) {
                Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    ArrayList<AllianceListModel> list = new ArrayList<>();
                    list.add(response.body());

                    if (isLoadMore) {
                        if (allianceCenterListAdapter != null) {
                            allianceCenterListAdapter.hideProgressBar();
                            allianceCenterListAdapter.addData(list);
                        }
                    } else {
                        if (allianceCenterListAdapter != null)
                            allianceCenterListAdapter.setData(list);
                    }

                    totalItemCount += list.get(0).getResult().size();
                } else
                    allianceCenterListAdapter.hideProgressBar();

                updateView(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<AllianceListModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
    }

    void updateView(boolean isSuccessful) {
        if (isSuccessful || totalItemCount > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
