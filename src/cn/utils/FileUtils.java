package cn.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作工具类
 * 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 * @author ThinkGem
 * @version 2015-3-16
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**保存文件  
     * @param stream  
     * @param path  
     * @param filename  
     * @throws IOException  
     */  
    private static void saveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {         
        FileOutputStream fs=new FileOutputStream( path + "/"+ filename);   
        byte[] buffer =new byte[1024*1024];   
        int byteread = 0;    
        while ((byteread=stream.read(buffer))!=-1) {   
           fs.write(buffer,0,byteread);   
           fs.flush();   
        }    
        fs.close();   
        stream.close();         
    } 
	/**
	 * excel文件导入，同时把excel保存起来
	 * @param fileName 文件名
	 * @return 
	 */
	public static String saveImportExcelToDisk(MultipartFile multipartFile) {
		if (StringUtils.isBlank(multipartFile.getOriginalFilename())){
			throw new RuntimeException("导入文档为空!");
		}else if(!multipartFile.getOriginalFilename().toLowerCase().endsWith("xls") 
				&& !multipartFile.getOriginalFilename().toLowerCase().endsWith("xlsx")){    
        	throw new RuntimeException("文档格式不正确!");
        }  
		// 组装文件名 文件名+操作人名+YYYYMMDDHHmmss
		String filename = DateUtils.getDate("yyyyMMddHHmmss") + "_" + multipartFile.getOriginalFilename();
		// 获取保存路径
		String fileSavePath = "D:";
		// 保存文件
		try {
			saveFileFromInputStream(multipartFile.getInputStream(), fileSavePath, filename + "");
			return fileSavePath + "/" + filename;
		} catch (IOException e) {
			logger.error("保存文件失败。", e);
		}
		return null;
	}
}
