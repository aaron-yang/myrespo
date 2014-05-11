package com.automation.demo.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.os.PowerManager;
import android.test.InstrumentationTestCase;
import android.view.WindowManager;

import com.automation.demo.LoginActivity;
import com.automation.demo.test.entity.TestCaseEntity;
import com.automation.demo.test.entity.TestCaseEntity.TestCaseStatus;
import com.automation.deom.test.utils.LogUtil;
import com.automation.deom.test.utils.NetworkUtil;
import com.automation.deom.test.utils.UIUtil;
import com.automation.deom.test.utils.Utils;

public class BasicTestCase extends InstrumentationTestCase {
	private static UIUtil uiUtil = null;
	private Class<?> startActivity = LoginActivity.class;
	private static PowerManager.WakeLock wakeScreenObject;
	private TestCaseEntity testCaseEntity = null;
	private TestCaseStatus status = TestCaseStatus.UNKNOWN;

	protected void setUp(String testCaseId, String priority) throws Exception {
		try {
			// TODO Auto-generated method stub
			super.setUp();
			if (wakeScreenObject == null) {
				wakeScreenObject = Utils.wakeScreen(this);
			}
			NetworkUtil.setAirplaneModeOffAndNetworkOn(getInstrumentation()
					.getTargetContext());
			Utils.unlock(getInstrumentation());
			Instrumentation ins = getInstrumentation();
			ActivityMonitor startScreenMonitor = ins.addMonitor(
					startActivity.getName(), null, false);
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setClassName(ins.getTargetContext(), startActivity.getName());
			ins.startActivitySync(intent);
			final Activity activity = getInstrumentation().waitForMonitor(
					startScreenMonitor);
			uiUtil = new UIUtil(getInstrumentation(), activity);
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
		} catch (Exception ex) {
			LogUtil.e(LogUtil.TAG, "Error in setup:" + ex);
			status = TestCaseStatus.ERROR;
			uiUtil.takeScreenShot(this.getClass().getSimpleName());
			throw new SetupException(ex);
		}
	}

	@Override
	protected void runTest() throws Throwable {
		try {
			super.runTest();
		} catch (junit.framework.AssertionFailedError afe) {
			LogUtil.w(LogUtil.TAG, "AssertionFailedError:" + afe);
			status = TestCaseStatus.FAILURE;
			uiUtil.takeScreenShot(this.getClass().getSimpleName());
			throw afe;
		} catch (android.test.AssertionFailedError aafe) {
			LogUtil.w(LogUtil.TAG, "AssertionFailedError:" + aafe);
			status = TestCaseStatus.FAILURE;
			uiUtil.takeScreenShot(this.getClass().getSimpleName());
			throw aafe;
		} catch (Throwable th) {
			LogUtil.w(LogUtil.TAG, "Error in runTest:" + th);
			status = TestCaseStatus.ERROR;
			uiUtil.takeScreenShot(this.getClass().getSimpleName());
			throw new RunTestException(th);
		}
	}
	
	

	@Override
	protected void tearDown() throws Exception{
		try {
			if (wakeScreenObject != null) {
				wakeScreenObject.release();
				wakeScreenObject = null;
			}
			uiUtil.finishOpenedActivities();
			uiUtil.finalize();
			uiUtil = null;
			status = TestCaseStatus.PASSED;
			Utils.saveTestResult(status, testCaseEntity);
			super.tearDown();
		}catch (Throwable e) {
			LogUtil.e(LogUtil.TAG, "Error in basicTearDown():" + e);
			status = TestCaseStatus.ERROR;
			uiUtil.takeScreenShot(this.getClass().getSimpleName());
			throw new TearDownException(e);
		}
	}

	protected UIUtil getUIUtil() {
		return uiUtil;
	}

	private class SetupException extends Exception {
		private static final long serialVersionUID = 1L;

		SetupException(Throwable e) {
			super("Error in BasicTestCase.setup() e:" + e.toString());
		}

	}

	private class RunTestException extends Exception {
		private static final long serialVersionUID = 2L;

		RunTestException(Throwable e) {
			super("Error in BasictestCase.runTest()  e:" + e.toString(), e);
		}
	}
	
	private class TearDownException extends Exception {
        private static final long serialVersionUID = 3L;

        TearDownException(Throwable e) {
            super("Error in BasicTestCase.tearDown()  e:" + e.getMessage()==null?e.toString():e.getMessage(),e);
        }
    }

}
