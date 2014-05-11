package com.aaronyang.note;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aaronyang.note.db.NoteDao;
import com.aaronyang.note.entity.Note;

public class MainActivity extends Activity {
	private TextView titleTV;
	private Button addNoteBtn;
	private ListView noteLV;
	private List<Note> noteList;
	private static final int LOAD_FINISHED = 8;
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == LOAD_FINISHED){
				noteLV.setAdapter(new NoteAdapter());
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		initViews(intent.getBooleanExtra("isAdminUser", false));
	}


	private void initViews(boolean isAdminUser) {
		titleTV = (TextView) findViewById(R.id.titleTV);
		addNoteBtn = (Button) findViewById(R.id.addNoteBtn);
		noteLV = (ListView) findViewById(R.id.noteLV);
		if (isAdminUser) {
			titleTV.setText("Admin User");
			addNoteBtn.setVisibility(View.INVISIBLE);
		} else {
			titleTV.setText("Normal User");
			addNoteBtn.setVisibility(View.VISIBLE);
		}
		addNoteBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
				startActivity(intent);
			}
			
		});
		loadNotes();
	}
	
	
	private class NoteAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return noteList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return noteList.get(arg0);
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
						R.layout.noteitem, null);
				holder = new ViewHolder();
				holder.noteSubjectTV = (TextView) convertView
						.findViewById(R.id.noteSubjectTV);
				holder.lastModifyDateTV = (TextView) convertView
						.findViewById(R.id.lastModifyDateTV);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Note note = noteList.get(position);
			holder.noteSubjectTV.setText(note.getSubject());
			holder.lastModifyDateTV.setText(String.valueOf(note.getLastModifyDate()));
			return convertView;
		}

	}

	private static class ViewHolder {
		TextView noteSubjectTV;
		TextView lastModifyDateTV;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		loadNotes();
		super.onResume();
	}
	
	private void loadNotes(){
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				NoteDao noteDao = new NoteDao(MainActivity.this);
				noteList = noteDao.findAll();
				Message msg = Message.obtain();
				msg.what = LOAD_FINISHED;
				handler.sendMessage(msg);
			}
			
		}.start();
	}

}
