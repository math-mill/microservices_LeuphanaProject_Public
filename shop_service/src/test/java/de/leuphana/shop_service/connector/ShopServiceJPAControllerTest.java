package de.leuphana.shop_service.connector;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import de.leuphana.shop_service.component.behaviour.ShopService;
import de.leuphana.shop_service.component.behaviour.exceptionhandling.CartNotFoundException;
import de.leuphana.shop_service.component.structure.Cart;
import de.leuphana.shop_service.component.structure.CartItem;
import de.leuphana.shop_service.component.structure.Catalog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {ShopService.class})
@TestPropertySource(locations="classpath:test.properties")
public class ShopServiceJPAControllerTest {

	@Autowired
	ShopJPAController sJPAc;
	
	public Cart createTestCart() {
		Cart cart = new Cart();
		cart.setCustomerId(UUID.randomUUID());
		CartItem cartItem = new CartItem();
		cartItem.setArticleId(UUID.randomUUID());
		cartItem.setCart(cart);
		cartItem.setQuantity(100);
		List<CartItem> cartItems = new ArrayList<CartItem>();
		cartItems.add(cartItem);
		cart.setCartItems(cartItems);
		return cart;
	}
	
	public Catalog createTestCatalog() {
		Catalog catalog = new Catalog();
		List<UUID> articleIds = new ArrayList<UUID>();
		for(int i = 0; i < 8; i++) {articleIds.add(UUID.randomUUID());}
		catalog.setArticleIds(articleIds);
		return catalog;
	}
	
	@Test
	public void saveCartToDBTest() {
		Cart cartToSave = this.createTestCart();
		Cart cartSaved = sJPAc.saveCartToDB(cartToSave);
		assertEquals(cartToSave, cartSaved);
	}
	
	@Test
	public void loadCartByIdTest() {
		Cart cart = this.createTestCart();
		Cart cartSaved = sJPAc.saveCartToDB(cart);
		Cart cartLoaded = sJPAc.loadCartFromDB(cart.getCartId());
		assertEquals(cartSaved.getCartId(), cartLoaded.getCartId());
	}
	
	@Test
	public void loadCartByCustomerIdTest() {
		Cart cart = this.createTestCart();
		Cart cartSaved = sJPAc.saveCartToDB(cart);
		Cart cartLoaded = sJPAc.loadCartFromDBByCustomerId(cart.getCustomerId());
		assertEquals(cartSaved.getCartId(), cartLoaded.getCartId());
	}
	
	@Test
	public void deleteCartById() {
		boolean thrown = false;
		Cart cart = this.createTestCart();
		sJPAc.saveCartToDB(cart);
		sJPAc.deleteCartFromDB(cart.getCartId());
		try {
			sJPAc.loadCartFromDB(cart.getCartId());
		} catch(CartNotFoundException e) {
			thrown = true;
		}
		assertEquals(thrown, true);
	}
}
