package de.leuphana.shop_service.component.structure;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CartItem {
	
	@Id
	@GeneratedValue
	@Column(columnDefinition = "BINARY(16)")
	private UUID cartItemId;
	
	@Column(columnDefinition = "BINARY(16)")
	private UUID articleId;
	
	private int quantity;
	
	@ManyToOne
	private Cart cart;
	
	public UUID getCartItemId() {return this.cartItemId;}
	public int getQuantity() {return this.quantity;}
	public void setQuantity(int quantity) {this.quantity = quantity;}
	public UUID getArticleId() {return this.articleId;}
	public void setArticleId(UUID articleId) {this.articleId = articleId;}
	public Cart getCart() {return this.cart;}
	public void setCart(Cart cart) {this.cart = cart;}
	
	public void incQuantity() {
		this.quantity += 1;
	}
	
	public void decQuantity() {
		this.quantity -= 1;
	}
}
