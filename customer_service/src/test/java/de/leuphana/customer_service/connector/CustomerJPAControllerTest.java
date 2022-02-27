package de.leuphana.customer_service.connector;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import de.leuphana.customer_service.component.behaviour.CustomerService;
import de.leuphana.customer_service.component.behaviour.exceptionhandling.CustomerNotFoundException;
import de.leuphana.customer_service.component.structure.Address;
import de.leuphana.customer_service.component.structure.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {CustomerService.class})
public class CustomerJPAControllerTest {
	
	@Autowired
	private CustomerJPAController cJPAc;
	
	private int customerCount = 0;
	
	public Customer createTestCustomer() {
		Customer customer = new Customer();
		Address address = new Address();
		customer.setFirstName("TestFirstName");
		customer.setLastName("TestLastName");
		customer.setUserName("TestUserName");
		address.setStreet("TestStreet");
		address.setCity("TestCity");
		address.setPostalCode("TestPostalCode");
		address.setNumber("TestNumber");
		customer.setAddress(address);
		customerCount++;
		return customer;
	}
	
	@Test
	public void saveCustomerToDB() {
		Customer customerToSave = this.createTestCustomer();
		cJPAc.saveAddressToDB(customerToSave.getAddress());
		Customer customerSaved = cJPAc.saveCustomerToDB(customerToSave);
		assertEquals(customerSaved, customerToSave);
	}
	
	@Test
	public void getAllCustomers() {
		for(int i = 0; i < 7; i++) {
			Customer customerToSave = this.createTestCustomer();
			cJPAc.saveAddressToDB(customerToSave.getAddress());
			cJPAc.saveCustomerToDB(customerToSave);
		}
		List<Customer> customersSaved = cJPAc.loadAllCustomersFromDB();
		assertEquals(customersSaved.size() - 1, customerCount);
	}
	
	@Test
	public void loadCustomerById() {
		Customer customerToSave = this.createTestCustomer();
		cJPAc.saveAddressToDB(customerToSave.getAddress());
		cJPAc.saveCustomerToDB(customerToSave);
		Customer customerLoaded = cJPAc.loadCustomerFromDB(customerToSave.getCustomerId());
		assertEquals(customerToSave.getCustomerId(), customerLoaded.getCustomerId());
	}
	
	@Test
	public void deleteArticleById() {
		boolean thrown = false;
		Customer customer = this.createTestCustomer();
		cJPAc.saveAddressToDB(customer.getAddress());
		cJPAc.saveCustomerToDB(customer);
		cJPAc.deleteCustomerFromDB(customer.getCustomerId());
		try {
			cJPAc.loadCustomerFromDB(customer.getCustomerId());
		} catch(CustomerNotFoundException e) {
			thrown = true;
		}
		assertEquals(thrown, true);
	}
	
}
