package com.dang.actions.order;

import java.util.ArrayList;
import java.util.List;

import com.dang.actions.BaseAction;
import com.dang.pojo.Address;
import com.dang.pojo.CartItem;
import com.dang.pojo.Order;
import com.dang.pojo.OrderItem;
import com.dang.pojo.User;
import com.dang.services.Cart;
import com.dang.services.CartFactory;
import com.dang.services.OrderHandle;
import com.dang.services.impl.OrderHandleImpl;
import com.dang.utils.Constant;

public class ConfirmAction extends BaseAction{
	//input
	private String receiveName;
	private String fullAddress;
	private String postalCode;
	private String phone;
	private String mobile;
	//output
	private int orderId;
	private double allPrice;
	
	public String execute(){
		List<OrderItem> items=new ArrayList<OrderItem>();
		Address address=new Address();
		address.setAddress(fullAddress);
		address.setMobile(mobile);
		address.setName(receiveName);
		address.setPhone(phone);
		address.setPostalCode(postalCode);
		//获得购物车对象
		Cart cart=CartFactory.getCart(session);
		List<CartItem> list=cart.getBuyList();
		if(cart.getBuyList().size()==0){
			return "main";
		}
		User user=(User)session.get(Constant.USER);
		if(user==null){
			return "login";
		}
		address.setUser(user);
		Order order=new Order();
		order.setStatus(Constant.WAIT_PAY);
		order.setAddress(address);
		order.setTime(System.currentTimeMillis());
		double total=0;
		for(CartItem ci:list){
			OrderItem item=new OrderItem();
			item.setPnum(ci.getQty());
			item.setPro(ci.getPro());
			item.setAmount(ci.getPro().getDangPrice()*ci.getQty());
			item.setOrder(order);
			total+=item.getAmount();
			items.add(item);
		}
		order.setTotal(total);
		OrderHandle handle=new OrderHandleImpl();
		try {
			orderId=handle.confirm(items, order,address);
			allPrice=order.getTotal();
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "main";
		}
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public double getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}
}
