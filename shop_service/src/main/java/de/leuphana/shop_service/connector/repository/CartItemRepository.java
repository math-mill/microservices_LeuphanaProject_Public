package de.leuphana.shop_service.connector.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import de.leuphana.shop_service.component.structure.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, UUID>{
	
}
