package de.leuphana.shop_service.component.behaviour.exceptionhandling;

import java.util.UUID;

@SuppressWarnings("serial")
public class CartItemNotFoundException extends RuntimeException {

	public CartItemNotFoundException(UUID cartItemId) {
		super("CartItem konnte nicht gefunden werden (" + cartItemId + ").");
	}
}
