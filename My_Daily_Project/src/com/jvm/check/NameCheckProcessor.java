package com.jvm.check;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
//��*��ʾ֧������Annotation
@SupportedAnnotationTypes("*")
//ֻ֧��JDK1.8
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor {
	
	private NameChecker nameChecker;
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		nameChecker = new NameChecker(processingEnv);
	}

	/**
	 * ��������﷨���ĸ����ڵ�������Ƽ��
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if(!roundEnv.processingOver()) {
			for(Element element : roundEnv.getRootElements()) {
				nameChecker.checkNames(element);
			}
		}
		return false;
	}

}
