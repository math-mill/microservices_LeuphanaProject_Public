package de.leuphana.customer_service.component.behaviour.exceptionhandling;

import java.util.UUID;

@SuppressWarnings("serial")
public class CustomerNotFoundException extends RuntimeException{
	
	public CustomerNotFoundException(UUID customerId){
		super("Customer konnte nicht gefunden werden (" +customerId + ").");
	}
}
