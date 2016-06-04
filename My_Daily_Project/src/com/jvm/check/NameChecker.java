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
	 * ��Java�н����������
	 * @param element
	 */
	public void checkNames(Element element) {
		scanner.scan(element);
	}
	
	/**
	 * ���Ƽ����ʵ���࣬������JDK1.8���ṩ��ElementScanner8
	 * ������Visitorģʽ���ʳ����﷨���е�Ԫ��
	 * @author GavinCee
	 *
	 */
	private class NameCheckScanner extends ElementScanner8<Void, Void> {
		
		/**
		 * �˷������ڼ��Java��
		 */
		@Override
		public Void visitType(TypeElement e, Void p) {
			scan(e.getTypeParameters(), p);
			checkCamelCase(e, true);
			super.visitType(e, p);
			return null;
		}
		
		/**
		 * �˷����z�鷽���������Ƿ�Ϸ�
		 */
		@Override
		public Void visitExecutable(ExecutableElement e, Void p) {
			if(e.getKind() == ElementKind.METHOD) {
				Name name = e.getSimpleName();
				if(name.contentEquals(e.getEnclosingElement().getSimpleName())) {
					message.printMessage(Kind.WARNING, "һ����ͨ����" + name + "��Ӧ���������ظ��������빹�캯������", e);
				}
				checkCamelCase(e, false);
			}
			super.visitExecutable(e, p);
			return null;
		}
		
		/**
		 * ���������Ƿ�Ϸ�
		 */
		@Override
		public Void visitVariable(VariableElement e, Void p) {
			//����������ʱö�ٻ����ǳ������򰴴�д��飬�������շ���
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
		 * ��鴫���Element�Ƿ�����շ���������������ϣ������������Ϣ
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
					message.printMessage(Kind.WARNING, "����" + name + "Ӧ����Сд��ĸ��ͷ", e);
					return;
				}
			} else if(Character.isLowerCase(firstCodePoint)) {
				if(initialCaps) {
					message.printMessage(Kind.WARNING, "����" + name + "Ӧ����Сд��ĸ��ͷ", e);
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
				message.printMessage(Kind.WARNING, "����" + name + "Ӧ�������շ�������", e);
			}
			
		}
		
		/**
		 * ��д������飬Ҫ���һ����ĸ�����Ǵ�д��Ӣ����ĸ�����ಿ�ֿ������»��߻����Ǵ�д��ĸ 
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
				message.printMessage(Kind.WARNING, "����" + name + "Ӧ��ȫ���Դ�д��ĸ���»�����������������ĸ��ͷ", e);
			}
		}
		
	}

}
