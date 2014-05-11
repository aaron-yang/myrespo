package com.example.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExternalStorageActivity extends Activity implements OnClickListener {
	private Button savePrivate, savePubic;
	private TextView label;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_external_storage);
		findViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.external_storage, menu);
		return true;
	}
	
	private boolean isSDExist(){
		String state = Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equals(state)){
			return true;
		}else{
			return false;
		}
	}
	
	private void callMediaScanner(String[] paths){
		MediaScannerConnection.scanFile(this, paths, null, new MediaScannerConnection.OnScanCompletedListener() {
			
			@Override
			public void onScanCompleted(String path, Uri uri) {
				// TODO Auto-generated method stub
				Log.i("TAG", "Scanned"+path+":");
				Log.i("TAG","->uri="+uri);
			}
		});
	}
	
	
	private void createFile(File file){
		File parentPath = file.getParentFile();
		if(!isSDExist()){
			Toast.makeText(this, "SD卡没法正常加载!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(!parentPath.exists()){
			parentPath.mkdirs();
		}
		if(file.exists())
			file.delete();
		InputStream is = getResources().openRawResource(R.drawable.ja);
		try {
			OutputStream os = new FileOutputStream(file);
			byte[] data = new byte[is.available()];
			is.read(data);
			os.write(data);
			label.setText("文件已储存在"+file.toString());
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] paths = {file.toString()};
		callMediaScanner(paths);
	}
	
	
	private void findViews() {
		savePrivate = (Button) findViewById(R.id.savePrivate);
		savePrivate.setOnClickListener(this);
		savePubic = (Button) findViewById(R.id.savePublic);
		savePubic.setOnClickListener(this);
		label = (TextView) findViewById(R.id.label2);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.savePrivate:
			saveToPrivate();
			break;
		case R.id.savePublic:
			saveToPublic();
			break;
		}
	}
	
	private void saveToPrivate(){
		File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File file = new File(path,"ja.jpg");
		createFile(file);
	}
	
	private void saveToPublic(){
		File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File file = new File(path,"ja.jpg");
		createFile(file);
	}

}
