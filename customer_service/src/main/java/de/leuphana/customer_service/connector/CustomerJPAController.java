package de.leuphana.customer_service.connector;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.leuphana.customer_service.component.behaviour.exceptionhandling.AddressNotFoundException;
import de.leuphana.customer_service.component.behaviour.exceptionhandling.CustomerNotFoundException;
import de.leuphana.customer_service.component.structure.Address;
import de.leuphana.customer_service.component.structure.Customer;
import de.leuphana.customer_service.connector.repository.AddressRepository;
import de.leuphana.customer_service.connector.repository.CustomerRepository;

@Component
public class CustomerJPAController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	AddressRepository addressRepository;

//##################################################
	
	@Transactional
	public Customer loadCustomerFromDB(UUID customerId) {
		if(customerRepository.findById(customerId).isPresent()) {
			return customerRepository.findById(customerId).get();
		} else {
			return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
		}
	}
	
	@Transactional
	public List<Customer> loadAllCustomersFromDB(){
		return customerRepository.findAll();
	}
	
	@Transactional
	public Customer saveCustomerToDB(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Transactional
	public void deleteCustomerFromDB(UUID customerId) {
		if(customerRepository.findById(customerId).isPresent()) {
			customerRepository.deleteById(customerId);
		} else {
			customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
		}
	}

//##################################################
	
	@Transactional
	public Address loadAddressFromDB(UUID addressId) {
		if(addressRepository.findById(addressId).isPresent()) {
			return addressRepository.findById(addressId).get();
		} else {
			return addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
		}
	}
	
	@Transactional
	public void saveAddressToDB(Address address) {
		addressRepository.save(address);
	}
	
	@Transactional
	public void deleteAddressFromDB(UUID addressId) {
		if(addressRepository.findById(addressId).isPresent()) {
			addressRepository.deleteById(addressId);
		} else {
			addressRepository.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
		}
	}

}
