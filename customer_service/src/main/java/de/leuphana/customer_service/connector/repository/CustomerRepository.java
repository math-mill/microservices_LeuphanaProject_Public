package de.leuphana.customer_service.connector.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import de.leuphana.customer_service.component.structure.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID>{

}
