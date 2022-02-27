package de.leuphana.shop_service.connector.feign;

import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.leuphana.shop_service.component.structure.feignobjects.CustomerFeignObject;

@FeignClient(value="CustomerFeignClient", url="${custom.feignClient.customerURL}")
public interface CustomerFeignClient {
	
	@RequestMapping(value="/{customerId}", method=RequestMethod.GET)
	public CustomerFeignObject getCustomerById(@PathVariable(value="customerId") UUID customerId);
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<CustomerFeignObject> getAllCustomers();
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public CustomerFeignObject createNewCustomer(@RequestBody CustomerFeignObject customerFeignObejct);
	
	@RequestMapping(value="/{customerId}", method=RequestMethod.PUT)
	public CustomerFeignObject replaceCustomerById(@RequestBody CustomerFeignObject customerFeignObject, @PathVariable(value="customerId") UUID customerId);
	
	@RequestMapping(value="/{customerId}", method=RequestMethod.DELETE)
	public void deleteCustomer(@PathVariable(value="customerId") UUID customerId);
}
