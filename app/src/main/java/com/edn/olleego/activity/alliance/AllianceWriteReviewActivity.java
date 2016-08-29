package com.edn.olleego.activity.alliance;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.adapter.alliance.AllianceWriteReviewAdapter;
import com.edn.olleego.common.OlleegoEvent;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.alliance.AllianceEditReviewBody;
import com.edn.olleego.model.alliance.AllianceTrainersModel;
import com.edn.olleego.model.alliance.AllianceWriteReviewBody;
import com.edn.olleego.model.alliance.AllianceWriteReviewResult;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by pc on 2016-08-16.
 *
 * 제휴센터 > 상세페이지 > 리뷰 > 리뷰 작성하기 페이지
 */
public class AllianceWriteReviewActivity extends AppCompatActivity implements AllianceWriteReviewAdapter.WriteReviewAdapterListener {
    private final String TAG = this.getClass().getSimpleName();
    private Resources res;
    private AllianceWriteReviewAdapter adapter;
    private int id;
    private int trainerId;
    private String content;
    private int textId;
    private float rating;
    private boolean isEdit;
    private boolean isSelectedTrainer;

    private EventBus bus = EventBus.getDefault();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.confirm)
    RelativeLayout confirm;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.img_menu)
    ImageView close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        ButterKnife.bind(this);
        res = getResources();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        content = intent.getStringExtra("content");
        rating = intent.getFloatExtra("rating", 0);
        isEdit = intent.getBooleanExtra("isEdit", false);
        textId = intent.getIntExtra("textId", 0);
        trainerId = intent.getIntExtra("trainerId", 0);

        editText.setText(content);
        ratingBar.setRating(rating);

        if (isEdit) isSelectedTrainer = true;

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(res.getColor(R.color.fitness_blue));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AllianceWriteReviewAdapter(this);
        recyclerView.setAdapter(adapter);

        RestfulAdapter.setOlleegoInterface(null);
        Call<AllianceTrainersModel> call = RestfulAdapter.getInstance().getTrainersList(id);
        call.enqueue(new Callback<AllianceTrainersModel>() {
            @Override
            public void onResponse(Call<AllianceTrainersModel> call, retrofit2.Response<AllianceTrainersModel> response) {
                Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    adapter.setData(response.body(), trainerId);
                }
            }

            @Override
            public void onFailure(Call<AllianceTrainersModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
    }

    @OnClick({R.id.img_menu})
    public void onClickClose() {
        finish();
    }

    @OnClick({R.id.confirm})
    public void onClickConfirm(View view) {
        String token = "Olleego " + Util.getPreferencesString(this, "login_token");
        String userId = Util.getPreferencesString(this, "user_id");
        Call<AllianceWriteReviewResult> call = null;

        if (isSelectedTrainer) {
            if (ratingBar.getRating() > 0) {
                String content = editText.getText().toString().replaceAll("\\s", "");
                if (content != null && !TextUtils.isEmpty(content)) {
                    RestfulAdapter.setOlleegoInterface(null);
                    if (isEdit) { //수정
                        AllianceEditReviewBody body = new AllianceEditReviewBody();
                        body.setBody(editText.getText().toString());
                        body.setPoint((int) ratingBar.getRating());
                        body.setTrainer(trainerId);

                        call = RestfulAdapter.getInstance().editReview(token, textId, body);
                    } else { //새로 작성
                        AllianceWriteReviewBody body = new AllianceWriteReviewBody();
                        body.setBody(editText.getText().toString());
                        body.setPoint((int) ratingBar.getRating());

                        if (userId != null && !TextUtils.isEmpty(userId))
                            body.setWriter(Integer.parseInt(userId));
                        body.setCenter(id);
                        body.setTrainer(trainerId);

                        call = RestfulAdapter.getInstance().sendReview(token, body);
                    }
                    call.enqueue(new Callback<AllianceWriteReviewResult>() {
                        @Override
                        public void onResponse(Call<AllianceWriteReviewResult> call, retrofit2.Response<AllianceWriteReviewResult> response) {
                            Log.d(TAG + "_onResponse", "isSuccessful : " + response.isSuccessful());
                            if (response.isSuccessful()) {
                                OlleegoEvent.WriteReviewEvent event = new OlleegoEvent.WriteReviewEvent();
                                event.setSuccess(response.isSuccessful());
                                if (event != null) bus.post(event);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<AllianceWriteReviewResult> call, Throwable t) {
                            Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                        }
                    });
                } else
                    Toast.makeText(this, res.getString(R.string.write_review_toast3), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, res.getString(R.string.write_review_toast2), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, res.getString(R.string.write_review_toast1), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setReviewData(int id) {
        isSelectedTrainer = true;
        //선택된 트레이너 id
        trainerId = id;
        Log.d(TAG, "trainerId : " + trainerId);
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
}
