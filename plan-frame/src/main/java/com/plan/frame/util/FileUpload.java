package com.plan.frame.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 上传文件 创建人：FH 创建时间：2014年12月23日
 * 
 * @version
 */
public class FileUpload {
	
	static Logger logger=Logger.getLogger(FileUpload.class);

	/**
	 * @param file
	 *            //文件对象
	 * @param filePath
	 *            //上传路径
	 * @param fileName
	 *            //文件名
	 * @return 文件名
	 */
	public static String fileUp(MultipartFile file, String filePath,
                                String fileName) {
		String extName = ""; // 扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
				extName = file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf("."));
			}
			
			copyFile(file.getInputStream(), filePath.trim(), fileName + extName);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return fileName + extName;
	}
	/**
	 * 不包含扩展名的上传
	 * @param file
	 *            //文件对象
	 * @param filePath
	 *            //上传路径
	 * @param fileName
	 *            //文件名
	 * @return 文件名
	 */
	public static String fileUpNotExtName(MultipartFile file, String filePath,
                                          String fileName) {
		try {
			copyFile(file.getInputStream(), filePath.trim(), fileName );
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return fileName ;
	}
	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * 
	 * @param in
	 * @param
	 * @throws IOException
	 */
	public static String copyFile(InputStream in, String dir, String realName)
			throws IOException {
		File file = FileUtil.createFile(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		FileUtils.copyInputStreamToFile(in, file);
		return realName;
	}
}
