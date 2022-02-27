package de.leuphana.order_service.component.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//##############################################################################################################

@Entity
@Table(name="ORDERTABLE") //"ORDER" is not allowed as table name 
public class Order {
	
	@Id
	@GeneratedValue
	@Column(columnDefinition = "BINARY(16)")
	UUID orderId;
	
	private String orderDate;
	
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "order", orphanRemoval=true)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	@Column(columnDefinition = "BINARY(16)")
	private UUID customerId;

//##############################################################################################################
	
	public UUID getOrderId() {
		return this.orderId;
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getOrderDate() {
		return this.orderDate;
	}
	
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
	
	public UUID getCustomerId() {
		return this.customerId;
	}
	
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}
	
	public Order removeOrderItemFromOrder(OrderItem orderItem) {
		Iterator<OrderItem> itr = this.orderItems.iterator();
		UUID orderIdToBeRemoved = orderItem.getOrderItemId();
		while(itr.hasNext()) {	
			if (((OrderItem) itr).getOrderItemId() == orderIdToBeRemoved) {
				itr.remove();
			}
		}
		return this;
	}
	
	public void addOrderItemToOrder(OrderItem orderItem) {
		this.orderItems.add(orderItem);
	}
	
}