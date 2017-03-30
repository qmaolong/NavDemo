package com.amap.map2d.demo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.amap.api.maps2d.MapsInitializer;
import com.amap.map2d.demo.R;
import com.amap.map2d.demo.car.ShareCarDemoActivity;
import com.amap.map2d.demo.basic.AbroadMapSwitchActivity;
import com.amap.map2d.demo.basic.Animate_CameraActivity;
import com.amap.map2d.demo.basic.BaseMapFragmentActivity;
import com.amap.map2d.demo.basic.BasicMapActivity;
import com.amap.map2d.demo.basic.CameraActivity;
import com.amap.map2d.demo.basic.EventsActivity;
import com.amap.map2d.demo.basic.GestureSettingsActivity;
import com.amap.map2d.demo.basic.LayersActivity;
import com.amap.map2d.demo.basic.LogoSettingsActivity;
import com.amap.map2d.demo.basic.MapOptionActivity;
import com.amap.map2d.demo.basic.OsmMapActivity;
import com.amap.map2d.demo.basic.ScreenShotActivity;
import com.amap.map2d.demo.basic.TwoMapActivity;
import com.amap.map2d.demo.basic.UiSettingsActivity;
import com.amap.map2d.demo.basic.ZoomActivity;
import com.amap.map2d.demo.busline.BusStationActivity;
import com.amap.map2d.demo.busline.BuslineActivity;
import com.amap.map2d.demo.cloud.CloudActivity;
import com.amap.map2d.demo.district.DistrictActivity;
import com.amap.map2d.demo.district.DistrictWithBoundaryActivity;
import com.amap.map2d.demo.geocoder.GeocoderActivity;
import com.amap.map2d.demo.geocoder.ReGeocoderActivity;
import com.amap.map2d.demo.inputtip.InputtipsActivity;
import com.amap.map2d.demo.location.CustomLocationActivity;
import com.amap.map2d.demo.location.LocationMarkerActivity;
import com.amap.map2d.demo.location.LocationSourceActivity;
import com.amap.map2d.demo.overlay.CircleActivity;
import com.amap.map2d.demo.overlay.CustomMarkerActivity;
import com.amap.map2d.demo.overlay.GroundOverlayActivity;
import com.amap.map2d.demo.overlay.InfoWindowActivity;
import com.amap.map2d.demo.overlay.MarkerActivity;
import com.amap.map2d.demo.overlay.MarkerClickActivity;
import com.amap.map2d.demo.overlay.PolygonActivity;
import com.amap.map2d.demo.overlay.PolylineActivity;
import com.amap.map2d.demo.poisearch.PoiAroundSearchActivity;
import com.amap.map2d.demo.poisearch.PoiIDSearchActivity;
import com.amap.map2d.demo.poisearch.PoiKeywordSearchActivity;
import com.amap.map2d.demo.poisearch.SubPoiSearchActivity;
import com.amap.map2d.demo.route.BusRouteActivity;
import com.amap.map2d.demo.route.DriveRouteActivity;
import com.amap.map2d.demo.route.RideRouteActivity;
import com.amap.map2d.demo.route.RouteActivity;
import com.amap.map2d.demo.route.WalkRouteActivity;
import com.amap.map2d.demo.routepoi.RoutePOIActivity;
import com.amap.map2d.demo.share.ShareActivity;
import com.amap.map2d.demo.tools.CalculateDistanceActivity;
import com.amap.map2d.demo.tools.ContainsActivity;
import com.amap.map2d.demo.tools.CoordConverActivity;
import com.amap.map2d.demo.tools.GeoToScreenActivity;
import com.amap.map2d.demo.view.FeatureView;
import com.amap.map2d.demo.weather.WeatherSearchActivity;

/**
 * AMapV1地图demo总汇
 */
public final class MainActivity1 extends ListActivity {
	private static class DemoDetails {
		private final int titleId;
		private final int descriptionId;
		private final Class<? extends android.app.Activity> activityClass;

		public DemoDetails(int titleId, int descriptionId,
				Class<? extends android.app.Activity> activityClass) {
			super();
			this.titleId = titleId;
			this.descriptionId = descriptionId;
			this.activityClass = activityClass;
		}
	}

	private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
		public CustomArrayAdapter(Context context, DemoDetails[] demos) {
			super(context, R.layout.feature, R.id.title, demos);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FeatureView featureView;
			if (convertView instanceof FeatureView) {
				featureView = (FeatureView) convertView;
			} else {
				featureView = new FeatureView(getContext());
			}
			DemoDetails demo = getItem(position);
			featureView.setTitleId(demo.titleId, demo.activityClass!=null);
//			featureView.setDescriptionId(demo.descriptionId);
			return featureView;
		}
	}

	private static final DemoDetails[] demos = {
//      创建地图
		new DemoDetails(R.string.map_create, R.string.blank, null),
		//共享电动车Demo
		new DemoDetails(R.string.share_car_demo, R.string.share_description,
				ShareCarDemoActivity.class),
		//显示地图
		new DemoDetails(R.string.basic_map, R.string.basic_description,
				BasicMapActivity.class),
		//OSM地图
		new DemoDetails(R.string.osm_map, R.string.osm_description,
				OsmMapActivity.class),
		//Fragment创建地图
		new DemoDetails(R.string.base_fragment_map, R.string.base_fragment_description,
				BaseMapFragmentActivity.class),
		//地图多实例
		new DemoDetails(R.string.multi_inst, R.string.blank,
				TwoMapActivity.class),
		//amapoptions实现地图
		new DemoDetails(R.string.mapOption_demo,
				R.string.mapOption_description, MapOptionActivity.class),
//-----------与地图交互-----------------------------------------------------------------------------------------------
		new DemoDetails(R.string.map_interactive, R.string.blank, null),
		//缩放控件、定位按钮、指南针、比例尺等的添加
		new DemoDetails(R.string.uisettings_demo,
				R.string.uisettings_description, UiSettingsActivity.class),
		//地图logo位置改变
		new DemoDetails(R.string.logo,
				R.string.uisettings_description, LogoSettingsActivity.class),
		//地图图层
		new DemoDetails(R.string.layers_demo, R.string.layers_description,
				LayersActivity.class),
		//缩放、旋转、拖拽和改变仰角操作地图
		new DemoDetails(R.string.gesture,
				R.string.uisettings_description, GestureSettingsActivity.class),
		//监听点击、长按、拖拽地图等事件
		new DemoDetails(R.string.events_demo, R.string.events_description,
				EventsActivity.class),
		//改变地图中心点
		new DemoDetails(R.string.camera_demo, R.string.camera_description,
				CameraActivity.class),
		//地图动画效果
		new DemoDetails(R.string.animate_demo, R.string.animate_description,
				Animate_CameraActivity.class),
		//改变缩放级别
		new DemoDetails(R.string.map_zoom, R.string.blank, ZoomActivity.class),
		//地图截屏
		new DemoDetails(R.string.screenshot_demo,
				R.string.screenshot_description, ScreenShotActivity.class),
		//切换国内外地图
		new DemoDetails(R.string.abroad_demo, R.string.abroad_description,
				AbroadMapSwitchActivity.class),
//----------------------------------------------------------------------------------------------------------------------------------------
		//在地图上绘制
		new DemoDetails(R.string.map_overlay, R.string.blank, null),
		//绘制点
		new DemoDetails(R.string.marker_demo, R.string.marker_description,
				MarkerActivity.class),
		//marker点击回调
		new DemoDetails(R.string.marker_click, R.string.marker_click,
				MarkerClickActivity.class),
		//绘制地图上的信息窗口
		new DemoDetails(R.string.infowindow_demo, R.string.infowindow_demo, InfoWindowActivity.class),
		//绘制自定义点
		new DemoDetails(R.string.custommarker_demo, R.string.blank,
				CustomMarkerActivity.class),
		//绘制默认定位小蓝点
		new DemoDetails(R.string.locationsource_demo,R.string.locationsource_description,LocationSourceActivity.class),
		//绘制自定义定位小蓝点图标
		new DemoDetails(R.string.customlocation_demo,R.string.customlocation_demo,CustomLocationActivity.class),
		//定位箭头旋转效果
		new DemoDetails(R.string.location_rotatemarker, R.string.location_rotatemarker, LocationMarkerActivity.class),
		//绘制实线、虚线
		new DemoDetails(R.string.polyline_demo,
				R.string.polyline_description, PolylineActivity.class),
		//绘制圆
		new DemoDetails(R.string.circle_demo, R.string.circle_description,
				CircleActivity.class),
		//矩形、多边形
		new DemoDetails(R.string.polygon_demo,
				R.string.polygon_description, PolygonActivity.class),
		//绘制groundoverlay
		new DemoDetails(R.string.groundoverlay_demo,
				R.string.groundoverlay_description,GroundOverlayActivity.class),
//-----------------------------------------------------------------------------------------------------------------------------------------------------
		//获取地图数据
		new DemoDetails(R.string.search_data, R.string.blank, null),
		//关键字检索
		new DemoDetails(R.string.poikeywordsearch_demo,
				R.string.poikeywordsearch_description,
				PoiKeywordSearchActivity.class),
		//周边搜索
		new DemoDetails(R.string.poiaroundsearch_demo,
				R.string.poiaroundsearch_description,
				PoiAroundSearchActivity.class),
		//ID检索
		new DemoDetails(R.string.poiidsearch_demo,
				R.string.poiidsearch_demo,
				PoiIDSearchActivity.class),
		//沿途搜索
		new DemoDetails(R.string.routepoisearch_demo,
				R.string.routepoisearch_demo,
				RoutePOIActivity.class),
		//输入提示查询
		new DemoDetails(R.string.inputtips_demo, R.string.inputtips_description,
				InputtipsActivity.class),
		//POI父子关系
		new DemoDetails(R.string.subpoi_demo, R.string.subpoi_description,
				SubPoiSearchActivity.class),
		//天气查询
		new DemoDetails(R.string.weather_demo,
				R.string.weather_description, WeatherSearchActivity.class),
		//地理编码
		new DemoDetails(R.string.geocoder_demo,
				R.string.geocoder_description, GeocoderActivity.class),
		//逆地理编码
		new DemoDetails(R.string.regeocoder_demo,
				R.string.regeocoder_description, ReGeocoderActivity.class),
		//行政区划查询
		new DemoDetails(R.string.district_demo,
				R.string.district_description, DistrictActivity.class),
		//行政区边界查询
		new DemoDetails(R.string.district_boundary_demo,
				R.string.district_boundary_description,
				DistrictWithBoundaryActivity.class),
		//公交路线查询
		new DemoDetails(R.string.busline_demo,
				R.string.busline_description, BuslineActivity.class),
		//公交站点查询
		new DemoDetails(R.string.busstation_demo,
				R.string.blank, BusStationActivity.class),
		//云图
		new DemoDetails(R.string.cloud_demo, R.string.cloud_description,
				CloudActivity.class),
		//出行路线规划
		new DemoDetails(R.string.search_route, R.string.blank, null),
		//驾车出行路线规划
		new DemoDetails(R.string.route_drive, R.string.blank, DriveRouteActivity.class),
		//步行出行路线规划
		new DemoDetails(R.string.route_walk, R.string.blank, WalkRouteActivity.class),
		//公交出行路线规划
		new DemoDetails(R.string.route_bus, R.string.blank, BusRouteActivity.class),
		//骑行出行路线规划
		new DemoDetails(R.string.route_ride, R.string.blank, RideRouteActivity.class),
		//route综合demo
		new DemoDetails(R.string.route_demo, R.string.route_description,
				RouteActivity.class),
		//短串分享
		new DemoDetails(R.string.search_share, R.string.blank, null),
		new DemoDetails(R.string.share_demo, R.string.share_description,
				ShareActivity.class),


		//地图计算工具
		new DemoDetails(R.string.map_tools, R.string.blank, null),

		//其他坐标系转换为高德坐标系
		new DemoDetails(R.string.coordconvert_demo, R.string.coordconvert_demo, CoordConverActivity.class),
		//地理坐标和屏幕像素坐标转换
		new DemoDetails(R.string.convertgeo2point_demo, R.string.convertgeo2point_demo, GeoToScreenActivity.class),
		//两点间距离计算
		new DemoDetails(R.string.calculateLineDistance, R.string.calculateLineDistance, CalculateDistanceActivity.class),
		//判断点是否在多边形内
		new DemoDetails(R.string.contains_demo, R.string.contains_demo, ContainsActivity.class)
	};



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		setTitle("2D地图Demo" + MapsInitializer.getVersion());
		ListAdapter adapter = new CustomArrayAdapter(
				this.getApplicationContext(), demos);
		setListAdapter(adapter);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
		if (demo.activityClass != null) {
			startActivity(new Intent(this.getApplicationContext(),
					demo.activityClass));
		}
	}
}
