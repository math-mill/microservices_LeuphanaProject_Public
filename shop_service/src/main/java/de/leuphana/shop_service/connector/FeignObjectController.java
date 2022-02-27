package de.leuphana.shop_service.connector;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.leuphana.shop_service.component.structure.feignobjects.ArticleFeignObject;
import de.leuphana.shop_service.component.structure.feignobjects.CustomerFeignObject;
import de.leuphana.shop_service.component.structure.feignobjects.OrderFeignObject;
import de.leuphana.shop_service.connector.feign.ArticleFeignClient;
import de.leuphana.shop_service.connector.feign.CustomerFeignClient;
import de.leuphana.shop_service.connector.feign.OrderFeignClient;

@Component
public class FeignObjectController {

	@Autowired
	ArticleFeignClient articleFeignClient;
	
	@Autowired
	CustomerFeignClient customerFeignClient;
	
	@Autowired
	OrderFeignClient orderFeignClient;
	
//##############################_Article_##############################
	
	public ArticleFeignObject getArticleById(UUID articleId){
		return articleFeignClient.getArticleById(articleId);
	}
	
	public List<ArticleFeignObject> getAllArticles(){
		return articleFeignClient.getAllArticles();
	}
	
	public ArticleFeignObject createNewArticle(ArticleFeignObject articleFeignObject) {
		return articleFeignClient.createNewArticle(articleFeignObject);
	}
	
	public ArticleFeignObject replaceArticleById(ArticleFeignObject articleFeignObject, UUID articleId) {
		return articleFeignClient.replaceArticleById(articleFeignObject, articleId);
	}
	
	public void deleteArticle(UUID articleId) {
		articleFeignClient.deleteArticle(articleId);
	}
	
//##############################_Customer_##############################
	
	public CustomerFeignObject getCustomerById(UUID customerId){
		return customerFeignClient.getCustomerById(customerId);
	}
	
	public List<CustomerFeignObject> getAllCustomers(){
		return customerFeignClient.getAllCustomers();
	}
	
	public CustomerFeignObject createNewCustomer(CustomerFeignObject customerFeignObject) {
		return customerFeignClient.createNewCustomer(customerFeignObject);
	}
	
	public CustomerFeignObject replaceCustomerById(CustomerFeignObject customerFeignObject, UUID customerId) {
		return customerFeignClient.replaceCustomerById(customerFeignObject, customerId);
	}
	
	public void deleteCustomer(UUID customerId) {
		customerFeignClient.deleteCustomer(customerId);
	}

//##############################_Order_##############################
	
	public OrderFeignObject getOrderById(UUID orderId) {
		return orderFeignClient.getOrderById(orderId);
	}
	
	public List<OrderFeignObject> getAllOrders(){
		return orderFeignClient.getAllOrders();
	}
	
	public List<OrderFeignObject> getOrdersByCustomerId(UUID customerId){
		return orderFeignClient.gerOrdersByCustomerId(customerId);
	}
	
	public OrderFeignObject createNewOrder(OrderFeignObject orderFeignObject) {
		return orderFeignClient.createNewOrder(orderFeignObject);
	}
	
	public OrderFeignObject replaceOrderById(OrderFeignObject orderFeignObject, UUID orderId) {
		return orderFeignClient.replaceOrderById(orderFeignObject, orderId);
	}
	
	public void deleteOrder(UUID orderId) {
		orderFeignClient.deleteOrder(orderId);
	}
	
}
