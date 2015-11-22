package org.smart4j.framework.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.FileUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;

/**
 * 文件上传助手类
 * @author GavinCee
 *
 */
public class UploadHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);
	
	private static ServletFileUpload servletFileUpload = null;
	
	//初始化
	public static void init(ServletContext servletContext) {
		File repository = (File)servletContext.getAttribute("javax.servlet.context.tempdir");
		servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
		int uploadLimit = ConfigHelper.getAppUploadLimit();
		if(0 != uploadLimit) {
			servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
		}
	}
	
	//判断请求是否是multipart类型
	public static boolean isMultipart(HttpServletRequest req) {
		return ServletFileUpload.isMultipartContent(req);
	}
	
	//创建请求对象
	public static Param createParam(HttpServletRequest req) throws IOException{
		List<FormParam> formParamList = new ArrayList<FormParam>();
		List<FileParam> fileParamList = new ArrayList<FileParam>();
		try {
			Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(req);
			if(CollectionUtil.isNotEmpty(fileItemListMap)) {
				for(Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap.entrySet()) {
					String fieldName = fileItemListEntry.getKey();
					List<FileItem> fileItemList = fileItemListEntry.getValue();
					if(CollectionUtil.isNotEmpty(fileItemList)) {
						for(FileItem fileItem : fileItemList) {
							if(fileItem.isFormField()) {
								String fieldValue = fileItem.getString("UTF-8");
								formParamList.add(new FormParam(fieldName, fieldValue));
							} else {
								String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
								if(StringUtil.isNotEmpty(fileName)) {
									long fileSize = fileItem.getSize();
									String contentType = fileItem.getContentType();
									InputStream is = fileItem.getInputStream();
									fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, is));
								}
							}
						}
					}
				}
			}
		} catch (FileUploadException e) {
			LOGGER.error("create param fail", e);
			throw new RuntimeException(e);
		}
		return new Param(formParamList, fileParamList);
	}
	
	//上传文件
	public static void uploadFile(String basePath, FileParam fileParam) {
		try {
			if(fileParam != null) {
				String filePath = basePath + fileParam.getFileName();
				FileUtil.createFile(filePath);
				InputStream is = new BufferedInputStream(fileParam.getInputStream());
				OutputStream os = new BufferedOutputStream(new FileOutputStream(filePath));
				StreamUtil.copyStream(is, os);
			}
		} catch (Exception e) {
			LOGGER.error("upload file fail", e);
			throw new RuntimeException(e);
		}
	}
	
	//批量上传文件
	public static void uploadFile(String basePath, List<FileParam> fileParamList) {
		try {
			if(CollectionUtil.isNotEmpty(fileParamList)) {
				for(FileParam fileParam : fileParamList) {
					uploadFile(basePath, fileParam);
				}
			}
		} catch (Exception e) {
			LOGGER.error("upload file fail", e);
			throw new RuntimeException(e);
		}
	}
}
