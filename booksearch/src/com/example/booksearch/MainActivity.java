package com.example.booksearch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends Activity {
	private Button startScanBtn;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startScanBtn = (Button) findViewById(R.id.startScanBtn);
		startScanBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentIntegrator integrator = new IntentIntegrator(
						MainActivity.this);
				integrator.initiateScan();
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		final IntentResult result = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, data);
		if ((result == null) || (result.getContents() == null)) {
			Log.v("", "User cancel scan by pressing back key");
			return;
		}
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.communicating));
		progressDialog.show();
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = Message.obtain();
				msg.obj = Utils.download(BookAPI.URL_ISBN_BASE
						+ result.getContents());

			}

		}.start();
	}

	private static class DownloadHandler extends Handler {

		private MainActivity mActivity;

		public DownloadHandler(MainActivity activity) {
			super();
			mActivity = activity;
		}

		@Override
		public void handleMessage(Message msg) {
			if ((msg.obj == null) || (mActivity.progressDialog == null)
					|| (!mActivity.progressDialog.isShowing())) {
				return;
			}

			mActivity.progressDialog.dismiss();

			NetResponse response = (NetResponse) msg.obj;

			if (response.getCode() != BookAPI.RESPONSE_CODE_SUCCEED) {
				// 通信异常处理
				Toast.makeText(
						mActivity,
						"["
								+ response.getCode()
								+ "]: "
								+ mActivity.getString((Integer) response
										.getMessage()), Toast.LENGTH_LONG)
						.show();
			} else {
				mActivity.startBookInfoDetailActivity((BookInfo) response.getMessage());
			}
		}

	}
	
	private void startBookInfoDetailActivity(BookInfo bookInfo) {
        if (bookInfo == null) {
            return;
        }
        
        Intent intent = new Intent(this, BookInfoDetailActivity.class);
        intent.putExtra(BookInfo.class.getName(), bookInfo);  
        startActivity(intent);
    }

}
