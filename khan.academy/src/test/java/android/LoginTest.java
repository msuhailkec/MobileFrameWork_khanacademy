package android;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Method;

import org.testng.AssertJUnit;
import org.testng.ITestResult;

import com.appiumserverconfig.LaunchAppiumServer;
import com.capabilities.Capabilities;
import com.pom.LoginPOM;
import com.report.ScreenShot;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;


public class LoginTest {

	private static AndroidDriver<AndroidElement> driver;
	private LoginPOM loginPOM;
	private ScreenShot screenShot;
	AppiumDriverLocalService service;
	String tcName;

	@BeforeClass
	public void setUpBeforeClass() throws Exception {
		service = LaunchAppiumServer.startServer();
		Capabilities.launchEmulator();
		driver = Capabilities.setCapabilities_Android();
		screenShot = new ScreenShot(driver);
		loginPOM = new LoginPOM(driver); 
		
	}


	@BeforeMethod
	public void setUp(Method method){
		tcName = method.getName();
		
	}
	@AfterMethod
	public void screenShot(ITestResult result)
	{
		if(ITestResult.FAILURE==result.getStatus())
		{
			screenShot.captureScreenShot(tcName);
		}
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		
		driver.quit();
	    service.stop();
	}

	
	

	@Test(priority =1)
	public void invalidLogin() throws Exception  {
		
		
		loginPOM.clickDismiss();
		Thread.sleep(5000);
		loginPOM.clickDismiss();
		loginPOM.clickLoginOption();
		loginPOM.clickLoginOption();
		loginPOM.sendUserName("msuhail.kec");
		loginPOM.sendPassword("Nimda1234sdda");
		loginPOM.clickSignIn(); 
		AssertJUnit.assertEquals(false, loginPOM.invalidLogin());
		Thread.sleep(5000);
		
		
	}
	
	@Test(priority =2)
	public void validLoginTest() throws Exception  {
		
		loginPOM.sendUserName("msuhail.kec@gmail.com");
		loginPOM.sendPassword("Nimda1234");
		loginPOM.clickSignIn(); 
		AssertJUnit.assertEquals(true, loginPOM.userLogedIn());
		Thread.sleep(5000);
		
		
	}
	
	
	@Test(priority =3,dependsOnMethods = { "validLoginTest" })
	public void validSuccessfulLogoutTest() throws Exception  {
		
		
		loginPOM.clickSignOut(); 
		AssertJUnit.assertEquals(true, loginPOM.successfullLogout());
		Thread.sleep(5000);
		
		
	}
}
