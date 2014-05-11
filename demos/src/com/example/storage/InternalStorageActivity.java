package com.example.storage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InternalStorageActivity extends Activity implements OnClickListener{
	private Button save, saveAppend, open;
	private EditText contentET;
	private TextView label;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internal_storage);
		findViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.internal_storage, menu);
		return true;
	}
	
	private void findViews() {
		contentET = (EditText) findViewById(R.id.context);
		label = (TextView) findViewById(R.id.label);
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(this);
		saveAppend = (Button) findViewById(R.id.saveAndAppend);
		saveAppend.setOnClickListener(this);
		open = (Button) findViewById(R.id.open);
		open.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.save:
			save();
			break;
		case R.id.saveAndAppend:
			saveAndAppend();
			break;
		case R.id.open:
			open();
			break;
		}
	}
	
	private void save(){
		String text = contentET.getText().toString();
		try {
			FileOutputStream fos = openFileOutput("test.txt",Context.MODE_PRIVATE);
			fos.write(text.toString().getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		label.setText("保存成功！");
	}
	
	private void saveAndAppend(){
		String text = contentET.getText().toString();
		try {
			FileOutputStream fos = openFileOutput("test.txt",Context.MODE_APPEND);
			fos.write(text.toString().getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		label.setText("追加保存成功！");
	}
	
	private void open(){
		try {
			FileInputStream fis = openFileInput("test.txt");
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			String line = "";
			while( (line = br.readLine()) != null){
				sb.append(line);
			}
			br.close();
			isr.close();
			fis.close();
			label.setText(sb.toString());;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
