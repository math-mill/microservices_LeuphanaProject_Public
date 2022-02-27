package de.leuphana.order_service.connector;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import de.leuphana.order_service.component.behaviour.OrderService;
import de.leuphana.order_service.component.behaviour.exceptionhandling.OrderNotFoundException;
import de.leuphana.order_service.component.structure.Order;
import de.leuphana.order_service.component.structure.OrderItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {OrderService.class})
public class OrderJPAControllerTest {

	@Autowired
	private OrderJPAController oJPAc;
	
	private int orderCount = 0;
	
	public Order createTestOrder() {
		Order order = new Order();
		order.setCustomerId(UUID.randomUUID());
		order.setOrderDate("TestDate");
		OrderItem orderItem = new OrderItem();
		orderItem.setArticleId(UUID.randomUUID());
		orderItem.setQuantity(10);
		orderItem.setOrder(order);
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(orderItem);
		order.setOrderItems(orderItems);
		orderCount++;
		return order;
	}
	
	@Test
	public void saveOrderToDB() {
		Order orderToSave = this.createTestOrder();
		Order orderSaved = oJPAc.saveOrderToDB(orderToSave);
		assertEquals(orderSaved, orderToSave);
	}
	
	@Test
	public void getAllOrders() {
		for(int i = 0; i < 7; i++) {
			Order orderToSave = this.createTestOrder();
			oJPAc.saveOrderToDB(orderToSave);
		}
		List<Order> ordersSaved = oJPAc.loadAllOrdersFromDB();
		assertEquals(ordersSaved.size(), orderCount);
	}
	
	@Test
	public void loadOrderById() {
		Order orderToSave = this.createTestOrder();
		oJPAc.saveOrderToDB(orderToSave);
		Order orderLoaded = oJPAc.loadOrderFromDB(orderToSave.getOrderId());
		assertEquals(orderToSave.getOrderId(), orderLoaded.getOrderId());
	}
	
	@Test
	public void deleteOrderById() {
		boolean thrown = false;
		Order order = this.createTestOrder();
		oJPAc.saveOrderToDB(order);
		oJPAc.deleteOrderFromDB(order.getOrderId());
		try {
			oJPAc.loadOrderFromDB(order.getOrderId());
		} catch(OrderNotFoundException e) {
			thrown = true;
		}
		assertEquals(thrown, true);
	}
}
