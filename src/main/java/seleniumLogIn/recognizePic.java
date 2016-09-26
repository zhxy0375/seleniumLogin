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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

/**
 * @author zhxy
 *
 */
public class recognizePic {
	
//	error opening data file ./tessdata/eng.traineddata
//	Please make sure the TESSDATA_PREFIX environment variable is set to the parent directory of your "tessdata" directory.
//	Failed loading language 'eng'
//	Tesseract couldn't load any languages!
	
	public static void main(String[] args) throws TesseractException {
		System.out.println("doOCR on a PNG image"); 
//		ITesseract instance = new Tesseract();
//        File imageFile = new File("/Users/zhxy/code.png");  
//        String result = instance.doOCR(imageFile);  
//        System.out.println(result);  
		
		
		
		 System.out.println(System.getProperty("user.dir")); 
//		 Runtime rt = Runtime.getRuntime();
//		 rt.exec(command)
		 
		
		ITesseract instance = new Tesseract();
        /*
		// 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
		BufferedImage textImage = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(panel.image, startX, startY, endX, endY));
		// 图片锐化,自己使用中影响识别率的主要因素是针式打印机字迹不连贯,所以锐化反而降低识别率
		// textImage = ImageHelper.convertImageToBinary(textImage);
		// 图片放大5倍,增强识别率(很多图片本身无法识别,放大5倍时就可以轻易识,但是考滤到客户电脑配置低,针式打印机打印不连贯的问题,这里就放大5倍)
		textImage = ImageHelper.getScaledInstance(textImage, endX * 5, endY * 5);
		*/
		String prefix = "jpeg";
//		String prefix = "png";
        try {
			File codeFile1 = new File("/Users/zhxy/code."+prefix);
			
			File codeFile = new File("/Users/zhxy/code4."+prefix);
			FileUtils.copyFile(codeFile1, codeFile);
			cleanImage(codeFile, "/Users/zhxy/",prefix);
			
			BufferedImage img = ImageIO.read(codeFile);
			
//			BufferedImage newImage = ImageHelper.convertImageToGrayscale(img);
			
            BufferedImage newImage = zoomOut(img, 2);
            newImage = ImageHelper.convertImageToBinary(newImage);

            ImageIO.write(newImage, prefix, codeFile);
            Thread.sleep(1000);
            
            
//			ImageHelper.getScaledInstance(iioSource, scale)
//			ImageHelper.convertImageToBinary(image)
			String result = instance.doOCR(codeFile);
			String fileName = codeFile.toString().substring(codeFile.toString().lastIndexOf("\\")+1);
			System.out.println("code 图片名：" + fileName +" 识别结果："+result);
		} catch (Exception e) {
			System.err.println(e.getMessage());
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
    public static void cleanImage(File sfile, String destDir,String type)  
            throws IOException  
    {  
        File destF = new File(destDir);  
        if (!destF.exists())  
        {  
            destF.mkdirs();  
        }  
  
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
  
        ImageIO.write(binaryBufferedImage, type, new File(destDir, sfile  
                .getName()));  
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
