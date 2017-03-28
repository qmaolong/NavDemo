package com.amap.map2d.demo.basic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.map2d.demo.R;

public class BaseMapFragmentActivity extends FragmentActivity {
	private AMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basemap_fragment_activity);
		setUpMapIfNeeded();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		}
	}

}
