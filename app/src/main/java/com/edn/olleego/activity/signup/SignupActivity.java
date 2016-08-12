package com.edn.olleego.activity.signup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.activity.login.LoginActivity;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.fragment.Policy_Fragment;
import com.edn.olleego.model.SignupModel;
import com.edn.olleego.model.UserModel;
import com.edn.olleego.server.SignupAPI;
import com.edn.olleego.server.UserAPI;
import com.edn.olleego.server.request.Signup;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class SignupActivity extends FragmentActivity {


    TextView mDateDisplay;
    private int mYear;
    private int mMonth;
    private int mDay;

    @BindView(R.id.email_)
    EditText email;

    @BindView(R.id.name_)
    EditText name;

    @BindView(R.id.password_)
    EditText password;

    @BindView(R.id.password_chk)
    EditText password_chk;

    @BindView(R.id.birtday_)
    TextView birtday;

    @BindView(R.id.birtday_btn)
    ImageView birtday_btn;

    @BindView(R.id.sex_m)
    ImageView sex_m;

    @BindView(R.id.sex_w)
    ImageView sex_w;

    SharedPreferences olleego_SP;


    boolean check_m, check_w;

    String gender;
    String birtday_r;
    Date date;
    static final int DATE_DIALOG_ID = 0;

    TextInputLayout test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        Init();
    }

    public void Init() {
        check_m = false;
        check_w = false;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();
    }


    @OnClick(R.id.birtday_btn)
    void Birtday_btn_click() {
        showDialog(DATE_DIALOG_ID);
    }


    @OnClick(R.id.sex_m)
    void sexm_btn_click() {
        if(!check_m) {
            sex_m.setImageResource(R.drawable.m_btn_ok);
            check_m = true;
            gender = "M";
            if(check_w) {sex_w.setImageResource(R.drawable.f_btn_default); check_w = false; gender = "";}
        } else if (check_m) {
            sex_m.setImageResource(R.drawable.m_btn_default);
            check_m = false;
            gender = "";
        }
    }

    @OnClick(R.id.sex_w)
    void sexw_btn_click() {
        if(!check_w){
            sex_w.setImageResource(R.drawable.f_btn_ok); check_w = true; gender = "W";
            if(check_m) {
                sex_m.setImageResource(R.drawable.m_btn_default);
                check_m = false;
                gender = "";
            }
        }
        else if (check_w) {sex_w.setImageResource(R.drawable.f_btn_default); check_w = false; gender = "";}
    }


    @OnClick(R.id.join_ok)
    void join_ok_click() {
        if(email.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "[이메일] 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if(name.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "[이름] 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "[비밀번호] 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().length() > 1 && password.getText().length() < 8) {
            Toast.makeText(getApplicationContext(), "[비밀번호] 8자 이상 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if(password_chk.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "[비밀번호 확인] 입력하세요.", Toast.LENGTH_SHORT).show();
        }
        else if(!password.getText().toString().equals(password_chk.getText().toString())) {
            Toast.makeText(getApplicationContext(), "비밀번호가 서로 일치하지않습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(check_m == false && check_w == false || check_m == true && check_w == true) {
            Toast.makeText(getApplicationContext(), "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            if(checkEmail(email.getText().toString())) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                CookieManager cookieManager = new CookieManager();
                cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

                final OkHttpClient client = configureClient(new OkHttpClient().newBuilder()) //인증서 무시
                        .connectTimeout(15, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                        .writeTimeout(15, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                        .readTimeout(15, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                        .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                        .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                        .build();


                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ServerInfo.OLLEEGO_HOST)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SignupAPI signupAPI = retrofit.create(SignupAPI.class);
                Signup signup = new Signup(name.getText().toString(),email.getText().toString(),birtday_r,gender,password.getText().toString());

                final Call<SignupModel> repos = signupAPI.listRepos(signup);

                repos.enqueue(new Callback<SignupModel>() {
                    @Override
                    public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {

                        if(response.isSuccessful()) {
                            final SignupModel forgot_model_ = response.body();

                            //프렌트먼트로 토큰값 저장해두자
                            // forgot_model_.getToken()

                            Toast.makeText(getApplicationContext(), "회원가입 완료!", Toast.LENGTH_SHORT).show();

                            Retrofit retrofit22 = new Retrofit.Builder()
                                    .baseUrl(ServerInfo.OLLEEGO_HOST)
                                    .client(client)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();


                            UserAPI userAPI = retrofit22.create(UserAPI.class);
                            String token = "olleego " + forgot_model_.getToken();
                            final Call<UserModel> repos2 = userAPI.listRepos(token);

                            repos2.enqueue(new Callback<UserModel>() {
                                @Override
                                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                    UserModel userModel = response.body();

                                    int last = userModel.getResult().getIn_body().size();



                                    olleego_SP = getSharedPreferences("olleego", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = olleego_SP.edit();
                                    editor.putString("login_chk", "true");
                                    editor.putString("login_email", email.getText().toString());
                                    editor.putString("login_token", forgot_model_.getToken());
                                    editor.putString("user_id" , String.valueOf(userModel.getResult().get_id()));
                                    editor.putString("login_name", userModel.getResult().getName());
                                    editor.putString("login_gender", userModel.getResult().getGender());

                                    SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                                    String nowDate =  dateFormat.format(userModel.getResult().getBirthday());


                                    editor.putString("login_birthday", nowDate);
                                    editor.putString("login_img", userModel.getResult().getAvatar());
                                    try {
                                        editor.putFloat("user_bmi", (float) userModel.getResult().getIn_body().get(last - 1).getBmi());
                                        editor.putInt("user_health_temp", (int) userModel.getResult().getIn_body().get(last - 1).getHealth_temp());
                                    }
                                    catch (ArrayIndexOutOfBoundsException e ){
                                        editor.putFloat("user_bmi", 0);
                                        editor.putInt("user_health_temp", 0);
                                    }
                                    editor.commit();


                                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<UserModel> call, Throwable t) {

                                }
                            });

                        } else {
                            Converter<ResponseBody, SignupModel> converter = retrofit.responseBodyConverter(SignupModel.class, new Annotation[0]);

                            SignupModel error = null;

                            try {
                                error = converter.convert(response.errorBody());
                            } catch (IOException e) {
                                Log.e("error", e.getLocalizedMessage());
                            }

                            // 아이디와 비밀번호가 틀릴경우..
                            if(error.getMessage().length() > 0 ) {
                                Toast.makeText(getApplicationContext(), "중복된 이메일 주소 입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupModel> call, Throwable t) {
                        Log.e("error", t.getLocalizedMessage());
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "이메일 형식을 다시 확인해주세요.", Toast.LENGTH_LONG).show();
            }

        }
    }

    @OnClick(R.id.policy)
    void policy_click() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Policy_Fragment policy_ = new Policy_Fragment();
        transaction.replace(R.id.policy_, policy_);
        transaction.commit();

    }

    @OnClick(R.id.join_exit)
    void join_exit_click() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_DIALOG_ID : return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
        }

        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            // 사용자가 지정한 날짜를 출력
            updateDisplay();

        }
    };

    // 설정된 날짜를 TextView에 출력
    private void updateDisplay() {
        // main.xml의 레이아웃에 배치된 날짜 입력 TextView에 인식된 날짜 출력
        birtday.setText(
                new StringBuilder()
                        //월은 시스템에서 0~11로 인식하기 때문에 1을 더해줌
                        .append(mYear).append("년 ")
                        .append(mMonth+1).append("월")
                        .append(mDay).append("일")
        );

        birtday_r = String.valueOf(new StringBuilder()
                //월은 시스템에서 0~11로 인식하기 때문에 1을 더해줌
                .append(mYear).append("-")
                .append(mMonth+1).append("-")
                .append(mDay).append("")
        );


    }

    public boolean checkEmail(String email){
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;
    }


    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder) {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }
        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };

            builder.sslSocketFactory(ctx.getSocketFactory()).hostnameVerifier(hostnameVerifier);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return builder;
    }


}
