package com.edn.olleego.fragment.video;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.model.video.VideoDetailsModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by kym on 2016. 8. 5..
 */
public class VideoDetailsFragment extends Fragment implements YouTubePlayer.OnInitializedListener {
    private final String TAG = this.getClass().getSimpleName();
    private final String DEVELOPER_KEY = "AIzaSyC27EEGH1RsB8CIvlrxovdJIxQEsI29q-c";
    private Context mContext;
    private String id;
    private Resources res;

    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.ex_sort)
    TextView exSort;
    @BindView(R.id.ex_method)
    TextView exMethod;
    @BindView(R.id.key_ex_part)
    TextView keyExPart;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.method)
    TextView method;
    @BindView(R.id.warning)
    TextView warning;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.youtube_fragment)
    FrameLayout youtubeLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        res = context.getResources();
        /*if (context instanceof VideoDetailsListener) {
            listener = (VideoDetailsListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implemenet VideoDetailsFragment.VideoDetailsListener");
        }*/
    }

    public VideoDetailsFragment() {
    }

    public static VideoDetailsFragment newInstance(String id) {
        final VideoDetailsFragment f = new VideoDetailsFragment();
        final Bundle args = new Bundle();
        args.putString("id", id);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments() != null ? getArguments().getString("id") : "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_details, container, false);
        ButterKnife.bind(this, v);
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(DEVELOPER_KEY, this);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            RestfulAdapter.setOlleegoInterface(null);
            Call<VideoDetailsModel> call = RestfulAdapter.getInstance().getVideo(id);
            call.enqueue(new Callback<VideoDetailsModel>() {
                @Override
                public void onResponse(Call<VideoDetailsModel> call, retrofit2.Response<VideoDetailsModel> response) {
                    Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                    ArrayList<VideoDetailsModel> list = new ArrayList<>();
                    if (response.isSuccessful()) {
                        list.add(response.body());
                        String url = response.body().getResult().getMovie_url();
                        if (url != null && !TextUtils.isEmpty(url)) {

                            int index = 0;
                            if (url.contains("=")) //유투브 동영상 주소 형식이 상이하므로.. 필요한 부분만 잘라냄.
                                index = url.indexOf("=") + 1;
                            else
                                index = url.lastIndexOf("/") + 1;

                            Log.d("onResponse", "substring : " + url.substring(index, url.length()));
                            youTubePlayer.cueVideo(url.substring(index, url.length()));
                        } else { //영상이 없는 경우 이미지 세팅
                            image.setVisibility(View.VISIBLE);
                            Glide.with(mContext)
                                    .load(response.body().getResult().getThum_jpg())
                                    .asBitmap()
                                    .centerCrop()
                                    .into(image);
                        }

                        level.setText(response.body().getResult().getLv_attr().getValue());
                        exSort.setText(response.body().getResult().getLg_sort().getValue());
                        exMethod.setText(response.body().getResult().getDt_sort().getValue());
                        description.setText(response.body().getResult().getDescription());
                        method.setText(response.body().getResult().getMethod());
                        warning.setText(response.body().getResult().getWarning());

                        int cnt = 0;
                        StringBuilder sb = new StringBuilder();
                        for (VideoDetailsModel.SmSort info : response.body().getResult().getSm_sort()) {
                            if (cnt == 0) sb.append(info.getValue());
                            else sb.append("," + info.getValue());
                            cnt++;
                        }
                        keyExPart.setText(res.getString(R.string.key_ex_part) + " " + sb.toString());
                    }
                }

                @Override
                public void onFailure(Call<VideoDetailsModel> call, Throwable t) {
                    Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                }
            });
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
