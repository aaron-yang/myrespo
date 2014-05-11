package com.example.cachemanager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView cleanStatus;
	private ProgressBar pb;
	private LinearLayout cleanCacheCont;
	private List<String> cachePackageNames;
	private Map<String,Long> cacheInfos;
	private PackageManager pm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cleanStatus = (TextView) findViewById(R.id.cleanStatus);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		cleanCacheCont = (LinearLayout) findViewById(R.id.cleanCacheCont);
		pm = this.getPackageManager();
		scanPackages();
	}

	private void scanPackages(){
		
		new AsyncTask<Void, Integer, Void>(){
			List<PackageInfo> packageInfos;
			
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				int i = 0;
				for(PackageInfo info : packageInfos){
					String packageName = info.packageName;
					getSize(pm,packageName);
					i++;
					try{
						Thread.sleep(1000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					publishProgress(i);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				cleanStatus.setText("扫描完毕..."+"发现有"+cachePackageNames.size()+"个缓存信息");
				for(final String pn : cachePackageNames){
					View child = View.inflate(getApplicationContext(), R.layout.cache_item, null);
					child.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (Build.VERSION.SDK_INT >= 9) {
								// 跳转至“清理缓存”的界面（可以通过：设置-->应用程序-->点击任意应用程序后的界面）
								Intent intent = new Intent();
								intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
								intent.addCategory(Intent.CATEGORY_DEFAULT);
								intent.setData(Uri.parse("package:" + pn));
								startActivity(intent);
							} else {
								Intent intent = new Intent();
								intent.setAction("android.intent.action.VIEW");
								intent.addCategory(Intent.CATEGORY_DEFAULT);
								intent.addCategory("android.intent.category.VOICE_LAUNCH");
								intent.putExtra("pkg", pn);
								startActivity(intent);
							}
						}
					});
					ImageView iv_icon = (ImageView)child.findViewById(R.id.iv_cache_icon);
					iv_icon.setImageDrawable(getApplicationIcon(pn));
					TextView tv_name = (TextView) findViewById(R.id.tv_cache_name);
					tv_name.setText(getPackageName());
					TextView tv_size = (TextView) findViewById(R.id.tv_cache_size);
					tv_size.setText("缓存大小："+Formatter.formatFileSize(getApplicationContext(), cacheInfos.get(pn)));
				}
				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				cachePackageNames = new ArrayList<String>();
				cacheInfos = new HashMap<String,Long>();
				packageInfos = pm.getInstalledPackages(0);
				pb.setMax(packageInfos.size());
				cleanStatus.setText("开始扫描...");
				super.onPreExecute();
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				pb.setProgress(values[0]);
				cleanStatus.setText("正在扫描"+values[0]+"条目");
				super.onProgressUpdate(values);
			}
			
			
			
		}.execute();
	}
	
	private void getSize(PackageManager pm,String packageName){
		Method method;
		try {
			method = pm.getClass().getDeclaredMethod("getPackageSizeInfo", new Class[]{String.class,IPackageStatsObserver.class});
			method.invoke(pm, new Object[]{packageName,new MyObersver(packageName)});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private class MyObersver extends IPackageStatsObserver.Stub {
		private String packname;

		public MyObersver(String packname) {
			this.packname = packname;
		}

		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// 以下是根据ApplicationsState代码中的SizeInfo对象中定义的
			// 缓存大小
			long cacheSize = pStats.cacheSize;
			// 代码大小
			long codeSize = pStats.codeSize;
			// 数据的大小
			long dataSize = pStats.dataSize;
			// 判断这个包名对应的应用程序是否有缓存，如果有，则存入到集合中。
			if (cacheSize > 0) {
				cachePackageNames.add(packname);
				cacheInfos.put(packname, cacheSize);
			}
		}
	}
	
	private String getApplicationName(String packname) {
		try {
			PackageInfo packinfo = pm.getPackageInfo(packname, 0);
			return packinfo.applicationInfo.loadLabel(pm).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return packname;
		}
	}

	private Drawable getApplicationIcon(String packname) {
		try {
			PackageInfo packinfo = pm.getPackageInfo(packname, 0);
			return packinfo.applicationInfo.loadIcon(pm);

		} catch (Exception e) {
			e.printStackTrace();
			return getResources().getDrawable(R.drawable.ic_launcher);
		}
	}

}
