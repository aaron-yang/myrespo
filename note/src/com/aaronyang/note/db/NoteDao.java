package com.aaronyang.note.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aaronyang.note.entity.Note;

public class NoteDao {
	
	private NoteDBOpenHelper helper;
	
	public NoteDao(Context context){
		helper = new NoteDBOpenHelper(context);
	}
	
	public boolean find(String detail){
		boolean result = false;
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			Cursor cursor = db.rawQuery("select * from note where detail =?", new String[]{detail});
			if(cursor.moveToFirst()){
				result = true;
			}
			cursor.close();
			db.close();
		}
		return result;
	} 
	
	
	public boolean add(Note note){
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("insert into note (subject,detail,lastModifyDate) values(?,?,?)", new Object[]{note.getSubject(),note.getDetail(),note.getLastModifyDate()});
			db.close();
		}
		return find(note.getDetail());
	}
	
	public void delete(int id){
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("delete from note where _id=?", new Object[]{id});
			db.close();
		}
	}
	
	public void update(String subject,String detail,long lastModifyDate,int id){
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("update note set subject=?,detail=?,lastModifyDate=? where _id=?", new Object[]{subject,detail,lastModifyDate,id});
			db.close();
		}
	}
	
	public List<Note> findAll(){
		List<Note> notes = new ArrayList<Note>();
		SQLiteDatabase db = helper.getReadableDatabase();
		if(db.isOpen()){
			Cursor cursor = db.rawQuery("select _id,subject,detail,lastModifyDate from note",null);
			while(cursor.moveToNext()){
				Note note = new Note();
				note.setId(cursor.getInt(0));
				note.setSubject(cursor.getString(1));
				note.setDetail(cursor.getString(2));
				note.setLastModifyDate(cursor.getString(3));
				notes.add(note);
			}
			cursor.close();
			db.close();
		}
		return notes;
	}

}
