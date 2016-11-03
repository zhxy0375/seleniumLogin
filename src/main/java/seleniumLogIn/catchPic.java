/**   
* @Title: dsf.java 
* @Package seleniumLogIn 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhxy
* @date 2016年9月23日 下午2:51:03 
* @version V1.0   
*/
package seleniumLogIn;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.base.Strings;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

/**
 * @author zhxy
 *
 */
public class catchPic {
	
//	error opening data file ./tessdata/eng.traineddata
//	Please make sure the TESSDATA_PREFIX environment variable is set to the parent directory of your "tessdata" directory.
//	Failed loading language 'eng'
//	Tesseract couldn't load any languages!
	
	public static void main(String[] args) throws TesseractException {
		System.out.println("catch a PNG image"); 
		String env = "com";
		
		SimpleDateFormat dateFormater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long start = System.currentTimeMillis();
		System.out.println(dateFormater.format(new Date())+"---- start");
//		// 设置 chrome 的路径
		System.setProperty(
				"webdriver.chrome.driver",
				"./chrome/chromedriver");
		WebDriver driver =new ChromeDriver();
		driver.get(String.format("https://login.dooioo.%s/login", env));
 
		try {
			Thread.sleep(500);
		}catch (InterruptedException e1) {
			System.out.println(e1.getMessage());
		}
		WebElement captchaImage = driver.findElement(By.id("captchaImage"));
		
		String picPath = "/Users/zhxy/catchPic/ct"+RandomUtils.nextInt(10000)+".png";
		System.out.println("picPath:"+picPath);
		try {
			screenShotForElement(driver, captchaImage, picPath);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
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
	
	 
}
