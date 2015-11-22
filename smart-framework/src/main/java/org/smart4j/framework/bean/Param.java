package org.smart4j.framework.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.smart4j.framework.util.CastUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.StringUtil;

/**
 * 请求参数对象
 * @author GavinCee
 *
 */
public class Param {
	
	private List<FormParam> formParamList;
	
	private List<FileParam> fileParamList;
	
	public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
		this.formParamList = formParamList;
		this.fileParamList = fileParamList;
	}
	
	public Param(List<FormParam> formParamList) {
		this.formParamList = formParamList;
	}
	
	//获取请求参数映射
	public Map<String, Object> getFieldMap() {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		if(CollectionUtil.isNotEmpty(formParamList)) {
			for (FormParam formParam : formParamList) {
				String fieldName = formParam.getFieldName();
				Object fieldValue = formParam.getFieldValue();
				if(fieldMap.containsKey(fieldName)) {
					fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
				}
				fieldMap.put(fieldName, fieldValue);
			}
		}
		return fieldMap;
	}
	
	//获取上传文件参数
	public Map<String, List<FileParam>> getFileMap() {
		Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
		if(CollectionUtil.isNotEmpty(fileParamList)) {
			for (FileParam fileParam : fileParamList) {
				String fieldName = fileParam.getFieldName();
				List<FileParam> fileParamList ;
				if(fileMap.containsKey(fieldName)) {
					fileParamList = fileMap.get(fieldName);
				} else {
					fileParamList = new ArrayList<FileParam>();
				}
				fileParamList.add(fileParam);
				fileMap.put(fieldName, fileParamList);
			}
		}
		return fileMap;
	}
	
	//获取所有上传文件
	public List<FileParam> getFileList(String fieldName) {
		return getFileMap().get(fieldName);
	}
	
	//获取唯一上传文件
	public FileParam getFile(String fieldName) {
		List<FileParam> fileParamList = getFileList(fieldName);
		if(CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
			return fileParamList.get(0);
		}
		return null;
	}
	
	public String getString(String name) {
		return CastUtil.castString(getFieldMap().get(name));
	}
	
	public double getDouble(String name) {
		return CastUtil.castDouble(getFieldMap().get(name));
	}

	public long getLong(String name) {
		return CastUtil.castLong(getFieldMap().get(name));
	}
	
	public int getInt(String name) {
		return CastUtil.castInt(getFieldMap().get(name));
	}
	
	public boolean getBoolean(String name) {
		return CastUtil.castBollean(getFieldMap().get(name));
	}
	
	public boolean isEmpty() {
		return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamList);
	}
}
