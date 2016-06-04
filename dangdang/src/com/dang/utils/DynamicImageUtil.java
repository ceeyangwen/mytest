package com.dang.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DynamicImageUtil {
	private static final String[] CHARS = { "A", "B", "C", "D", "E", "1", "2",
		"3", "4", "5", "中", "发", "白", "东", "南" };
	private static final int WIDTH=200;
	private static final int HEIGHT=100;
	private static final int LINES=20;
	private static final int LEN=5;
	public static Map<String,BufferedImage> getDynamicImage(){
		BufferedImage image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g=image.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		Random ran=new Random();
		String code="";
		for(int i=0;i<LEN;i++){
			String chs=CHARS[ran.nextInt(CHARS.length)];
			code+=chs;
			g.setColor(Color.red);
			g.setFont(new Font(null,Font.BOLD+Font.ITALIC,40));
			g.drawString(chs, i*(WIDTH/LEN), HEIGHT*2/5);
		}
		//干扰线
		for(int i=0;i<LINES;i++){
			g.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
			g.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
		}
		Map<String,BufferedImage> map=new HashMap<String,BufferedImage>();
		map.put(code, image);
		return map;
	}
	public static InputStream getInputStream(BufferedImage image){
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		JPEGImageEncoder encode=JPEGCodec.createJPEGEncoder(bos);
		try {
			encode.encode(image);
			byte[] bys=bos.toByteArray();
			InputStream bis=new ByteArrayInputStream(bys);
			return bis;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
