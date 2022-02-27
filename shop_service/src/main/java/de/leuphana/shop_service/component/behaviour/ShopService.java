package de.leuphana.shop_service.component.behaviour;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import de.leuphana.shop_service.component.structure.Cart;
import de.leuphana.shop_service.component.structure.CartItem;
import de.leuphana.shop_service.component.structure.Catalog;
import de.leuphana.shop_service.component.structure.feignobjects.AddressFeignObject;
import de.leuphana.shop_service.component.structure.feignobjects.ArticleFeignObject;
import de.leuphana.shop_service.component.structure.feignobjects.CustomerFeignObject;
import de.leuphana.shop_service.component.structure.feignobjects.OrderFeignObject;
import de.leuphana.shop_service.component.structure.feignobjects.OrderItemFeignObject;
import de.leuphana.shop_service.connector.FeignObjectController;
import de.leuphana.shop_service.connector.ShopJPAController;

//##############################################################################################################

@SpringBootApplication
@ComponentScan(basePackages = "de.leuphana.shop_service")
@EntityScan(basePackages = "de.leuphana.shop_service")
@EnableJpaRepositories(basePackages = {"de.leuphana.shop_service.connector"})
@EnableFeignClients(basePackages = "de.leuphana.shop_service")
public class ShopService{
	
	@Autowired
	ShopJPAController shopJPAController;
	
	@Autowired
	FeignObjectController feignObjectController;
	
	public static void main(String[] args) {
		SpringApplication.run(ShopService.class);
	}

//##############################_ShopService_##############################
	
	public CustomerFeignObject createCustomerWithCart(String firstName, String lastName,
			  						   				  String userName,  String street,
			  						   				  String number,	String city,
			  						   				  String postalCode) {
		AddressFeignObject newAddress = new AddressFeignObject();
		newAddress.setStreet(street);
		newAddress.setNumber(number);
		newAddress.setPostalCode(postalCode);
		newAddress.setCity(city);
		CustomerFeignObject newCustomer = new CustomerFeignObject();
		newCustomer.setFirstName(firstName);
		newCustomer.setLastName(lastName);
		newCustomer.setUserName(userName);
		newCustomer.setAddress(newAddress);
		newCustomer = feignObjectController.createNewCustomer(newCustomer);
		shopJPAController.saveCartToDB(createCart(newCustomer.getCustomerId()));
		return newCustomer;
	}

//####################_Catalog_####################
	
	public void createCatalog(UUID[] articleIds) {
		List<UUID> articleIdList = new ArrayList<UUID>();
		for(int i = 0; i < articleIds.length; i++) {articleIdList.add(articleIds[i]);}
		Catalog catalog = new Catalog();
		catalog.setArticleIds(articleIdList);
		shopJPAController.saveCatalogToDB(catalog);
	}
		
	public void editArticlesInCatalog(Catalog catalog, UUID[] articleIds) {
		List<UUID> articleIdList = new ArrayList<UUID>();
		for(int i = 0; i < articleIds.length; i++) {articleIdList.add(articleIds[i]);}
		catalog.setArticleIds(articleIdList);	
		shopJPAController.saveCatalogToDB(catalog);
	}
	
	public Catalog getCatalog() {
		List<Catalog> catalogs = shopJPAController.loadAllCatalogsFromDB();
		for(Catalog c : catalogs) {return c;}
		return new Catalog();
	}
	
	public void removeArticleFromCatalog(Catalog catalog, UUID articleId, boolean outOfStock) {
		List<UUID> articleIds = catalog.getArticleIds();
		articleIds.remove(articleId);
		catalog.setArticleIds(articleIds);		
		if(!outOfStock) {deleteArticleFromAllCarts(articleId);}
		shopJPAController.saveCatalogToDB(catalog);
	}

//####################_Cart_####################
	
	public Cart createCart(UUID customerId) {
		Cart cart = new Cart();
		cart.setCustomerId(customerId);
		return cart;
	}
	
	public Cart getCartFromCustomerId(UUID customerId) {
		return shopJPAController.loadCartFromDBByCustomerId(customerId);
	}
	
	public void deleteArticleFromAllCarts(UUID articleId) {
		for(CartItem CI : shopJPAController.loadAllCartItemsFromDB()) {
			if(articleId.equals(CI.getArticleId()))	shopJPAController.deleteCartItemFromDB(CI.getCartItemId());
		}
	}
	
	public CartItem getCartItem(UUID cartItemId) {
		return shopJPAController.loadCartItemFromDB(cartItemId);
	}
	
	public void addCartItemToCartFromArticleId(UUID customerId, UUID articleId) {
		boolean found = false;
		try {
			Cart cart = getCartFromCustomerId(customerId);
			List<CartItem> cartItems = cart.getCartItems();
			CartItem cartItem = new CartItem();
			for (CartItem cI : cartItems) {
				if(cI.getArticleId().equals(articleId)) {cartItem = cI;	found = true;}
			}
			if(found) {
				if(modifyArticleQuantity(articleId, -1)) {cart.incQuantity(cartItem.getCartItemId());}
			} else {
				if(modifyArticleQuantity(articleId, -1)) {
					cartItem.setArticleId(articleId);
					cartItem.setQuantity(1);
					cartItem.setCart(cart);
					cartItems.add(cartItem);
				}
			}
			cart.setCartItems(cartItems);
			shopJPAController.saveCartToDB(cart);
		} catch (Exception e) {
			//some error handling
		}
	}
		
	public void removeCartItemFromCart(UUID customerId, UUID articleId) {
		Cart cart = getCartFromCustomerId(customerId);
		CartItem cartItem = new CartItem();
		List<CartItem> cartItems = cart.getCartItems();
		for (CartItem cI : cartItems) {
			if(cI.getArticleId().equals(articleId)) {cartItem = cI;}
		}
		if(cartItem.getQuantity() > 1) {
			if(modifyArticleQuantity(articleId, 1)) {cartItem.decQuantity();}
		}
		else {
			if(modifyArticleQuantity(articleId, 1)) {
				cartItems.remove(cartItem);
				cart.setCartItems(cartItems);
				shopJPAController.deleteCartItemFromDB(cartItem.getCartItemId());
			}
		}
		cart.setCartItems(cartItems);
		shopJPAController.saveCartToDB(cart);
	}
	
	public void checkOutCart(UUID customerId) {
		SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy ' - ' HH:mm z");
		String date = formatter.format(new Date(System.currentTimeMillis()));
		OrderFeignObject order = new OrderFeignObject();
		OrderItemFeignObject orderItem;			
		List<OrderItemFeignObject> orderItems = new ArrayList<OrderItemFeignObject>();
		for(CartItem cI : getCartFromCustomerId(customerId).getCartItems()) {
			orderItem = new OrderItemFeignObject();
			orderItem.setArticleId(cI.getArticleId());
			orderItem.setQuantity(cI.getQuantity());
			orderItems.add(orderItem);
		}
		order.setCustomerId(customerId);
		order.setOrderDate(date);
		order.setOrderItems(orderItems);
		feignObjectController.createNewOrder(order);
		deleteAllCartItemsFromCart(customerId);
	}
	
	public void deleteAllCartItemsFromCart(UUID customerId) {
		Cart cart = getCartFromCustomerId(customerId);
		List<CartItem> cartItems = cart.getCartItems();
		List<CartItem> toRemove = new ArrayList<CartItem>();
		for(CartItem cI : cartItems) {
			shopJPAController.deleteCartItemFromDB(cI.getCartItemId());
			toRemove.add(cI);
		}
		cartItems.removeAll(toRemove);
		cart.setCartItems(cartItems);
		shopJPAController.saveCartToDB(cart);
	}
	
	public boolean modifyArticleQuantity(UUID articleId, int quantity) {
		ArticleFeignObject article = feignObjectController.getArticleById(articleId);
		if (article.getStockQuantity() + quantity >= 0) {
			article.setStockQuantity(article.getStockQuantity() + quantity);
			feignObjectController.replaceArticleById(article, article.getArticleId());
			if (article.getStockQuantity() == 0) {removeArticleFromCatalog(getCatalog(), articleId, true);}
			return true;
		}
		return false;
	}
	
//####################_Order_####################
	
	public OrderFeignObject getOrderByOrderId(UUID orderId) {
		return feignObjectController.getOrderById(orderId);
	}
	
	public List<OrderFeignObject> getOrdersByCustomerId(UUID customerId) {
		return feignObjectController.getOrdersByCustomerId(customerId);
	}
	
	public List<OrderItemFeignObject> getOrderItemsFromOrder(UUID orderId){
		return feignObjectController.getOrderById(orderId).getOrderItems();
	}
	
	public List<ArticleFeignObject> getArticlesFromOrder(OrderFeignObject order){
		List<ArticleFeignObject> articles = new ArrayList<ArticleFeignObject>();
		for(OrderItemFeignObject oI : order.getOrderItems()) {
			articles.add(feignObjectController.getArticleById(oI.getArticleId()));
		}
		return articles;
	}
	
//##############################_ArticleService_##############################
//
// This part only uses the Microservice ArticleService.
//
// Please use the Methods in Section "ShopService"
//############################################################################
	
	public ArticleFeignObject createNewFeignArticle(String name,	  String manufactor,
													BigDecimal price, String articleType,
													int stockQuantity) {
		ArticleFeignObject newArticle = new ArticleFeignObject();
		newArticle.setArticleName(name);
		newArticle.setManufactor(manufactor);
		newArticle.setPrice(price);
		newArticle.setArticleType(articleType);
		newArticle.setStockQuantity(stockQuantity);
		return feignObjectController.createNewArticle(newArticle);
	}
	
	public ArticleFeignObject editFeignArticle(UUID articleId,	   String name,
											   String manufactor,  BigDecimal price,
											   String articleType, int stockQuantity) {
		ArticleFeignObject editArticle = feignObjectController.getArticleById(articleId);
		editArticle.setArticleName(name);
		editArticle.setManufactor(manufactor);
		editArticle.setPrice(price);
		editArticle.setArticleType(articleType);
		editArticle.setStockQuantity(stockQuantity);
		return feignObjectController.replaceArticleById(editArticle, articleId);
	}
	
	public List<ArticleFeignObject> getAllArticles(){
		return feignObjectController.getAllArticles();
	}
	
	public ArticleFeignObject getArticleById(UUID articleId) {
		return feignObjectController.getArticleById(articleId);
	}
	
	public void deleteArticle(UUID articleId) {
		feignObjectController.deleteArticle(articleId);
	}

//##############################_CustomerService_##############################
//
// This part only uses the Microservice CustomerService.
//
// Please use the Methods in Section "ShopService"
//############################################################################
	
	public CustomerFeignObject createNewFeignCustomer(String firstName, String lastName,
													  String userName,  String street,
													  String number,    String city,
													  String postalCode) {
		AddressFeignObject newAddress = new AddressFeignObject();
		newAddress.setStreet(street);
		newAddress.setNumber(number);
		newAddress.setPostalCode(postalCode);
		newAddress.setCity(city);
		CustomerFeignObject newCustomer = new CustomerFeignObject();
		newCustomer.setFirstName(firstName);
		newCustomer.setLastName(lastName);
		newCustomer.setUserName(userName);
		newCustomer.setAddress(newAddress);
		return feignObjectController.createNewCustomer(newCustomer);
	}

	public CustomerFeignObject editFeignCustomer(UUID customerId, String firstName,
			   									 String lastName, String userName,
			   									 String street,   String number,
			   									 String city,     String postalCode) {
		CustomerFeignObject editCustomer = feignObjectController.getCustomerById(customerId);
		editCustomer.setFirstName(firstName);
		editCustomer.setLastName(lastName);
		editCustomer.setUserName(userName);
		AddressFeignObject address = editCustomer.getAddress();
		address.setStreet(street);
		address.setNumber(number);
		address.setCity(city);
		address.setPostalCode(postalCode);
		editCustomer.setAddress(address);
		return feignObjectController.replaceCustomerById(editCustomer, customerId);
	}
	
	public CustomerFeignObject getCustomerById(UUID customerId) {
		return feignObjectController.getCustomerById(customerId);
	}
	
	public List<CustomerFeignObject> getAllCustomers() {
		return feignObjectController.getAllCustomers();
	}
	
	public void deleteCustomer(UUID customerId) {
		feignObjectController.deleteCustomer(customerId);
	}
	
}