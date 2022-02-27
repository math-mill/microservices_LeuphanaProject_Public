package de.leuphana.order_service.connector.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import de.leuphana.order_service.component.structure.Order;

public interface OrderRepository extends JpaRepository<Order, UUID>{
	
	@Query(value = "SELECT * FROM ordertable o WHERE o.customer_id = ?1", nativeQuery = true)
	public List<Order> findByCustomerId(UUID customerId);

}
