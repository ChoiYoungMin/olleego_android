package com.edn.olleego.fragment.mycenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mycenter.MyReviewListAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.mycenter.MyReviewListModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by pc on 2016-08-21.
 */
public class MyReviewFragment extends Fragment implements MyReviewListAdapter.MyReviewAdapterListener {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private MyReviewListAdapter adapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view_layout)
    RelativeLayout emptyView;

    public MyReviewFragment() {
    }

    public static MyReviewFragment newInstance() {
        final MyReviewFragment f = new MyReviewFragment();
        final Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        res = mContext.getResources();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_review, container, false);
        ButterKnife.bind(this, v);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyReviewListAdapter(mContext, this);
        recyclerView.setAdapter(adapter);

        String token = "Olleego " + Util.getPreferencesString(mContext, "login_token");

        RestfulAdapter.setOlleegoInterface(null);
        Call<MyReviewListModel> call = RestfulAdapter.getInstance().getMyReviewList(token);
        call.enqueue(new Callback<MyReviewListModel>() {
            @Override
            public void onResponse(Call<MyReviewListModel> call, final retrofit2.Response<MyReviewListModel> response) {
                boolean isSuccessful = response.isSuccessful();
                Log.d(TAG, "isSuccessful : " + isSuccessful);
                if (isSuccessful) {
                    adapter.setData(response.body().getResult());
                }
                updateView(isSuccessful);
            }

            @Override
            public void onFailure(Call<MyReviewListModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
        return v;
    }

    @Override
    public void setEmptyView() {
        updateView(false);
    }

    void updateView(boolean isSuccessful) {
        if (isSuccessful) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
