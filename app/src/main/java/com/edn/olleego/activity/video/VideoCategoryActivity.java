package com.edn.olleego.activity.video;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.fragment.video.VideoCategoryFragment;
import com.edn.olleego.fragment.video.VideoListFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

/**
 * Created by kym on 2016. 7. 29..
 */
public class VideoCategoryActivity extends AppCompatActivity implements VideoCategoryFragment.ExerciseVideoListener {
    private final String TAG = this.getClass().getSimpleName();
    private VideoListFragment videoListFragment;
    private Resources res;

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_menu)
    ImageView menu;
    @BindView(R.id.spinner)
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_video);
        res = getResources();
        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        title.setText(res.getString(R.string.exercise_video_title));

        VideoCategoryFragment videoCategoryFragment = new VideoCategoryFragment().newInstance();
        replaceFragment(videoCategoryFragment);
    }

    void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_item_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnItemSelected({R.id.spinner})
    public void onItemSelected(int position) {
        Log.d(TAG, "position : " + position);
        videoListFragment.setItemPosition(position);
    }

    FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            Log.d(TAG, "getBackStackEntryCount() : " + getSupportFragmentManager().getBackStackEntryCount());
            int cnt = getSupportFragmentManager().getBackStackEntryCount();
            if (cnt == 0) {
                finish();
            } else if (cnt == 1) {
                title.setVisibility(View.VISIBLE);
                menu.setVisibility(View.VISIBLE);
                spinner.setSelection(Spinner.INVALID_POSITION);
                spinner.setVisibility(View.GONE);
                toolbar.setNavigationIcon(null);
            }
        }
    };

    void replaceFragment(Fragment fragment) {
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragTransaction.replace(R.id.fragment_common, fragment);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSupportFragmentManager().removeOnBackStackChangedListener(mOnBackStackChangedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);
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
    public void onClickExercise(int id) {
        Log.d(TAG, "onClickExercise");
        videoListFragment = new VideoListFragment();
        replaceFragment(videoListFragment);

        initializeSpinner();
        title.setVisibility(View.GONE);
        menu.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        int position = id;
        if (id == 300) position = 5; // 스트레칭
        spinner.setSelection(position);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
