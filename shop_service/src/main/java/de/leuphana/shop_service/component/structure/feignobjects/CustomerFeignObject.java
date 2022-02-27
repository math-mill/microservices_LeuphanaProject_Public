package de.leuphana.shop_service.component.structure.feignobjects;

import java.util.UUID;

import javax.persistence.GeneratedValue;

public class CustomerFeignObject {
	
	@GeneratedValue
	private UUID customerId;
	
	private AddressFeignObject address;
	private String lastName;
	private String firstName;
	private String userName;
	
	public UUID getCustomerId() {return this.customerId;}
	public void setCustomerId(UUID customerId) {this.customerId = customerId;}
	public AddressFeignObject getAddress() {return address;}
	public void setAddress(AddressFeignObject address) {this.address = address;}
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public String getUserName() {return userName;}
	public void setUserName(String userName) {this.userName = userName;}
}
