package seleniumLogIn;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
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

import net.sourceforge.htmlunit.corejs.javascript.EcmaError;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;

public class ExampleForChrome2 {
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
		
		if(args.length >=4){
			 empNo = Strings.isNullOrEmpty(args[2].trim()) ? "103485":args[2].trim();
			 pwd =  Strings.isNullOrEmpty(args[3].trim()) ? "dy#qy7ttko":args[3].trim();
		}
		
		
		SimpleDateFormat dateFormater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long start = System.currentTimeMillis();
		System.out.println(dateFormater.format(new Date())+"---- start");
//		// 设置 chrome 的路径
		System.setProperty(
				"webdriver.chrome.driver",
				"./chrome/chromedriver");
		WebDriver driver =new ChromeDriver();
		int loopCount = 5;
		int n = 1;
		while( !logInPageFlow(env, empNo, pwd, driver) ){
			if(n>=loopCount){
//				break;
				return;
			}
			n++;
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
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
		
		try {
			// 通过判断 title 内容等待搜索页面加载完毕，间隔10秒
			/*
			 * WebDriverWait类的构造方法接受了一个WebDriver对象和一个等待最长时间（10秒）。然后调用until方法，其中重写了ExpectedCondition接口中的apply方法，
			 * 让其返回一个WebElement,即加载完成的元素，然后点击。默认情况下，WebDriverWait每500毫秒调用一次ExpectedCondition，直到有成功的返回，当然如果超过设定的值还没有成功的返回，将抛出异常
			 */
			String dakaText = (new WebDriverWait(driver, 10)).until(
//					new ExpectedCondition<Boolean>() {
//				public Boolean apply(WebDriver d) {
//					return d.getTitle().toLowerCase().endsWith("ztree");
//				}
//			}
				new ExpectedCondition<WebElement>(){
					public WebElement apply(WebDriver d) {
		                return d.findElement(By.className("today"));  
		            }
			}).getText();
//			WebElement today = driver.findElement(By.className("today"));
//			String dakaText = today.getText();
			System.out.println(dakaText);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		long end = System.currentTimeMillis();
		System.out.println(dateFormater.format(new Date())+"---- total time cost:"+(end -start));
		// 关闭浏览器
//		driver.quit();
		// 关闭 ChromeDriver 接口
//		service.stop();
		System.exit(0);
	}

	public static boolean logInPageFlow(String env, String empNo, String pwd, WebDriver driver)  {
		driver.get(String.format("https://login.dooioo.%s/login", env));
		System.out.println("1 Page title is: " + driver.getTitle()+String.format("https://login.dooioo.%s/login", env));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			System.out.println(e1.getMessage());
		}
		WebElement usercode = driver.findElement(By.id("usercode"));
		WebElement password = driver.findElement(By.id("password"));
		
		usercode.sendKeys(empNo);
		password.sendKeys(pwd);
		
		WebElement captchaImage = driver.findElement(By.id("captchaImage"));
		
		String prefix = "png";
		
		String picPath = "/Users/zhxy/codeNew."+prefix;
		try {
			screenShotForElement(driver, captchaImage, picPath,true);
		} catch (InterruptedException e2) {
			System.out.println(e2.getMessage());
			return false;
		}
		
		ITesseract instance = new Tesseract();
		
        try {
			File codeFile = new File(picPath);
			String result = instance.doOCR(codeFile);
			String fileName = codeFile.toString().substring(codeFile.toString().lastIndexOf("\\")+1);
			String resultFinal = result.replace(" ", "");
			System.out.println("code 图片名：" + fileName +" 识别结果："+result+" ,resultFinal:"+resultFinal);
			
			WebElement captcha = driver.findElement(By.id("captcha"));
			captcha.sendKeys(resultFinal);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		
		WebElement companyId = driver.findElement(By.xpath("//*[@id=\"selectCompanyContainer\"]/label[1]/input"));
		companyId.click();
		try {
			WebElement submit = driver.findElement(By.xpath("//*[@id='fm1']/input[1]"));
//			submit.click();  error: org.openqa.selenium.WebDriverException: Element is not clickable at point (411, 675). Other element would receive the click: .
			//http://stackoverflow.com/questions/11908249/debugging-element-is-not-clickable-at-point-error
			Actions actions = new Actions(driver);
			actions.moveToElement(submit).click().perform();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		try {
			Thread.sleep(500);
		}catch (InterruptedException e1) {
			System.out.println(e1.getMessage());
		}
		
		try {
			WebElement captcha = driver.findElement(By.id("captcha"));
			if(captcha !=null){
				System.out.println("验证没通过");
				return false;
			}
		} catch (NoSuchElementException e){
			return true;
		}
		
		return true;
	}
	
	/**
     * This method for screen shot element
     * 
     * @param driver
     * @param element
     * @param path
     * @param isYouHua 是否去噪
     * @throws InterruptedException
     */
    public static void screenShotForElement(WebDriver driver,
            WebElement element, String path,boolean isYouHua) throws InterruptedException {
        File scrFile = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
        String screenShotPath = path.substring(0,path.indexOf("."))+"_"+path.substring(path.indexOf("."));
        System.out.println(path+","+isYouHua+","+screenShotPath);
        File file = new File(screenShotPath);  
        
        try {
        	FileUtils.copyFile(scrFile, file);
        	
            Point p = element.getLocation();
            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            System.out.println(String.format("x:%s,y:%s,w:%s,h:%s;", p.getX(), p.getY(),width, height));
            Rectangle rect = new Rectangle(width, height);
            
            // 这里不能用 copy出来的 file，应该用scrFile
            BufferedImage img = ImageIO.read(scrFile);
            BufferedImage dest = img.getSubimage(p.getX(), p.getY(),
                    rect.width, rect.height);
            
            File destFile = new File(path);
            ImageIO.write(dest, "png", destFile);
        	Thread.sleep(1000);
            if(isYouHua){
				cleanImage(destFile, path);
            }
//            else{
//                FileUtils.copyFile(destFile, new File(path));
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method for screen shot element
     * 
     * @param driver
     * @param element
     * @param path
     * @throws InterruptedException
     */
    public static void screenShotForElement(WebDriver driver,
            WebElement element, String path) throws InterruptedException {
        File scrFile = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
        try {
            Point p = element.getLocation();
            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            Rectangle rect = new Rectangle(width, height);
            BufferedImage img = ImageIO.read(scrFile);
            BufferedImage dest = img.getSubimage(p.getX(), p.getY(),
                    rect.width, rect.height);
            ImageIO.write(dest, "png", scrFile);
            Thread.sleep(1000);
            FileUtils.copyFile(scrFile, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 
	* @summary 放大图片
	* @author zhxy
	* @date 2016年9月26日 下午2:39:41
	* @version v1
	* @param img
	* @param times
	* @return
	* @return BufferedImage
	*/
	public static BufferedImage zoomOut(BufferedImage img, int times) {
		int width = img.getWidth()*times;
	
		int height = img.getHeight()*times;
	
		BufferedImage newImage = new BufferedImage(width,height,img.getType());
	
		Graphics g = newImage.getGraphics();
		g.drawImage(img, 0,0,width,height,null);
		g.dispose();
		return newImage;
	}
	
	
	
	/** 
	 *  
	 * @param sfile 
	 *            需要去噪的图像 
	 * @param destDir 
	 *            去噪后的图像保存地址 
	 * @throws IOException 
	 */  
	public static void cleanImage(File sfile, String path)  
	        throws IOException  
	{  
	    BufferedImage bufferedImage = ImageIO.read(sfile);  
	    int h = bufferedImage.getHeight();  
	    int w = bufferedImage.getWidth();  
	
	    // 灰度化  
	    int[][] gray = new int[w][h];  
	    for (int x = 0; x < w; x++)  
	    {  
	        for (int y = 0; y < h; y++)  
	        {  
	            int argb = bufferedImage.getRGB(x, y);  
	            // 图像加亮（调整亮度识别率非常高）  
	            int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);  
	            int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);  
	            int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);  
	            if (r >= 255)  
	            {  
	                r = 255;  
	            }  
	            if (g >= 255)  
	            {  
	                g = 255;  
	            }  
	            if (b >= 255)  
	            {  
	                b = 255;  
	            }  
	            gray[x][y] = (int) Math  
	                    .pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2)  
	                            * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);  
	        }  
	    }  
	
	    // 二值化  
	    int threshold = ostu(gray, w, h);  
	    BufferedImage binaryBufferedImage = new BufferedImage(w, h,  
	            BufferedImage.TYPE_BYTE_BINARY);  
	    for (int x = 0; x < w; x++)  
	    {  
	        for (int y = 0; y < h; y++)  
	        {  
	            if (gray[x][y] > threshold)  
	            {  
	                gray[x][y] |= 0x00FFFF;  
	            } else  
	            {  
	                gray[x][y] &= 0xFF0000;  
	            }  
	            binaryBufferedImage.setRGB(x, y, gray[x][y]);  
	        }  
	    }  
	/*
	    // 矩阵打印  
	    for (int y = 0; y < h; y++)  
	    {  
	        for (int x = 0; x < w; x++)  
	        {  
	            if (isBlack(binaryBufferedImage.getRGB(x, y)))  
	            {  
	                System.out.print("*");  
	            } else  
	            {  
	                System.out.print(" ");  
	            }  
	        }  
	        System.out.println();  
	    }  
	*/
	    ImageIO.write(binaryBufferedImage, "png", new File(path));  
	}  
	
	public static boolean isBlack(int colorInt)  
	{  
	    Color color = new Color(colorInt);  
	    if (color.getRed() + color.getGreen() + color.getBlue() <= 300)  
	    {  
	        return true;  
	    }  
	    return false;  
	}  
	
	public static boolean isWhite(int colorInt)  
	{  
	    Color color = new Color(colorInt);  
	    if (color.getRed() + color.getGreen() + color.getBlue() > 300)  
	    {  
	        return true;  
	    }  
	    return false;  
	}  
	
	public static int isBlackOrWhite(int colorInt)  
	{  
	    if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730)  
	    {  
	        return 1;  
	    }  
	    return 0;  
	}  
	
	public static int getColorBright(int colorInt)  
	{  
	    Color color = new Color(colorInt);  
	    return color.getRed() + color.getGreen() + color.getBlue();  
	}  
	
	public static int ostu(int[][] gray, int w, int h)  
	{  
	    int[] histData = new int[w * h];  
	    // Calculate histogram  
	    for (int x = 0; x < w; x++)  
	    {  
	        for (int y = 0; y < h; y++)  
	        {  
	            int red = 0xFF & gray[x][y];  
	            histData[red]++;  
	        }  
	    }  
	
	    // Total number of pixels  
	    int total = w * h;  
	
	    float sum = 0;  
	    for (int t = 0; t < 256; t++)  
	        sum += t * histData[t];  
	
	    float sumB = 0;  
	    int wB = 0;  
	    int wF = 0;  
	
	    float varMax = 0;  
	    int threshold = 0;  
	
	    for (int t = 0; t < 256; t++)  
	    {  
	        wB += histData[t]; // Weight Background  
	        if (wB == 0)  
	            continue;  
	
	        wF = total - wB; // Weight Foreground  
	        if (wF == 0)  
	            break;  
	
	        sumB += (float) (t * histData[t]);  
	
	        float mB = sumB / wB; // Mean Background  
	        float mF = (sum - sumB) / wF; // Mean Foreground  
	
	        // Calculate Between Class Variance  
	        float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);  
	
	        // Check if new maximum found  
	        if (varBetween > varMax)  
	        {  
	            varMax = varBetween;  
	            threshold = t;  
	        }  
	    }  
	
	    return threshold;  
	} 
}
