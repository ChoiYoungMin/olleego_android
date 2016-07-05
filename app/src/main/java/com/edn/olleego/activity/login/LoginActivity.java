package com.edn.olleego.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.edn.olleego.R;
import com.edn.olleego.activity.Main3Activity;
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


    /**
     *  2016-06-15 최영민
     * 이메일로 로그인 버튼을 누르면 이메일 로그인 화면으로 보내요.
     */

    @OnClick(R.id.email_login)
    void email_login_click() {
        Intent intent = new Intent(LoginActivity.this, EmailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.email_signup)
    void email_signup_click() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.free)
    void free_click() {
        //Intent intent = new Intent(LoginActivity.this, DiaryActivity.class); 다이어리 기능 구현
        //Intent intent = new Intent(LoginActivity.this, MainActivity.class); 메인부분

        //Intent intent = new Intent(LoginActivity.this, Main2Activity.class); 트래커
        Intent intent = new Intent(LoginActivity.this, Main3Activity.class);
        startActivity(intent);
    }

}
