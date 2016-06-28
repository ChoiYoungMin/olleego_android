package com.edn.olleego.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class OlleegoGymActivity extends AppCompatActivity {

    static final LatLng SEOUL = new LatLng( 37.490735, 126.9239126);

    static final LatLng SEOUL2 = new LatLng( 37.492735, 126.9232226);
    private GoogleMap map;

    public String s;

    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olleego_gym);

        tv = (TextView)findViewById(R.id.gym_type);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.addMarker(new MarkerOptions().position(SEOUL).title("새마을 휘트니스"));
        map.addMarker(new MarkerOptions().position(SEOUL2).title("내맘 휘트니스"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom( SEOUL, 1));
        map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

    }


}
