package com.edn.olleego.activity.alliance;


import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.adapter.alliance.DayOfWeekAdapter;
import com.edn.olleego.adapter.alliance.PurchasePTtrainerListAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.alliance.AlliancePurchaseIamportRequestBody;
import com.edn.olleego.model.alliance.AlliancePurchaseInfoBody;
import com.edn.olleego.model.alliance.AlliancePurchaseInfoResponse;
import com.edn.olleego.model.alliance.AlliancePurchasePaymentsBody;
import com.edn.olleego.model.alliance.AllianceTrainersModel;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by pc on 2016-08-16.
 *
 * 제휴센터 > 상세 페이지 > 하단 구매하기 > 상품 선택 > 구매 상세페이지
 */
public class AlliancePurchasePTActivity extends AppCompatActivity implements PurchasePTtrainerListAdapter.TrainerListAdapterListener,
        DayOfWeekAdapter.DayOfWeekAdapterListener {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private static final int REQUEST_CODE_PAYMENT = 77;
    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private Resources res;
    private PurchasePTtrainerListAdapter trainerListAdapter;
    private DayOfWeekAdapter dayOfWeekAdapter;
    private List<String> selectedDayList;
    private SparseBooleanArray sparseBooleanArray;
    private String trainerAvatar;
    private String trainerName;
    private String ptName; //pt권 이름
    private String start;
    private String end;
    private String paymentOption = "card"; //결제방법
    private String merchant_uid;
    private int purchaseId;
    private int trainerId;
    private int pageIndex;
    private int centerId;
    private int ptId;
    private boolean inbodyPublic;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.layout_pay_completed)
    RelativeLayout layout_pay_completed;

    @BindView(R.id.image_completed)
    ImageView image_completed;
    @BindView(R.id.text_completed1)
    TextView text_completed1;
    @BindView(R.id.text_completed2)
    TextView text_completed2;


    @BindView(R.id.layout_content_main)
    LinearLayout layout_content_main;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.parent_layout_condition)
    RelativeLayout parent_layout_condition;
    @BindView(R.id.layout_option)
    RelativeLayout layout_option;
    @BindView(R.id.payment_option)
    RelativeLayout payment_option;
    @BindView(R.id.bottom_button)
    RelativeLayout bottom_button;
    @BindView(R.id.bottom_button_text)
    TextView bottom_button_text;

    @BindView(R.id.layout_credit_card)
    RelativeLayout layout_credit_card;
    @BindView(R.id.layout_account_transfer)
    RelativeLayout layout_account_transfer;
    @BindView(R.id.credit_card)
    TextView credit_card;
    @BindView(R.id.account_transfer)
    TextView account_transfer;


    @BindView(R.id.status1)
    TextView status1;
    @BindView(R.id.text_status1)
    TextView text_status1;
    @BindView(R.id.status2)
    TextView status2;
    @BindView(R.id.text_status2)
    TextView text_status2;
    @BindView(R.id.status3)
    TextView status3;
    @BindView(R.id.text_status3)
    TextView text_status3;


    @BindView(R.id.center_name)
    TextView center_name;
    @BindView(R.id.pt_name)
    TextView pt_name;
    @BindView(R.id.expiry)
    TextView expiry;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.layout_time_picker1)
    RelativeLayout layout_time_picker1;
    @BindView(R.id.layout_time_picker2)
    RelativeLayout layout_time_picker2;
    @BindView(R.id.start_time)
    TextView start_time;
    @BindView(R.id.end_time)
    TextView end_time;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;

    @BindView(R.id.layout_product_info)
    RelativeLayout layout_product_info;
    @BindView(R.id.arrow_product)
    ImageView arrow_product;
    @BindView(R.id.exp_product)
    ExpandableRelativeLayout exp_product;

    @BindView(R.id.layout_condition)
    RelativeLayout layout_condition;
    @BindView(R.id.arrow_condition)
    ImageView arrow_condition;
    @BindView(R.id.exp_condition)
    ExpandableRelativeLayout exp_condition;

    @BindView(R.id.layout_health_info)
    RelativeLayout layout_health_info;
    @BindView(R.id.arrow_health_info)
    ImageView arrow_health_info;
    @BindView(R.id.exp_health)
    ExpandableRelativeLayout exp_health;

    @BindView(R.id.trainer_name)
    TextView trainer_name;
    @BindView(R.id.trainer_image)
    ImageView trainer_image;
    @BindView(R.id.day_of_week)
    TextView day_of_week;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.make_public)
    TextView make_public;
    @BindView(R.id.not_selected_text)
    TextView not_selected_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alliance_purchase_pt);
        ButterKnife.bind(this);
        mContext = this;
        res = getResources();

        Intent intent = getIntent();
        centerId = intent.getIntExtra("centerId", 0);
        ptId = intent.getIntExtra("ptId", 0);
        int mExpiry = intent.getIntExtra("expiry", 0);
        int mMoney = intent.getIntExtra("money", 0);
        int saleMoney = intent.getIntExtra("saleMoney", 0);
        String centerName = intent.getStringExtra("centerName");
        ptName = intent.getStringExtra("ptName");


        status1.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_bg_ov);
        status1.setTextColor(Color.parseColor("#ffffff"));
        text_status1.setTextColor(Color.parseColor("#606060"));


        center_name.setText(centerName);
        pt_name.setText(ptName);
        expiry.setText(String.format(res.getString(R.string.expiry_text), String.valueOf(mExpiry)));
        money.setText(Util.getCurrency(mMoney));
        amount.setText(Util.getCurrency(mMoney));

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(res.getColor(R.color.fitness_blue));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        trainerListAdapter = new PurchasePTtrainerListAdapter(this);
        recyclerView.setAdapter(trainerListAdapter);

        RestfulAdapter.setOlleegoInterface(null);
        Call<AllianceTrainersModel> call = RestfulAdapter.getInstance().getTrainersList(centerId);
        call.enqueue(new Callback<AllianceTrainersModel>() {
            @Override
            public void onResponse(Call<AllianceTrainersModel> call, retrofit2.Response<AllianceTrainersModel> response) {
                Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    trainerListAdapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<AllianceTrainersModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });

        List<String> dayOfWeekList = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.day_of_week_array)));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        dayOfWeekAdapter = new DayOfWeekAdapter(this);
        recyclerView2.setAdapter(dayOfWeekAdapter);
        dayOfWeekAdapter.setData(dayOfWeekList);
    }

    @Override
    public void onBackPressed() {
        if (pageIndex == 0)
            super.onBackPressed();
        else if (pageIndex == 1) {
            pageIndex = 0;

            //상단 결제 진행 상태 변경 status = 1
            status1.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_bg_ov);
            status1.setTextColor(Color.parseColor("#ffffff"));
            text_status1.setTextColor(Color.parseColor("#606060"));

            status2.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_bg);
            status2.setTextColor(Color.parseColor("#cbcbc9"));
            text_status2.setTextColor(Color.parseColor("#cbcbc9"));

            bottom_button_text.setText(res.getString(R.string.purchase_next_page));
            layout_option.setVisibility(View.VISIBLE);
            parent_layout_condition.setVisibility(View.GONE);
            payment_option.setVisibility(View.GONE);
        } else {
            pageIndex = 1;
            //상단 결제 진행 상태 변경 status = 2
            status3.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_bg);
            status3.setTextColor(Color.parseColor("#cbcbc9"));
            text_status3.setTextColor(Color.parseColor("#cbcbc9"));

            status2.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_bg_ov);
            status2.setTextColor(Color.parseColor("#ffffff"));
            text_status2.setTextColor(Color.parseColor("#606060"));

            bottom_button_text.setText(res.getString(R.string.payment));
            layout_content_main.setVisibility(View.VISIBLE);
            payment_option.setVisibility(View.VISIBLE);
            layout_pay_completed.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.layout_credit_card, R.id.layout_account_transfer})
    public void onClickPaymentOption(View v) {
        switch (v.getId()) {
            case R.id.layout_credit_card:
                paymentOption = "card";
                account_transfer.setTextColor(Color.parseColor("#cbcbc9"));
                layout_account_transfer.setBackgroundResource(R.drawable.alliance_review_text_border);

                credit_card.setTextColor(Color.parseColor("#ffffff"));
                layout_credit_card.setBackgroundColor(Color.parseColor("#5e7efc"));
                break;
            case R.id.layout_account_transfer:
                paymentOption = "trans";
                credit_card.setTextColor(Color.parseColor("#cbcbc9"));
                layout_credit_card.setBackgroundResource(R.drawable.alliance_review_text_border);

                account_transfer.setTextColor(Color.parseColor("#ffffff"));
                layout_account_transfer.setBackgroundColor(Color.parseColor("#5e7efc"));
                break;
        }
    }

    @OnClick({R.id.bottom_button})
    public void onClickButton(View v) {
        if (pageIndex == 0) {
            pageIndex = 1;

            //상단 결제 진행 상태 변경
            status1.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_bg);
            status1.setTextColor(Color.parseColor("#cbcbc9"));
            text_status1.setTextColor(Color.parseColor("#cbcbc9"));

            status2.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_bg_ov);
            status2.setTextColor(Color.parseColor("#ffffff"));
            text_status2.setTextColor(Color.parseColor("#606060"));

            bottom_button_text.setText(res.getString(R.string.payment));
            layout_option.setVisibility(View.GONE);
            parent_layout_condition.setVisibility(View.VISIBLE);
            payment_option.setVisibility(View.VISIBLE);


            //희망조건 정보
            if (trainerId > 0) {
                not_selected_text.setVisibility(View.GONE);
                trainer_name.setText(trainerName);
                if (trainerAvatar != null && !TextUtils.isEmpty(trainerAvatar)) {
                    Glide.with(this)
                            .load(trainerAvatar)
                            .bitmapTransform(new CropCircleTransformation(this))
                            .centerCrop()
                            .into(trainer_image);
                }
            } else {
                not_selected_text.setVisibility(View.VISIBLE);
            }

            if (sparseBooleanArray != null) {
                selectedDayList = new ArrayList<>();
                StringBuilder stringBuilder = new StringBuilder();
                String[] arr = res.getStringArray(R.array.day_of_week_array);
                int cnt = 0;

                for (int i = 0; i < arr.length; i++) {
                    if (sparseBooleanArray.get(i)) {
                        selectedDayList.add(arr[i]);
                        if (cnt == 0)
                            stringBuilder.append(arr[i]);
                        else
                            stringBuilder.append("," + arr[i]);
                        cnt++;
                    }
                }

                day_of_week.setText(stringBuilder.toString());
                if (TextUtils.isEmpty(stringBuilder.toString()))
                    day_of_week.setText(res.getString(R.string.not_selected));

            }

            int radioButtonId = radioGroup.getCheckedRadioButtonId();
            if (radioButtonId != -1) {
                if (radioButtonId == R.id.option1) {
                    inbodyPublic = true;
                    make_public.setText(R.string.make_public);
                    make_public.setTextColor(Color.parseColor("#5e7efc"));
                } else {
                    inbodyPublic = false;
                    make_public.setText(R.string.close);
                    make_public.setTextColor(Color.parseColor("#ff5b53"));
                }
            }

            String start = start_time.getText().toString();
            String end = end_time.getText().toString();
            if (!TextUtils.isEmpty(start) || !TextUtils.isEmpty(end)) {
                time.setVisibility(View.VISIBLE);
                time.setText(start + " ~ " + end);
            }

        } else if (pageIndex == 1) { //결제하기

            AlliancePurchaseInfoBody body = new AlliancePurchaseInfoBody();
            body.setCenter(centerId);
            body.setPt(ptId);
            body.setInbody_public(inbodyPublic);
            if (selectedDayList != null && selectedDayList.size() > 0)
                body.getHope().setDay(selectedDayList);
            body.getHope().setStart(start);
            body.getHope().setEnd(end);
            body.getHope().setTrainer(trainerId);

            final String token = "Olleego " + Util.getPreferencesString(this, "login_token");
            RestfulAdapter.setOlleegoInterface(null);
            Call<AlliancePurchaseInfoResponse> call = RestfulAdapter.getInstance().sendPurchaseInfo(token, body);
            call.enqueue(new Callback<AlliancePurchaseInfoResponse>() {
                @Override
                public void onResponse(Call<AlliancePurchaseInfoResponse> call, retrofit2.Response<AlliancePurchaseInfoResponse> response) {
                    Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        AlliancePurchaseInfoResponse.Result resp = response.body().getResult();

                        merchant_uid = resp.getMerchant_uid();
                        purchaseId = resp.get_id();

                        AlliancePurchaseIamportRequestBody requestBody = new AlliancePurchaseIamportRequestBody();
                        requestBody.setAmount(resp.getAmount());
                        requestBody.setMerchant_uid(resp.getMerchant_uid());
                        requestBody.setPay_method(paymentOption); //결제방법
                        requestBody.setPurchases_id(purchaseId); //구매id
                        requestBody.setName(resp.getPt().getTitle()); //pt권 이름

                        //임시 결제 테스트.. 하드값.. 로그인 정보로 변경 필요.
                        requestBody.setBuyer_name("이름");
                        requestBody.setBuyer_tel("02-123-4567");
                        String email = Util.getPreferencesString(mContext, "login_email");
                        requestBody.setBuyer_email(email);

                        RestfulAdapter.setOlleegoInterface(null);
                        Call<ResponseBody> call2 = RestfulAdapter.getInstance().iamport(token, requestBody);
                        call2.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                                if (response.isSuccessful()) {
                                    try {
                                        String html = response.body().string();
                                        Log.w(TAG, "response.body().string : " + html);
                                        Intent intent = new Intent(mContext, AllianceIamPortWebViewActivity.class);
                                        intent.putExtra("html", html);
                                        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<AlliancePurchaseInfoResponse> call, Throwable t) {
                    Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                }
            });

        } else // 구매완료 페이지 확인 버튼
            finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w(TAG, "requestCode : " + requestCode);
        Log.w(TAG, "resultCode : " + resultCode);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                pageIndex = 2;

                //상단 결제 진행 상태 변경
                status2.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_bg);
                status2.setTextColor(Color.parseColor("#cbcbc9"));
                text_status2.setTextColor(Color.parseColor("#cbcbc9"));

                status3.setTextColor(Color.parseColor("#ffffff"));
                text_status3.setTextColor(Color.parseColor("#606060"));

                //구매 성공,실패 view
                layout_pay_completed.setVisibility(View.VISIBLE);
                bottom_button_text.setText(res.getString(R.string.dialog_confirm));

                boolean success = data.getBooleanExtra("success", false);
                if (success) { //결제 성공시 서버에 결제 정보를 보냄.
                    status3.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_bg_ov);
                    //구매성공 이미지, 텍스트 변경
                    image_completed.setImageResource(R.drawable.pay_completed);
                    text_completed1.setText(res.getString(R.string.pay_completed));
                    text_completed2.setText(res.getString(R.string.pay_completed_content));
                    payment_option.setVisibility(View.GONE);

                    String imp_uid = data.getStringExtra("imp_uid");

                    String token = "Olleego " + Util.getPreferencesString(this, "login_token");
                    AlliancePurchasePaymentsBody body = new AlliancePurchasePaymentsBody();
                    body.setMerchant_uid(merchant_uid);
                    body.setImp_uid(imp_uid);
                    body.setPurchases_id(purchaseId);

                    RestfulAdapter.setOlleegoInterface(null);
                    Call<ResponseBody> call = RestfulAdapter.getInstance().payments(token, body);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                            Log.d(TAG+"_payments_resp", "isSuccessful : " + response.isSuccessful());
                            if (response.isSuccessful()) {
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                        }
                    });
                } else { //결제 실패
                    status3.setBackgroundResource(R.drawable.alliance_purchase_pt_status_number_failed_bg);

                    //구매실패 이미지, 텍스트 변경
                    image_completed.setImageResource(R.drawable.pay_cancel);
                    text_completed1.setText(res.getString(R.string.pay_failed));
                    text_completed2.setText(res.getString(R.string.pay_failed_content));

                    layout_content_main.setVisibility(View.GONE);
                }
            }
        }
    }

    @OnClick({R.id.layout_time_picker1, R.id.layout_time_picker2})
    public void onClickTime(View v) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat mHour = new SimpleDateFormat("HH");
        String hour = mHour.format(date);
        SimpleDateFormat mMinute = new SimpleDateFormat("mm");
        String min = mMinute.format(date);

        TimePickerDialog dialog = null;
        switch (v.getId()) {
            case R.id.layout_time_picker1:
                dialog = new TimePickerDialog(this, listener1, Integer.parseInt(hour), Integer.parseInt(min), true);
                break;
            case R.id.layout_time_picker2:
                dialog = new TimePickerDialog(this, listener2, Integer.parseInt(hour), Integer.parseInt(min), true);
                break;
        }
        dialog.show();
    }

    private TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            start = hourOfDay + ":" + minute;
            start_time.setText(start);
        }
    };
    private TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            end = hourOfDay + ":" + minute;
            end_time.setText(end);
        }
    };

    @OnClick({R.id.layout_product_info, R.id.layout_condition, R.id.layout_health_info})
    public void onClickItem(View v) {
        ExpandableRelativeLayout expRelativeLayout = null;
        ImageView imageView = null;

        switch (v.getId()) {
            case R.id.layout_product_info:
                imageView = arrow_product;
                expRelativeLayout = exp_product;
                break;
            case R.id.layout_condition:
                imageView = arrow_condition;
                expRelativeLayout = exp_condition;
                break;
            case R.id.layout_health_info:
                imageView = arrow_health_info;
                expRelativeLayout = exp_health;
                break;
        }

        startRotate(expRelativeLayout, imageView);
    }

    void startRotate(ExpandableRelativeLayout layout, ImageView imageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (layout.isExpanded()) { // rotate clockwise
                layout.collapse();
                imageView.setRotation(ROTATED_POSITION);
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                layout.expand();
                imageView.setRotation(INITIAL_POSITION);
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }
            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            imageView.startAnimation(rotateAnimation);
        }
    }

    @Override
    public void setTrainerInfo(int trainerId, String name, String avatar) {
        this.trainerId = trainerId;
        this.trainerAvatar = avatar;
        this.trainerName = name;
    }

    @Override
    public void setSelectedItem(SparseBooleanArray array) {
        this.sparseBooleanArray = array;
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
