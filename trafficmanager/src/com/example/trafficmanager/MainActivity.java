package com.example.trafficmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ListView trafficList;
	private LinearLayout loading;
	private List<TrafficInfo> infos;
	private static final int LOAD_TRAFFIC_FINISEED = 8;

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == LOAD_TRAFFIC_FINISEED){
				loading.setVisibility(View.INVISIBLE);
				trafficList.setAdapter(new TrafficAdapter());
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}

	private void initViews(){
		trafficList = (ListView) findViewById(R.id.trafficList);
		loading = (LinearLayout) findViewById(R.id.loading);
		loading.setVisibility(View.INVISIBLE);
		new Thread(){

			@Override
			public void run() {
				infos = getTrafficInfos();
				Message msg = Message.obtain();
				msg.what = LOAD_TRAFFIC_FINISEED;
				handler.sendMessage(msg);
			}
			
		}.start();
	}
	
	private List<TrafficInfo> getTrafficInfos(){
		infos = new ArrayList<TrafficInfo>();
		PackageManager pm = this.getPackageManager();
		List<PackageInfo> packageinfos = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		for(PackageInfo info : packageinfos){
			String[] permissions = info.requestedPermissions;
			if(permissions != null && permissions.length>0){
				for(String permission : permissions){
					if("android.permission.INTERNET".equals(permission)){
						TrafficInfo trafficInfo = new TrafficInfo();
						trafficInfo.setPackname(info.packageName);
						trafficInfo.setIcon(info.applicationInfo.loadIcon(pm));
						trafficInfo.setAppname(info.applicationInfo.loadLabel(pm).toString());
						int uid = info.applicationInfo.uid;
						trafficInfo.setRx(TrafficStats.getUidRxBytes(uid));
						trafficInfo.setTx(TrafficStats.getUidTxBytes(uid));
						infos.add(trafficInfo);
						trafficInfo = null;
						break;
					}
				}
			}
		}
		return infos;
	}
	
	private class TrafficAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return infos.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(getApplicationContext(), R.layout.traffic_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_traffic_icon);
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_traffic_name);
				holder.tv_rx = (TextView) convertView.findViewById(R.id.tv_traffic_rx);
				holder.tv_tx = (TextView) convertView.findViewById(R.id.tv_traffic_tx);
				holder.tv_total = (TextView) convertView.findViewById(R.id.tv_traffic_total);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			TrafficInfo info = infos.get(position);
			holder.iv_icon.setImageDrawable(info.getIcon());
			holder.tv_name.setText(info.getAppname());
			holder.tv_rx.setText(Formatter.formatFileSize(getApplicationContext(), info.getRx()));
			holder.tv_tx.setText(Formatter.formatFileSize(getApplicationContext(),info.getTx()));
			holder.tv_total.setText(Formatter.formatFileSize(getApplicationContext(),info.getTx()+info.getTx()));
			return convertView;
		}
		
	}
	
    static class ViewHolder{
    	ImageView iv_icon;
    	TextView tv_name;
    	TextView tv_tx;
    	TextView tv_rx;
    	TextView tv_total;
    }

}
