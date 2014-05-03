package com.example.appmanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
	private LinearLayout loading;
	private TextView memoryAva, sdAvali;
	private ListView listView;
	private List<AppInfo> appInfos;
	private PackageManager pm;
	private static final int LOAD_APP_FINISH = 8;
	private PopupWindow popupWindow;
	private LinearLayout popup_uninstall, popup_start, popup_share;
	private String slectAppPackageName;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == LOAD_APP_FINISH) {
				loading.setVisibility(View.INVISIBLE);
				listView.setAdapter(new AppManagerAdapter());
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		memoryAva = (TextView) findViewById(R.id.memoryAva);
		sdAvali = (TextView) findViewById(R.id.sdAva);
		loading = (LinearLayout) findViewById(R.id.loading);
		listView = (ListView) findViewById(R.id.listView);
		sdAvali.setText("SD卡可用: " + getAvailSDSize());
		memoryAva.setText("内存可用: " + getAvailableROMSize());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				dismissPopupWindow();
				View contentView = View.inflate(getApplicationContext(),
						R.layout.popup_item, null);
				popup_uninstall = (LinearLayout) contentView
						.findViewById(R.id.popup_uninstall);
				popup_start = (LinearLayout) contentView
						.findViewById(R.id.popup_start);
				popup_share = (LinearLayout) contentView
						.findViewById(R.id.popup_share);
				popup_share.setOnClickListener(MainActivity.this);
				popup_start.setOnClickListener(MainActivity.this);
				popup_uninstall.setOnClickListener(MainActivity.this);
				LinearLayout ll_popup_container = (LinearLayout) contentView
						.findViewById(R.id.ll_popup_container);
				ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
				sa.setDuration(300);
				slectAppPackageName = appInfos.get(arg2).getPackageName();
				int top = arg1.getTop();
				int bottom = arg1.getBottom();
				popupWindow = new PopupWindow(contentView, DensityUtil.dip2px(
						getApplicationContext(), 200), bottom - top
						+ DensityUtil.dip2px(getApplicationContext(), 20));
				popupWindow.setBackgroundDrawable(new ColorDrawable(
						Color.TRANSPARENT));
				int[] location = new int[2];
				arg1.getLocationInWindow(location);
				popupWindow.showAtLocation(arg1, Gravity.TOP | Gravity.LEFT,
						location[0] + 20, location[1]);
				ll_popup_container.startAnimation(sa);

			}
		});

		pm = this.getPackageManager();
		fillData();
	}

	private boolean isUserApp(ApplicationInfo info) {
		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			return true;
		} else {
			return false;
		}
	}

	private class AppManagerAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return appInfos.get(arg0);
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
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.app_item, null);
				holder = new ViewHolder();
				holder.iconIV = (ImageView) convertView
						.findViewById(R.id.iconIv);
				holder.appNameTv = (TextView) convertView
						.findViewById(R.id.appNameTv);
				holder.versionTv = (TextView) convertView
						.findViewById(R.id.versionTv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			AppInfo appInfo = appInfos.get(position);
			holder.iconIV.setImageDrawable(appInfo.getAppIcon());
			holder.appNameTv.setText(appInfo.getAppName());
			holder.versionTv.setText("版本号: " + appInfo.getVersion());
			return convertView;
		}

	}

	private static class ViewHolder {
		ImageView iconIV;
		TextView appNameTv;
		TextView versionTv;
	}

	private String getAvailSDSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long availableBlocks = stat.getAvailableBlocks();
		long blockSize = stat.getBlockSize();
		return Formatter.formatFileSize(this, availableBlocks * blockSize);
	}

	private String getAvailableROMSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long availableBlocks = stat.getAvailableBlocks();
		long blockSize = stat.getBlockSize();
		return Formatter.formatFileSize(this, availableBlocks * blockSize);
	}

	private void dismissPopupWindow() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.popup_share:
			shareApplication();
			break;
		case R.id.popup_start:
			startAppliction();
			break;
		case R.id.popup_uninstall:
			uninstallApplication();
			break;
		}
	}

	private void shareApplication() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SEND");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("text/plain");
		intent.putExtra("subject", "分享的标题");
		intent.putExtra("sms_body", "推荐你使用一款软件" + slectAppPackageName);
		intent.putExtra(Intent.EXTRA_TEXT, "分享");
		startActivity(intent);
	}

	private void uninstallApplication() {
		dismissPopupWindow();
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DELETE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:" + slectAppPackageName));
		startActivityForResult(intent, 1);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			// 通知界面更新数据.
			fillData();
			sdAvali.setText("SD卡可用" + getAvailSDSize());
			memoryAva.setText("内存可用:" + getAvailableROMSize());
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void startAppliction() {
		dismissPopupWindow();
		Intent intent = new Intent();
		PackageInfo packinfo;
		try {
			packinfo = pm.getPackageInfo(slectAppPackageName,
					PackageManager.GET_ACTIVITIES);

			ActivityInfo[] activityinfos = packinfo.activities;
			// 判断清单文件中是否存在Activity对应的节点
			if (activityinfos != null && activityinfos.length > 0) {
				// 启动清单文件中的第一个Activity节点
				String className = activityinfos[0].name;
				intent.setClassName(slectAppPackageName, className);
				startActivity(intent);
			} else {
				Toast.makeText(this, "不能启动当前应用", 0).show();
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(this, "不能启动当前应用", 0).show();
		}
	}

	private void fillData() {
		loading.setVisibility(View.VISIBLE);
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<PackageInfo> packageInfors = pm
						.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
				appInfos = new ArrayList<AppInfo>();
				for (PackageInfo info : packageInfors) {
					if (isUserApp(info.applicationInfo)) {
						AppInfo appInfo = new AppInfo();
						appInfo.setPackageName(info.packageName);
						appInfo.setVersion(info.versionName);
						appInfo.setAppIcon(info.applicationInfo.loadIcon(pm));
						appInfo.setAppName(info.applicationInfo.loadLabel(pm)
								.toString());
						appInfos.add(appInfo);
					}
				}
				Message msg = Message.obtain();
				msg.what = LOAD_APP_FINISH;
				handler.sendMessage(msg);
			}

		}.start();
	}
}
