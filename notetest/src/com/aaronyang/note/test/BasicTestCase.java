package com.aaronyang.note.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.os.PowerManager;
import android.test.InstrumentationTestCase;
import android.view.WindowManager;

import com.aaronyang.note.LoginActivity;
import com.aaronyang.note.test.entity.TestCaseEntity;
import com.aaronyang.note.test.entity.TestCaseEntity.TestCaseStatus;
import com.aaronyang.note.test.utils.LogUtil;
import com.aaronyang.note.test.utils.NetworkUtil;
import com.aaronyang.note.test.utils.Utils;

public class BasicTestCase extends InstrumentationTestCase {
	private PowerManager.WakeLock wakeScreenObject = null;
	private Class<?> startActivity = LoginActivity.class;
	private SoloWrapper soloWrapper = null;
	protected UIHelper uiHelper = null;
	private TestCaseEntity testCaseEntity = null;
	private TestCaseStatus status = TestCaseStatus.UNKNOWN;

	protected void setUp(String testCaseId, String priority)
			throws SetUpException {
		try {
			super.setUp();
			init();
			startTargetApplication(testCaseId, priority);
		} catch (Exception e) {
			LogUtil.e(LogUtil.TAG, "Error in setup:" + e);
			status = TestCaseStatus.ERROR;
			soloWrapper.takeScreenShot(this.getClass().getSimpleName());
			// in order to clear reference in tearDown, so need to throw
			throw new SetUpException(e);
		}
	}

	@Override
	protected void runTest() throws Throwable {
		try {
			super.runTest();
		} catch (junit.framework.AssertionFailedError afe) {
			LogUtil.e(LogUtil.TAG, "AssertionFailedError:" + afe);
			status = TestCaseStatus.FAILURE;
			soloWrapper.takeScreenShot(this.getClass().getSimpleName());
			throw afe;
		} catch (Throwable th) {
			LogUtil.e(LogUtil.TAG, "Error in runTest:" + th);
			status = TestCaseStatus.ERROR;
			soloWrapper.takeScreenShot(this.getClass().getSimpleName());
			throw new RunTestException(th);
		}
	}

	/**
	 * from junit source code public void runBare() throws Throwable { Throwable
	 * exception = null; setUp(); try { runTest(); } catch (Throwable running) {
	 * exception = running; } finally { try { tearDown(); } catch (Throwable
	 * tearingDown) { if (exception == null) exception = tearingDown; } } if
	 * (exception != null) throw exception; }
	 */

	@Override
	public void runBare() throws Throwable {
		// TODO Auto-generated method stub
		try {
            super.runBare();
        } catch (SetUpException sue) {
        	tearDown();
            throw sue;
            //test later,may comment out
        } catch (RunTestException rte) {
        	tearDown();
            throw rte;
        } 
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		if (wakeScreenObject != null) {
			wakeScreenObject.release();
			wakeScreenObject = null;
		}
		soloWrapper.finishOpenedActivities();
		try {
			soloWrapper.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		soloWrapper = null;
		if (status == TestCaseStatus.UNKNOWN) {
			status = TestCaseStatus.PASSED;
        }
		Utils.saveTestResult(status, testCaseEntity);
		testCaseEntity = null;
		uiHelper = null;
		super.tearDown();
	}

	/**
	 * wake up screen unlock set network on
	 */
	private void init() {
		if (wakeScreenObject == null) {
			wakeScreenObject = Utils.wakeScreen(this);
		}
		Utils.unlock(getInstrumentation());
		NetworkUtil.setAirplaneModeOffAndNetworkOn(getInstrumentation()
				.getTargetContext());
	}

	/**
	 * start application under test
	 * 
	 * @param testCaseId
	 * @param priority
	 */
	private void startTargetApplication(String testCaseId, String priority) {
		Instrumentation ins = getInstrumentation();
		ActivityMonitor startScreenMonitor = ins.addMonitor(
				startActivity.getName(), null, false);
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClassName(ins.getTargetContext(), startActivity.getName());
		ins.startActivitySync(intent);
		final Activity activity = ins.waitForMonitorWithTimeout(
				startScreenMonitor, 3000);
		soloWrapper = new SoloWrapper(getInstrumentation(), activity);
		uiHelper = new UIHelper(soloWrapper);
		getInstrumentation().runOnMainSync(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				activity.getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}

		});
		getInstrumentation().removeMonitor(startScreenMonitor);
		testCaseEntity = new TestCaseEntity(testCaseId, priority);
	}

	private class SetUpException extends Exception {
		private static final long serialVersionUID = 1L;

		SetUpException(Throwable e) {  
			super(
					"Error in BasicTestCase.setUp()!" + e.getMessage() == null ? e
							.toString() : e.getMessage(), e);
		}
	}

	private class RunTestException extends Exception {
		private static final long serialVersionUID = 2L;

		RunTestException(Throwable e) {
			super("Error in MultiTestCase.runTest()  e:" + e.toString(), e);
		}
	}

}
