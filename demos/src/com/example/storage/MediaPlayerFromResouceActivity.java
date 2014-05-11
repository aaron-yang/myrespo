package com.example.storage;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MediaPlayerFromResouceActivity extends Activity implements
		OnClickListener {
	private Button play, pause, stop;
	MediaPlayer mp;
	private boolean isStoped = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_player_from_resouce);
		findViews();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media_player_from_resouce, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.play:
			if(mp == null || isStoped){
				mp = MediaPlayer.create(this, R.raw.ring);
				isStoped = false;
			}
			mp.start();
			break;
		case R.id.pause:
			if(mp == null || isStoped){
				return;
			}
			mp.pause();
			break;
		case R.id.stop:
			if(mp == null || isStoped){
				return;
			}
			mp.stop();
			isStoped = true;
			break;
		}

	}

	private void findViews() {
		play = (Button) findViewById(R.id.play);
		pause = (Button) findViewById(R.id.pause);
		stop = (Button) findViewById(R.id.stop);
		play.setOnClickListener(this);
		stop.setOnClickListener(this);
		pause.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mp != null){
			mp.release();
			mp = null;
		}
	}
	
	

}
