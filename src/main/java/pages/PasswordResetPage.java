package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Actions;

public class PasswordResetPage extends Actions {
	
	public PasswordResetPage(WebDriver driver) {
			
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (id = "email")
	WebElement EmailField;
	
	@FindBy (className = "btn__primary")
	WebElement sendButton;
	
	@FindBy(className = "phil-error")
	WebElement errorMessage;
	
	
	
	public void sendVerificationEmail(String email) {
		
		enterData(EmailField, email);
		clickOnElement(sendButton);
	}
	
	public String getErrorMessage() {
		
		String errorMsg = getText(errorMessage);
		
		return errorMsg;
	}
}