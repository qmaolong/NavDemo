package com.amap.map2d.demo.tools;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMarkerDragListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.map2d.demo.R;

public class CalculateDistanceActivity extends Activity implements OnMarkerDragListener {
	private AMap aMap;
	private MapView mapView;
	private LatLng latlngA = new LatLng(39.926516, 116.389366);
	private LatLng latlngB = new LatLng(39.924870, 116.403270);
	private Marker makerA;
	private Marker makerB;
	private TextView Text;
	private float distance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arc_activity);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
		distance = AMapUtils.calculateLineDistance(makerA.getPosition(), makerB.getPosition());
		Text.setText("长按Marker可拖动\n两点间距离为："+distance+"m");
	}
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		Text = (TextView) findViewById(R.id.info_text);
	}
	private void setUpMap() {
		aMap.setOnMarkerDragListener(this);
		makerA = aMap.addMarker(new MarkerOptions().position(latlngA)
				.draggable(true)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
		makerB = aMap.addMarker(new MarkerOptions().position(latlngB)
				.draggable(true)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.925516,
				116.395366), 15));
//		
	}

	@Override
	public void onMarkerDrag(Marker marker) {

		distance = AMapUtils.calculateLineDistance(makerA.getPosition(), makerB.getPosition());
		Text.setText("长按Marker可拖动\n两点间距离为："+distance+"m");
		
	}
	@Override
	public void onMarkerDragEnd(Marker arg0) {
		
	}
	@Override
	public void onMarkerDragStart(Marker arg0) {
		
	}


	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

}
