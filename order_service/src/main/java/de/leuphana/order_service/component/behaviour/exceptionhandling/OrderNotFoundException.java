package de.leuphana.order_service.component.behaviour.exceptionhandling;

import java.util.UUID;

@SuppressWarnings("serial")
public class OrderNotFoundException extends RuntimeException{

	public OrderNotFoundException(UUID orderId){
		super("Order not found (" + orderId + ").");
	}
}
