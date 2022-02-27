package de.leuphana.order_service.connector;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.leuphana.order_service.component.behaviour.exceptionhandling.OrderItemNotFoundException;
import de.leuphana.order_service.component.behaviour.exceptionhandling.OrderNotFoundException;
import de.leuphana.order_service.component.structure.Order;
import de.leuphana.order_service.component.structure.OrderItem;
import de.leuphana.order_service.connector.repository.OrderItemRepository;
import de.leuphana.order_service.connector.repository.OrderRepository;

@Component
public class OrderJPAController {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

//##############################_Order_##############################
	
	@Transactional
	public Order saveOrderToDB(Order order) {
		return orderRepository.save(order);
	}
	
	@Transactional
	public Order loadOrderFromDB(UUID orderId) {
		if(orderRepository.findById(orderId).isPresent()) {
			return orderRepository.findById(orderId).get();
		}else {
			return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		}
	}
	
	@Transactional
	public List<Order> loadAllOrdersFromDB() {
		return orderRepository.findAll();
	}
	
	@Transactional
	public List<Order> loadOrdersByCustomerIdFromDB(UUID customerId){
		return orderRepository.findByCustomerId(customerId);
	}
	
	@Transactional
	public void deleteOrderFromDB(UUID orderId) {
		if(orderRepository.findById(orderId).isPresent()) {
			orderRepository.deleteById(orderId);
		}else {
			orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		}
	}

//##############################_OrderItem_##############################
	
	@Transactional
	public void saveOrderItemToDB(OrderItem orderItem) {
		orderItemRepository.save(orderItem);
	}
	
	@Transactional
	public OrderItem loadOrderItemFromDB(UUID orderItemId) {
		if(orderItemRepository.findById(orderItemId).isPresent()) {
			return orderItemRepository.findById(orderItemId).get();
		}else {
			return orderItemRepository.findById(orderItemId).orElseThrow(() -> new OrderItemNotFoundException(orderItemId));
		}
	}
	
	@Transactional
	public List<OrderItem> loadAllOrderItemsFromDB() {
		return orderItemRepository.findAll();
	}
	
	@Transactional
	public void deleteOrderItemFromDB(UUID orderItemId) {
		if(orderItemRepository.findById(orderItemId).isPresent()) {
			orderItemRepository.deleteById(orderItemId);
		}else {
			orderItemRepository.findById(orderItemId).orElseThrow(() -> new OrderItemNotFoundException(orderItemId));
		}
	}
	
}
