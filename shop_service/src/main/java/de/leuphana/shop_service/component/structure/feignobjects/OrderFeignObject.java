package de.leuphana.shop_service.component.structure.feignobjects;

import java.util.List;
import java.util.UUID;
import javax.persistence.GeneratedValue;

public class OrderFeignObject{

	@GeneratedValue
	private UUID orderId;
	
	private String orderDate;
	private List<OrderItemFeignObject> orderItmes;
	private UUID customerId;
	

	public UUID getOrderId() {return orderId;}
	public void setOrderId (UUID orderId) {this.orderId = orderId;}
	public String getOrderDate() {return orderDate;}
	public void setOrderDate(String orderDate) {this.orderDate = orderDate;}
	public List<OrderItemFeignObject> getOrderItems() {return orderItmes;}
	public void setOrderItems(List<OrderItemFeignObject> orderItmes) {this.orderItmes = orderItmes;}
	public UUID getCustomerId() {return customerId;}
	public void setCustomerId(UUID customerId) {this.customerId = customerId;}
	
}
