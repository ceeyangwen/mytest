package com.dang.actions.user;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Map;

import com.dang.actions.BaseAction;
import com.dang.utils.DynamicImageUtil;

public class ImageCodeAction extends BaseAction{
	private InputStream imageStream;
	
	public String execute(){
		//动态生成图片
		Map<String,BufferedImage> map=DynamicImageUtil.getDynamicImage();
		//取得图片上的字符串
		String code=map.keySet().iterator().next();
		session.put("code", code);
		BufferedImage image=map.get(code);
		//将图片给imageStream赋值
		imageStream=DynamicImageUtil.getInputStream(image);
		return "success";
	}
	public InputStream getImageStream() {
		return imageStream;
	}

	public void setImageStream(InputStream imageStream) {
		this.imageStream = imageStream;
	}
	
}
