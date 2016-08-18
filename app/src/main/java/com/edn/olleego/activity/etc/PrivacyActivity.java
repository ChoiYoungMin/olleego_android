package com.edn.olleego.activity.etc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.edn.olleego.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("개인정보 취급방침");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this) 를 사용할 경우 모든 Activity를 Destroy 시키고 부모 Activity로 돌아간다.
                // NavUtils.navigateUpFromSameTask(this);
                // finish() 를 사용할 경우 현재 Activity를 Destroy하고 부모 Activity로 돌아간다.
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
