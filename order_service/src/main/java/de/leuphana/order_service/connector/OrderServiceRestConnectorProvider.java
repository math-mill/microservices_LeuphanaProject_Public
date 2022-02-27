package de.leuphana.order_service.connector;

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
import de.leuphana.order_service.component.behaviour.exceptionhandling.OrderNotFoundException;
import de.leuphana.order_service.component.structure.Order;
import de.leuphana.order_service.component.structure.OrderItem;

@RestController
public class OrderServiceRestConnectorProvider {
	
	private static final String serviceURL = "${service.URL}";
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private OrderJPAController orderJPAController;
	
	@GetMapping(serviceURL)
	public List<Order> getAllOrders() {
		return orderJPAController.loadAllOrdersFromDB();
	}
	
	@GetMapping(serviceURL + "/{orderId}")
	public Order getOrderById(@PathVariable UUID orderId) {
		return orderJPAController.loadOrderFromDB(orderId);
	}
	
	@GetMapping(serviceURL + "/bycustomer/{customerId}")
	public List<Order> getOrderByCustomerId(@PathVariable UUID customerId){
		return orderJPAController.loadOrdersByCustomerIdFromDB(customerId);
	}
	
	@PostMapping(serviceURL)
	public void createNewOrder(@RequestBody Order newOrder) {
		List<OrderItem> orderItems = newOrder.getOrderItems();
		for(OrderItem oI : orderItems) {
			oI.setOrder(newOrder);
		}
		newOrder.setOrderItems(orderItems);
		orderJPAController.saveOrderToDB(newOrder);
	}
	
	@PutMapping(serviceURL + "/{orderId}")
	public Order replaceOrderById(@RequestBody Order newOrder, @PathVariable UUID orderId) {
		try {
			Order order = orderJPAController.loadOrderFromDB(orderId);
			order.setOrderDate(newOrder.getOrderDate());
			order.setOrderItems(newOrder.getOrderItems());
			order.setCustomerId(newOrder.getCustomerId());
			orderJPAController.saveOrderToDB(order);
			return order;
		} catch (OrderNotFoundException e) {
			orderJPAController.saveOrderToDB(newOrder);
			return newOrder;
		}
	}
	
	@DeleteMapping(serviceURL + "/{orderId}")
	public void deleteOrderById(@PathVariable UUID orderId) {
		orderJPAController.deleteOrderFromDB(orderId);
	}
	
	@RequestMapping("/service-instance/{ApplicationService}")
	public @ResponseBody String getClientsByApplicationName(@PathVariable String ApplicationService) {
		return this.discoveryClient.getInstances(ApplicationService).get(0).getUri().toString();
	}
	
}
	
	