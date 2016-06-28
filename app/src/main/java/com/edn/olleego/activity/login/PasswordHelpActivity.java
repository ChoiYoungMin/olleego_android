package com.edn.olleego.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.ForgotModel;
import com.edn.olleego.server.ForgotAPI;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PasswordHelpActivity extends Activity {

    @BindView(R.id.help_email)
    EditText help_email;


    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_help);
    }


    @OnClick(R.id.help_exit)
    void exitbtn_click() {

        if (help_email.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();

        }
        else {

            retrofit = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ForgotAPI forgotAPI = retrofit.create(ForgotAPI.class);
            final Call<ForgotModel> repos = forgotAPI.listRepos(help_email.getText(), true);

            repos.enqueue(new Callback<ForgotModel>() {
                @Override
                public void onResponse(Call<ForgotModel> call, Response<ForgotModel> response) {
                    ForgotModel forgotModel = response.body();

                    if(forgotModel.getSuccess().equals("true")) {
                        //로딩 페이지 필요함 ! ^_^
                        Toast.makeText(getApplicationContext(), "입력하신 이메일로 전송했습니다.", Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(getApplicationContext(), "입력하신 이메일이 정확하지않습니다..", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ForgotModel> call, Throwable t) {

                }
            });
        }
    }
}
