package pages;

import org.openqa.selenium.support.PageFactory;

import static stepDefinitions.Hooks.driver;

public abstract class CommonPage {


    public CommonPage() {
        PageFactory.initElements(driver, this);
    }

    private AccountHomePage accountHomePage;
    private LoginPage loginPage;
    private EmailSMSPage emailSMSPage;

    public EmailSMSPage getEmailSMSPage() {
        if (emailSMSPage == null) {
            emailSMSPage = new EmailSMSPage();
        }
        return emailSMSPage;
    }

    public AccountHomePage getAccountHomePage() {
        if (accountHomePage == null) {
            accountHomePage = new AccountHomePage();
        }
        return accountHomePage;
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

}
