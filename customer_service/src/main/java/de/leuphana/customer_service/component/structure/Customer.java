package de.leuphana.customer_service.component.structure;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

//##############################################################################################################

@Entity
public class Customer {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "BINARY(16)")
	private UUID customerId;
	
	@OneToOne
	private Address address;
	
	private String lastName;
	private String firstName;
	private String userName;
	
//##############################################################################################################
	
	public UUID getCustomerId() {
		return this.customerId;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Address getAddress() {
		return this.address;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public UUID getAddressId() {
		return this.address.getAddressId();
	}
	
}
