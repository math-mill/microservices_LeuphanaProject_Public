package de.leuphana.shop_service.component.structure;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cart{
	
	@Id
	@GeneratedValue
	@Column(columnDefinition = "BINARY(16)")
	private UUID cartId;
	
	@Column(columnDefinition = "BINARY(16)")
	private UUID customerId;
	
	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "cart", orphanRemoval=true)
	private List<CartItem> cartItems;
	
	public UUID getCartId() {
		return this.cartId;
	}
	
	public UUID getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
	
	public List<CartItem> getCartItems(){
		return this.cartItems;
	}
	
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	public void addCartItemToCart(CartItem cartItem) {
		this.cartItems.add(cartItem);
	}
	
	public void deleteCartItemFromCart(CartItem cartItem) {
		this.cartItems.remove(cartItem);
	}
	
	public void incQuantity(UUID cartItemId) {
		for(CartItem cartItem : this.cartItems) {
			if(cartItem.getCartItemId().equals(cartItemId)) {
				cartItem.incQuantity();
			}
		}
	}
	
	public void decQuantity(UUID cartItemId) {
		for(CartItem cartItem : this.cartItems) {
			if(cartItem.getCartItemId().equals(cartItemId)) {
				cartItem.decQuantity();
			}
		}
	}
}
