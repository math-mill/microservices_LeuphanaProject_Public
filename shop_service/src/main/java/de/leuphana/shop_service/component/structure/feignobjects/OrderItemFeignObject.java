package de.leuphana.shop_service.component.structure.feignobjects;

import java.util.UUID;
import javax.persistence.GeneratedValue;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderItemFeignObject{

	@GeneratedValue
	private UUID orderItemId;
	
	private int quantity;
	private UUID articleId;
	
	@JsonIgnore
	private OrderFeignObject order;
	
	public UUID getOrderItemId() {return orderItemId;}
	public void setOrderItemId(UUID orderItemId) {this.orderItemId = orderItemId;}
	public int getQuantity() {return quantity;}
	public void setQuantity(int quantity) {this.quantity = quantity;}
	public UUID getArticleId() {return articleId;}
	public void setArticleId(UUID articleId) {this.articleId = articleId;}
	public OrderFeignObject getOrder() {return order;}
	public void setOrder(OrderFeignObject order) {this.order = order;}
	
	
}
