package com.dang.services;

import java.util.List;

import com.dang.pojo.CartItem;

public interface Cart {
	/**
	 * ����
	 * @param pid ��Ʒid
	 * @return
	 */
	public boolean buy(int pid);
	/**
	 * ɾ��
	 * @param pid
	 */
	public void delete(int pid);
	/**
	 * �ָ�
	 * @param pid
	 */
	public void recoyery(int pid);
	/**
	 * ��������
	 * @param pid ��Ʒid
 	 * @param pnum ��������
	 */
	public void update(int pid,int pnum);
	//��ȡȷ�Ϲ������Ʒ�б�
	public List<CartItem> getBuyList();
	//��ȡɾ������Ʒ�б�
	public List<CartItem> getDelList();
	//ͳ��ȷ�Ϲ�����Ʒ�Ľ��
	public double count();
}
