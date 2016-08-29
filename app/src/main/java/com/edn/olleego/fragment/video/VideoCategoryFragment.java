package com.edn.olleego.fragment.video;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kym on 2016. 7. 31..
 */
public class VideoCategoryFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ExerciseVideoListener listener;

    @BindView(R.id.home_training)
    RelativeLayout home_training;
    @BindView(R.id.fitness_center)
    RelativeLayout fitness_center;
    @BindView(R.id.part)
    RelativeLayout part;
    @BindView(R.id.pilates)
    RelativeLayout pilates;
    @BindView(R.id.swim)
    RelativeLayout swim;
    @BindView(R.id.stretching)
    RelativeLayout stretching;

    public interface ExerciseVideoListener {
        void onClickExercise(int position);
    }

    public VideoCategoryFragment() {
    }

    public static VideoCategoryFragment newInstance() {
        final VideoCategoryFragment f = new VideoCategoryFragment();
        final Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExerciseVideoListener) {
            listener = (ExerciseVideoListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implemenet VideoCategoryFragment.ExerciseVideoListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_category, container, false);
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
    }

    @OnClick({R.id.home_training, R.id.fitness_center, R.id.part,
            R.id.pilates, R.id.swim, R.id.stretching})
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        int id = 0;
        if (v.getTag().toString() != null && !TextUtils.isEmpty(v.getTag().toString()))
            id = Integer.parseInt(v.getTag().toString());

        listener.onClickExercise(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
