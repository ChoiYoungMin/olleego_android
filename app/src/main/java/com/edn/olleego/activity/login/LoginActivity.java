package com.edn.olleego.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.edn.olleego.R;
import com.edn.olleego.activity.MainActivity;
import com.edn.olleego.activity.signup.SignupActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *  2016-06-15 최영민
     * 이메일로 로그인 버튼을 누르면 이메일 로그인 화면으로 보내요.
     */

    @OnClick(R.id.email_login)
    void email_login_click() {
        Intent intent = new Intent(LoginActivity.this, EmailActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.email_signup)
    void email_signup_click() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }



}
