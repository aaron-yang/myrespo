package com.example.storage;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotificationActivity extends Activity implements OnClickListener{
	private Button open,delete;
	private NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        findViews();
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
		case R.id.open:
			show();
			break;
		case R.id.delete:
			nm.cancel(0);
			break;
		}
		
	}
	
	
	private void show(){
		Intent intent = new Intent(this,NotificationActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		Notification notification = new Notification.Builder(this)
		.setTicker("通知栏标题")
		.setContentTitle("滑下来通知标题")
		.setContentText("滑下来通知内容")
		.setSmallIcon(R.drawable.ic_launcher)
		.setAutoCancel(true)
		.setContentIntent(pendingIntent)
		.getNotification();
		nm.notify(0, notification);
	}
	
	private void findViews(){
		open = (Button) findViewById(R.id.open);
		delete = (Button) findViewById(R.id.delete);
		open.setOnClickListener(this);
		delete.setOnClickListener(this);
	}
    
}
