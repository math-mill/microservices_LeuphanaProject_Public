package de.leuphana.shop_service.connector;

import org.springframework.ui.Model;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import de.leuphana.shop_service.component.behaviour.ShopService;
import de.leuphana.shop_service.component.structure.CartItem;
import de.leuphana.shop_service.component.structure.feignobjects.ArticleFeignObject;

@Controller
public class ShopServiceWebUIController {
	private static final String serviceURL = "/service_main";
	private static final RedirectView backToASMain = new RedirectView("/service_main/article_service");
	private static final RedirectView backToCSMain = new RedirectView("/service_main/customer_service");
	private static final RedirectView backToShopMain = new RedirectView("/service_main/shop_service");
	
	@Autowired
	private ShopService shopService;

//#########################_Service_Main_#########################
	
	@GetMapping(value=serviceURL)
	public String service_main() {
		return "service_main";
	}

//#######################_Article_Service_########################
	
	@GetMapping(value=serviceURL + "/article_service")
	public String as_main() {
		return "as_Main";
	}
	
	@GetMapping(value=serviceURL + "/article_service/article")
	public String as_ArticleList(Model model) {
		model.addAttribute("articles", shopService.getAllArticles());
		return "as_ArticleList";
	}
	
	@GetMapping(value=serviceURL + "/article_service/article/{articleid}")
	public String as_ArticleSingle(@PathVariable("articleid") UUID articleid, Model model) {
		model.addAttribute("article", shopService.getArticleById(articleid));
		return "as_ArticleSingle";
	}
	
	@GetMapping(value=serviceURL + "/article_service/article/action")
	public String actionHandlerArticle(@RequestParam("action") String action,
							  		   @RequestParam(required = false) String articleid,
							  		   Model model) {
		switch(action) {
			case "create":
				return "as_CreateNewArticle";
			case "edit":
				model.addAttribute("articleid", articleid);
				model.addAttribute("article", shopService.getArticleById(UUID.fromString(articleid)));
				return "as_EditArticle";
			case "delete":
				model.addAttribute("articleid", articleid);
				model.addAttribute("article", shopService.getArticleById(UUID.fromString(articleid)));
				return "as_DeleteArticle";
		}
		return "as_Main";
	}
	
	@GetMapping(value=serviceURL + "/article_service/article/action/create/perform")
	public RedirectView createNewArticle(@RequestParam("articlename") String name,
										 @RequestParam("articlemanufactor") String manufactor,
										 @RequestParam("articleprice") BigDecimal price,
										 @RequestParam("articletype") String articleType,
										 @RequestParam("articlestockqty") int stockQuantity) {
		shopService.createNewFeignArticle(name, manufactor, price, articleType, stockQuantity);
		return backToShopMain;
	}
	
	@GetMapping(value=serviceURL + "/article_service/article/action/edit/perform")
	public RedirectView editArticle(@RequestParam("articleid") UUID articleId,
									@RequestParam("articlename") String name,
									@RequestParam("articlemanufactor") String manufactor,
									@RequestParam("articleprice") BigDecimal price,
									@RequestParam("articletype") String articleType,
									@RequestParam("articlestockqty") int stockQuantity) {
		shopService.editFeignArticle(articleId, name, manufactor, price, articleType, stockQuantity);
		return backToASMain;
	}
	
	@GetMapping(value=serviceURL + "/article_service/article/action/delete/perform")
	public RedirectView deleteArticle(@RequestParam("articleid") UUID articleId) {
		shopService.deleteArticle(articleId);
		return backToASMain;
	}
	
//#######################_Customer_Service_#######################
	
	@GetMapping(value=serviceURL + "/customer_service")
	public String cs_main() {
		return "cs_Main";
	}
	
	@GetMapping(value=serviceURL + "/customer_service/customer")
	public String cs_CustomerList(Model model) {
		model.addAttribute("customers", shopService.getAllCustomers());
		return "cs_CustomerList";
	}
	
	@GetMapping(value=serviceURL + "/customer_service/customer/{customerid}")
	public String cs_CustomerSingle(@PathVariable("customerid") UUID customerid, Model model) {
		model.addAttribute("customer", shopService.getCustomerById(customerid));
		model.addAttribute("address", shopService.getCustomerById(customerid).getAddress());
		return "cs_CustomerSingle";
	}
	
	@GetMapping(value=serviceURL + "/customer_service/customer/action")
	public String actionHandlerCustomer(@RequestParam("action") String action,
							    		@RequestParam(required = false) UUID customerid,
							    		Model model) {
		switch(action) {
			case "create":
				return "cs_CreateNewCustomer";
			case "edit":
				model.addAttribute("customerid", customerid);
				model.addAttribute("customer", shopService.getCustomerById(customerid));
				model.addAttribute("address", shopService.getCustomerById(customerid).getAddress());
				return "cs_EditCustomer";
			case "delete":
				model.addAttribute("customerid", customerid);
				model.addAttribute("customer", shopService.getCustomerById(customerid));
				return "cs_DeleteCustomer";
		}
		return "cs_Main";
	}
	
	@GetMapping(value=serviceURL + "/customer_service/customer/action/create/perform")
	public RedirectView createNewCustomer(@RequestParam("firstname") String firstName,
										  @RequestParam("lastname") String lastName,
										  @RequestParam("username") String userName,
										  @RequestParam("street") String street,
										  @RequestParam("number") String number,
										  @RequestParam("city") String city,
										  @RequestParam("postalcode") String postalCode) {
		shopService.createNewFeignCustomer(firstName, lastName, userName, street, number, city, postalCode);
		return backToCSMain;
	}
	
	@GetMapping(value=serviceURL + "/customer_service/customer/action/edit/perform")
	public RedirectView editCustomer(@RequestParam("customerid") UUID customerId,
									 @RequestParam("firstname") String firstName,
									 @RequestParam("lastname") String lastName,
									 @RequestParam("username") String userName,
									 @RequestParam("street") String street,
									 @RequestParam("number") String number,
									 @RequestParam("city") String city,
									 @RequestParam("postalCode") String postalCode) {
		shopService.editFeignCustomer(customerId, firstName, lastName, userName, street, number, city, postalCode);
		return backToCSMain;
	}
	
	@GetMapping(value=serviceURL + "/customer_service/customer/action/delete/perform")
	public RedirectView deleteCustomer(@RequestParam("customerid") UUID customerId) {
		shopService.deleteCustomer(customerId);
		return backToCSMain;
	}

//#######################_Shop_Service_#######################
	
	@GetMapping(value=serviceURL + "/shop_service")
	public String shop_Main(Model model) {
		model.addAttribute("customers", shopService.getAllCustomers());
		return "shop_Main";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/action")
	public String actionHandler(@RequestParam("action")String action, Model model) {
		switch(action) {
			case "c_cwc":
				return "shop_CCWC";
			case "c_catalog":
				model.addAttribute("articles", shopService.getAllArticles());
				return "shop_CreateCatalog";
		}
		return "shop_Main";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/action/c_cwc/perform")
	public RedirectView createCustomerWithCart(@RequestParam("firstname") String firstName,
			  								   @RequestParam("lastname") String lastName,
			  								   @RequestParam("username") String userName,
			  								   @RequestParam("street") String street,
			  								   @RequestParam("number") String number,
			  								   @RequestParam("city") String city,
			  								   @RequestParam("postalcode") String postalCode) {
		shopService.createCustomerWithCart(firstName, lastName, userName, street, number, city, postalCode);
		return backToShopMain;
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user")
	public String user_Main(@RequestParam("customerid") UUID customerId, Model model) {
		model.addAttribute("customer", shopService.getCustomerById(customerId));
		return "user_Main";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/action/c_catalog/perform")
	public RedirectView createCatalog(@RequestParam("allarticleids") UUID[] allArticleIds) {
		shopService.createCatalog(allArticleIds);
		return backToShopMain;
	}
	
	@GetMapping(value=serviceURL + "/shop_service/action/e_catalog/perform")
	public RedirectView editCatalog(@RequestParam("allarticleids") UUID[] allArticleIds) {
		shopService.editArticlesInCatalog(shopService.getCatalog(), allArticleIds);
		return backToShopMain;
	}
	
	@GetMapping(value=serviceURL + "/shop_service/admin/catalog")
	public String catalogAdminView(Model model) {
		List<UUID> articleIds = shopService.getCatalog().getArticleIds();
		List<UUID> allArticleIds = new ArrayList<UUID>();
		List<ArticleFeignObject> articlesInCatalog = new ArrayList<ArticleFeignObject>();
		List<ArticleFeignObject> articlesNotInCatalog = new ArrayList<ArticleFeignObject>();
		for(ArticleFeignObject afb : shopService.getAllArticles()) {allArticleIds.add(afb.getArticleId());}
		allArticleIds.removeAll(articleIds);
		for(UUID id : articleIds) {articlesInCatalog.add(shopService.getArticleById(id));}
		for(UUID id : allArticleIds) {articlesNotInCatalog.add(shopService.getArticleById(id));}
		model.addAttribute("articles", articlesNotInCatalog);
		model.addAttribute("articlesInCatalog", articlesInCatalog);
		return "admin_Catalog";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/admin/catalog/{articleid}")
	public String admin_ArticleSingle(@PathVariable(value="articleid") UUID articleId, Model model) {
		model.addAttribute("article", shopService.getArticleById(articleId));
		return "admin_ArticleSingle";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/admin/catalog/action/")
	public String catalogActionHandler(@RequestParam("action") String action, @RequestParam(required = false)UUID articleid, Model model) {
		switch(action) {
			case "edit":
				model.addAttribute("article", shopService.getArticleById(articleid));
				return "admin_EditArticle";
			case "delete":
				model.addAttribute("article", shopService.getArticleById(articleid));
				return "admin_DeleteArticle";
			case "createarticle":
				return "admin_CreateNewArticle";
		}
		return "shop_Main";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/admin/catalog/action/delete/perform")
	public RedirectView deleteArticleFromCatalogAndDB(@RequestParam("articleid") UUID articleId) {
		shopService.removeArticleFromCatalog(shopService.getCatalog(), articleId, false);
		shopService.deleteArticle(articleId);
		return backToShopMain;
	}
	
	@GetMapping(value=serviceURL + "/shop_service/admin/catalog/action/edit/perform")
	public RedirectView editArticleAdmin(@RequestParam("articleid") UUID articleId,
										 @RequestParam("articlename") String name,
										 @RequestParam("articlemanufactor") String manufactor,
										 @RequestParam("articleprice") BigDecimal price,
										 @RequestParam("articletype") String articleType,
										 @RequestParam("articlestockqty") int stockQuantity) {
		shopService.editFeignArticle(articleId, name, manufactor, price, articleType, stockQuantity);
		return backToShopMain;
	}
	
	@GetMapping(value=serviceURL + "/shop_service/admin/catalog/action/createarticle/perform")
	public RedirectView createNewArticleAdmin(@RequestParam("articlename") String name,
										 	  @RequestParam("articlemanufactor") String manufactor,
										 	  @RequestParam("articleprice") BigDecimal price,
										 	  @RequestParam("articletype") String articleType,
										 	  @RequestParam("articlestockqty") int stockQuantity) {
		shopService.createNewFeignArticle(name, manufactor, price, articleType, stockQuantity);
		return backToShopMain;
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/catalog/")
	public String user_Catalog(@RequestParam("customerid") UUID customerId, Model model) {
		List<UUID> articleIds = shopService.getCatalog().getArticleIds();
		List<ArticleFeignObject> articlesInCatalog = new ArrayList<ArticleFeignObject>();
		for(UUID id : articleIds) {articlesInCatalog.add(shopService.getArticleById(id));}
		model.addAttribute("customerId", customerId);
		model.addAttribute("articles", articlesInCatalog);
		return "user_Catalog";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/catalog/articledetail")
	public String user_ArticleSingle(@RequestParam("customerid") UUID customerId, @RequestParam("articleid") UUID articleId, Model model) {
		model.addAttribute("article", shopService.getArticleById(articleId));
		model.addAttribute("customerid", customerId);
		model.addAttribute("articleid", articleId);
		model.addAttribute("origin", "catalog");
		return "user_ArticleSingle";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/cart/articledetail")
	public String user_CartArticleSingle(@RequestParam("customerid") UUID customerId, @RequestParam("articleid") UUID articleId, Model model) {
		model.addAttribute("article", shopService.getArticleById(articleId));
		model.addAttribute("customerid", customerId);
		model.addAttribute("articleid", articleId);
		model.addAttribute("origin", "cart");
		return "user_ArticleSingle";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/cart/")
	public String user_Cart(@RequestParam("customerid") UUID customerId, Model model) {
		boolean found = false;
		try {
			BigDecimal totalPrice = new BigDecimal(0);
			List<CartItem> cartItems = new ArrayList<CartItem>();
			List<ArticleFeignObject> articlesInCart = new ArrayList<ArticleFeignObject>();
			for (CartItem cI : shopService.getCartFromCustomerId(customerId).getCartItems()) {
				cartItems.add(cI);
				articlesInCart.add(shopService.getArticleById(cI.getArticleId()));
				totalPrice = totalPrice.add((shopService.getArticleById(cI.getArticleId()).getPrice()).multiply(BigDecimal.valueOf(cI.getQuantity())));
				found = true;
			}
			model.addAttribute("totalprice", totalPrice);
			model.addAttribute("cartItems", cartItems);
			model.addAttribute("articles", articlesInCart);
			model.addAttribute("customerid", customerId);
		} catch (Exception e) {
			model.addAttribute("customerid", customerId);
			return "user_CartEmpty";
		}
		if (found) {return "user_Cart";}
		else {return "user_CartEmpty";}
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/catalog/addtocart")
	public RedirectView addArticleToCartFromCatalog(@RequestParam("customerid") UUID customerId, @RequestParam("articleid") UUID articleId) {
		shopService.addCartItemToCartFromArticleId(customerId, articleId);
		return new RedirectView("/service_main/shop_service/user/catalog/?customerid=" + customerId);
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/cart/addtocart")
	public RedirectView addArticleToCartFromCart(@RequestParam("customerid") UUID customerId, @RequestParam("articleid") UUID articleId) {
		shopService.addCartItemToCartFromArticleId(customerId, articleId);
		return new RedirectView("/service_main/shop_service/user/cart/?customerid=" + customerId);
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/cart/removefromcart")
	public RedirectView removeArticleFromCart(@RequestParam("customerid") UUID customerId, @RequestParam("articleid") UUID articleId) {
		shopService.removeCartItemFromCart(customerId, articleId);
		return new RedirectView("/service_main/shop_service/user/cart/?customerid=" + customerId);
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/orders/")
	public String user_Orders(@RequestParam("customerid") UUID customerId, Model model) {
		try {
			model.addAttribute("orders", shopService.getOrdersByCustomerId(customerId));
			model.addAttribute("customerid", customerId);
		} catch (Exception e) {
			return "user_NoOrders";
		}
		return "user_Orders";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/orders/orderdetail")
	public String user_OrderSingle(@RequestParam("orderid") UUID orderId, @RequestParam("customerid") UUID customerId, Model model) {
		model.addAttribute("order", shopService.getOrderByOrderId(orderId));
		model.addAttribute("orderItems", shopService.getOrderItemsFromOrder(orderId));
		model.addAttribute("articles", shopService.getAllArticles());
		model.addAttribute("customerid", customerId);
		return "user_OrderSingle";
	}
	
	@GetMapping(value=serviceURL + "/shop_service/user/cart/checkout")
	public RedirectView checkOutCart(@RequestParam("customerid") UUID customerId) {
		shopService.checkOutCart(customerId);
		return new RedirectView(serviceURL + "/shop_service/user/orders/?customerid=" + customerId.toString());
	}
	
}
