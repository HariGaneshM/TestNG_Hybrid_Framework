package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Actions;

public class LoginPage extends Actions {
		
	public LoginPage(WebDriver driver) {
		
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "email")
	WebElement emailField;
		
	@FindBy(id = "password")
	WebElement passwordField;
	
	@FindBy(id = "submitBtn")
	WebElement loginButton;
	
	By forgotPasswordLink = By.cssSelector("div.forgot-password a");
	
	public void logIn(String email, String password) {
			
		enterData(emailField, email);
		enterData(passwordField, password);
		clickOnElement(loginButton);
	}
	
	public void goToForgotPasswordPage() {
		
		driver.findElement(forgotPasswordLink).click();
	}
}
