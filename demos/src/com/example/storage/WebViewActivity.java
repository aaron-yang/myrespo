package com.example.storage;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
	private WebView webView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		webView1 = (WebView) findViewById(R.id.webView1);
		webView1.getSettings().setJavaScriptEnabled(true);
		webView1.loadUrl("http://www.baidu.com");
		webView1.setWebViewClient(new MyWebViewClient());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_view, menu);
		return true;
	}
	
	private class MyWebViewClient extends WebViewClient{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(KeyEvent.KEYCODE_BACK == keyCode && webView1.canGoBack()){
			webView1.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	

}
