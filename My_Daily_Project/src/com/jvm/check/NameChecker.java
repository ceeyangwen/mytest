package com.jvm.check;

import java.util.EnumSet;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic.Kind;

public class NameChecker {
	
	private final Messager message;
	
	NameCheckScanner scanner = new NameCheckScanner();
	
	public NameChecker(ProcessingEnvironment processingEnv) {
		this.message = processingEnv.getMessager();
	}
	
	/**
	 * 对Java中进行命名检查
	 * @param element
	 */
	public void checkNames(Element element) {
		scanner.scan(element);
	}
	
	/**
	 * 名称检查器实现类，集成了JDK1.8中提供的ElementScanner8
	 * 将会以Visitor模式访问抽象语法树中的元素
	 * @author GavinCee
	 *
	 */
	private class NameCheckScanner extends ElementScanner8<Void, Void> {
		
		/**
		 * 此方法用于检查Java类
		 */
		@Override
		public Void visitType(TypeElement e, Void p) {
			scan(e.getTypeParameters(), p);
			checkCamelCase(e, true);
			super.visitType(e, p);
			return null;
		}
		
		/**
		 * 此方法檢查方法的命名是否合法
		 */
		@Override
		public Void visitExecutable(ExecutableElement e, Void p) {
			if(e.getKind() == ElementKind.METHOD) {
				Name name = e.getSimpleName();
				if(name.contentEquals(e.getEnclosingElement().getSimpleName())) {
					message.printMessage(Kind.WARNING, "一個普通方法" + name + "不应该与类名重复，避免与构造函数混淆", e);
				}
				checkCamelCase(e, false);
			}
			super.visitExecutable(e, p);
			return null;
		}
		
		/**
		 * 检查变量名是否合法
		 */
		@Override
		public Void visitVariable(VariableElement e, Void p) {
			//如果这个变量时枚举或者是常量，则按大写检查，否则按照驼峰检查
			if(e.getKind() == ElementKind.ENUM_CONSTANT ||
					e.getConstantValue() != null || isConstant(e)) {
				checkAllCaps(e);
			} else {
				checkCamelCase(e, false);
			}
			return null;
		}
		
		private boolean isConstant(VariableElement e) {
			if(e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
				return true;
			} else if(e.getKind() == ElementKind.FIELD && 
					e.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL))) {
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * 检查传入的Element是否符合驼峰命名，如果不符合，则输出警告信息
		 * @param e
		 * @param initialCaps
		 */
		private void checkCamelCase(Element e, boolean initialCaps) {
			String name = e.getSimpleName().toString();
			boolean previousUpper = false;
			boolean conventional = true;
			int firstCodePoint = name.codePointAt(0);
			
			if(Character.isUpperCase(firstCodePoint)) {
				previousUpper = true;
				if(!initialCaps) {
					message.printMessage(Kind.WARNING, "名称" + name + "应当以小写字母开头", e);
					return;
				}
			} else if(Character.isLowerCase(firstCodePoint)) {
				if(initialCaps) {
					message.printMessage(Kind.WARNING, "名称" + name + "应当以小写字母开头", e);
					return;
				}
			} else {
				conventional = false;
			}
			
			if(conventional) {
				int cp = firstCodePoint;
				for(int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
					cp = name.codePointAt(i);
					if(Character.isUpperCase(cp)) {
						if(previousUpper) {
							conventional = false;
							break;
						}
						previousUpper = true;
					} else {
						previousUpper = false;
					}
				}
			} 
			
			if(!conventional){
				message.printMessage(Kind.WARNING, "名称" + name + "应当符合驼峰命名法", e);
			}
			
		}
		
		/**
		 * 大写命名检查，要求第一个字母必须是大写的英文字母，其余部分可以是下划线或者是大写字母 
		 * @param e
		 */
		private void checkAllCaps(Element e) {
			String name = e.getSimpleName().toString();
			boolean conventional = true;
			int firstCodePoint = name.codePointAt(0);
			
			if(!Character.isUpperCase(firstCodePoint)) {
				conventional = false;
			} else {
				boolean underScore = false;
				int cp = firstCodePoint;
				for(int i = Character.charCount(cp); i <name.length(); i += Character.charCount(cp)) {
					cp = name.codePointAt(i);
					if(cp == (int)('_')) {
						if(underScore) {
							conventional = false;
							break;
						}
						underScore = true;
					} else {
						underScore = false;
						if(!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
							conventional = false;
							break;
						}
					}
				}
			}
			
			if(!conventional) {
				message.printMessage(Kind.WARNING, "常量" + name + "应当全部以大写字母或下划线命名，并且以字母开头", e);
			}
		}
		
	}

}
