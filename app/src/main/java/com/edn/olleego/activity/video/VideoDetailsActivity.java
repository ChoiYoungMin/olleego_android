package com.edn.olleego.activity.video;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.fragment.video.VideoDetailsFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kym on 2016. 7. 29..
 */
public class VideoDetailsActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Resources res;

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_menu)
    ImageView menu;

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

        menu.setImageResource(R.drawable.ic_close);

        Intent intent = getIntent();
        String mTitle = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        title.setText(mTitle);

        VideoDetailsFragment videoDetailsFragment = new VideoDetailsFragment().newInstance(id);
        replaceFragment(videoDetailsFragment);
    }

    @OnClick({R.id.img_menu})
    public void onClickClose(View v){
        finish();
    }

    void replaceFragment(Fragment fragment) {
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragTransaction.replace(R.id.fragment_common, fragment);
        fragTransaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
