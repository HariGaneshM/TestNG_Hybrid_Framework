package base;

import org.testng.annotations.BeforeMethod;

import pages.AddRxPage;
import pages.HomePage;
import pages.LoginPage;
import pages.myPhil.AccountPage;
import pages.myPhil.MyPhilLoginPage;
import pages.stages.CreatePAPage;
import pages.stages.PhilRxTransferPage;
import pages.stages.RunInsurancePage;
import pages.stages.ScrubAndRoutePage;
import utilities.Actions;

public class PageObjects extends BaseTest {
	
	public Actions actions;
	public LoginPage ops;
	public HomePage opsDash;
	public ScrubAndRoutePage sr;
	public PhilRxTransferPage pt;
	public CreatePAPage pa;
	public RunInsurancePage ri;
	public AddRxPage addRx;
	public MyPhilLoginPage myPhil;
	public AccountPage myAc;
	
	@BeforeMethod
	public void setPageObjects() {
		
		actions = new Actions(driver);
		ops = new LoginPage(driver);
		opsDash = new HomePage(driver);
		sr = new ScrubAndRoutePage(driver);
		pa = new CreatePAPage(driver);
		pt = new PhilRxTransferPage(driver);
		ri = new RunInsurancePage(driver);
		addRx = new AddRxPage(driver);
		myPhil = new MyPhilLoginPage(driver);
		myAc = new AccountPage(driver);
	}
}
