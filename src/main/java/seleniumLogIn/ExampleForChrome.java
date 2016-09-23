package seleniumLogIn;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Strings;

public class ExampleForChrome {
	public static void main(String[] args) throws IOException {
		String env = "org";
		
		if(args == null){
			return ;
		}
		if(args != null && args.length>0 && (args[0].trim().equals("com") || args[0].trim().equals("net"))){
			env = args[0].trim();
		}
		String empNo = "103485";
		String pwd =  "dy#qy7ttko";
		int port = 18381;
		if(args.length >=2){
			port = Integer.valueOf(args[1].trim());
		}
		if(args.length >=4){
			 empNo = Strings.isNullOrEmpty(args[2].trim()) ? "103485":args[2].trim();
			 pwd =  Strings.isNullOrEmpty(args[3].trim()) ? "dy#qy7ttko":args[3].trim();
		}
		
		
		SimpleDateFormat dateFormater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long start = System.currentTimeMillis();
		System.out.println(dateFormater.format(new Date())+"---- start");
		// 设置 chrome 的路径
		System.setProperty(
				"webdriver.chrome.driver",
				"/Users/zhxy/Documents/workspaceOth/seleniumLogIn/chrome/chromedriver");
		WebDriver driver =new ChromeDriver();
		// 创建一个 ChromeDriver 的接口，用于连接 Chrome
//		@SuppressWarnings("deprecation")
//		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File("/Users/zhxy/Documents/workspaceOth/seleniumLogIn/chrome/chromedriver")).usingPort(port).build();
//				.usingChromeDriverExecutable(new File("chrome/chromedriver"))
		
//				.usingAnyFreePort().build();
//		service.start();
		// 创建一个 Chrome 的浏览器实例
//		WebDriver driver = new RemoteWebDriver(service.getUrl(),
//				DesiredCapabilities.chrome());
		driver.get(String.format("https://login.dooioo.%s/login", env));
		System.out.println("1 Page title is: " + driver.getTitle()+String.format("https://login.dooioo.%s/login", env));
		WebElement usercode = driver.findElement(By.id("usercode"));
		usercode.sendKeys(empNo);
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(pwd);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			System.out.println(e1.getMessage());
		}
		WebElement companyId = driver.findElement(By.xpath("//*[@id=\"selectCompanyContainer\"]/label[1]/input"));
		companyId.click();
		
		WebElement submit = driver.findElement(By.xpath("//*[@id='fm1']/input[1]"));
//		submit.click();  error: org.openqa.selenium.WebDriverException: Element is not clickable at point (411, 675). Other element would receive the click: .
		//http://stackoverflow.com/questions/11908249/debugging-element-is-not-clickable-at-point-error
		Actions actions = new Actions(driver);
		actions.moveToElement(submit).click().perform();
		
		
//		driver.switchTo().window("");
		
		// 显示搜索结果页面的 title
		System.out.println("2 Page title is: " + driver.getTitle());

		driver.get(String.format("http://blog.dooioo.%s",env));
		System.out.println("3 Page title is: " + driver.getTitle());
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			WebElement pop = driver.findElement(By.xpath("//*[@id=\"attendance_prompt\"]/div[2]/div[1]/div/a"));
			pop.click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		WebElement daka = null;
		try {
			daka = driver.findElement(By.id("__daka"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}   
		daka.click();
		
		System.out.println(dateFormater.format(new Date())+"---- daka time cost:"+(System.currentTimeMillis() -start));
		try {
			Thread.sleep(500);
			Alert alert = driver.switchTo().alert();  
		      String text = alert.getText();  
		      System.out.println(text);  
		      alert.accept();
//		      alert.dismiss(); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		driver.get(String.format("http://kaoqin.dooioo.%s",env));
		
		driver.switchTo().window(driver.getWindowHandle());
		System.out.println("4 Page title is: " + driver.getTitle());
		
		// 通过判断 title 内容等待搜索页面加载完毕，间隔10秒
		/*
		 * WebDriverWait类的构造方法接受了一个WebDriver对象和一个等待最长时间（10秒）。然后调用until方法，其中重写了ExpectedCondition接口中的apply方法，
		 * 让其返回一个WebElement,即加载完成的元素，然后点击。默认情况下，WebDriverWait每500毫秒调用一次ExpectedCondition，直到有成功的返回，当然如果超过设定的值还没有成功的返回，将抛出异常
		 */
		String dakaText = (new WebDriverWait(driver, 10)).until(
//				new ExpectedCondition<Boolean>() {
//			public Boolean apply(WebDriver d) {
//				return d.getTitle().toLowerCase().endsWith("ztree");
//			}
//		}
			new ExpectedCondition<WebElement>(){
				public WebElement apply(WebDriver d) {
	                return d.findElement(By.className("today"));  
	            }
		}).getText();
//		WebElement today = driver.findElement(By.className("today"));
//		String dakaText = today.getText();
		System.out.println(dakaText);
		long end = System.currentTimeMillis();
		System.out.println(dateFormater.format(new Date())+"---- total time cost:"+(end -start));
		// 关闭浏览器
//		driver.quit();
				
		// 关闭 ChromeDriver 接口
//		service.stop();
		System.exit(0);
	}
}
