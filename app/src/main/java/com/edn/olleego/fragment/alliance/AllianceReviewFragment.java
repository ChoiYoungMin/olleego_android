package com.edn.olleego.fragment.alliance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.activity.alliance.AllianceWriteReviewActivity;
import com.edn.olleego.adapter.alliance.AllianceReviewAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.model.alliance.AllianceIsPurchasedResponse;
import com.edn.olleego.model.alliance.AllianceReviewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016. 8. 7..
 * <p/>
 * 센터 상세 페이지내 리뷰 탭
 */
public class AllianceReviewFragment extends Fragment implements AllianceReviewAdapter.ReviewAdapterListener {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private int id;
    private int index;
    private int totalItemCount;
    private AllianceReviewAdapter reviewAdapter;
    private SharedPreferences olleego_SP;
    private String userId;
    private String token;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.review_count)
    TextView count;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.write_review)
    RelativeLayout writeReview;

    public AllianceReviewFragment() {
    }

    public static AllianceReviewFragment newInstance(int id) {
        final AllianceReviewFragment f = new AllianceReviewFragment();
        final Bundle args = new Bundle();
        args.putInt("id", id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;
        totalItemCount = 0;
        id = getArguments() != null ? getArguments().getInt("id") : 0;
        Log.w(TAG, "id : " + id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        res = mContext.getResources();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alliance_review, container, false);
        ButterKnife.bind(this, v);
        olleego_SP = mContext.getSharedPreferences("olleego", Context.MODE_PRIVATE);
        userId = olleego_SP.getString("user_id", "");
        token = "Olleego " + olleego_SP.getString("login_token", "");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        reviewAdapter = new AllianceReviewAdapter(mContext, this, userId, token);
        recyclerView.setAdapter(reviewAdapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.write_review})
    public void onClickWriteReview() {
        String isLogin = olleego_SP.getString("login_chk", "");
        if (isLogin.equals("true")) {
            //센터 구매여부 체크
            RestfulAdapter.setOlleegoInterface(null);
            Call<AllianceIsPurchasedResponse> call = RestfulAdapter.getInstance().isPurchase(token, id);
            call.enqueue(new Callback<AllianceIsPurchasedResponse>() {
                @Override
                public void onResponse(Call<AllianceIsPurchasedResponse> call, retrofit2.Response<AllianceIsPurchasedResponse> response) {
                    Log.d(TAG + "_onResponse", "isSuccessful : " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        boolean isPurchase = response.body().getResult().isPurchase();
                        Log.d(TAG, "isPurchase : " + isPurchase);
                        if (isPurchase) { //구매한 사용자 -> 리뷰 작성 가능
                            Intent intent = new Intent(mContext, AllianceWriteReviewActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("isEdit", false);
                            mContext.startActivity(intent);
                        } else {
                            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                            View dialogView = LayoutInflater.from(mContext).inflate(R.layout.review_custom_dialog, null);
                            LinearLayout linearLayout = (LinearLayout) dialogView.findViewById(R.id.layout_two_btn);
                            LinearLayout layout_confirm = (LinearLayout) dialogView.findViewById(R.id.layout_confirm);
                            TextView message = (TextView) dialogView.findViewById(R.id.text_message);

                            linearLayout.setVisibility(View.GONE);
                            layout_confirm.setVisibility(View.VISIBLE);
                            message.setText(res.getString(R.string.dialog_write_review_warning_msg));

                            dialogBuilder.setView(dialogView);

                            final AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.show();

                            layout_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) { //확인
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<AllianceIsPurchasedResponse> call, Throwable t) {
                    Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                }
            });
        } else {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            View dialogView = LayoutInflater.from(mContext).inflate(R.layout.review_custom_dialog, null);
            RelativeLayout cancel = (RelativeLayout) dialogView.findViewById(R.id.layout_cancel);
            RelativeLayout confirm = (RelativeLayout) dialogView.findViewById(R.id.layout_ok);
            TextView message = (TextView) dialogView.findViewById(R.id.text_message);
            TextView textLogin = (TextView) dialogView.findViewById(R.id.text_ok);
            message.setText(res.getString(R.string.dialog_login_msg));
            textLogin.setText(res.getString(R.string.dialog_login));
            dialogBuilder.setView(dialogView);

            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //로그인 로직 태우기..
                    alertDialog.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onClickMore() { //후기 더보기
        Log.d(TAG, "onClickMore");
        index += 20;
        Log.d(TAG, "index : " + index);
        setData(index);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData(index);
    }

    void setData(final int index) {
        RestfulAdapter.setOlleegoInterface(null);
        Call<AllianceReviewModel> call = RestfulAdapter.getInstance().getReviewList(id, index);
        call.enqueue(new Callback<AllianceReviewModel>() {
            @Override
            public void onResponse(Call<AllianceReviewModel> call, retrofit2.Response<AllianceReviewModel> response) {
                Log.d(TAG + "_onResponse", "isSuccessful : " + response.isSuccessful());
                if (response.isSuccessful()) {

                    AllianceReviewModel model = response.body();
                    if (index == 0) {
                        model.getResult().add(model.getResult().size(), null);
                        reviewAdapter.setData(model);
                    } else
                        reviewAdapter.addData(model);

                    totalItemCount += model.getResult().size();
                    count.setText(String.valueOf(totalItemCount - 1)); //footer view 제외
                }
                updateView(response.isSuccessful(), totalItemCount);
            }

            @Override
            public void onFailure(Call<AllianceReviewModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
    }

    void updateView(boolean isSuccessful, int count) {
        if (isSuccessful || count > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setEmptyView() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItemCount(int itemCount) {
        count.setText(String.valueOf(itemCount));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
