package de.leuphana.customer_service.component.behaviour.exceptionhandling;

import java.util.UUID;

@SuppressWarnings("serial")
public class AddressNotFoundException extends RuntimeException{

	public AddressNotFoundException(UUID addressId) {
		super("Adresse konnte nicht gefunden werden (" + addressId + ").");
	}
}