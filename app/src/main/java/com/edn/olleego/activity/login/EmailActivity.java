package com.edn.olleego.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.UserModel;
import com.edn.olleego.server.UserAPI;
import com.edn.olleego.server.request.Login;
import com.edn.olleego.model.LoginModel;
import com.edn.olleego.server.LoginAPI;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmailActivity extends Activity {

    @BindView(R.id.login_email)
    EditText login_email;

    @BindView(R.id.login_password)
    EditText login_password;


    Retrofit retrofit;

    private static OkHttpClient client;


    SharedPreferences olleego_SP;

    LoginModel loginModel;

    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        ButterKnife.bind(this);


    }


    @OnClick(R.id.email_exit)
    void email_exit_click() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.password_help)
    void password_help_click() {
        Intent intent = new Intent(getApplicationContext(), PasswordHelpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.login_ok)
    void login_ok() {

        if(login_email.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if(login_password.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else {


            retrofit = new Retrofit.Builder()
                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            LoginAPI loginAPI = retrofit.create(LoginAPI.class);
            Login login = new Login(login_email.getText().toString(), login_password.getText().toString());
            final Call<LoginModel> repos = loginAPI.listRepos(login);

            repos.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                    if(response.isSuccessful()) {

                        loginModel = response.body();

                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();


                        retrofit = new Retrofit.Builder()
                                .baseUrl(ServerInfo.OLLEEGO_HOST)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        UserAPI userAPI = retrofit.create(UserAPI.class);
                        String token = "olleego " + loginModel.getToken();
                        final Call<UserModel> repos2 = userAPI.listRepos(token);

                        repos2.enqueue(new Callback<UserModel>() {
                            @Override
                            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                UserModel userModel = response.body();

                                int last = userModel.getResult().getIn_body().size();



                                olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);
                                SharedPreferences.Editor editor = olleego_SP.edit();
                                editor.putString("login_chk", "true");
                                editor.putString("login_email", login_email.getText().toString());
                                editor.putString("login_name", userModel.getResult().getName());
                                editor.putString("login_gender", userModel.getResult().getGender());

                                SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                                String nowDate =  dateFormat.format(userModel.getResult().getBirthday());


                                editor.putString("login_birthday", nowDate);
                                editor.putString("login_img", userModel.getResult().getAvatar());

                                editor.putString("login_token", loginModel.getToken());
                                editor.putString("user_id" , String.valueOf(userModel.getResult().get_id()));

                                try {
                                    editor.putFloat("user_bmi", (float) userModel.getResult().getIn_body().get(last - 1).getBmi());
                                    editor.putInt("user_health_temp", (int) userModel.getResult().getIn_body().get(last - 1).getHealth_temp());
                                }
                                catch (ArrayIndexOutOfBoundsException e ){
                                    editor.putFloat("user_bmi", 0);
                                    editor.putInt("user_health_temp", 0);
                                }
                                editor.commit();


                                Intent intent = new Intent(EmailActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<UserModel> call, Throwable t) {

                            }
                        });

















                    } else {
                        Converter<ResponseBody, LoginModel> converter = retrofit.responseBodyConverter(LoginModel.class, new Annotation[0]);

                        LoginModel error = null;

                        try {
                            error = converter.convert(response.errorBody());
                        } catch (IOException e) {
                            Log.e("error", e.getLocalizedMessage());
                        }

                        // 아이디와 비밀번호가 틀릴경우..
                        if(error.getEmail() == false && error.getPw() == false || error.getEmail() == true && error.getPw() == false ) {
                            Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }


                    }
                }


                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {

                }
            });
        }
    }


}
