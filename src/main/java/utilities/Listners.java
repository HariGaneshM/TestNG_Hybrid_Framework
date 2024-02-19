package utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Listners implements ITestListener{
	
	ExtentTest test;
	ExtentReports extent = Utils.getReports();
	  
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("--------------------------------------------------");
		System.out.println("Test: "+result.getMethod().getDescription()+" - started");
		test = extent.createTest(result.getMethod().getMethodName()+": "+result.getMethod().getDescription());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		System.out.println("Test: "+result.getMethod().getDescription()+" - passed");
		test.log(Status.PASS, MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
		String ssName = (result.getMethod().getDescription()+" Passed").replace(" ", "_");
    	try {
    		String base64Img = Utils.getBase64Img(ssName);
    		test.pass(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Img).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		System.out.println("Test: "+result.getMethod().getDescription()+" - failed");
		test.log(Status.FAIL, MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
		test.fail(result.getThrowable());
    	String ssName = (result.getMethod().getDescription()+" Failed").replace(" ", "_");
    	try {
    		String base64Img = Utils.getBase64Img(ssName);
    		test.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Img).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		test.log(Status.SKIP, MarkupHelper.createLabel("Test Skipped", ExtentColor.ORANGE));
		test.fail(result.getThrowable());
    	String ssName = (result.getMethod().getDescription()+" Skipped").replace(" ", "_");
    	try {
    		String base64Img = Utils.getBase64Img(ssName);
    		test.skip(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Img).build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
		
		extent.flush();
	}
}
