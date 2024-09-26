package releaseTests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AddRxPage;
import pages.HomePage;
import pages.LoginPage;
import pages.PasswordResetPage;
import pages.myPhil.AccountPage;
import pages.myPhil.MyPhilLoginPage;
import pages.stages.CreatePAPage;
import pages.stages.PhilRxTransferPage;
import pages.stages.RunInsurancePage;
import pages.stages.ScrubAndRoutePage;
import philAPIs.mockOrder;
import utilities.Configs;
import utilities.Utils;

public class MyRTCs extends BaseTest {
	
	@Test(description = "PA to Alt Med", retryAnalyzer = Utils.class)
	public void RTC1() throws Exception {
		
		LoginPage ops = new LoginPage(getDriver());
		HomePage opsDash = new HomePage(getDriver());
		ScrubAndRoutePage sr = new ScrubAndRoutePage(getDriver());
		PhilRxTransferPage pt = new PhilRxTransferPage(getDriver());
		RunInsurancePage ri = new RunInsurancePage(getDriver());
		
		goToPage(Configs.getProperty("ops.url").replace("env", env));
		ops.logIn(Configs.getProperty("ops.email"), Configs.getProperty("ops.password"));
		HashMap<String, String> orderDetails = 
				mockOrder.createOrder(Configs.getProperty("medName"), Configs.getProperty("stage"),
						Configs.getProperty("insType1"), Configs.getProperty("npiPref"));
		opsDash.searchOrder((String)orderDetails.get("rxNumber"));
		Assert.assertEquals(sr.getPAPreference(), Configs.getProperty("exp.PAPref"));
		sr.routeToPharmacy();
		pt.sendToPP();
		navigateToPage(Configs.getProperty("ops.url").replace("env", env)+"/logout");
		ops.logIn(Configs.getProperty("pp.emailCDS"), Configs.getProperty("pp.passwordCDS"));
//		opsDash.searchOrder(mockOrder.rxNumber);
		ri.cannotFillToPA((String)orderDetails.get("rxNumber"));
		Assert.assertEquals(opsDash.getPPStageLabel(), Configs.getProperty("exp.label1"));
	}
	
	@Test(description = "Insurance Upload", retryAnalyzer = Utils.class)
	public void RTC2() {
		
		LoginPage ops = new LoginPage(getDriver());
		HomePage opsDash = new HomePage(getDriver());
		AddRxPage addRx = new AddRxPage(getDriver());
		MyPhilLoginPage myPhil = new MyPhilLoginPage(getDriver());
		AccountPage myAc = new AccountPage(getDriver());
		
		goToPage(Configs.getProperty("ops.url").replace("env", env));
		ops.logIn(Configs.getProperty("ops.email"), Configs.getProperty("ops.password"));
		HashMap<String, String> orderDetails = 
				mockOrder.createOrder(Configs.getProperty("medName"), Configs.getProperty("stage"),
						Configs.getProperty("insType1"), Configs.getProperty("npi"));
		opsDash.searchOrder((String)orderDetails.get("rxNumber"));
		String orderNumber = addRx.createAddRxOrder((String)orderDetails.get("patientEmail"), "existing", 
				(String)orderDetails.get("patientName"), "patient", Configs.getProperty("medNDC"), Configs.getProperty("npiPref"));
		System.out.println(orderNumber);
		navigateToPage(Configs.getProperty("myPhil.url").replace("env", env));
		myPhil.logIn((String)orderDetails.get("patientEmail"), Configs.getProperty("myPhil.password"));
		myAc.uploadInsuranceImg ("manager", "ins.jpg");
		myAc.checkForToastDisplayed();
		myAc.uploadInsuranceImg ("patient", "blank.jpg");
		myAc.checkForToastDisplayed();
	}
	
	@Test(description = "Ask for CN on Dash", retryAnalyzer = Utils.class)
	public void RTC3() {
		
		LoginPage ops = new LoginPage(getDriver());
		HomePage opsDash = new HomePage(getDriver());
		ScrubAndRoutePage sr = new ScrubAndRoutePage(getDriver());
		CreatePAPage pa = new CreatePAPage(getDriver());
		
		goToPage(Configs.getProperty("ops.url").replace("env", env));
		ops.logIn(Configs.getProperty("ops.email"), Configs.getProperty("ops.password"));
		HashMap<String, String> orderDetails = 
				mockOrder.createOrder(Configs.getProperty("medName"), Configs.getProperty("stage"),
						Configs.getProperty("insType1"), Configs.getProperty("npi"));
		opsDash.searchOrder((String)orderDetails.get("rxNumber"));
		sr.moveToPAqueue();
		pa.startPA();
		pa.askForCN();
		Assert.assertEquals(opsDash.getStageLabel(), Configs.getProperty("exp.label2"));
	}
	
	@Test
	public void FogotPasswordflow() {
		
		LoginPage ops = new LoginPage(getDriver());
		PasswordResetPage p = new PasswordResetPage(getDriver());
				
		goToPage(Configs.getProperty("ops.url").replace("env", env));
		ops.goToForgotPasswordPage();
		p.sendVerificationEmail(Configs.getProperty("ops.email"));
		
		Assert.assertEquals(p.getErrorMessage(), Configs.getProperty("exp.forgotpasswordError"));
	}
}
