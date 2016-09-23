/**   
* @Title: dsf.java 
* @Package seleniumLogIn 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zhxy
* @date 2016年9月23日 下午2:51:03 
* @version V1.0   
*/
package seleniumLogIn;

import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

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
        
        try {
			File codeFile = new File("/Users/zhxy/code.png");
			String result = instance.doOCR(codeFile);
			String fileName = codeFile.toString().substring(codeFile.toString().lastIndexOf("\\")+1);
			System.out.println("code 图片名：" + fileName +" 识别结果："+result);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
