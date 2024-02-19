package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import base.BaseTest;

public class Utils implements IRetryAnalyzer {
	
	public static ExtentReports getReports() {
		
		String destFile = System.getProperty("user.dir")+File.separator+"reports"+File.separator+"Test_Report.html";
		ExtentSparkReporter report = new ExtentSparkReporter(destFile);
		report.config().setReportName("Automation Test Report");
		report.config().setDocumentTitle("Automation Test Report");
		
		ExtentReports extentReport = new ExtentReports();
		extentReport.attachReporter(report);
		extentReport.setSystemInfo("Tester", "Hari Ganesh");
		
		return extentReport;
	}
	
	public static String getScreenshot(String name) throws IOException {
		
		TakesScreenshot screenshotDriver = (TakesScreenshot) BaseTest.driver;
	    File srcFile=screenshotDriver.getScreenshotAs(OutputType.FILE);
	    String destFilePath = System.getProperty("user.dir")+File.separator+"screenshots"+File.separator+name+".png";
		File destFile = new File(destFilePath);
		FileUtils.copyFile(srcFile, destFile);
		
		return destFilePath;
	}
	
	public static String getBase64Img(String name) throws IOException {
		
		String destFilePath = getScreenshot(name);
		byte[] file = FileUtils.readFileToByteArray(new File(destFilePath));
        String base64Img = Base64.encodeBase64String(file);
		
		return base64Img;
	}
	
	int count = 0;
	@Override
	public boolean retry(ITestResult result) {
		
		if (count<1) {
			System.out.println("Test: "+result.getMethod().getDescription()+" - failed");
			System.out.println("Test: "+result.getMethod().getDescription()+" - retrying");
			count++;
			
			return true;
			}
		
		return false;
	}
}
