package com.amap.map2d.demo.car;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.map2d.demo.R;
import com.amap.map2d.demo.lib.LocationTask;
import com.amap.map2d.demo.lib.RegeocodeTask;
import com.amap.map2d.demo.lib.Utils;
import com.amap.map2d.demo.util.ToastUtil;

/**
 * UI settings一些选项设置响应事件
 */
public class ShareCarDemoActivity extends Activity implements
        OnCheckedChangeListener, OnClickListener, LocationSource,
        AMapLocationListener,AMap.OnMapLoadedListener,  AMap.OnMapClickListener,
        AMap.OnMapLongClickListener, AMap.OnCameraChangeListener {
    private AMap aMap;
    private MapView mapView;
    private UiSettings mUiSettings;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private TextView currentLocationView;
    private Marker mPositionMark;
    private RegeocodeTask mRegeocodeTask;
    private LocationTask mLocationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_car_demo);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        currentLocationView = (TextView) findViewById(R.id.current_location);
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setMyLocationButtonEnabled(true);

            aMap.setOnMapLoadedListener(this);
            aMap.setLocationSource(this);// 设置定位监听
            mUiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
            aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层

            setUpMap();
        }
        Button buttonScale = (Button) findViewById(R.id.buttonScale);
        buttonScale.setOnClickListener(this);
        CheckBox scaleToggle = (CheckBox) findViewById(R.id.scale_toggle);
        scaleToggle.setOnClickListener(this);
        CheckBox zoomToggle = (CheckBox) findViewById(R.id.zoom_toggle);
        zoomToggle.setOnClickListener(this);
        CheckBox compassToggle = (CheckBox) findViewById(R.id.compass_toggle);
        compassToggle.setOnClickListener(this);
        CheckBox mylocationToggle = (CheckBox) findViewById(R.id.mylocation_toggle);
        mylocationToggle.setOnClickListener(this);
        CheckBox scrollToggle = (CheckBox) findViewById(R.id.scroll_toggle);
        scrollToggle.setOnClickListener(this);
        CheckBox zoom_gesturesToggle = (CheckBox) findViewById(R.id.zoom_gestures_toggle);
        zoom_gesturesToggle.setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.logo_position);
        radioGroup.setOnCheckedChangeListener(this);

    }

    /**
     * amap添加一些事件监听器
     */
    private void setUpMap() {
        aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
        aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
        aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
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
        deactivate();
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

    /**
     * 设置logo位置，左下，底部居中，右下
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (aMap != null) {
            if (checkedId == R.id.bottom_left) {
                mUiSettings
                        .setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);// 设置地图logo显示在左下方
            } else if (checkedId == R.id.bottom_center) {
                mUiSettings
                        .setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);// 设置地图logo显示在底部居中
            } else if (checkedId == R.id.bottom_right) {
                mUiSettings
                        .setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);// 设置地图logo显示在右下方
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * 一像素代表多少米
             */
            case R.id.buttonScale:
                float scale = aMap.getScalePerPixel();
                ToastUtil.show(ShareCarDemoActivity.this, "每像素代表" + scale + "米");
                break;
            /**
             * 设置地图默认的比例尺是否显示
             */
            case R.id.scale_toggle:
                mUiSettings.setScaleControlsEnabled(((CheckBox) view).isChecked());

                break;
            /**
             * 设置地图默认的缩放按钮是否显示
             */
            case R.id.zoom_toggle:
                mUiSettings.setZoomControlsEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图默认的指南针是否显示
             */
            case R.id.compass_toggle:
                mUiSettings.setCompassEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图默认的定位按钮是否显示
             */
            case R.id.mylocation_toggle:
                aMap.setLocationSource(this);// 设置定位监听
                mUiSettings.setMyLocationButtonEnabled(((CheckBox) view)
                        .isChecked()); // 是否显示默认的定位按钮
                aMap.setMyLocationEnabled(((CheckBox) view).isChecked());// 是否可触发定位并显示定位层
                break;
            /**
             * 设置地图是否可以手势滑动
             */
            case R.id.scroll_toggle:
                mUiSettings.setScrollGesturesEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图是否可以手势缩放大小
             */
            case R.id.zoom_gestures_toggle:
                mUiSettings.setZoomGesturesEnabled(((CheckBox) view).isChecked());
                break;
            default:
                break;
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//                final Marker marker = aMap.addMarker(new MarkerOptions().position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())).title("当前位置").snippet("DefaultMarker"));
                Utils.addEmulateData(aMap, new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            mLocationOption.setMockEnable(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onMapLoaded() {

        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.setFlat(true);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(0, 0));
        markerOptions
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.drawable.icon_loaction_start)));
        mPositionMark = aMap.addMarker(markerOptions);
        mPositionMark.setPositionByPixels(mapView.getWidth() / 2,
                mapView.getHeight() / 2);
        mLocationTask.startSingleLocate();
    }

    /**
     * 对单击地图事件回调
     */
    @Override
    public void onMapClick(LatLng point) {
        currentLocationView.setText("选中位置：" + "(" + point.latitude + "," + point.longitude + ")");
//        final Marker marker = aMap.addMarker(new MarkerOptions().position(point).title("选中位置").snippet("DefaultMarker"));

    }

    /**
     * 对长按地图事件回调
     */
    @Override
    public void onMapLongClick(LatLng point) {
        currentLocationView.setText("选中位置：" + "(" + point.latitude + "," + point.longitude + ")");
    }

    /**
     * 对正在移动地图事件回调
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
//        currentLocationView.setText("onCameraChange:" + cameraPosition.toString());
    }

    /**
     * 对移动地图结束事件回调
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        /*currentLocationView.setText("onCameraChangeFinish:"
                + cameraPosition.toString());
        VisibleRegion visibleRegion = aMap.getProjection().getVisibleRegion(); // 获取可视区域、
        LatLngBounds latLngBounds = visibleRegion.latLngBounds;// 获取可视区域的Bounds*/
        mlocationClient.stopLocation();
        currentLocationView.setText("中心位置：" + "(" + cameraPosition.target.latitude + "," + cameraPosition.target.longitude + ")");
        Utils.addEmulateData(aMap, new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude));
    }
}
