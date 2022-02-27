package de.leuphana.order_service.component.behaviour.exceptionhandling;

import java.util.UUID;

@SuppressWarnings("serial")
public class OrderItemNotFoundException extends RuntimeException{

	public OrderItemNotFoundException(UUID orderItemId){
		super("OrderItem not found (" + orderItemId + ").");
	}
}