package de.leuphana.shop_service.connector.feign;

import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import de.leuphana.shop_service.component.structure.feignobjects.OrderFeignObject;


@FeignClient(value="OrderFeignClient", url="${custom.feignClient.orderURL}")
public interface OrderFeignClient {
	
	@RequestMapping(value="/{orderId}", method=RequestMethod.GET)
	public OrderFeignObject getOrderById(@PathVariable(value="orderId") UUID orderId);
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<OrderFeignObject> getAllOrders();
	
	@RequestMapping(value="/bycustomer/{customerId}")
	public List<OrderFeignObject> gerOrdersByCustomerId(@PathVariable(value="customerId") UUID customerId);
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public OrderFeignObject createNewOrder(@RequestBody OrderFeignObject orderFeignObejct);
	
	@RequestMapping(value="/{orderId}", method=RequestMethod.PUT)
	public OrderFeignObject replaceOrderById(@RequestBody OrderFeignObject orderFeignObejct, @PathVariable(value="orderId") UUID orderId);
	
	@RequestMapping(value="/{orderId}", method=RequestMethod.DELETE)
	public void deleteOrder(@PathVariable(value="orderId") UUID orderId);

}
