package de.leuphana.order_service.connector.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import de.leuphana.order_service.component.structure.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}
