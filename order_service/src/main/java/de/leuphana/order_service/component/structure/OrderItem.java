package de.leuphana.order_service.component.structure;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

//##############################################################################################################

@Entity
public class OrderItem {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "BINARY(16)")
	UUID orderItemId;
	
	private int quantity;
	
	@Column(columnDefinition = "BINARY(16)")
	private UUID articleId;
	
	@ManyToOne
	@JsonIgnore
	private Order order;

//##############################################################################################################
	
	public UUID getOrderItemId() {
		return this.orderItemId;
	}
	
	public void setQuantity(int qty) {
		this.quantity = qty;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setArticleId(UUID articleId) {
		this.articleId = articleId;
	}
	
	public UUID getArticleId() {
		return this.articleId;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Order getOrder() {
		return this.order;
	}
}
