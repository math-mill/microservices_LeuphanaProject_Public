package de.leuphana.customer_service.connector;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import de.leuphana.customer_service.component.behaviour.exceptionhandling.CustomerNotFoundException;
import de.leuphana.customer_service.component.structure.Address;
import de.leuphana.customer_service.component.structure.Customer;

@RestController
public class CustomerServiceRestConnectorProvider {
	
	private static final String serviceURL = "${service.URL}";
	
	@Autowired
	private CustomerJPAController customerJPAController;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping(serviceURL)
	public List<Customer> getAllCustomers() {
		return customerJPAController.loadAllCustomersFromDB();
	}
	
	@GetMapping(serviceURL + "/{customerId}")
	public Customer getCustomerById(@PathVariable UUID customerId) {
		return customerJPAController.loadCustomerFromDB(customerId);
	}
	
	@PostMapping(serviceURL)
	public Customer createNewCustomer(@RequestBody Customer newCustomer) {
		customerJPAController.saveAddressToDB(newCustomer.getAddress());
		customerJPAController.saveCustomerToDB(newCustomer);
		return newCustomer;
	}
	
	@PutMapping(serviceURL + "/{customerId}")
	public Customer replaceCustomerById(@RequestBody Customer newCustomer, @PathVariable UUID customerId) {
		try {
			Customer customer = customerJPAController.loadCustomerFromDB(customerId);
			Address oldAddress = customerJPAController.loadAddressFromDB(customer.getAddress().getAddressId());
			Address newAddress = newCustomer.getAddress();
			oldAddress.setCity(newAddress.getCity());
			oldAddress.setStreet(newAddress.getStreet());
			oldAddress.setPostalCode(newAddress.getPostalCode());
			oldAddress.setNumber(newAddress.getNumber());
			customerJPAController.saveAddressToDB(oldAddress);
			customer.setFirstName(newCustomer.getFirstName());
			customer.setLastName(newCustomer.getLastName());
			customer.setUserName(newCustomer.getUserName());
			customer.setAddress(newCustomer.getAddress());
			customerJPAController.saveCustomerToDB(customer);
			return customer;
		} catch (CustomerNotFoundException e) {
			customerJPAController.saveAddressToDB(newCustomer.getAddress());
			customerJPAController.saveCustomerToDB(newCustomer);
			return newCustomer;
		}
	}
	
	@DeleteMapping(serviceURL + "/{customerId}")
	void deleteCustomerById(@PathVariable UUID customerId) {
		UUID addressId = customerJPAController.loadCustomerFromDB(customerId).getAddress().getAddressId();
		customerJPAController.deleteCustomerFromDB(customerId);
		customerJPAController.deleteAddressFromDB(addressId);
	}
	
	@RequestMapping("/service-instance/{ApplicationService}")
	public @ResponseBody String getClientsByApplicationName(@PathVariable String ApplicationService) {
		return this.discoveryClient.getInstances(ApplicationService).get(0).getUri().toString();
	}
	
}
