package com.aaronyang.note;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aaronyang.note.db.NoteDao;
import com.aaronyang.note.entity.Note;

public class AddNoteActivity extends Activity {
	private Button saveNoteBtn;
	private EditText noteSubjectET,noteDetailET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_note);
		initViews();
	}
	
	private void initViews(){
		saveNoteBtn = (Button) findViewById(R.id.saveNoteBtn);
		noteSubjectET = (EditText) findViewById(R.id.noteSubjectET);
		noteDetailET = (EditText) findViewById(R.id.noteDetailET);
		saveNoteBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String subject = noteSubjectET.getText().toString();
				String detail = noteDetailET.getText().toString();
				if("".equals(detail)){
					Toast.makeText(AddNoteActivity.this, "Note should not be empty!", Toast.LENGTH_LONG).show();
				}else{
					if("".equals(subject)){
						detail = "NoTitle";
					}
					Note note = new Note();
					note.setSubject(subject);
					note.setDetail(detail);
					Date date = new Date();
				    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					note.setLastModifyDate(dateformat.format(date));
					NoteDao noteDao = new NoteDao(AddNoteActivity.this);
					if(noteDao.add(note)){
						AddNoteActivity.this.finish();
					}else{
						Toast.makeText(AddNoteActivity.this, "Add note failed!", Toast.LENGTH_LONG).show();
					}
				}
			}
			
		});
	}

}
