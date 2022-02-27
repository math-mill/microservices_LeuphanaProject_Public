package de.leuphana.shop_service.connector;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.leuphana.shop_service.component.behaviour.exceptionhandling.CartNotFoundException;
import de.leuphana.shop_service.component.structure.Cart;
import de.leuphana.shop_service.component.structure.CartItem;
import de.leuphana.shop_service.component.structure.Catalog;
import de.leuphana.shop_service.connector.repository.CartItemRepository;
import de.leuphana.shop_service.connector.repository.CartRepository;
import de.leuphana.shop_service.connector.repository.CatalogRepository;

@Component
public class ShopJPAController {
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CatalogRepository catalogRepository;
	
	@Transactional
	public Cart loadCartFromDB(UUID cartId) {
		if(cartRepository.findById(cartId).isPresent()) {
			return cartRepository.findById(cartId).get();
		} else {
			return cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
		}
	}
	
	@Transactional
	public Cart loadCartFromDBByCustomerId(UUID customerId) {
		return cartRepository.findByCustomerId(customerId);
	}
	
	@Transactional
	public Cart saveCartToDB(Cart cart) {
		return cartRepository.save(cart);
	}
	
	@Transactional
	public void deleteCartFromDB(UUID cartId) {
		if(cartRepository.findById(cartId).isPresent()) {
			cartRepository.deleteById(cartId);
		} else {
			cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
		}
	}
	
	@Transactional
	public void saveCatalogToDB(Catalog catalog) {
		catalogRepository.save(catalog);
	}
	
	@Transactional
	public Catalog loadCatalogFromDB(UUID catalogId) {
		return catalogRepository.findById(catalogId).get();
	}
	
	@Transactional
	public List<Catalog> loadAllCatalogsFromDB(){
		return catalogRepository.findAll();
	}
	
	@Transactional
	public CartItem loadCartItemFromDB(UUID cartItemId) {
		return cartItemRepository.findById(cartItemId).get();
	}
	
	@Transactional
	public void saveCartItemToDB(CartItem cartItem) {
		cartItemRepository.save(cartItem);
	}
	
	@Transactional
	public void deleteCartItemFromDB(UUID cartItemId) {
		cartItemRepository.deleteById(cartItemId);
	}
	
	@Transactional
	public List<CartItem> loadAllCartItemsFromDB(){
		return cartItemRepository.findAll();
	}
	
	@Transactional
	public List<Cart> loadAllCartsFromDB(){
		return cartRepository.findAll();
	}

}
