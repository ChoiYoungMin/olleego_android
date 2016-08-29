package com.edn.olleego.fragment.video;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.adapter.video.VideoListAdapter;
import com.edn.olleego.adapter.video.VideoPartListAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.model.video.VideoListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016. 7. 31..
 */
public class VideoListFragment extends Fragment implements VideoPartListAdapter.VideoPartListener {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private VideoListAdapter videoListAdapter;
    private ArrayList<VideoListModel> list;

    @BindView(R.id.recyclerview_vertical)
    RecyclerView recyclerVertical;
    @BindView(R.id.recyclerview_horizontal)
    RecyclerView recyclerHorizontal;
    @BindView(R.id.video_content_text)
    TextView content;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.layout)
    RelativeLayout layout;

    public VideoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_video_list, container, false);
        ButterKnife.bind(this, v);
        recyclerVertical.setLayoutManager(new LinearLayoutManager(mContext));
        videoListAdapter = new VideoListAdapter(mContext, this);
        recyclerVertical.setAdapter(videoListAdapter);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClickPartItem(String id) {
        Log.d(TAG, "horizontal_id : " + id);

        RestfulAdapter.setOlleegoInterface(null);
        Call<VideoListModel> call = RestfulAdapter.getInstance().getVideoList(2);
        if (!id.equals("0"))
            call =  RestfulAdapter.getInstance().getPartList(id);

        call.enqueue(new Callback<VideoListModel>() {
            @Override
            public void onResponse(Call<VideoListModel> call, retrofit2.Response<VideoListModel> response) {
                Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                list = new ArrayList<>();
                if (response.isSuccessful()) {
                    list.add(response.body());
                }
                videoListAdapter.setData(list);
                updateView(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<VideoListModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
    }

    public void setItemPosition(int position) {
        if (position == 2)
            initHorizontalView();
        else {
            recyclerHorizontal.setVisibility(View.GONE);
        }
        getItem(position);
    }

    void initHorizontalView() {
        recyclerHorizontal.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerHorizontal.setLayoutManager(layoutManager);
        VideoPartListAdapter videoPartListAdapter = new VideoPartListAdapter(mContext, this);
        recyclerHorizontal.setAdapter(videoPartListAdapter);
    }

    void updateView(boolean isSuccessful) {
        if (isSuccessful) {
            recyclerVertical.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            recyclerVertical.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    void getItem(int position) {
        String[] contentArray = getResources().getStringArray(R.array.video_content_array);
        content.setText(contentArray[position]);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.video_category_icon_array);
        TypedArray typedArray2 = getResources().obtainTypedArray(R.array.video_category_background_array);
        layout.setBackgroundResource(typedArray2.getResourceId(position, -1));

        Glide.with(mContext)
                .load(typedArray.getResourceId(position, -1))
                .asBitmap()
                .centerCrop()
                .into(icon);

        int id = position;
        if (position == 5) id = 300;

        RestfulAdapter.setOlleegoInterface(null);
        Call<VideoListModel> call = RestfulAdapter.getInstance().getVideoList(id);
        call.enqueue(new Callback<VideoListModel>() {
            @Override
            public void onResponse(Call<VideoListModel> call, retrofit2.Response<VideoListModel> response) {
                Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    list = new ArrayList<>();
                    list.add(response.body());
                    if (videoListAdapter != null)
                        videoListAdapter.setData(list);
                }
                updateView(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<VideoListModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
