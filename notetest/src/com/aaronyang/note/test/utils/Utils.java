package com.aaronyang.note.test.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.aaronyang.note.test.ConfigrationFile;
import com.aaronyang.note.test.entity.TestCaseEntity;
import com.aaronyang.note.test.entity.TestCaseEntity.TestCaseStatus;

public class Utils {

	/**
	 * wake up screen if needed
	 * 
	 * @param owner
	 * @return return wakeLock object,it will release after class done
	 */
	public static WakeLock wakeScreen(InstrumentationTestCase owner) {
		PowerManager pm = (PowerManager) owner.getInstrumentation()
				.getTargetContext().getSystemService(Context.POWER_SERVICE);
		WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
				| PowerManager.FULL_WAKE_LOCK
				| PowerManager.ACQUIRE_CAUSES_WAKEUP, owner.getClass()
				.getSimpleName());
		wakeLock.acquire();
		return wakeLock;
	}

	public static void unlock(Instrumentation instr) {
		try {
			Context targetContext = instr.getTargetContext();
			KeyguardManager keyGuardManager = (KeyguardManager) targetContext
					.getSystemService(Context.KEYGUARD_SERVICE);
			KeyguardLock mLock = keyGuardManager.newKeyguardLock("");
			mLock.disableKeyguard();
		} catch (Throwable e) {
			Log.e("", "disableKeyguard:", e);
		}
	}

	public static void saveTestResult(TestCaseStatus status, TestCaseEntity entity) {
		File file = new File(getLogFileFolder()+"/"+ConfigrationFile.TEST_RESULT_FILE);
		if (file == null || !file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			writer.write(entity.getTestCaseId()+" Priority:"+entity.getPriority()+" Status: "+status + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String getLogFileFolder(){
		File file = new File(Environment.getExternalStorageDirectory() + "/"+ConfigrationFile.LOG_FILE_FOLER);
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			if(file != null && !file.exists()){
				file.mkdir();
			}
		}
		return Environment.getExternalStorageDirectory() + "/"+ConfigrationFile.LOG_FILE_FOLER;
	}
}
