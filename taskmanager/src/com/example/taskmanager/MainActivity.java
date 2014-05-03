package com.example.taskmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private ListView userList, systemList;
	private Button userBtn, systemBtn;
	private static final int LOAD_PROCESS_INFO_FINISHED = 8;
	private List<ProcessInfo> processInfos;
	private List<ProcessInfo> userProcessInfors,systemProcessInfos;
	private UserAdapter useradapter;
	private SystemAdapter systemadapter;
	private boolean showUserApp;
	
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == LOAD_PROCESS_INFO_FINISHED){
				useradapter = new UserAdapter();
				systemadapter = new SystemAdapter();
				userList.setAdapter(useradapter);
				systemList.setAdapter(systemadapter);
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		new Thread(){

			@Override
			public void run() {
				processInfos = getProcessInfos();
				for(ProcessInfo info : processInfos){
					if(info.isUserprocess()){
						userProcessInfors.add(info);
					}else{
						systemProcessInfos.add(info);
					}
				}
				Message msg = Message.obtain();
				msg.what = LOAD_PROCESS_INFO_FINISHED;
				handler.sendMessage(msg);
			}
			
		}.start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.userBtn:
			userBtn.setBackground(getResources().getDrawable(
					R.drawable.bt_pressed));
			systemBtn.setBackground(getResources().getDrawable(
					R.drawable.bg_normal));
			userList.setVisibility(View.VISIBLE);
			systemList.setVisibility(View.INVISIBLE);
			showUserApp = true;
			break;
		case R.id.systemBtn:
			systemBtn.setBackground(getResources().getDrawable(
					R.drawable.bt_pressed));
			userBtn.setBackground(getResources().getDrawable(
					R.drawable.bg_normal));
			systemList.setVisibility(View.VISIBLE);
			userList.setVisibility(View.INVISIBLE);
			showUserApp = false;
			break;
		}
	}
	
	private void initViews(){
		userList = (ListView) findViewById(R.id.userList);
		systemList = (ListView) findViewById(R.id.systemList);
		userBtn = (Button) findViewById(R.id.userBtn);
		systemBtn = (Button) findViewById(R.id.systemBtn);
		userBtn.setOnClickListener(this);
		systemBtn.setOnClickListener(this);
		userBtn.setBackground(getResources().getDrawable(
				R.drawable.bt_pressed));
		systemBtn.setBackground(getResources().getDrawable(
				R.drawable.bg_normal));
		systemList.setVisibility(View.GONE);
		userProcessInfors = new ArrayList<ProcessInfo>();
		systemProcessInfos = new ArrayList<ProcessInfo>();
		userList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CheckBox cb = (CheckBox) view.findViewById(R.id.cb_taskmanager);
				ProcessInfo info = (ProcessInfo) userList
						.getItemAtPosition(position);
				if (info.getPackname().equals(getPackageName())) {
					return;
				}
				if (info.isChecked()) {
					info.setChecked(false);
					cb.setChecked(false);
				} else {
					info.setChecked(true);
					cb.setChecked(true);
				}

			}
		});
		systemList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CheckBox cb = (CheckBox) view.findViewById(R.id.cb_taskmanager);
				ProcessInfo info = (ProcessInfo) systemList
						.getItemAtPosition(position);
				if (info.isChecked()) {
					info.setChecked(false);
					cb.setChecked(false);
				} else {
					info.setChecked(true);
					cb.setChecked(true);
				}
			}
		});
	}
	
	private class UserAdapter extends BaseAdapter {

		public int getCount() {
			return userProcessInfors.size();
		}

		public Object getItem(int position) {
			return userProcessInfors.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.task_manager_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) convertView
						.findViewById(R.id.iv_taskmanger_icon);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_taskmanager_appname);
				holder.tv_mem = (TextView) convertView
						.findViewById(R.id.tv_taskmanager_mem);
				holder.cb = (CheckBox) convertView.findViewById(R.id.cb_taskmanager);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ProcessInfo info = userProcessInfors.get(position);
			if (info.getPackname().equals(getPackageName())) {
				holder.cb.setVisibility(View.INVISIBLE);
			} else {
				holder.cb.setVisibility(View.VISIBLE);
			}
			holder.iv_icon.setImageDrawable(info.getIcon());
			holder.tv_name.setText(info.getAppname());
			holder.tv_mem.setText(Formatter.formatFileSize(
					getApplicationContext(), info.getMemsize()));
			holder.cb.setChecked(info.isChecked());
			return convertView;
		}
	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_mem;
		CheckBox cb;
	}

	private class SystemAdapter extends BaseAdapter {

		public int getCount() {
			return systemProcessInfos.size();
		}

		public Object getItem(int position) {
			return systemProcessInfos.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			View view;
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.task_manager_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view
						.findViewById(R.id.iv_taskmanger_icon);
				holder.tv_name = (TextView) view
						.findViewById(R.id.tv_taskmanager_appname);
				holder.tv_mem = (TextView) view
						.findViewById(R.id.tv_taskmanager_mem);
				holder.cb = (CheckBox) view.findViewById(R.id.cb_taskmanager);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			ProcessInfo info = systemProcessInfos.get(position);
			holder.iv_icon.setImageDrawable(info.getIcon());
			holder.tv_name.setText(info.getAppname());
			holder.tv_mem.setText(Formatter.formatFileSize(
					getApplicationContext(), info.getMemsize()));
			holder.cb.setChecked(info.isChecked());

			return view;
		}
	}

	
	private List<ProcessInfo> getProcessInfos(){
		ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager pm = this.getPackageManager();
		List<RunningAppProcessInfo> runningAppsInfos = am.getRunningAppProcesses();
		processInfos = new ArrayList<ProcessInfo>();
		for(RunningAppProcessInfo runningAppProcessInfo : runningAppsInfos){
			ProcessInfo pInfo = new ProcessInfo();
			int pid = runningAppProcessInfo.pid;
			pInfo.setPid(pid);
			String packageName = runningAppProcessInfo.processName;
			pInfo.setPackname(packageName);
			pInfo.setMemsize(am.getProcessMemoryInfo(new int[]{pid})[0].getTotalPrivateDirty()*1024);
			try{
				ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
				if(filterApp(applicationInfo)){
					pInfo.setUserprocess(true);
				}else{
					pInfo.setUserprocess(false);
				}
				pInfo.setAppname(applicationInfo.loadLabel(pm).toString());
				pInfo.setIcon(applicationInfo.loadIcon(pm));
			}catch(Exception ex){
				ex.printStackTrace();
				pInfo.setUserprocess(false);
				pInfo.setIcon(this.getResources().getDrawable(R.drawable.ic_launcher));
				pInfo.setAppname(packageName);
			}
			processInfos.add(pInfo);
		}
		return processInfos;
	}
	
	
	private boolean filterApp(ApplicationInfo info) {
		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			return true;
		}
		return false;
	}

	
	public void selectAll(View view) {
		if (showUserApp) {
			for (ProcessInfo info : userProcessInfors) {
				info.setChecked(true);
				useradapter.notifyDataSetChanged();
			}

		} else {
			for (ProcessInfo info : systemProcessInfos) {
				info.setChecked(true);
				systemadapter.notifyDataSetChanged();
			}
		}
	}

	
	public void oneKeyClear(View v) {
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		int count = 0;
		long memsize = 0;
		List<ProcessInfo> killedProcessInfo = new ArrayList<ProcessInfo>();
		if (showUserApp) {
			for (ProcessInfo info : userProcessInfors) {
				if (info.isChecked()) {
					count++;
					memsize += info.getMemsize();
					am.killBackgroundProcesses(info.getPackname());
					killedProcessInfo.add(info);
				}
			}

		} else {
			for (ProcessInfo info : systemProcessInfos) {
				if (info.isChecked()) {
					count++;
					memsize += info.getMemsize();
					am.killBackgroundProcesses(info.getPackname());
					killedProcessInfo.add(info);

				}
			}
		}
		for (ProcessInfo info : killedProcessInfo) {
			if (info.isUserprocess()) {
				if (userProcessInfors.contains(info)) {
					userProcessInfors.remove(info);
				}
			} else {
				if (systemProcessInfos.contains(info)) {
					systemProcessInfos.remove(info);
				}
			}
		}
		if (showUserApp) {
			useradapter.notifyDataSetChanged();
		} else {
			systemadapter.notifyDataSetChanged();
		}
		Toast.makeText( this, "杀死了" + count + "个进程,释放了" +
		  Formatter.formatFileSize(this, memsize) + "内存", 1) .show();
	}
}
