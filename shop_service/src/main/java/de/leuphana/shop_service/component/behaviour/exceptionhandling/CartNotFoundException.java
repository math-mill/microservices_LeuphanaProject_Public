package de.leuphana.shop_service.component.behaviour.exceptionhandling;

import java.util.UUID;

@SuppressWarnings("serial")
public class CartNotFoundException extends RuntimeException {

	public CartNotFoundException(UUID cartId) {
		super("Cart konnte nicht gefunden werden (" + cartId + ").");
	}
}
