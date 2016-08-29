package com.edn.olleego.fragment.alliance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kym on 2016. 8. 8..
 *
 * 센터 상세 페이지 상단 이미지 슬라이드
 */
public class AllianceImageSliderFragment extends Fragment {
    private String url;
    private Context mContext;
    @BindView(R.id.image)
    ImageView imageView;

    public static AllianceImageSliderFragment newInstance(String url) {
        final AllianceImageSliderFragment f = new AllianceImageSliderFragment();
        final Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments() != null ? getArguments().getString("url") : "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alliance_image_slider, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
