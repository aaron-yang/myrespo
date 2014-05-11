package com.example.storage;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MediaPlayerFromWebActivity extends Activity  implements OnClickListener{
	private Button playFromSD,playFromURL,stop2;
	private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player_from_web);
        findViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.media_player_from_web, menu);
        return true;
    }
    
    private void findViews(){
    	playFromSD = (Button) findViewById(R.id.playFromSD);
    	playFromURL = (Button) findViewById(R.id.playFromURL);
    	stop2 = (Button) findViewById(R.id.stop2);
		playFromSD.setOnClickListener(this);
		playFromURL.setOnClickListener(this);
		stop2.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.playFromSD:
			String path = "/sdcard/ring.mp3";
			playAudio(path);
			break;
		case R.id.playFromURL:
			String path2 = "http://sites.google.com/site/ronforwork/Home/android-2/ring.mp3";
			playAudio(path2);
			break;
		case R.id.stop2:
			if(mp != null){
				mp.stop();
			}
			break;
		}
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
	
    
	private void playAudio(String path){
		if(mp == null)
			mp = new MediaPlayer();
		try {
			mp.reset();
			mp.setDataSource(path);
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.prepare();
			mp.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
