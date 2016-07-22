package com.edn.olleego.activity.mission.ExMission;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.exmission.ExMission_Adapter;

public class ExMissionActivity extends AppCompatActivity {


    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_mission);

        listView = (ListView)findViewById(R.id.exmission_listview);

        ExMission_Adapter exMission_adapter = new ExMission_Adapter(getLayoutInflater());
        listView.setAdapter(exMission_adapter);
        exMission_adapter.add("dd","dd","dd");
        exMission_adapter.add("dd","dd","dd");
        exMission_adapter.add("dd","dd","dd");
        exMission_adapter.add("dd","dd","dd");
        exMission_adapter.add("dd","dd","dd");
        listViewHeightSet(exMission_adapter, listView);


        findViewById(R.id.testbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExMissionActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void listViewHeightSet(Adapter listAdapter, ListView listView)
    {
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
