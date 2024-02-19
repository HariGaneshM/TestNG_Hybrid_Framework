package releaseTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.PageObjects;
import philAPIs.mockOrder;
import utilities.Configs;
import utilities.Utils;

public class MyRTCs extends PageObjects {
	
	@Test(description = "PA to Alt Med", retryAnalyzer = Utils.class)
	public void RTC1() throws Exception {
		
		goToPage(Configs.getProperty("ops.url").replace("env", env));
		ops.logIn(Configs.getProperty("ops.email"), Configs.getProperty("ops.password"));
		mockOrder.createOrder(Configs.getProperty("medName"), Configs.getProperty("stage"),
						Configs.getProperty("insType1"), Configs.getProperty("npiPref"));
		opsDash.searchOrder(mockOrder.rxNumber);
		Assert.assertEquals(sr.getPAPreference(), Configs.getProperty("exp.PAPref"));
		sr.routeToPharmacy();
		pt.sendToPP();
		navigateToPage(Configs.getProperty("ops.url").replace("env", env)+"/logout");
		ops.logIn(Configs.getProperty("pp.emailCDS"), Configs.getProperty("pp.passwordCDS"));
//		opsDash.searchOrder(mockOrder.rxNumber);
		ri.cannotFillToPA();
		Assert.assertEquals(opsDash.getPPStageLabel(), Configs.getProperty("exp.label1"));
	}
	
	@Test(description = "Insurance Upload", retryAnalyzer = Utils.class)
	public void RTC2() {
		
		goToPage(Configs.getProperty("ops.url").replace("env", env));
		ops.logIn(Configs.getProperty("ops.email"), Configs.getProperty("ops.password"));
		mockOrder.createOrder(Configs.getProperty("medName"), Configs.getProperty("stage"),
						Configs.getProperty("insType1"), Configs.getProperty("npi"));
		opsDash.searchOrder(mockOrder.rxNumber);
		String orderNumber = addRx.createAddRxOrder(mockOrder.patientEmail, "existing", 
				mockOrder.patientName, "patient", Configs.getProperty("medNDC"), Configs.getProperty("npiPref"));
		System.out.println(orderNumber);
		navigateToPage(Configs.getProperty("myPhil.url").replace("env", env));
		myPhil.logIn(mockOrder.patientEmail, Configs.getProperty("myPhil.password"));
		myAc.uploadInsuranceImg ("manager", "ins.jpg");
		myAc.checkForToastDisplayed();
		myAc.uploadInsuranceImg ("patient", "blank.jpg");
		myAc.checkForToastDisplayed();
	}
	
	@Test(description = "Ask for CN on Dash", retryAnalyzer = Utils.class)
	public void RTC3() {
		
		goToPage(Configs.getProperty("ops.url").replace("env", env));
		ops.logIn(Configs.getProperty("ops.email"), Configs.getProperty("ops.password"));
		mockOrder.createOrder(Configs.getProperty("medName"), Configs.getProperty("stage"),
						Configs.getProperty("insType1"), Configs.getProperty("npi"));
		opsDash.searchOrder(mockOrder.rxNumber);
		sr.moveToPAqueue();
		pa.startPA();
		pa.askForCN();
		Assert.assertEquals(opsDash.getStageLabel(), Configs.getProperty("exp.label2"));
	}
}
